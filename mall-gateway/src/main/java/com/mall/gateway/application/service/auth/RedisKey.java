package com.mall.gateway.application.service.auth;

/**
 * RedisKey
 * @author youfu.wang
 * @date 2019-02-02
 */
public class RedisKey {
    public static String getConfigKey(String key){
        return "config:" + key;
    }
    public static String getDataKey(String key){
        return "data:" + key;
    }
}
