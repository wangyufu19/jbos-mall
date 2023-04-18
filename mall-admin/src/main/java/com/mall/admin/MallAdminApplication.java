package com.mall.admin;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * AdminApplication
 * @author youfu.wang
 * @date 2021-08-19
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@EnableApolloConfig
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