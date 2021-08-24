package com.mall.auth.common.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;


/**
 * JwtTokenProvider
 * @author youfu.wang
 * @date 2021-01-01
 */
@Component
@Slf4j
public class JwtTokenProvider {

    private static final long JWT_EXPIRATION =  60*60 * 1000L; // 默认过期时间一小时

    public static final String TOKEN_PREFIX = "Bearer "; // token 的开头字符串

    private String secret = "123456";//密钥

    private long expireTime;//过期时间

    public void setSecret(String secret){
        this.secret=secret;
    }
    public void setExpireTime(long expireTime){
        this.expireTime=expireTime;
    }
    /**
     * 生成 JWT：从以通过验证的认证对象中获取用户信息，然后用指定加密方式生成 token
     * token是有三个部分分别用"."隔开
     * 第一部分是签名算法
     * 第二部分是加密数据，如下就是用userId
     * 第三部分是密钥，如下就是"123456"，解密第二部分数据时需要用到
     * @param signData
     * @return
     */
    public String generateToken(Map<String,String> signData) {
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
            if(signData!=null){
                //加密数据
                for(Map.Entry<String,String> entry:signData.entrySet()){
                    builder.withClaim(entry.getKey(),entry.getValue());
                }
            }
            token=builder.withIssuedAt(iatDate) // sign time
                    .withExpiresAt(expireDate) // expire time
                    .sign(Algorithm.HMAC256(secret)); // signature
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
            verifier.verify(token);
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
}
