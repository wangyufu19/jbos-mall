package com.mall.admin.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;


/**
 * JwtTokenProvider
 * @author youfu.wang
 * @date 2021-01-01
 */
public class JwtTokenProvider {

    /**
     * 获取荷载信息：从 token 的荷载部分里解析加密数据。
     * @param token
     * @return
     */
    public static String getSignDataFromJWT(String token,String key) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(key).asString();
        } catch (JWTDecodeException jwtDecodeException) {
            return null;
        }
    }
}
