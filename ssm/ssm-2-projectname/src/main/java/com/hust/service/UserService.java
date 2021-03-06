package com.hust.service;

import com.hust.entity.domain.User;
import com.hust.entity.dto.LoginDto;
import com.hust.entity.dto.LoginResultDto;
import com.hust.entity.dto.TokenResultDto;
import com.hust.util.apitemplate.BasicOutput;
import com.hust.util.apitemplate.TDRequest;

public interface UserService {
    int insert(User record);

    User selectByPrimaryKey(long id);

    User selectByName(String name);

    public LoginResultDto login(TDRequest<LoginDto> tdRequest, String clientType, boolean generateToken, boolean isCheckImgCode);

    boolean checkMobileExist(String name, BasicOutput basicOutput);

    public TokenResultDto createToken(long userId, String pwdMd5, String clientType, String language);
}
