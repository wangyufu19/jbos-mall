package com.mall.gateway;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.mall.gateway.common.spring.ApplicationContextListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * GatewayApplication
 * @author youfu.wang
 * @date 2021-08-19
 */
@SpringBootApplication(exclude={DruidDataSourceAutoConfigure.class, DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
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
