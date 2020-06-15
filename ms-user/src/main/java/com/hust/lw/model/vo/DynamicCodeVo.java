package com.hust.lw.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lyh
 * @Description: 验证码结果数据结构
 */
@Data
public class DynamicCodeVo implements Serializable {
    private static final long serialVersionUID = 1783769495833943251L;
    /**
     * 图片验证码id
     */

    private String idCode;

    /**
     * 图片验证码BASE64
     */

    private String imgData;

    private String publicKey;
}
