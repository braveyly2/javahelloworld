package com.hust.entity.dto;

import lombok.Data;

@Data
public class WxBindAccountDto {
    /**
     * 微信授权码
     */
    String code;

    /**
     * 微信登录过程中绑定的手机号或邮箱号
     */
    String loginName;

    /**
     * 微信登录过程中验证的手机号或邮箱的动态验证码
     */
    String dynCode;
}
