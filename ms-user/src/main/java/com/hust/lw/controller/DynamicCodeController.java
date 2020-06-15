package com.hust.lw.controller;

import com.hust.lw.constant.GlobalVariable;
import com.hust.lw.constant.UserConstant;
import com.hust.lw.model.domain.User;
import com.hust.lw.model.dto.GetDynamicCodeDto;
import com.hust.lw.model.vo.DynamicCodeVo;
import com.hust.lw.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DynamicCodeController {
    @Autowired
    //private UserService userService;
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public int hello(){
        System.out.println("var=");
        return 1;
    }

    @RequestMapping(value = "/user/sms-code/no-token/get", method = RequestMethod.POST)
    public TDResponse<DynamicCodeVo> getNoTokenDynamicCode(@RequestBody TDRequest<GetDynamicCodeDto> tdRequest) {
        GetDynamicCodeDto getDynamicCodeDto = tdRequest.getData();
        int type = getDynamicCodeDto.getBusinessType();
        String rediskey = "";
        TDResponse<DynamicCodeVo> tdResponse = new TDResponse<>();
        BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic());
        tdResponse.setBasic(basicOutput);
        switch (type) {
            case 11:
                //登录
                rediskey = UserConstant.REDIS_LOGIN_DYNAMIC_CODE;
                break;
            case 12:
                //找回密码
                /*
                String loginName = userLoginService.getRealLoginName(getDynamicCodeDto.getLoginName(), getDynamicCodeDto.getLoginType());
                if (PublicUtil.isNotEmpty(loginName)) {
                    rediskey = UserConstant.REDIS_FINDPWD_DYNAMIC_CODE;
                } else {
                    basicOutput.setCode(ErrorCodeEnum.TD7001.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD7001.msg());
                    return tdResponse;
                }
                */

                break;
            case 13:
                //微信登录绑定账号获取验证码
                rediskey = UserConstant.REDIS_WX_BIND_DYNAMIC_CODE;
                break;
            case 14:
                //终端绑定验证
                //根据输入的登录名和验证类型去获取 对应的验证登录名
                /*
                String loginUserName = userLoginService.getRealLoginName(getDynamicCodeDto.getLoginName(), getDynamicCodeDto.getLoginType());
                if (PublicUtil.isNotEmpty(loginUserName)) {
                    getDynamicCodeDto.setLoginName(loginUserName);
                    rediskey = UserConstant.REDIS_TERMINAL_BIND_DYNAMIC_CODE_AUTO;
                }
                */

                break;
            case 15:
                //注册时获取验证码
                int loginType = getDynamicCodeDto.getLoginType();
                String name = getDynamicCodeDto.getLoginName();
                boolean isExist = false;
                if (loginType == UserConstant.LOGIN_TYPE_MOBILE) {
                    //isExist = userService.checkMobileExist(name, basicOutput);
                    if(basicOutput.getCode() != ErrorCodeEnum.TD200.code()){
                        return tdResponse;
                    }
                    else if (isExist) {
                        basicOutput.setCode(ErrorCodeEnum.TD7006.code());
                        basicOutput.setMsg(ErrorCodeEnum.TD7006.msg());
                        return tdResponse;
                    }
                } else if (loginType == UserConstant.LOGIN_TYPE_EMAIL) {
                    //isExist = userService.checkEmailExist(name);
                    if (isExist) {
                        basicOutput.setCode(ErrorCodeEnum.TD7023.code());
                        basicOutput.setMsg(ErrorCodeEnum.TD7023.msg());
                        return tdResponse;
                    }
                }
                rediskey = UserConstant.REDIS_REGISTER_CODE;
                break;
            default:
        }
        if (PublicUtil.isNotEmpty(rediskey)) {
            if (15 == type) {
                //注册发送成功后，生成publicKey
                //TDResponse<DynamicCodeVo> response = sendDynamicCode(tdRequest.getData(), null, rediskey, tdRequest.getBasic());
                TDResponse<DynamicCodeVo> response = new TDResponse<>();
                basicOutput.setCode(ErrorCodeEnum.TD200.code());
                basicOutput.setMsg(ErrorCodeEnum.TD200.msg());
                response.setBasic(basicOutput);
                DynamicCodeVo vo = new DynamicCodeVo();
                vo.setIdCode("");
                vo.setImgData("");
                tdResponse.setData(vo);
                /////end

                if (response.getBasic().getCode() == ErrorCodeEnum.TD200.code()) {
                    try {
                        //生成私钥、公钥对
                        Map<String, String> keyMap = RSAUtil.createKeys(1024);
                        String publicKey = keyMap.get("publicKey");
                        String privateKey = keyMap.get("privateKey");
                        //将私钥，验证码放入缓存
                        //String codeKey = UserConstant.REDIS_REGISTER_CODE + getDynamicCodeDto.getLoginName() + UserConstant.REDIS_CODE;
                        //DynamicCodeRedisDto dynamicCodeDto = (DynamicCodeRedisDto) jedisUtils.get(codeKey);
                        //String dynamicCode = dynamicCodeDto.getCode();
                        String dynamicCode = "888888";
                        //end

                        //String keyKey = UserConstant.REDIS_REGISTER_PRIVATE_KEY + getDynamicCodeDto.getLoginName();
                        //jedisUtils.setString(keyKey, privateKey, 5 * 60L);
                        GlobalVariable.PRIVATE_KEY = privateKey;
                        //end

                        basicOutput.setCode(ErrorCodeEnum.TD200.code());
                        basicOutput.setMsg(ErrorCodeEnum.TD200.msg());
                        //生成签名
                        String sign = MD5Util.encrypt(publicKey + "#" + dynamicCode);
                        basicOutput.setSign(sign);
                        response.getData().setPublicKey(publicKey);
                        response.setBasic(basicOutput);
                        return response;
                    } catch (Exception e) {
                        LogUtil.error("注册时生成私钥失败", "ms-user", e);
                        basicOutput.setCode(ErrorCodeEnum.TD9500.code());
                        basicOutput.setMsg(ErrorCodeEnum.TD9500.msg());
                        response.setBasic(basicOutput);
                        return response;
                    }
                } else {
                    return response;
                }
            } else {
                //return sendDynamicCode(tdRequest.getData(), null, rediskey, tdRequest.getBasic());
                return null;
            }
        } else {
            basicOutput.setCode(ErrorCodeEnum.TD4500.code());
            basicOutput.setMsg(ErrorCodeEnum.TD4500.msg());
            return tdResponse;
        }
    }
}
