package com.hust.dao;

import com.hust.entity.domain.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * @param record
     */
    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(long id);

    User selectByName(String name);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}