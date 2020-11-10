package com.hust.lw.test;

import com.hust.lw.dao.UserMapper;
import com.hust.lw.model.User;
import com.hust.lw.model.UserExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: MybatisTest
 * @author: benjamin
 * @version: 1.0
 * @description: TODO
 * @createTime: 2019/07/13/11:50
 */

public class MyBatisTest {
    private SqlSessionFactory factory;
    private UserMapper userMapper;
    private InputStream in;
    private SqlSession session;

    // 作用：在测试方法前执行这个方法
    @Before
    public void setUp() throws Exception {
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //3.创建SqlSession工厂对象
        SqlSessionFactory factory = builder.build(in);
        //4.使用工厂生产SqlSession对象
        session = factory.openSession();
        //5.创建Dao接口的代理对象
        userMapper = session.getMapper(UserMapper.class);
    }

    @After//在测试方法执行完成之后执行
    public void destroy() throws IOException {
        session.commit();
        session.close();
        in.close();
    }

    //通过Id查询一个用户
    @Test
    public void testFindUserById() {
        //5.使用代理对象执行方法
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(1L);
        List<User> userList = userMapper.selectByExample(userExample);
        System.out.println(userList);
    }

    //根据用户名模糊查询用户列表
    @Test
    public void testFindUserByUserName() {

    }

    //添加用户
    @Test
    public void testInsertUser() throws IOException {

    }

    //更新用户
    @Test
    public void testUpdateUserById() throws IOException {

    }

    //删除用户
    @Test
    public void testDeleteUserById() throws IOException {
        //userdao.deleteUserById(29);
    }
}