package com.mall.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * MallRedisApplication
 * @author youfu.wang
 * @date 2021-08-19
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class MallDataApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MallDataApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MallDataApplication.class, args);
    }
    static {
        System.setProperty("es.set.netty.runtime.available.processors","false");
    }
}
