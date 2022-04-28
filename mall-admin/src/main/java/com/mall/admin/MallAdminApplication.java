package com.mall.admin;

import com.mall.admin.application.service.UserMgrService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * AdminApplication
 * @author youfu.wang
 * @date 2021-08-19
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MallAdminApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MallAdminApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MallAdminApplication.class, args);
    }
}

//public class MallAdminApplication{
//    public static void main(String[] args) throws IOException {
//        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext();
//        applicationContext.register(AppConfig.class);
//        applicationContext.refresh();
//        System.out.println("appConfig="+applicationContext.getBean(UserMgrService.class));
//        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
//        Enumeration<URL> urls = classLoader != null ? classLoader.getResources("META-INF/spring.factories") : ClassLoader.getSystemResources("META-INF/spring.factories");
//        while(urls.hasMoreElements()) {
//            URL url = (URL) urls.nextElement();
//            UrlResource resource = new UrlResource(url);
//            System.out.println("******resource "+resource);
//        }
//
//    }
//}