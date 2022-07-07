package com.mall.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

/**
 * GatewayApplication
 * @author youfu.wang
 * @date 2021-08-19
 */
@SpringBootApplication
@EnableDiscoveryClient

public class SpringCloudGatewayApplication  {

    public static void main(String[] args) {
        SpringApplication springApplication=new SpringApplication(SpringCloudGatewayApplication.class);
        ApplicationContext applicationContext=springApplication.run(args);
    }
}
