package com.hust.lw.test;

import com.hust.lw.dao.UserDao;
import com.hust.lw.model.User;
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
    private UserDao userdao;
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
        userdao = session.getMapper(UserDao.class);
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
        User user = userdao.findUserById(1);
        System.out.println(user);
    }

    //根据用户名模糊查询用户列表
    @Test
    public void testFindUserByUserName() {
        List<User> list = userdao.findUserByUsername("王");
        for (User user : list) {
            System.out.println(user);
        }
    }

    //添加用户
    @Test
    public void testInsertUser() throws IOException {
        User user = new User();
        user.setUsername("xiaoxiao");
        user.setBirthday(new Date());
        user.setAddress("sadfsafsafs");
        user.setSex("2");
        int i = userdao.insertUser(user);
        System.out.println("插入id:" + user.getId());//插入id:35
    }

    //更新用户
    @Test
    public void testUpdateUserById() throws IOException {
        User user = new User();
        user.setId(35);
        user.setUsername("一一");
        user.setBirthday(new Date());
        user.setAddress("西安市");
        user.setSex("1");
        userdao.updateUserById(user);

        System.out.println(user.getId());
    }

    //删除用户
    @Test
    public void testDeleteUserById() throws IOException {
        userdao.deleteUserById(29);
    }
}