package com.hust.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TX
 * @Description: Token
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
