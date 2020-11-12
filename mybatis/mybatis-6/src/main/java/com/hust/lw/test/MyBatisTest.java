package com.hust.lw.test;

import com.hust.lw.dao.ShoppingcartMapper;
import com.hust.lw.model.Shoppingcart;
import com.hust.lw.mybatis.objectFactory.ShoppingCartObjectFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    private ShoppingcartMapper shoppingCartMapper;
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
        shoppingCartMapper = session.getMapper(ShoppingcartMapper.class);
    }

    @After//在测试方法执行完成之后执行
    public void destroy() throws IOException {
        session.commit();
        session.close();
        in.close();
    }

    //通过Id查询一个用户
    @Test
    public void testShoppingCart() {
        //5.使用代理对象执行方法
       Shoppingcart shoppingcart = new Shoppingcart();
       //shoppingcart.setProductId(1L);
       shoppingcart.setProductName("clothes");
       shoppingcart.setPrice(20.89);
       shoppingcart.setNumber(8);
       shoppingcart.setTotalAmount(0.0);
       int n = shoppingCartMapper.insert(shoppingcart);
       if(n<1){
           System.out.println("failed to insert shoppingcart");
       }
       {
           Shoppingcart shoppingcart2 = shoppingCartMapper.selectByPrimaryKey(1L);
           System.out.println("product_id=1 product totalAmount=" + shoppingcart2.getTotalAmount());
       }
    }

    @Test
    public void testManualObjectFactory(){
        ShoppingCartObjectFactory e = new ShoppingCartObjectFactory();

        List constructorArgTypes = new ArrayList();
        constructorArgTypes.add(Long.class);
        constructorArgTypes.add(String.class);
        constructorArgTypes.add(Integer.class);
        constructorArgTypes.add(Double.class);
        constructorArgTypes.add(Double.class);

        List constructorArgs = new ArrayList();
        constructorArgs.add(1L);
        constructorArgs.add("run shoes");
        constructorArgs.add(5);
        constructorArgs.add(300.0);
        constructorArgs.add(0.0);

        Shoppingcart sCart = (Shoppingcart)e.create(Shoppingcart.class,constructorArgTypes,constructorArgs);
        System.out.println(sCart.getTotalAmount());
        session.close();

    }
}