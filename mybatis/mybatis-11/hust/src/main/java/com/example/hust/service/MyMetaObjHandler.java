package com.example.hust.service;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 类功能描述
 *
 * @author nick.liwei
 * @since 2021-05-10
 */
@Component
public class MyMetaObjHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if(metaObject.hasSetter("createTime")){
            setInsertFieldValByName("create_time", LocalDateTime.now(),metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if(metaObject.hasSetter("updateTime")){
            setUpdateFieldValByName("update_time",LocalDateTime.now(),metaObject);
        }
    }
}