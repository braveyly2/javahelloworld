package com.example.hust.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.hust.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 类功能描述
 *
 * @author nick.liwei
 * @since 2021-05-07
 */
public interface UserMapper extends BaseMapper<User> {
    //自定义sql
    //@Select("select * from user ${ew.customSqlSegment}")
    List<User> selectBySql(@Param(Constants.WRAPPER) Wrapper<User> userWrapper);

    List<User> selectByName(@Param("name") String name);

    //传入对象参数
    //List<User> selectAllUsers(@Param("user") User user, @Param("bm") String bm);
}
