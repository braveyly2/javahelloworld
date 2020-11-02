package com.hust.entity.dto;

import lombok.Data;

@Data
public class UnbindAccountDto {
    /**
     * 解绑类型
     */
    int unbindType;

    /**
     * 动态验证码
     */
    String dynCode;
}
