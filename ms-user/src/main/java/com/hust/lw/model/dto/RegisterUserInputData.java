package com.hust.lw.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hust.lw.constant.CheckParaMsgFormat;
import com.hust.lw.utils.PublicUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterUserInputData extends CheckParaResult {
    private String loginName;
    private int loginType;
    private String password;


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
     * @param password
     */
    @JsonProperty("password")
    private void checkPassword(String password) {
        this.password = password;
        if (PublicUtil.isEmpty(password)) {
            super.getCheckResults().add(String.format(CheckParaMsgFormat.PARAM_IS_EMPTY, "password"));
        } else if (password.length() > CheckParaMsgFormat.RSA_LEN) {
            super.getCheckResults().add(String.format(CheckParaMsgFormat.PARAM_IS_TOO_LONG, "password", CheckParaMsgFormat.RSA_LEN));
        }
    }
}
