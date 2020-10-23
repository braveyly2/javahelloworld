package com.hust.accountcommon.entity.dto;

import lombok.Data;

/**
 * @author lw
 * @Title: TokenResultDto
 * @Description: TOKEN 结果类，用于响应给客户端
 * @date 2018/9/10 10:26
 */
@Data
public class TokenResultDto {
    /**
     * Token唯一标识
     */
    private Long tokenId;
    /**
     * refreshToken唯一标识
     */
    private Long refreshTokenId;
    /**
     * token字符串
     */
    private String token;
    /**
     * refreshToken字符串
     */
    private String refreshToken;
    /**
     * SID
     */
    private String sid;
}
