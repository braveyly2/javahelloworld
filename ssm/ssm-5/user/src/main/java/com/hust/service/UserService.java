package com.hust.service;

import com.hust.accountcommon.util.apitemplate.BasicInput;
import com.hust.accountcommon.util.apitemplate.TDResponse;
import com.hust.entity.domain.User;
import com.hust.entity.dto.FindLoginPwdDto;
import com.hust.entity.dto.LoginResultDto;
import com.hust.entity.dto.LoginDto;
import com.hust.accountcommon.entity.dto.TokenResultDto;
import com.hust.accountcommon.util.apitemplate.BasicOutput;
import com.hust.accountcommon.util.apitemplate.TDRequest;

import java.util.List;

public interface UserService {
    public int insert(User record);

    public User selectByPrimaryKey(long id);

    public LoginResultDto login(TDRequest<LoginDto> tdRequest, String clientType, int loginType, boolean generateToken, boolean isCheckImgCode);

    public boolean checkAccountExist(String name, int loginType);

    public User getUserInfoByAccount(String name, int loginType);

    public TokenResultDto createToken(long userId, String pwdMd5, String clientType, String language, String role, List<String> authList);

    public int findPwd(FindLoginPwdDto findLoginPwdDto, int loginType, BasicInput basicInput);
}
