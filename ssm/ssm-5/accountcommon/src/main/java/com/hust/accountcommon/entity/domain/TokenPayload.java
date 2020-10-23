package com.hust.accountcommon.entity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lw
 * @Title: TokenPayload
 * @Description: token的payload定义
 * @date 2018/9/10 10:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenPayload {
    /**
     * 签发时间
     */
    private long iat;
    /**
     * 过期时间
     */
    private long exp;
    /**
     * 授权中心ID
     */
    private int dc;
    /**
     * TokenID
     */
    private long tid;
    /**
     * UserID
     */
    private long uid;
    /**
     * 用户角色
     */
    private String role;
    /**
     * 权限
     */
    private List<String> auth;
    /**
     * 类型 1 业务 2运维
     */
    private int type;
}
