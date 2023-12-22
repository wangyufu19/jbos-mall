package com.mall.common.utils;

import com.alibaba.fastjson.JSON;


/**
 * JsonUtils
 *
 * @author youfu.wang
 * @date 2019-02-02
 */
public class JsonUtils {
    /**
     * 对象-json
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * json-对象
     * @param json
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}
