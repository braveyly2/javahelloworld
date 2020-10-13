package com.hust.service;

import com.hust.entity.domain.User;
import com.hust.entity.dto.LoginDto;
import com.hust.entity.dto.LoginResultDto;
import com.hust.util.BasicOutput;
import com.hust.util.TDRequest;

public interface UserService {
    int insert(User record);

    User selectByPrimaryKey(int id);

    User selectByName(String name);

    public LoginResultDto login(TDRequest<LoginDto> tdRequest, String clientType, boolean generateToken, boolean isCheckImgCode);

    boolean checkMobileExist(String name, BasicOutput basicOutput);
}
