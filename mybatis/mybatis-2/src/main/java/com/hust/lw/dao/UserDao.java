package com.hust.lw.dao;

import com.hust.lw.model.User;

import java.util.List;

public interface UserDao {
    // 通过ID查询一个用户
    public User findUserById(Integer id);
    // 根据用户名模糊查询用户列表
    public List<User> findUserByUserName(String userName);
    // 添加用户
    public int insertUser(User user);
    // 更新用户
    public void updateUserById(User user);
    // 删除用户
    public void deleteUserById(Integer id);
}
