package com.mall.redis.application.service;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * RedisService
 * @author youfu.wag
 * @daate 2019-02-02
 */
@Component
public class RedisService {
    /**  默认过期时长60分钟，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 ;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    private RedisTemplate redisTemplate;
    /**
     * 构造方法
     * @param redisTemplate
     */
    public RedisService(RedisTemplate redisTemplate){
        this.redisTemplate=redisTemplate;
    }
    public void set(String key, Object value, long expire){
        this.redisTemplate.opsForValue().set(key, value);
        if(expire != RedisService.NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value){
        set(key, value, RedisService.DEFAULT_EXPIRE);
    }

    public Object get(String key) {
        return get(key, RedisService.NOT_EXPIRE);
    }

    public Object get(String key, long expire) {
        Object value = this.redisTemplate.opsForValue().get(key);
        if(expire != RedisService.NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public Long getExpireTime(String key) {
        return this.redisTemplate.opsForValue().getOperations().getExpire(key);
    }
}
