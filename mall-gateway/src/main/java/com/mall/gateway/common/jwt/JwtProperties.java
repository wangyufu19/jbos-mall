package com.mall.gateway.common.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JwtProperties
 *
 * @author youfu.wang
 * @date 2021-01-01
 */
@ConfigurationProperties("spring.jwt")
public class JwtProperties {
    /**
     * 密钥
     */
    private String secret = "123456";
    /**
     * 过期时间,默认60分钟
     */
    private long expireTime = 60;

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
}
