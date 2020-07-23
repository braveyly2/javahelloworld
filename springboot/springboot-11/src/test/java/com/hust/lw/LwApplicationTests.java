package com.hust.lw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

@SpringBootTest
class LwApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void test() throws Exception {

        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "111");
        //Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));

    }

}
