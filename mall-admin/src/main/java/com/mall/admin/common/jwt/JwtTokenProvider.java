package com.mall.admin.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * JwtTokenProvider
 *
 * @author youfu.wang
 * @date 2021-01-01
 */
@Slf4j
@Data
public class JwtTokenProvider {
    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * token名称
     */
    public static final String TOKEN = "accessToken";
    /**
     * jwtProperties
     */
    private JwtProperties jwtProperties;

    /**
     * JwtTokenProvider
     *
     * @param jwtProperties
     */
    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private Map<String, Object> getHeader() {
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        return header;
    }


    /**
     * 验证 JWT：指定和签发相同的加密方式，验证这个 token 是否是本服务器签发，是否篡改或者已过期。
     *
     * @param token
     * @return bool/false
     */
    public boolean verifyToken(String token) {
        if (ObjectUtils.isEmpty(this.jwtProperties) || StringUtils.isEmpty(this.jwtProperties.getSecret())) {
            return false;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.jwtProperties.getSecret());
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException jwtVerificationException) {
            return false;
        }
    }

    /**
     * 获取荷载信息：从 token 的荷载部分里解析加密数据。
     *
     * @param token
     * @param key
     * @return claim value
     */
    public String getSignDataFromJWT(String token, String key) {
        Assert.notNull(token, "Token must not be null");
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(key).asString();
        } catch (JWTDecodeException jwtDecodeException) {
            return null;
        }
    }

    /**
     * 获取荷载信息：从 token 的荷载部分里解析加密数据。
     *
     * @param token
     * @param key
     * @return GrantedAuthority
     */
    public List<String> getGrantedAuthorityFromJWT(String token, String key) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            List<String> authorities = jwt.getClaim(key).asList(String.class);
            return authorities;
        } catch (JWTDecodeException jwtDecodeException) {
            return null;
        }
    }
}
