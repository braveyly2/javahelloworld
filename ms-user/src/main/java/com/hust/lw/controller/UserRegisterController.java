package com.hust.lw.controller;

import com.hust.lw.constant.GlobalVariable;
import com.hust.lw.constant.UserConstant;
import com.hust.lw.model.domain.User;
import com.hust.lw.model.domain.UserLogin;
import com.hust.lw.model.dto.RegisterUserInputData;
import com.hust.lw.utils.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserRegisterController {
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public TDResponse registerUser(@RequestBody TDRequest<RegisterUserInputData> request){
        TDResponse response = new TDResponse<>();
        BasicInput basicInput = request.getBasic();
        BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(request.getBasic());
        response.setBasic(basicOutput);
        RegisterUserInputData inputData = request.getData();
        if (inputData.getLoginType() == UserConstant.LOGIN_TYPE_MOBILE) {

            //boolean isExist = userServiceImpl.checkMobileExist(inputData.getLoginName(), basicOutput);
            boolean isExist = false;
            //end;
            if(basicOutput.getCode() != ErrorCodeEnum.TD200.code()){
                return response;
            }
            else if (isExist) {
                basicOutput.setCode(ErrorCodeEnum.TD7006.code());
                basicOutput.setMsg(ErrorCodeEnum.TD7006.msg());
                return response;

            }
        } else {
            //if (userServiceImpl.checkEmailExist(inputData.getLoginName())) {
            if (false) {
                basicOutput.setCode(ErrorCodeEnum.TD7023.code());
                basicOutput.setMsg(ErrorCodeEnum.TD7023.msg());
                return response;
            }
        }
        //从缓存获取动态码
        /*DynamicCodeRedisDto dynamicCodeDto = (DynamicCodeRedisDto) jedisUtils.get(UserConstant.REDIS_REGISTER_CODE + inputData.getLoginName() + UserConstant.REDIS_CODE);
        if (PublicUtil.isEmpty(dynamicCodeDto)) {
            basicOutput.setCode(ErrorCodeEnum.TD1008.code());
            basicOutput.setMsg(ErrorCodeEnum.TD1008.msg());
            return response;
        }
        String code = dynamicCodeDto.getCode();
        */
        String code = "888888";
        //end

        try {
            User userBaseInfo = new User();
            if (inputData.getLoginType() == UserConstant.LOGIN_TYPE_MOBILE) {
                userBaseInfo.setMobile(inputData.getLoginName());
            } else {
                userBaseInfo.setEmail(inputData.getLoginName());
            }
            //从缓存读取私钥
            String privateKeyKey = UserConstant.REDIS_REGISTER_PRIVATE_KEY + inputData.getLoginName();
            //String privateKey = jedisUtils.getString(privateKeyKey);
            String privateKey = GlobalVariable.PRIVATE_KEY;
            //end
                    String password = inputData.getPassword();
            //用私钥解密密码
            password = RSAUtil.privateDecrypt(password, privateKey);
            userBaseInfo.setPasswd(password);
            //验证签名
            String sign = basicInput.getSign();
            String checkSign = MD5Util.encrypt(password + "#" + code);
            if (sign == null || (!sign.equals(checkSign))) {
                //签名错误
                basicOutput.setCode(ErrorCodeEnum.TD7005.code());
                basicOutput.setMsg(ErrorCodeEnum.TD7005.msg());
            } else {
                userBaseInfo.setId((long)1);
                UserLogin userLogin = new UserLogin();
                userLogin.setUserId(userBaseInfo.getId());
                userLogin.setLoginName(inputData.getLoginName());
                userLogin.setLoginType(inputData.getLoginType());
                //注册时，随机生成默认昵称
                String defaultName = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
                userBaseInfo.setNickName(defaultName);
                //int n = userServiceImpl.insertUser(userBaseInfo, userLogin);
                int n = 1;
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
            LogUtil.error("用户注册异常", "ms-user", e);
            basicOutput.setCode(ErrorCodeEnum.TD9500.code());
            basicOutput.setMsg(ErrorCodeEnum.TD9500.msg());
        }
        return response;
    }
}
