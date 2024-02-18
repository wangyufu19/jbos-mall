package com.mall.admin.common.config;


import com.auth0.jwt.JWT;

import com.mall.admin.common.jwt.JwtProperties;
import com.mall.admin.common.jwt.JwtTokenProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JwtConfig
 *
 * @author youfu.wang
 * @date 2021-01-01
 */
@Configuration
@ConditionalOnClass({JWT.class})
@EnableConfigurationProperties({JwtProperties.class})
public class JwtConfig {
    /**
     * jwtTokenProvider
     * @param jwtProperties
     * @return JwtTokenProvider
     */
    @Bean
    @ConditionalOnMissingBean
    public JwtTokenProvider jwtTokenProvider(JwtProperties jwtProperties) {
        JwtTokenProvider jwtTokenProvide = new JwtTokenProvider(jwtProperties);
        return jwtTokenProvide;
    }
}
