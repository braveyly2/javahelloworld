package com.hust.entity.vo;

import com.hust.entity.domain.UserLogin;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lyh
 * @Description: 登录结果数据结构
 */
@Data
public class LoginVo implements Serializable {
    private static final long serialVersionUID = 7292998886677636927L;

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

    /**
     * 图片验证码BASE64
     */
    private String imgData;

    /**
     * P2P连接标识
     */
    private String p2pId;

    private List<UserLogin> userLoginList;
}
