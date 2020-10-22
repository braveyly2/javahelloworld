package com.hust.accountcommon.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author TX
 * @Description: Token携带数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDataDto {
    /**
     * 用户ID
     */
    private long userId;
    /**
     * 用户角色
     */
    private String role;
    /**
     * 权限
     */
    private List<String> authority;
    /**
     * 服务识别码
     */
    private String sid;
    /**
     * tokenId
     */
    private long tid;

    /**
     * 客户端类型
     */
    private String clientType;

    //private TokenPayload tokenPayload;
}
