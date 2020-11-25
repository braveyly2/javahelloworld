package com.hust.dao;

import com.hust.entity.User;
import junit.framework.TestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserMapperTest extends TestCase {
    private ApplicationContext applicationContext;

    @Autowired
    UserMapper userMapper;

    public void setUp() throws Exception {
        // 加载spring配置文件
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        // 导入需要测试的
        userMapper = applicationContext.getBean(UserMapper.class);
    }

    public void tearDown() throws Exception {
    }

    public void testInsert() {
        User user = new User();
        user.setId(1);
        user.setName("liwei");
        user.setPassword("123");
        user.setMark("thisisme");
        userMapper.insert(user);
    }
}