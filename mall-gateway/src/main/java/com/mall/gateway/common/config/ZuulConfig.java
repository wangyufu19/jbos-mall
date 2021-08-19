package com.mall.gateway.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * ZuulConfig
 * @author youfu.wang
 * @date 2021-05-05
 */
@Configuration
public class ZuulConfig {
    @Bean
    @RefreshScope
    @ConfigurationProperties("zuul")
    @Primary
    public ZuulProperties zuulProperties(){
        ZuulProperties zuulProperties=new ZuulProperties();
        return zuulProperties;
    }
}
