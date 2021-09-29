package com.mall.auth.common.config;

import com.mall.common.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JwtTokenConfig
 * @author youfu.wang
 * @date 2021-05-21
 */
@Configuration
public class JwtTokenConfig {
    @Value("${spring.jwt.secret}")
    private String secret;
    @Value("${spring.jwt.expireTime}")
    private long expireTime;
    @Bean
    public JwtTokenProvider jwtTokenProvider(){
        JwtTokenProvider jwtTokenProvider=new JwtTokenProvider();
        jwtTokenProvider.setSecret(secret);
        jwtTokenProvider.setExpireTime(expireTime*60*60*1000L);
        return jwtTokenProvider;
    }
}
