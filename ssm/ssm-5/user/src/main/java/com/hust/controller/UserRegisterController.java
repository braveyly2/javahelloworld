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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
{
	"basic": {
		"ver": "1.0",
		"time": 1592399555986,
		"id": 30,
		"nonce": 1567246549,
		"token": null,
		"sign": "6b9856ff2ee4832fda9aec420530570d"   //md5(password#code)  ->md5(123456#888888)
	},
	"data": {
		"loginName": "admin",
		"loginType": 1,
		"password": "ahtrKPPPt2us8NVWiP6ZMk1h+3zHeZ+3UzWZTGiMvw55yCqn8w43wGr3GUliZogz9E6zKdIz9ehGFYmK5tRIu1jWOHsYfrI5SHqKL1uRRntdtzDYzvRuqyF6nGhxIMDEatoJhVwAAbkT93HF/F2XjzwGHEfviGIPZJ1dqE73q6c="
		// rsa public key(password)  123456
	}
}

{
    "basic": {
        "id": "30",
        "time": 1592399555986,
        "code": 200,
        "msg": "operate successfully"
    },
    "data": null
}
*/
@RestController
public class UserRegisterController {
    @Autowired
    UserService userServiceImpl;

    @Autowired
    JedisUtils jedisUtils;

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public TDResponse registerUser(@RequestBody TDRequest<RegisterUserInputData> request) {
        TDResponse response = new TDResponse<>();
        BasicInput basicInput = request.getBasic();
        BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(request.getBasic());
        response.setBasic(basicOutput);
        RegisterUserInputData inputData = request.getData();

        if (basicOutput.getCode() != ErrorCodeEnum.TD200.code()) {
            return response;
        }

        if (inputData.getLoginType() == UserConstant.LOGIN_TYPE_MOBILE) {
            boolean isExist = userServiceImpl.checkMobileExist(inputData.getLoginName(), basicOutput);
            if (isExist) {
                basicOutput.setCode(ErrorCodeEnum.TD7006.code());
                basicOutput.setMsg(ErrorCodeEnum.TD7006.msg());
                return response;
            }
        } else if (inputData.getLoginType() == UserConstant.LOGIN_TYPE_EMAIL) {
            boolean isExist = userServiceImpl.checkMobileExist(inputData.getLoginName(), basicOutput);
            if (isExist) {
                basicOutput.setCode(ErrorCodeEnum.TD7023.code());
                basicOutput.setMsg(ErrorCodeEnum.TD7023.msg());
                return response;
            }
        } else {
            basicOutput.setCode(ErrorCodeEnum.TD1011.code());
            basicOutput.setMsg(ErrorCodeEnum.TD1011.msg());
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

        //String code = "888888";
        //end

        try {
            User userBaseInfo = new User();
            if (inputData.getLoginType() == UserConstant.LOGIN_TYPE_MOBILE) {
                userBaseInfo.setName(inputData.getLoginName());
            } else {
                userBaseInfo.setName(inputData.getLoginName());
            }
            //从缓存读取私钥
            String privateKeyKey = UserConstant.REDIS_REGISTER_PRIVATE_KEY + inputData.getLoginName();
            String privateKey = jedisUtils.getString(privateKeyKey);
            //String privateKey = GlobalVariable.PRIVATE_KEY;
            //end
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
                //userBaseInfo.setId((long)1);
                UserLogin userLogin = new UserLogin();
                //userLogin.setUserId((Long) userBaseInfo.getId());
                userLogin.setLoginName(inputData.getLoginName());
                userLogin.setLoginType(inputData.getLoginType());
                //注册时，随机生成默认昵称
                //String defaultName = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
                //userBaseInfo.setNickName(defaultName);
                int n = userServiceImpl.insert(userBaseInfo);
                //int n = 1;
                //end
                if (n > 0) {
                    basicOutput.setCode(ErrorCodeEnum.TD200.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD200.msg());
                } else {
                    basicOutput.setCode(ErrorCodeEnum.TD9004.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD9004.msg());
                }
            }
        } catch (Exception e) {
            com.hust.accountcommon.util.LogUtil.error("用户注册异常", "ms-user", e);
            basicOutput.setCode(ErrorCodeEnum.TD9500.code());
            basicOutput.setMsg(ErrorCodeEnum.TD9500.msg());
        }
        return response;
    }
}
