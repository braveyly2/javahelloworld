package hust.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisCluster;

public class RedisTest {
    @Test
    public void redisTest() {
        // 获得spring上下文，
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-redis.xml");
        JedisCluster jc = ctx.getBean(JedisCluster.class);
        System.out.println(jc.get("Hello"));
    }
}