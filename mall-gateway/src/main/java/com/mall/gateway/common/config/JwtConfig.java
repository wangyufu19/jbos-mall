package com.mall.gateway.common.config;


import com.auth0.jwt.JWT;
import com.mall.gateway.common.websecurity.jwt.JwtProperties;
import com.mall.gateway.common.websecurity.jwt.JwtTokenProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public JwtTokenProvider jwtTokenProvider(JwtProperties jwtProperties) {
        JwtTokenProvider jwtTokenProvide = new JwtTokenProvider(jwtProperties);
        return jwtTokenProvide;
    }
}
