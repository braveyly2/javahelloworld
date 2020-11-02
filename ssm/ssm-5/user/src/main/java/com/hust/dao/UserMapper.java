package com.hust.dao;

import com.hust.entity.domain.User;

public interface UserMapper {
    int deleteByPrimaryKey(long id);

    /**
     * @param record
     */
    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(long id);

    User selectByPhone(String phone);

    User selectByEmail(String email);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByVxId(String vxId);
}