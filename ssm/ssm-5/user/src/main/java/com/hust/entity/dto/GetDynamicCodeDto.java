package com.hust.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hust.constant.CheckParaMsgFormat;
import com.hust.accountcommon.util.PublicUtil;
import lombok.Data;

/**
 * @author lw
 * @Description: 获取验证码数据结构
 */
@Data
public class GetDynamicCodeDto extends CheckParaResult {

    /**
     * 业务
     */
    private int businessType;
    /**
     * 登录账号
     */
    private String loginName;
    /**
     * 登录账号类型
     */
    private int loginType;

    /**
     * 图片验证码id
     */
    private String idCode;

    /**
     * 图片验证码code
     */
    private String imgCode;

    private String lang;


    /**
     * 校验参数合理性
     * @param loginName
     */
    @JsonProperty("loginName")
    private void checkLoginName(String loginName) {
        this.loginName = loginName;
        if (PublicUtil.isEmpty(loginName)) {
            super.getCheckResults().add(String.format(CheckParaMsgFormat.PARAM_IS_EMPTY, "loginName"));
        } else if (!PublicUtil.isMobileNumber(loginName) && !PublicUtil.isEmail(loginName)) {
            super.getCheckResults().add(String.format(CheckParaMsgFormat.PARAM_ERROR_FORMAT,"loginName"));
        }
    }


    /**
     * 校验参数合理性
     * @param imgCode
     */
    @JsonProperty("imgCode")
    private void checkImgCode(String imgCode) {
        this.imgCode = imgCode;
        if (!PublicUtil.isEmpty(imgCode)) {
            if (imgCode.length() != CheckParaMsgFormat.IMG_CODE_LEN) {
                super.getCheckResults().add(String.format(CheckParaMsgFormat.PARAM_ERROR_FORMAT, "imgCode"));
            }
        }
    }


}
