package com.mall.data.common;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;


/**
 * RedisRock
 * @author youfu.wag
 * @daate 2019-02-02
 */
public class RedisLock {
    //互斥锁KEY
    public static final String MUTEX_LOCK="redis_lock";
    //过期时间，30秒
    public final static long MUTEX_LOCK_EXPIRE = 30;

    private RedisTemplate redisTemplate;
    /**
     * 构造方法
     * @param redisTemplate
     */
    public RedisLock(RedisTemplate redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    public boolean getLock(String key, Object value,long seconds){
        return this.redisTemplate.opsForValue().setIfAbsent(RedisLock.MUTEX_LOCK+"_"+key,value,seconds, TimeUnit.SECONDS);
    }
    public void unLock(String key){
        redisTemplate.delete(RedisLock.MUTEX_LOCK+"_"+key);
    }
}
