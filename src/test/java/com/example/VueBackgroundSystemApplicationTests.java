package com.example;

import com.example.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.support.RedisRepositoryFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.net.UnknownHostException;

@SpringBootTest
class VueBackgroundSystemApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    // JSON 工具
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void contextLoads() {
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        // 创建 Template
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置序列化工具
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        // key 和 hashKey 采用 String 序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // value 和 hashValue 采用 JSON 序列化
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        return redisTemplate;
    }

    @Test
    void testString() {
        // 插入一条 String 数据
        redisTemplate.opsForValue().set("name", "李四");
        // 读取一条 String 类型数据
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println("name = " + name);
    }

    @Test
    void testStringTemplate() throws JsonProcessingException {
        // 准备对象
        User user = new User();
        user.setUsername("虎哥");
        user.setNickname("18");
        // 手动序列化
        String json = mapper.writeValueAsString(user);
        // 写入一条数据到redis
        stringRedisTemplate.opsForValue().set("user:200", json);
        // 读取数据
        String val = stringRedisTemplate.opsForValue().get("user:200");
        // 反序列化
        User user1 = mapper.readValue(val, User.class);
        System.out.println("user1 = " + user1);
    }

}
