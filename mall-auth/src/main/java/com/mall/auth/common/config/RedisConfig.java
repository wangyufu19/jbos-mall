package com.mall.auth.common.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisConfig
 * @author youfu.wang
 * @date 2019-10-28
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
    /**  默认过期时长60分钟，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 ;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;
    private long expire=DEFAULT_EXPIRE;

	@Autowired
    private RedisConnectionFactory redisConnectionFactory;
	@Autowired
	private LettuceConnectionFactory factory;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
