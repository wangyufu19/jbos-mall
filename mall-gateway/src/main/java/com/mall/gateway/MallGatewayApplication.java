package com.mall.gateway;

import com.mall.gateway.common.spring.ApplicationContextListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
/**
 * GatewayApplication
 *
 * @author youfu.wang
 * @date 2021-08-19
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
//@EnableApolloConfig

public class MallGatewayApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MallGatewayApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MallGatewayApplication.class);
        springApplication.addListeners(new ApplicationContextListener());
        ApplicationContext applicationContext = springApplication.run(args);
    }

}
