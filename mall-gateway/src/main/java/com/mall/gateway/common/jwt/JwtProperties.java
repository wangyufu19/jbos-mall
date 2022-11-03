package com.mall.gateway.common.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JwtProperties
 * @author youfu.wang
 * @date 2021-01-01
 */
@ConfigurationProperties("spring.jwt")
public class JwtProperties {
    private String secret = "123456";//密钥

    private long expireTime=12;//过期时间

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getSecret(){
        return this.secret;
    }
    public long getExpireTime(){
        return this.expireTime;
    }
}
