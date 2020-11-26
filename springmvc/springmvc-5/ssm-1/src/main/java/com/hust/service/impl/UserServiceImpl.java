package com.hust.service.impl;

import com.hust.dao.UserMapper;
import com.hust.entity.User;
import com.hust.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(User record) {
        userMapper.insert(record);
        return 0;
    }

    @Override
    public User selectByPrimaryKey(Long id){
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User selectByName(String name) {
        return null;
    }

    @Override
    public List<User> selectAllByName(String name) { return null; }
}
