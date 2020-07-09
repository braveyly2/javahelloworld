package com.hust.lw.mapper;

import com.hust.lw.model.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findByName(@Param("name") String name);

    int insert(@Param("name") String name, @Param("age") Integer age);
}
