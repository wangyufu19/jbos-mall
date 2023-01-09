package com.mall.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * AdminApplication
 * @author youfu.wang
 * @date 2021-08-19
 */
@SpringBootApplication

public class MallMQApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MallMQApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MallMQApplication.class, args);
    }
}
