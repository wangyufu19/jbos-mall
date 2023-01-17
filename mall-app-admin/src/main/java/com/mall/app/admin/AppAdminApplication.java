package com.mall.app.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * AppAdminApplication
 * @author youfu.wang
 * @date 2021-08-19
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class AppAdminApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AppAdminApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AppAdminApplication.class, args);
    }
}
