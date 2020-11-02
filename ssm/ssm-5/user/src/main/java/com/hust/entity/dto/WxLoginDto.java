package com.hust.entity.dto;

import lombok.Data;

@Data
public class WxLoginDto {
    /**
     * 微信登录的授权码
     */
    String code;
}
