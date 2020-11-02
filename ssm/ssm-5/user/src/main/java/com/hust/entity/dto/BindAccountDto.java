package com.hust.entity.dto;

import lombok.Data;

@Data
public class BindAccountDto {
    /**
     * 手机或邮箱名
     */
    String loginName;

    /**
     * 动态验证码
     */
    String dynCode;

    /**
     * 是否同意合并
     */
    Boolean isAgreeMerge;
}
