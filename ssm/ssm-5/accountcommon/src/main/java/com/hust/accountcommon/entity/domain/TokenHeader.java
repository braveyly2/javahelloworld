package com.hust.accountcommon.entity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lw
 * @Title: TokenHeader
 * @Description: token的header定义
 * @date 2018/9/10 10:26
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
