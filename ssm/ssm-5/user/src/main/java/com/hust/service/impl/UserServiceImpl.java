package com.hust.service.impl;

import com.hust.entity.domain.User;
import com.hust.entity.dto.LoginResultDto;
import com.hust.service.UserService;
import com.hust.accountcommon.util.PublicUtil;
import com.hust.accountcommon.util.ciper.MD5Util;
import com.hust.constant.ErrorCodeEnum;
import com.hust.constant.I18nConstant;
import com.hust.constant.UserConstant;
import com.hust.dao.UserMapper;
import com.hust.entity.dto.LoginDto;
import com.hust.accountcommon.entity.dto.TokenResultDto;
import com.hust.accountcommon.util.apitemplate.BasicOutput;
import com.hust.accountcommon.util.apitemplate.TDRequest;
import com.hust.accountcommon.util.token.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public int insert(User record) {
        int n = userMapper.insert(record);
        return n;
    }

    @Override
    public User selectByPrimaryKey(long id){
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User selectByName(String name) {
        return userMapper.selectByName(name);
    }

    @Override
    public LoginResultDto login(TDRequest<LoginDto> tdRequest, String clientType, boolean generateToken, boolean isCheckImgCode) {
        LoginDto loginDto = tdRequest.getData();
        if (UserConstant.LOGIN_TYPE_PWD.equals(loginDto.getType())) {
            return this.loginByPwd(tdRequest, clientType, generateToken, isCheckImgCode);
        } else {
            return null;
        }
    }

    /**
     * 根据密码登录
     *
     * @param tdRequest 用户名和密码
     * @param clientType 客户端类型
     * @param generateToken 是否产生token
     * @param isCheckImgCode 是否检查图片验证码
     * @return LoginResultDto  AT/RT/userid等信息
     */
    private LoginResultDto loginByPwd(TDRequest<LoginDto> tdRequest, String clientType, boolean generateToken, boolean isCheckImgCode) {
        LoginDto loginDto = tdRequest.getData();
        LoginResultDto loginResultDto = new LoginResultDto();
        String loginName = loginDto.getUserName();
        String language = PublicUtil.isEmpty(loginDto.getLang()) ? I18nConstant.DEFAULT_LANGUAGE : loginDto.getLang();
        User user = getUserInfoByName(loginName);

        if (PublicUtil.isEmpty(user)) {
            loginResultDto.setResultCode(ErrorCodeEnum.TD7002);
            return loginResultDto;
        }

        String md5Str = MD5Util.encrypt(tdRequest.getBasic().getNonce() + "#" + tdRequest.getBasic().getTime() + "#" + loginName + "#" + user.getPassword());
        if (loginDto.getPassword().equals(md5Str)) {
            if (generateToken) {
                TokenResultDto tokenResultDto = tokenUtil.createToken(user.getId(), user.getPassword(), clientType, language,"customer",null);
                loginResultDto.setToken(tokenResultDto.getToken());
                loginResultDto.setRefreshToken(tokenResultDto.getRefreshToken());
                loginResultDto.setSid(tokenResultDto.getSid());
                loginResultDto.setTid(tokenResultDto.getTokenId());
                loginResultDto.setRefreshTokenId(tokenResultDto.getRefreshTokenId());
                loginResultDto.setUserId((long)user.getId());
            }
        } else {
            loginResultDto.setResultCode(ErrorCodeEnum.TD7002);
        }

        return loginResultDto;
    }

    public boolean checkMobileExist(String name, BasicOutput basicOutput){
        User user = userMapper.selectByName(name);
        if(null != user) {
            return true;
        }
        return false;
    }

    private User getUserInfoByName(String name){
        User user = userMapper.selectByName(name);
        return user;
    }

    public TokenResultDto createToken(long userId, String pwdMd5, String clientType,String language, String role, List<String> authList){
        return tokenUtil.createToken(userId, pwdMd5, clientType, null,role,authList);
    }
}
