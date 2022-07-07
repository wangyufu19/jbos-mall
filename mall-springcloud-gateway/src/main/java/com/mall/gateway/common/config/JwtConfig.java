package com.mall.gateway.common.config;

import com.mall.gateway.common.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JwtConfig
 * @author youfu.wang
 * @date 2021-01-01
 */
@Configuration
public class JwtConfig {
    @Bean
    public JwtTokenProvider jwtTokenProvider(){
        JwtTokenProvider jwtTokenProvide=new JwtTokenProvider();
        return jwtTokenProvide;
    }
}
