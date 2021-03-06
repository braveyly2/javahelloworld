package com.hust.controller;

import com.hust.accountcommon.util.IdWorker;
import com.hust.accountcommon.util.PublicUtil;
import com.hust.constant.UserConstant;
import com.hust.entity.domain.User;
import com.hust.entity.domain.UserLogin;
import com.hust.entity.dto.DynamicCodeRedisDto;
import com.hust.entity.dto.RegisterUserInputData;
import com.hust.service.UserService;
import com.hust.util.*;
import com.hust.accountcommon.util.ciper.MD5Util;
import com.hust.accountcommon.util.apitemplate.BasicInput;
import com.hust.accountcommon.util.apitemplate.BasicOutput;
import com.hust.accountcommon.util.apitemplate.TDRequest;
import com.hust.accountcommon.util.apitemplate.TDResponse;
import com.hust.accountcommon.util.ciper.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lw
 * @Title: UserRegisterController
 * @Description: 用户注册的controller
 * @date 2020/9/5 19:42
 */
@RestController
@Slf4j
public class UserRegisterController {
    @Autowired
    UserService userServiceImpl;

    @Autowired
    JedisUtils jedisUtils;

    /**
     * 使用邮箱或手机号进行注册
     *
     * @param request 用户注册信息
     * @return TDResponse  注册结果
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public TDResponse registerUser(@RequestBody TDRequest<RegisterUserInputData> request) {
        TDResponse response = new TDResponse<>();
        BasicInput basicInput = request.getBasic();
        BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(request.getBasic());
        response.setBasic(basicOutput);
        RegisterUserInputData inputData = request.getData();
        int loginType = inputData.getLoginType();

        if (basicOutput.getCode() != ErrorCodeEnum.TD200.code()) {
            return response;
        }

        boolean isAccountValid = true;
        if (loginType == UserConstant.LOGIN_TYPE_MOBILE) {
            if(!PublicUtil.isPhone(inputData.getLoginName())){
                isAccountValid = false;
            }
        } else if (loginType == UserConstant.LOGIN_TYPE_EMAIL) {
            if(!PublicUtil.isEmail(inputData.getLoginName())){
                isAccountValid = false;
            }
        }

        if(!isAccountValid){
            basicOutput.setCode(ErrorCodeEnum.TD1011.code());
            basicOutput.setMsg(ErrorCodeEnum.TD1011.msg());
            return response;
        }

        boolean isExist = userServiceImpl.checkAccountExist(inputData.getLoginName(), loginType);

        if (isExist && UserConstant.LOGIN_TYPE_MOBILE == loginType) {
            basicOutput.setCode(ErrorCodeEnum.TD7006.code());
            basicOutput.setMsg(ErrorCodeEnum.TD7006.msg());
            return response;
        }
        else if(isExist && UserConstant.LOGIN_TYPE_EMAIL == loginType){
            basicOutput.setCode(ErrorCodeEnum.TD7023.code());
            basicOutput.setMsg(ErrorCodeEnum.TD7023.msg());
            return response;
        }

        //从缓存获取动态码
        DynamicCodeRedisDto dynamicCodeDto = (DynamicCodeRedisDto) jedisUtils.get(UserConstant.REDIS_REGISTER_CODE + inputData.getLoginName() + UserConstant.REDIS_CODE);
        if (PublicUtil.isEmpty(dynamicCodeDto)) {
            basicOutput.setCode(ErrorCodeEnum.TD1008.code());
            basicOutput.setMsg(ErrorCodeEnum.TD1008.msg());
            return response;
        }
        String code = dynamicCodeDto.getCode();

        try {
            User userBaseInfo = new User();
            if (inputData.getLoginType() == UserConstant.LOGIN_TYPE_MOBILE) {
                userBaseInfo.setPhone(inputData.getLoginName());
            } else {
                userBaseInfo.setEmail(inputData.getLoginName());
            }
            //从缓存读取私钥
            String privateKeyKey = UserConstant.REDIS_REGISTER_PRIVATE_KEY + inputData.getLoginName();
            String privateKey = jedisUtils.getString(privateKeyKey);

            String password = inputData.getPassword();
            //用私钥解密密码
            password = RSAUtil.privateDecrypt(password, privateKey);
            userBaseInfo.setPassword(password);
            userBaseInfo.setId(IdWorker.getInstance().getId());
            //验证签名
            String sign = basicInput.getSign();
            String checkSign = MD5Util.encrypt(password + "#" + code);
            if (sign == null || (!sign.equals(checkSign))) {
                //签名错误
                basicOutput.setCode(ErrorCodeEnum.TD7005.code());
                basicOutput.setMsg(ErrorCodeEnum.TD7005.msg());
            } else {
                UserLogin userLogin = new UserLogin();
                userLogin.setLoginName(inputData.getLoginName());
                userLogin.setLoginType(inputData.getLoginType());

                int n = userServiceImpl.insert(userBaseInfo);
                if (n > 0) {
                    basicOutput.setCode(ErrorCodeEnum.TD200.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD200.msg());
                } else {
                    basicOutput.setCode(ErrorCodeEnum.TD9004.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD9004.msg());
                }
            }
        } catch (Exception e) {
            log.error("用户注册异常", "ms-user", e);
            basicOutput.setCode(ErrorCodeEnum.TD9500.code());
            basicOutput.setMsg(ErrorCodeEnum.TD9500.msg());
        }
        return response;
    }
}
