package com.hust.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hust.constant.CheckParaMsgFormat;
import com.hust.constant.I18nConstant;
import com.hust.entity.dto.CheckParaResult;
import com.hust.util.PublicUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author lyh
 * @Description: 登录数据结构
 */
@Data
public class LoginDto extends CheckParaResult implements Serializable {
    private static final long serialVersionUID = -4118389899757189722L;

    /**
     * 登录用户名（邮箱/手机号/账号）
     */
    private String userName;
    /**
     * 登录类型 1密码登录 2 手机短信动态码登录
     */
    private String type;
    /**
     * 密码MD5(nonce#time#userName#MD5(PSW))或者短信验证码
     */
    private String password;

    /**
     * 图片验证码id
     */
    private String idCode;

    /**
     * 图片验证码code
     */
    private String imgCode;

    private String uuid;



    private String lang= I18nConstant.DEFAULT_LANGUAGE;

}
