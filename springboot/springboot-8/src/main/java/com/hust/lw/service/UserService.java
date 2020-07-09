package com.hust.lw.service;

import com.hust.lw.model.entity.User;

public interface UserService {
    /**
     * 新增一个用户
     * @param name
     * @param age
     */
    void create(String name, Integer age);

    /**
     * 根据name删除一个用户高
     * @param name
     */
    User findByName(String name);
}
