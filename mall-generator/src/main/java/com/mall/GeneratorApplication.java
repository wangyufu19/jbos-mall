package com.mall;

import com.mall.application.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
/**
 * GeneratorApplication
 * @author youfu.wang
 * @date 2021-08-19
 */
@SpringBootApplication
public class GeneratorApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GeneratorApplication.class);
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext=SpringApplication.run(GeneratorApplication.class, args);
        System.out.println("UserController="+applicationContext.getBean(UserController.class));
    }
}
