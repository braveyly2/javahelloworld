package com.hust.service;

import com.hust.entity.domain.User;

public interface UserService {
    int insert(User record);

    User selectByPrimaryKey(int id);

    User selectByName(String name);
}
