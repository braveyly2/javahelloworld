package com.hust.constant;

public interface CheckParaMsgFormat {

    String PARAM_IS_TOO_LONG = "%s过长，超过%s个字符";
    String PARAM_IS_EMPTY = "%s为空";
    String PARAM_ERROR_FORMAT = "%s格式错误";
    String PARAM_SIZE_IS_TOO_BIG = "%s文件过大，超过%s";

    int NAME_LEN = 50;
    int MOBILE_LEN = 20;
    int EMAIL_LEN = 50;
    int IMG_CODE_LEN = 4;
    int DYNAMIC_CODE_LEN = 6;
    int FEEDBACK_LEN = 1024;
    int PASSWORD_MD5_LEN = 32;
    int RSA_LEN = 180;
    int USER_ADDRESS_LEN = 500;
    int SN_LEN = 30;
    int MD5_LEN = 32;
    // 前端传递加密后是44
    int AES_LEN = 64;
}
