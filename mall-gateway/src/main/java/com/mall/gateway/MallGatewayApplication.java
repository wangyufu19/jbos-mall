package com.mall.gateway;

import com.mall.gateway.common.spring.ApplicationContextListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;

/**
 * GatewayApplication
 * @author youfu.wang
 * @date 2021-08-19
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class MallGatewayApplication extends SpringBootServletInitializer {

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
