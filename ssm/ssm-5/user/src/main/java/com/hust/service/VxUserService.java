package com.hust.service;

import com.hust.entity.dto.LoginResultDto;

public interface VxUserService {

    public LoginResultDto vxLogin(String code);

    public LoginResultDto vxBindAccount(String code, String loginName, String dynCode);

    public int accountBindVx(String code, long userId);

    public int accountUnBindVx(long userId);
}
