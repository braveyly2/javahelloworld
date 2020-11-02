package com.hust.service.impl;

import com.hust.accountcommon.util.IdWorker;
import com.hust.accountcommon.util.apitemplate.BasicInput;
import com.hust.accountcommon.util.ciper.RSAUtil;
import com.hust.entity.domain.User;
import com.hust.entity.dto.*;
import com.hust.service.UserService;
import com.hust.accountcommon.util.PublicUtil;
import com.hust.accountcommon.util.ciper.MD5Util;
import com.hust.constant.ErrorCodeEnum;
import com.hust.constant.I18nConstant;
import com.hust.constant.UserConstant;
import com.hust.dao.UserMapper;
import com.hust.accountcommon.entity.dto.TokenResultDto;
import com.hust.accountcommon.util.apitemplate.BasicOutput;
import com.hust.accountcommon.util.apitemplate.TDRequest;
import com.hust.accountcommon.util.token.TokenUtil;
import com.hust.util.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hust.constant.UserConstant.LOGIN_TYPE_EMAIL;
import static com.hust.constant.UserConstant.LOGIN_TYPE_MOBILE;
import static com.hust.constant.UserConstant.REDIS_CODE;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private JedisUtils jedisUtils;

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
    public LoginResultDto login(TDRequest<LoginDto> tdRequest, String clientType, int loginType, boolean generateToken, boolean isCheckImgCode) {
        LoginDto loginDto = tdRequest.getData();
        if (UserConstant.LOGIN_METHOD_PWD.equals(loginDto.getLoginMethod())) {
            return this.loginByPwd(tdRequest, clientType, loginType, generateToken, isCheckImgCode);
        }
        else if(UserConstant.LOGIN_METHOD_DYNAMIC_CODE.equals(loginDto.getLoginMethod())){
            return this.loginByDynamicCode(tdRequest, clientType, loginType, generateToken, isCheckImgCode);
        }
        else {
            return null;
        }
    }

    @Override
    public boolean checkAccountExist(String name, int loginType){
        User user = null;
        if(UserConstant.LOGIN_TYPE_MOBILE == loginType){
            user = userMapper.selectByPhone(name);
        }
        else if(LOGIN_TYPE_EMAIL == loginType){
            user = userMapper.selectByEmail(name);
        }

        if(null != user) {
            return true;
        }
        return false;
    }

    @Override
    public User getUserInfoByAccount(String name, int loginType){
        User user = null;
        if(UserConstant.LOGIN_TYPE_MOBILE == loginType){
            user = userMapper.selectByPhone(name);
        }
        else if(LOGIN_TYPE_EMAIL == loginType){
            user = userMapper.selectByEmail(name);
        }
        return user;
    }

    @Override
    public TokenResultDto createToken(long userId, String pwdMd5, String clientType,String language, String role, List<String> authList){
        return tokenUtil.createToken(userId, pwdMd5, clientType, null,role,authList);
    }

    /**
     * 根据验证码登录
     *
     * @param tdRequest 用户名和密码
     * @param clientType 客户端类型
     * @param generateToken 是否产生token
     * @param isCheckImgCode 是否检查图片验证码
     * @return LoginResultDto  AT/RT/userid等信息
     */
    private LoginResultDto loginByDynamicCode(TDRequest<LoginDto> tdRequest, String clientType, int loginType, boolean generateToken, boolean isCheckImgCode) {
        LoginDto loginDto = tdRequest.getData();
        LoginResultDto loginResultDto = new LoginResultDto();
        String loginName = loginDto.getUserName();
        String language = PublicUtil.isEmpty(loginDto.getLang()) ? I18nConstant.DEFAULT_LANGUAGE : loginDto.getLang();
        User user = getUserInfoByAccount(loginName, loginType);
        Boolean isRegister = false;

        if (PublicUtil.isEmpty(user)) {
            //首次登录则为注册
            user = new User();
            user.setId(IdWorker.getInstance().getId());
            if(UserConstant.LOGIN_TYPE_MOBILE == loginType){
                user.setPhone(loginName);
            }
            else if(UserConstant.LOGIN_TYPE_EMAIL == loginType){
                user.setEmail(loginName);
            }
            int n = userMapper.insert(user);
            if(n<1){
                loginResultDto.setResultCode(ErrorCodeEnum.TD9004);
                return loginResultDto;
            }
            isRegister = true;
        }

        String codeKey = UserConstant.REDIS_LOGIN_DYNAMIC_CODE + loginName + UserConstant.REDIS_CODE;
        DynamicCodeRedisDto dynamicCodeDto = (DynamicCodeRedisDto) jedisUtils.get(codeKey);
        String dynamicCodeMd5 = MD5Util.encrypt(dynamicCodeDto.getCode());
        String md5Str = MD5Util.encrypt(tdRequest.getBasic().getNonce() + "#" + tdRequest.getBasic().getTime() + "#" + loginName + "#" + dynamicCodeMd5);
        if (loginDto.getPassword().equals(md5Str)) {
            if (generateToken) {
                String pwdMd5 = null;
                if(isRegister){
                    pwdMd5 = dynamicCodeMd5;
                }else{
                    pwdMd5 = user.getPassword();
                }
                TokenResultDto tokenResultDto = tokenUtil.createToken(user.getId(), pwdMd5, clientType, language,"customer",null);
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

    /**
     * 根据密码登录
     *
     * @param tdRequest 用户名和密码
     * @param clientType 客户端类型
     * @param generateToken 是否产生token
     * @param isCheckImgCode 是否检查图片验证码
     * @return LoginResultDto  AT/RT/userid等信息
     */
    private LoginResultDto loginByPwd(TDRequest<LoginDto> tdRequest, String clientType, int loginType, boolean generateToken, boolean isCheckImgCode) {
        LoginDto loginDto = tdRequest.getData();
        LoginResultDto loginResultDto = new LoginResultDto();
        String loginName = loginDto.getUserName();
        String language = PublicUtil.isEmpty(loginDto.getLang()) ? I18nConstant.DEFAULT_LANGUAGE : loginDto.getLang();
        User user = getUserInfoByAccount(loginName, loginType);

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

    public int findPwd(FindLoginPwdDto findLoginPwdDto, int loginType, BasicInput basicInput){
        //1.判断用户名是否存在，需要用到loginType
        String loginName = findLoginPwdDto.getLoginName();
        User user = null;
        if(UserConstant.LOGIN_TYPE_MOBILE == loginType){
            user = userMapper.selectByPhone(loginName);
            if(PublicUtil.isEmpty(user)){
                return ErrorCodeEnum.TD9500.code();
            }
        }
        if(UserConstant.LOGIN_TYPE_EMAIL == loginType){
            user = userMapper.selectByEmail(loginName);
            if(PublicUtil.isEmpty(user)){
                return ErrorCodeEnum.TD9500.code();
            }
        }

        //2.验证header中的签名
        String rsaPriKeyKey = UserConstant.REDIS_FINDPWD_PRIVATE_KEY + loginName;
        String codeKey = UserConstant.REDIS_FINDPWD_DYNAMIC_CODE + loginName + REDIS_CODE;
            //2.1 获取loginName对应的找回密码的RSAPrivatekey及loginName对应的找回密码的验证码
        String rsaPriKey = (String)jedisUtils.getString(rsaPriKeyKey);
        DynamicCodeRedisDto dynamicCodeRedisDto = (DynamicCodeRedisDto)jedisUtils.get(codeKey);
        String newPwd;
        if(PublicUtil.isEmpty(rsaPriKey) || PublicUtil.isEmpty(dynamicCodeRedisDto)){
            log.error("从redis集群中获取rsaprivatekey或短信验证码失败");
            return ErrorCodeEnum.TD9500.code();
        }

        try {
            //2.2 解密newpassword字段，获得新密码的md5值
            newPwd = RSAUtil.privateDecrypt(findLoginPwdDto.getNewPassword(), rsaPriKey);
            //2.4 根据nonce、utc、loginname、code、md5(pwd)来验证header中的sign
            String signMd5 = MD5Util.encrypt(basicInput.getNonce() + "#" + basicInput.getTime() + "#" + loginName + "#" + newPwd + "#" + dynamicCodeRedisDto.getCode());
            if(!signMd5.equals(basicInput.getSign())){
                return ErrorCodeEnum.TD9500.code();
            }
        }catch(Exception e){
            log.error("找回密码，RSA私钥解密失败");
            return ErrorCodeEnum.TD9500.code();
        }

        //3.修改密码
        User updUser = new User();
        updUser.setId(user.getId());
        updUser.setPassword(newPwd);
        int n = userMapper.updateByPrimaryKeySelective(updUser);
        if(n < 1){
            log.error("操作数据库失败");
            return ErrorCodeEnum.TD9500.code();
        }
        return ErrorCodeEnum.TD200.code();
    }

    public int updatePwd(long userId, String loginName, String newPassword, String oldPassword, BasicInput basicInput){
        //1.获取loginName对应的验证码和rsa私钥
        String codeKey = UserConstant.REDIS_UPDATEPWD_DYNAMIC_CODE + loginName + UserConstant.REDIS_CODE;
        String rsaPriKeyKey = UserConstant.REDIS_UPDATEPWD_PRIVATE_KEY + loginName;
        //2.rsa私钥解密newPassword和oldPassword
        DynamicCodeRedisDto dynamicCodeRedisDto = (DynamicCodeRedisDto)jedisUtils.get(codeKey);
        String rsaPriKey = jedisUtils.getString(rsaPriKeyKey);
        String newPasswordMD5 = RSAUtil.privateDecrypt(newPassword,rsaPriKey);
        String oldPasswordMD5 = RSAUtil.privateDecrypt(oldPassword,rsaPriKey);
        //3.验证oldPassword是否正确
        User user = userMapper.selectByPrimaryKey(userId);
        if(PublicUtil.isEmpty(user)){
            log.error("从db获取user失败，userId=" + userId);
            return ErrorCodeEnum.TD9500.code();
        }
        if(!user.getPassword().equals(oldPasswordMD5)){
            log.error("旧密码不正确");
            return ErrorCodeEnum.TD9500.code();
        }
        //4.验签header中的sign
        String signMd5 = MD5Util.encrypt(basicInput.getNonce() + "#" + basicInput.getTime() + "#" + loginName + "#" + newPasswordMD5
                            + "#" + oldPasswordMD5 + "#" + dynamicCodeRedisDto.getCode());
        if(!signMd5.equals(basicInput.getSign())){
            log.error("签名不正确");
            return ErrorCodeEnum.TD9500.code();
        }
        //5.更新密码
        user.setPassword(newPasswordMD5);
        int n = userMapper.updateByPrimaryKeySelective(user);
        if(n<1){
            log.error("user更新数据库失败");
            return ErrorCodeEnum.TD9500.code();
        }
        //TODO: 将已登录用户的token去掉，确保在修改密码后必须要重新登录
        return ErrorCodeEnum.TD200.code();
    }

    /**
     * 账户绑定账户
     *
     * @param loginName 被绑定账户名
     * @param dynCode 被绑定账户动态验证码
     * @param userId 用户id
     * @return int  绑定结果
     */
    public int bindAccount(String loginName, String dynCode, Boolean isAgreeMerge, long userId){
        //1. 被绑定账号的类型及已登录账号是否缺失此种类型的账号
        int bindLoginType = 0;
        if(PublicUtil.isEmail(loginName)){
            bindLoginType = LOGIN_TYPE_EMAIL;
        } else if(PublicUtil.isPhone(loginName)){
            bindLoginType = LOGIN_TYPE_MOBILE;
        }

        User user = userMapper.selectByPrimaryKey(userId);
        if(LOGIN_TYPE_EMAIL == bindLoginType){
            String email = user.getEmail();
            if(null!=email&&0!=email.length()){
                log.error("此账户已有邮箱绑定了，请先解绑");
                return ErrorCodeEnum.TD9500.code();
            }
        }
        if(LOGIN_TYPE_MOBILE == bindLoginType){
            String phone = user.getPhone();
            if(null!=phone&&0!=phone.length()){
                log.error("此账户已有手机号绑定了，请先解绑");
                return ErrorCodeEnum.TD9500.code();
            }
        }

        //2. 判断被绑定账号是否已注册
        User bindUser = null;
        if(LOGIN_TYPE_EMAIL == bindLoginType){
            bindUser = userMapper.selectByEmail(loginName);
            //2.1 已注册
            if(null!=bindUser){
                //2.1.1 已注册，可以合并
                if(null==bindUser.getPhone()||""==bindUser.getPhone()){
                    if(!isAgreeMerge){
                        log.error("提示用户是否需要合并");
                        return ErrorCodeEnum.TD9012.code();
                    }
                    //合并用户
                    user.setEmail(bindUser.getEmail());
                    userMapper.updateByPrimaryKey(user);
                    userMapper.deleteByPrimaryKey(bindUser.getId());

                //2.1.2 已注册，不可合并
                }else{
                    log.error("此用户已注册，不可绑定");
                    return ErrorCodeEnum.TD9500.code();
                }
            }
            //2.2 未注册走下面的流程
        }
        if(LOGIN_TYPE_MOBILE == bindLoginType){
            bindUser = userMapper.selectByPhone(loginName);
            //2.1.1 已注册，可以合并
            if(null==bindUser.getEmail()||""==bindUser.getEmail()){
                if(!isAgreeMerge){
                    log.error("提示用户是否需要合并");
                    return ErrorCodeEnum.TD9012.code();
                }
                user.setPhone(bindUser.getPhone());
                userMapper.updateByPrimaryKey(user);
                userMapper.deleteByPrimaryKey(bindUser.getId());
                log.error("提示用户是否需要合并");
                return ErrorCodeEnum.TD9012.code();
                //2.1.2 已注册，不可合并
            }else{
                log.error("此用户已注册，不可绑定");
                return ErrorCodeEnum.TD9500.code();
            }
        }


        //3. 验证动态验证码是否正确
        DynamicCodeRedisDto dynamicCodeRedisDto = (DynamicCodeRedisDto)jedisUtils.get(UserConstant.REDIS_LOGIN_DYNAMIC_CODE+loginName+REDIS_CODE);
        if(!dynamicCodeRedisDto.getCode().equals(dynCode)){
            log.error("动态验证码错误");
            return ErrorCodeEnum.TD9500.code();
        }

        //4. 绑定
        if(LOGIN_TYPE_EMAIL == bindLoginType){
            user.setEmail(loginName);
            userMapper.updateByPrimaryKey(user);
        }
        if(LOGIN_TYPE_MOBILE == bindLoginType){
            user.setPhone(loginName);
            userMapper.updateByPrimaryKey(user);
        }
        return ErrorCodeEnum.TD200.code();
    }

    public int unbindAccount(int unbindType, String dynCode, long userId){
        User user = userMapper.selectByPrimaryKey(userId);
        String userName = null;
        if(LOGIN_TYPE_EMAIL==unbindType){
            userName=user.getEmail();
        }else if(LOGIN_TYPE_MOBILE==unbindType){
            userName=user.getPhone();
        }

        DynamicCodeRedisDto dynamicCodeRedisDto=(DynamicCodeRedisDto)jedisUtils.get(UserConstant.REDIS_LOGIN_DYNAMIC_CODE+userName+UserConstant.REDIS_CODE);
        if(!(dynamicCodeRedisDto.getCode().equals(dynCode))){
            log.error("动态验证码错误");
            return ErrorCodeEnum.TD9500.code();
        }

        if(LOGIN_TYPE_EMAIL==unbindType){
            if(null==user.getPhone()||user.getPhone().equals("")) {
                log.error("此账户没有手机号，不支持解除邮箱");
                return ErrorCodeEnum.TD9500.code();
            }
            user.setEmail("");
        }else if(LOGIN_TYPE_MOBILE==unbindType){
            if(null==user.getEmail()||user.getEmail().equals("")) {
                log.error("此账户没有邮箱，不支持解除手机");
                return ErrorCodeEnum.TD9500.code();
            }
            user.setPhone("");
        }
        userMapper.updateByPrimaryKey(user);

        return ErrorCodeEnum.TD200.code();
    }
}
