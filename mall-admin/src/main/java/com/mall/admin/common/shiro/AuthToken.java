package com.mall.admin.common.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * AuthToken
 *
 * @author youfu.wang
 * @date 2024/2/6 11:23
 */
public class AuthToken implements AuthenticationToken {
    /**
     * token
     */
    private String token;

    /**
     * AuthToken
     * @param token
     */
    public AuthToken(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
