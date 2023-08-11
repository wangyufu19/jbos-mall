package com.mall.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * InjectionMetadata
 *
 * @author youfu.wang
 * @date 2023/8/11
 **/
public class InjectionMetadata {
    public static void inject(Object obj, Field field, Object value){
        int mod = field.getModifiers();
        if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
            return;
        }
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
