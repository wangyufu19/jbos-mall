package com.mall.gateway.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * ReactWebConfig
 *
 * @author youfu.wang
 * @date 2023/11/3 13:42
 */
@Configuration
public class ReactWebConfig implements WebFluxConfigurer {
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOriginPatterns("*")
        .allowCredentials(true)
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .maxAge(3600);
    }

}
