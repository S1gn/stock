package com.s1gn.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @ClassName TestRedis
 * @Description 测试redis
 * @Author S1gn
 * @Date 15:12
 * @Version 1.0
 */
@SpringBootTest
public class TestRedis {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("name", "s1gn");
        String name = redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }
}
