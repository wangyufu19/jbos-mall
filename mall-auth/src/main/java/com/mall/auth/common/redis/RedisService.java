package com.mall.auth.common.redis;
import java.util.concurrent.TimeUnit;

import com.mall.auth.common.config.RedisConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * RedisService
 * @author youfu.wag
 * @daate 2019-02-02
 */
@Component
public class RedisService {
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
        if(expire != RedisConfig.NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value){
        set(key, value, RedisConfig.DEFAULT_EXPIRE);
    }

    public Object get(String key) {
        return get(key, RedisConfig.NOT_EXPIRE);
    }

    public Object get(String key, long expire) {
        Object value = this.redisTemplate.opsForValue().get(key);
        if(expire != RedisConfig.NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
