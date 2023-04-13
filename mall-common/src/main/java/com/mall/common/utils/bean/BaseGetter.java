package com.mall.common.utils.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * BaseGetter
 * @author youfu.wang
 * @date 2023/4/13
 **/
public class BaseGetter implements Getter{
    private Class cls;
    private String propertyName;
    private String methodName;

    public BaseGetter(Class cls,String propertyName){
        this.cls=cls;
        this.propertyName=propertyName;
    }
    /**
     * 返回对象值
     * @param obj
     * @return
     */
    public Object get(Object obj){
        Object propertyValue="";
        Method method=this.getMethod();
        try {

            propertyValue=method.invoke(obj);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return propertyValue;
    }
    private Method getMethod(){
        Method method=null;
        String methodName="";
        try {
            methodName=this.getMethodName();
            method = cls.getMethod(methodName);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }
    private String getMethodName(){
        StringBuffer buf=new StringBuffer();
        for(int i=0;i<propertyName.length();i++){
            if(i==0){
                buf.append(Character.toUpperCase(propertyName.charAt(i)));
            }else
                buf.append(propertyName.charAt(i));
        }
        methodName="get"+buf.toString();
        return this.methodName;
    }
    public Class getReturnType(){
        Method method=this.getMethod();
        return method.getReturnType();
    }
}