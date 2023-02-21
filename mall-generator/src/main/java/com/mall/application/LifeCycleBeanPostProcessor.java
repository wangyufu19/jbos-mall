package com.mall.application;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LifeCycleBeanPostProcessor implements BeanPostProcessor {
    @Nullable
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof LifeCycle){
            System.out.println("===================postProcessBeforeInitialization="+bean );
        }

        return bean;
    }

    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof LifeCycle) {
            System.out.println("===================postProcessAfterInitialization=" + bean);
        }
        return bean;
    }
}
