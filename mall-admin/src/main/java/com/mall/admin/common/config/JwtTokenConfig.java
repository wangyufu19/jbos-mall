package com.mall.admin.common.config;

import com.mall.common.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JwtTokenConfig
 * @author youfu.wang
 * @date 2021-05-21
 */
@Configuration
public class JwtTokenConfig {
    @Bean
    public JwtTokenProvider jwtTokenProvider(){
        JwtTokenProvider jwtTokenProvider=new JwtTokenProvider();
        return jwtTokenProvider;
    }
}
