package com.hust.dao;

import com.hust.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * @param record
     */
    void insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByName(String name);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}