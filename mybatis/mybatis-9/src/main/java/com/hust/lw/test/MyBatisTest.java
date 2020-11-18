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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        in = Resources.getResourceAsStream("MybatisConfig.xml");
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
        for(User user:userList){
            System.out.println(user.getId()+"\t"+user.getEmail()+"\t"+user.getMark()+"\t"+user.getPhone());
        }
    }

    //添加用户
    @Test
    public void testinsertproc() throws IOException {
        User user = new User();
        user.setPhone("159");
        user.setEmail("sjliwei@126.com");
        int n = userMapper.insertByProc(user);
        if(n<1){
            System.out.println("Failed to insert");
        }
    }

    //添加用户
    @Test
    public void testdeleteproc() throws IOException {
        User user = new User();
        user.setId(1L);
        int n = userMapper.deleteByProc(user);
        if(n<1){
            System.out.println("Failed to insert");
        }
    }

    //添加用户
    @Test
    public void testdeleteprocmap() throws IOException {
        Map<String,Object> map = new HashMap<>();
        map.put("x_id",3);
        map.put("x_phone","");
        map.put("x_email","");

        int n = userMapper.deleteByProcMap((HashMap) map);
        if(n<1){
            System.out.println("Failed to insert");
        }
    }

    @Test
    public void testgetallbyproc() throws IOException {
            List<User> userList = userMapper.selectAllByProc();
        System.out.println(userList.size());
    }

    @Test
    public void testgetallbyprocmany() throws IOException {
        List<List<User>> userListList = userMapper.selectAllByProcMany();
        System.out.println(userListList.size());
    }

}