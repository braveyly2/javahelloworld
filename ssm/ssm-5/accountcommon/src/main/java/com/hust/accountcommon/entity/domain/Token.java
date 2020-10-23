package com.hust.accountcommon.entity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lw
 * @Title: Token
 * @Description: Token包含header、payload和sign
 * @date 2018/9/10 10:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    /**
     * 头
     */
    private TokenHeader header;
    /**
     * 负载
     */
    private TokenPayload payload;
    /**
     * 签名
     */
    private String sign;

//    public String calculateSign(){
//    }
}
