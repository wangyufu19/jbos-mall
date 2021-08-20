package com.mall.common.utils;

import com.alibaba.fastjson.JSON;

/**
 * JsonUtils
 * @author youfu.wang
 * @date 2019-02-02
 */
public class JsonUtils {
    public static String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    public static <T> T fromJson(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }
}
