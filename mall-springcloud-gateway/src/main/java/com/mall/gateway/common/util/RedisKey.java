package com.mall.gateway.common.util;

/**
 * RedisKey
 * @author youfu.wang
 * @date 2019-02-02
 */
public class RedisKey {
    /**
     * getConfigKey
     * @param key
     * @return key
     */
    public static String getConfigKey(String key){
        return "config:" + key;
    }

    /**
     * getDataKey
     * @param key
     * @return key
     */
    public static String getDataKey(String key){
        return "data:" + key;
    }
}
