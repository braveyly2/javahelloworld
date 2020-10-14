package com.hust.service.impl;

import com.hust.constant.ErrorCodeEnum;
import com.hust.constant.I18nConstant;
import com.hust.constant.UserConstant;
import com.hust.dao.UserMapper;
import com.hust.entity.domain.User;
import com.hust.entity.dto.LoginDto;
import com.hust.entity.dto.LoginResultDto;
import com.hust.entity.dto.TokenResultDto;
import com.hust.service.UserService;
import com.hust.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public User selectByPrimaryKey(int id){
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
            //ÃÜÂëµÇÂ¼
            return this.loginByPwd(tdRequest, clientType, generateToken, isCheckImgCode);
        } else {
            //¶¯Ì¬ÂëµÇÂ¼
            //return this.loginBySms(tdRequest, clientType);
            return null;
        }
    }

    /**
     * ÃÜÂëµÇÂ¼
     *
     * @param tdRequest µÇÂ¼²ÎÊý
     * @return
     */
    private LoginResultDto loginByPwd(TDRequest<LoginDto> tdRequest, String clientType, boolean generateToken, boolean isCheckImgCode) {
        LoginDto loginDto = tdRequest.getData();
        LoginResultDto loginResultDto = new LoginResultDto();
        String loginName = loginDto.getUserName();
        String language = PublicUtil.isEmpty(loginDto.getLang()) ? I18nConstant.DEFAULT_LANGUAGE : loginDto.getLang();
        User user = getUserInfoByName(loginName);
        //User user = new User();
        //user.setId(IdWorker.getInstance().getId());
        //user.setName("admin");
        //user.setPassword("e10adc3949ba59abbe56e057f20f883e");//123456
        //user.setMark("this is admin");

        if (PublicUtil.isEmpty(user)) {
            loginResultDto.setResultCode(ErrorCodeEnum.TD7002);
            return loginResultDto;
        }
        //MD5????MD5(nonce#time#userName#MD5(PSW))
        String md5Str = MD5Util.encrypt(tdRequest.getBasic().getNonce() + "#" + tdRequest.getBasic().getTime() + "#" + loginName + "#" + user.getPassword());
        if (loginDto.getPassword().equals(md5Str)) {
            if (generateToken) {
                TokenResultDto tokenResultDto = tokenUtil.createToken(user.getId(), user.getPassword(), clientType, language);
                loginResultDto.setToken(tokenResultDto.getToken());
                loginResultDto.setSid(tokenResultDto.getSid());
                loginResultDto.setTid(tokenResultDto.getTid());
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
}
