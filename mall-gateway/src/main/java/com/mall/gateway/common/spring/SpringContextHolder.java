package com.mall.gateway.common.spring;

import org.springframework.context.ApplicationContext;

/**
 * SpringContextHolder
 * @author youfu.wang
 * @date 2021-05-05
 */
public class SpringContextHolder {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext){
        SpringContextHolder.applicationContext=applicationContext;
    }
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}
