package com.hust.entity.dto;

import com.hust.constant.ErrorCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lyh
 * @Description: 登录结果数据结构
 */
@Data
public class LoginResultDto implements Serializable {
    private static final long serialVersionUID = 1783769495833943251L;

    /**
     * 登录结果
     */
    private ErrorCodeEnum resultCode;
    /**
     * 登录成功返回的token
     */
    private String token;
    /**
     * 服务器识别码
     */
    private String sid;
    /**
     * 图片验证码id
     */
    private String idCode;

    /**a
     * 图片验证码BASE64
     */
    private String imgData;
    /**
     * TokenId
     */
    private Long tid;
    /**
     * 用户ID
     */
    private Long userId;
}
