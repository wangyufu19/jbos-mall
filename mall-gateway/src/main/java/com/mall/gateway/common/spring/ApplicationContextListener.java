package com.mall.gateway.common.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * ApplicationContextListener
 * @author youfu.wang
 * @date 2019-10-28
 */
public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent>{
    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.applicationContext=event.getApplicationContext();
        SpringContextHolder.setApplicationContext(this.applicationContext);
    }
}
