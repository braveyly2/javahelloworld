package com.hust.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lw
 * @Title: PictureCodeDto
 * @Description: 后台图片验证码结构
 * @date 2018/8/28 15:58
 */
@Data
public class PictureCodeDto implements Serializable {


    private static final long serialVersionUID = 8589883774166363448L;
    /**
     * base64形式的字符串
     */
    private String imgCodeImgData;
    /**
     * 标记
     */
    private String idCode;
}
