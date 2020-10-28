package com.hust.service.impl;

import com.hust.constant.UserConstant;
import com.hust.entity.dto.PictureCodeDto;
import com.hust.service.CodeGenerateService;
import com.hust.util.GenPicCodeUtil;
import com.hust.util.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * @author Yurj
 * @date 2018-09-01 17:32
 * @description
 */
@Service
@Slf4j
public class CodeGenerateServiceImpl implements CodeGenerateService {

    @Autowired
    JedisUtils jedisUtils;

    /**
     * 图片验证码产生.
     *
     * @return PictureCodeDto实体
     */
    @Override
    public PictureCodeDto generatePictureCode() {
        Map<String, Object> codeResult = GenPicCodeUtil.generateCodeAndPic();

        PictureCodeDto pictureCodeDto = new PictureCodeDto();
        pictureCodeDto.setImgCodeImgData((String)codeResult.get("codeBase64"));
        String codeFlag = UUID.randomUUID().toString().replace("-", "");
        pictureCodeDto.setIdCode(codeFlag);
        jedisUtils.setString(UserConstant.REDIS_PICTURE_CODE + codeFlag, (String)codeResult.get("code"), 5 * 60L);//验证码存储redis,5 minutes
        return pictureCodeDto;
    }

    @Override
    public boolean checkPictureCode(String idCode, String imgCode){
        if(null==idCode || null==imgCode || ""==idCode || ""==imgCode){
            return false;
        }
        String imgCodeTmp = jedisUtils.getString(UserConstant.REDIS_PICTURE_CODE + idCode);
        if(imgCodeTmp.equals(imgCode)){
            return true;
        }
        return false;
    }
}
    

 