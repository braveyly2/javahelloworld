package com.hust.service;

import com.hust.entity.dto.LoginResultDto;

public interface WxUserService {

    public LoginResultDto wxLogin(String code);

    public LoginResultDto wxBindAccount(String code, String loginName, String dynCode);

    public int accountBindWx(String code, long userId);

    public int accountUnBindWx(long userId);
}
