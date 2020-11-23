package com.hust.dao;

import com.hust.entity.TTransaction;

public interface TTransactionMapper {
    int insert(TTransaction record);

    int insertSelective(TTransaction record);
}