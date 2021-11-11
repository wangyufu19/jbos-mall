package com.mall.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * EurekaServerApplication
 * @author youfu.wang
 * @create-date 2021-08-19
 */
@SpringBootApplication
@EnableEurekaServer
public class MallEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallEurekaServerApplication.class, args);
    }
}
