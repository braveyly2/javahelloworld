package com.hust.service;

import com.hust.entity.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    int insert(User record);

    User selectByPrimaryKey(int id);

    User selectByName(String name);

    List<User> selectAllByName(String name);
}
