package com.hust.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hust.accountcommon.util.PublicUtil;
import com.hust.constant.CheckParaMsgFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lw
 * @Description: 找回密码数据结构
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FindLoginPwdDto extends CheckParaResult {

    private String loginName;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 校验参数合理性
     * @param newPassword
     */
    @JsonProperty("newPassword")
    private void checkNewPassword(String newPassword) {
        this.newPassword = newPassword;
        if (PublicUtil.isEmpty(newPassword)) {
            super.getCheckResults().add(String.format(CheckParaMsgFormat.PARAM_IS_EMPTY, "newPassword"));
        }
        //else if (newPassword.length() != CheckParaMsgFormat.PASSWORD_MD5_LEN) {
        //    super.getCheckResults().add(String.format(CheckParaMsgFormat.PARAM_ERROR_FORMAT, "newPassword"));
        //}
    }

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

}
