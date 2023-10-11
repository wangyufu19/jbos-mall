package com.mall.gateway.common.websecurity.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * JwtProperties
 *
 * @author youfu.wang
 * @date 2021-01-01
 */
@ConfigurationProperties("spring.jwt")
public class JwtProperties {
    /**
     * 默认过期时间60分钟
     */
    public static final long JWT_EXPIRATION = 60 * 60 * 1000L;
    /**
     * 默认刷新时间30分钟
     */
    public static final long JWT_REFRESH_TIME = 30 * 60 * 1000L;

    /**
     * 密钥
     */
    private String secret = "123456";
    /**
     * 过期时间,默认60分钟
     */
    private long expireTime = JWT_EXPIRATION;
    /**
     * 刷新时间,默认30分钟
     */
    private long freshTime = JWT_REFRESH_TIME;

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getSecret() {
        return this.secret;
    }

    public long getExpireTime() {
        return this.expireTime;
    }

    public long getFreshTime() {
        return freshTime;
    }

    public void setFreshTime(long freshTime) {
        this.freshTime = freshTime;
    }
}
