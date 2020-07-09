package com.hust.lw.service.impl;

import com.hust.lw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    //@Autowired
    //private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcTemplate primaryJdbcTemplate;

    @Override
    public void create(String userName, Integer age) {
        primaryJdbcTemplate.update("insert into user2(userName, age) values(?, ?)", userName, age);
    }

    @Override
    public void deleteByName(String userName) {
        primaryJdbcTemplate.update("delete from user2 where userName = ?", userName);
    }

    @Override
    public Integer getAllUsers() {
        return primaryJdbcTemplate.queryForObject("select count(1) from user2", Integer.class);
    }

    @Override
    public void deleteAllUsers() {
        primaryJdbcTemplate.update("delete from user2");
    }
}
