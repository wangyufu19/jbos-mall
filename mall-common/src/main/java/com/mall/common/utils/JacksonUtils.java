package com.mall.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * JacksonUtils
 *
 * @author youfu.wang
 * @date 2021-07-08
 */
@Slf4j
public class JacksonUtils {
    /**
     * objectMapper
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        //Include.NON_NULL 属性为NULL 不序列化
        //Include.Include.ALWAYS 默认
        //Include.NON_DEFAULT 属性为默认值不序列化
        //Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化
        //Include.NON_NULL 属性为NULL 不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 美化输出
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 允许序列化空的POJO类
        // （否则会抛出异常）
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 把java.util.Date, Calendar输出为数字（时间戳）
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 在遇到未知属性的时候不抛出异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 强制JSON 空字符串("")转换为null对象值:
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

    }

    /**
     * Json->Object
     * @param json
     * @param t
     * @return t
     * @param <T>
     */
    public static <T> T parseObject(String json, Class<T> t) {
        if (json == null) {
            return null;
        }
        try {
            return objectMapper.readValue(json, t);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Object->Json
     * @param value
     * @return json
     */
    public static String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
