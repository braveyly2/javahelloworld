package com.hust.entity.dto;

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
     * token字符串
     */
    private String token;
    /**
     * SID
     */
    private String sid;
    /**
     * TokenId
     */
    private Long tid;
}
