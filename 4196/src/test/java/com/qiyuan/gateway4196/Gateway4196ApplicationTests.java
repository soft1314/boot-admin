package com.qiyuan.gateway4196;


import com.qiyuan.bautil.service.component.RedisCacheUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class Gateway4196ApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("Test");
    }
    @Resource
    private RedisTemplate redisTemplate;
    @Test
    public void testRedis() {
        System.out.println("Test Redis");
        redisTemplate.opsForValue().set("a","avalue");
        redisTemplate.opsForValue().set("b","bvalue",30, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("c","cvalue");
        redisTemplate.expire("c",40,TimeUnit.SECONDS);
        redisTemplate.expire("a",40,TimeUnit.SECONDS);

    }
    @Test
    public void testRedis1() {
        System.out.println("Test Redis");
        redisTemplate.expire("c",40,TimeUnit.SECONDS);
        redisTemplate.expire("a",40,TimeUnit.SECONDS);
    }
    @Test
    public void testRedis3() {
        System.out.println("Test Redis");
        redisTemplate.opsForHash().put("hasha","a","avalue");
        redisTemplate.opsForHash().put("hashb","b","bvalue");
        redisTemplate.opsForHash().put("hashc","c","cvalue");
        redisTemplate.expire("hasha",40,TimeUnit.SECONDS);
        redisTemplate.expire("hashc",60,TimeUnit.SECONDS);
//        ScanOptions scanOptions
    }
    @Resource
    private RedisCacheUtils redisCacheUtils;
    @Test
    public void testRedis4() {
        redisCacheUtils.setCacheObject("aaaaaa","11111111111111",1000,TimeUnit.MINUTES);
        redisCacheUtils.setCacheObject("aaaaaa","22222222222",1000,TimeUnit.MINUTES);
        String vale= redisCacheUtils.getCacheObject("aaaaaa");
        String vale1= redisCacheUtils.getCacheObject("cccc");
        Long l = redisCacheUtils.getExpire("aaaaaa");
        System.out.println(l);
    }
}
