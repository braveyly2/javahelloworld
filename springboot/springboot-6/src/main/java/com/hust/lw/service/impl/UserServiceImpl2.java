package com.hust.lw.service.impl;

import com.hust.lw.service.UserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl2 implements UserService2 {
    //@Autowired
    //private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcTemplate secondaryJdbcTemplate;

    @Override
    public void create(String userName, Integer age) {
        secondaryJdbcTemplate.update("insert into user22(userName, age) values(?, ?)", userName, age);
    }

    @Override
    public void deleteByName(String userName) {
        secondaryJdbcTemplate.update("delete from user22 where userName = ?", userName);
    }

    @Override
    public Integer getAllUsers() {
        return secondaryJdbcTemplate.queryForObject("select count(1) from user22", Integer.class);
    }

    @Override
    public void deleteAllUsers() {
        secondaryJdbcTemplate.update("delete from user22");
    }
}
