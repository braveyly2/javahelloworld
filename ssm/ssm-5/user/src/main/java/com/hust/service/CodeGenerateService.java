package com.hust.service;


import com.hust.entity.dto.PictureCodeDto;

/**
 * @author Yurj
 * @date 2018-09-01 17:30
 * @description
 */
public interface CodeGenerateService {
    /**
     * 图片验证码产生.
     * @return PictureCodeDto实体
     */
    PictureCodeDto generatePictureCode();

    /**
     * 验证图形验证码.
     * @return 通过则true，失败则false
     */
    boolean checkPictureCode(String idCode, String imgCode);
}