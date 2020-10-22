package com.hust.accountcommon.entity.dto;

import lombok.Data;

/**
 * @author Lyh
 * @Title: TokenResultDto
 * @Description: TOKEN 结果类
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
