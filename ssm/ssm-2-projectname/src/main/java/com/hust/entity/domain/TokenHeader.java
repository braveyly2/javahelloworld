package com.hust.entity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TX
 * @Description: Token头
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenHeader {
    /**
     * 版本号
     */
    private String ver;

    /**
     * 加密算法
     */
    private String alg;
}
