package com.mall.gateway.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;


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

}
