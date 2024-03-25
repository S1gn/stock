package com.s1gn.stock.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @ClassName RedisCacheConfig
 * @Description redis序列化方式，避免默认jdk乱码，体积大
 * @Author S1gn
 * @Date 15:05
 * @Version 1.0
 */
@Configuration
public class RedisCacheConfig {
    /**
     * @Auther s1gn
     * @Description 自定义模板对象，保证bean的名称是redisTemplate，保证注入时只注入自定义的这个
     * @Date 2024/3/21 15:11
     * @Param * @param redisConnectionFactory
     * @Return * @return {@link RedisTemplate }
     **/
    @Bean
    public RedisTemplate redisTemplate(@Autowired RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置key序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置value序列化方式
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        //设置hash key序列化方式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //设置hash value序列化方式
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        //初始化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
