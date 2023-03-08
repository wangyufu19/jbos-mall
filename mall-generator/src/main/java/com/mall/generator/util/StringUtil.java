package com.mall.generator.util;

public class StringUtil {

    public static String getUpperCamelCase(String arg1){
        StringBuffer buf=new StringBuffer();
        String s1=arg1;
        for(int i=0;i<s1.length();i++){
            if(i==0){
                buf.append(String.valueOf(s1.charAt(i)).toUpperCase());
            }else{
                buf.append(s1.charAt(i));
            }
        }
        return buf.toString();
    }
}
