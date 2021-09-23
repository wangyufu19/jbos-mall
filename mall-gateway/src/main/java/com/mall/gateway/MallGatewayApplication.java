package com.mall.gateway;

import com.mall.gateway.common.spring.ApplicationContextListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
/**
 * GatewayApplication
 * @author youfu.wang
 * @date 2021-08-19
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class MallGatewayApplication extends SpringBootServletInitializer {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MallGatewayApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication springApplication=new SpringApplication(MallGatewayApplication.class);
        springApplication.addListeners(new ApplicationContextListener());
        ApplicationContext applicationContext=springApplication.run(args);
    }
}
