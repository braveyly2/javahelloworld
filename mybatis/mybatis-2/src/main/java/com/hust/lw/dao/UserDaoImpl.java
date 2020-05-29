package com.hust.lw.dao;

import com.hust.lw.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserDaoImpl implements UserDao{

    private SqlSessionFactory sqlSessionFactory;

    // 通过构造方法注入
    public UserDaoImpl(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }


    public User findUserById(Integer id) {
        //sqlSession是线程不安全的，所以它的最佳使用范围在方法体内
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("test.findUserById",id);
        sqlSession.close();
        return user;
    }

    public List<User> findUserByUserName(String userName) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> list = sqlSession.selectList("test.findUserByUsername", userName);
        sqlSession.close();
        return list;
    }
    // 插入用户
    public int insertUser(User user){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int res = sqlSession.insert("test.insertUser", user);
        sqlSession.commit();
        sqlSession.close();
        return res;
    }

    // 更新用户
    public void updateUserById(User user){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("test.updateUserById",user);
        sqlSession.commit();
        sqlSession.close();
    }
    // 删除用户
    public void deleteUserById(Integer id){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("test.deleteUserById",id);
        sqlSession.commit();
        sqlSession.close();
    }
}
