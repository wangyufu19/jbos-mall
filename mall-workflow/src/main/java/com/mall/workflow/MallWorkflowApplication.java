package com.mall.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * MallWorkflowApplication
 * @author youfu.wang
 * @date 2021-08-19
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MallWorkflowApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MallWorkflowApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MallWorkflowApplication.class, args);
    }
}
