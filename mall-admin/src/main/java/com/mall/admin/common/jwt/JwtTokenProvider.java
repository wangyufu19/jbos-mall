package com.mall.admin.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mall.admin.common.user.JwtUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;


/**
 * JwtTokenProvider
 * @author youfu.wang
 * @date 2021-01-01
 */
public class JwtTokenProvider {

    private static final long JWT_EXPIRATION =  60*60 * 1000L; // 默认过期时间一小时

    public static final String TOKEN_PREFIX = "Bearer "; // token 的开头字符串

    private String secret = "123456";//密钥

    private long expireTime=JWT_EXPIRATION;//过期时间

    public void setSecret(String secret){
        this.secret=secret;
    }
    public void setExpireTime(long expireTime){
        this.expireTime=expireTime;
    }
    /**
     * 生成 JWT,token是有三个部分分别用"."隔开
     * 第一部分是头部信息
     *   {
     *         "type" :  "JWT",
     *         "ALG" :  "HS256"
     *   }
     * 第二部分是荷载信息（建议但不强制使用）
     * iss：JWT 签发者
     * sub：JWT 所面向的用户
     * aud：接收 JWT 的一方
     * exp：JWT 的过期时间，这个过期时间必须要大于签发时间
     * nbf：定义在什么时间之前，该 JWT 都是不可用的
     * iat：JWT 的签发时间
     * jti：JWT 的唯一身份标识，主要用来作为一次性 token, 从而回避重放攻击。
     * 第三部分是签名
     * @param signData
     * @return
     */
    public String generateToken(Map<String,String> signData, Collection<? extends GrantedAuthority> authorities) {
        String token="";
        Date iatDate = new Date();
        Date expireDate;
        if(this.expireTime>0){
            expireDate = new Date(System.currentTimeMillis() + this.expireTime); // 设置过期时间
        }else{
            expireDate = new Date(System.currentTimeMillis() + JWT_EXPIRATION); // 设置过期时间
        }
        try {
            JWTCreator.Builder builder=JWT.create();
            //头部信息
            Map<String,Object> header=new HashMap<String,Object>();
            header.put("alg","HS256");
            header.put("typ","JWT");
            builder.withHeader(header);
            //负载信息, 加密数据
            if(signData!=null){
                for(Map.Entry<String,String> entry:signData.entrySet()){
                    builder.withClaim(entry.getKey(),entry.getValue());
                }
            }
            if(authorities!=null){
                String[] grantedAuthorities=new String[authorities.size()];
                authorities.toArray(grantedAuthorities);
                builder.withArrayClaim(JwtUser.AUTHORITIES,grantedAuthorities);
            }
            builder.withIssuedAt(iatDate).withExpiresAt(expireDate);
            //签名
            token=builder.sign(Algorithm.HMAC256(secret));
            return  token;
        } catch (JWTCreationException jwtCreationException) {
            return null;
        }
    }

    /**
     * 验证 JWT：指定和签发相同的加密方式，验证这个 token 是否是本服务器签发，是否篡改或者已过期。
     * @param token
     * @return
     */
    public boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt=verifier.verify(token);
            return true;
        } catch (JWTVerificationException jwtVerificationException) {
            return false;
        }
    }

    /**
     * 获取荷载信息：从 token 的荷载部分里解析加密数据。
     * @param token
     * @return
     */
    public String getSignDataFromJWT(String token,String key) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(key).asString();
        } catch (JWTDecodeException jwtDecodeException) {
            return null;
        }
    }
    /**
     * 获取荷载信息：从 token 的荷载部分里解析加密数据。
     * @param token
     * @return
     */
    public List<GrantedAuthority> getGrantedAuthorityFromJWT(String token,String key) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            List<String> list=jwt.getClaim(key).asList(String.class);
            List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
            if(list!=null){
                for(String item:list){
                    authorities.add(new SimpleGrantedAuthority(item));
                }
            }
            return authorities;
        } catch (JWTDecodeException jwtDecodeException) {
            return null;
        }
    }
}
