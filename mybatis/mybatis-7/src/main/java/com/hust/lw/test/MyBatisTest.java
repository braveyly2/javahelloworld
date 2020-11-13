package com.hust.lw.test;

import com.hust.lw.dao.*;
import com.hust.lw.model.*;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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
    private TWifeMapper tWifeMapper;
    private THusbandMapper tHusbandMapper;
    private TUserMapper tUserMapper;
    private TTeacherMapper tTeacherMapper;
    private TStudentMapper tStudentMapper;
    private TOrderMapper tOrderMapper;
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
        tWifeMapper = session.getMapper(TWifeMapper.class);
        tHusbandMapper = session.getMapper(THusbandMapper.class);
        tUserMapper = session.getMapper(TUserMapper.class);
        tOrderMapper = session.getMapper(TOrderMapper.class);
        tStudentMapper = session.getMapper(TStudentMapper.class);
        tTeacherMapper = session.getMapper(TTeacherMapper.class);
    }

    @After//在测试方法执行完成之后执行
    public void destroy() throws IOException {
        session.commit();
        session.close();
        in.close();
    }

    //测试一对一关联
    @Test
    public void testWifeMapperWrite() {
        TWife wife = new TWife();
        wife.setAge(22);
        wife.setName("rose");
        wife.setBeauty("very");
        tWifeMapper.insert(wife);

        THusband husband = new THusband();
        husband.setUserName("jack");
        husband.setUserWork("coder");
        tHusbandMapper.insert(husband);
    }

    @Test
    public void testWifeMapperRead() {
        TWife wife = tWifeMapper.selectAll(1L);
        System.out.println(wife);
    }

    //测试一对多关联
    @Test
    public void testUserMapperWrite() {
        TUser user = new TUser();
        user.setId(1L);
        user.setPhone("13638679973");
        tUserMapper.insert(user);

        TOrder order1 = new TOrder();
        //order1.setId(1L);
        order1.setUserId(1L);
        order1.setNumber(10);
        tOrderMapper.insert(order1);
        session.commit();

        TOrder order2 = new TOrder();
        //order2.setId(2L);
        order2.setUserId(1L);
        order2.setNumber(20);
        tOrderMapper.insert(order2);
        session.commit();

        TOrder order3 = new TOrder();
        //order3.setId(3L);
        order3.setUserId(1L);
        order3.setNumber(30);
        tOrderMapper.insert(order3);
        session.commit();

    }

    @Test
    public void testUserMapperRead() {
        TUser user = tUserMapper.selectAll(6L);
        System.out.println(user.getOrderList());
    }
    //测试多对多关联
    @Test
    public void testTeacherStudentMapperWrite() {

    }

    //测试多对多关联
    @Test
    public void testStudentMapperRead() {
        TStudent tStudent = tStudentMapper.selectAll(1L);
        System.out.println(tStudent.getTeacherList());
    }

    //测试多对多关联
    @Test
    public void testTeacherMapperRead() {
        TTeacher tTeacher = tTeacherMapper.selectAll(3L);
        System.out.println(tTeacher.getStudentList());

    }
}