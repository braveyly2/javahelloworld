package com.hust.service;

import com.hust.entity.User;

import java.util.ArrayList;

public interface UserService {
    int insert(User record);

    User selectByPrimaryKey(int id);

    User selectByName(String name);
}
