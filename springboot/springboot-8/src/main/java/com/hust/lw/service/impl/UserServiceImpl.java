package com.hust.lw.service.impl;

import com.hust.lw.mapper.UserMapper;
import com.hust.lw.model.entity.User;
import com.hust.lw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    //@Autowired
    //private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void create(String userName, Integer age) {
        userMapper.insert(userName, age);
}

    @Override
    public User findByName(String userName) {
        return userMapper.findByName(userName);
    }

}
