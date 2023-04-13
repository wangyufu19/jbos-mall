package com.mall.common.utils.bean;

/**
 * BeanFactory
 * @author youfu.wang
 * @date 2023/4/13
 **/
public class BeanFactory {
    public static Getter getGetter(Class cls,String propertyName){
        Getter getter=new BaseGetter(cls,propertyName);
        return getter;
    }
}
