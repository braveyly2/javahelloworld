package com.hust.controller;

import com.hust.constant.UserConstant;
import com.hust.entity.dto.DynamicCodeRedisDto;
import com.hust.entity.dto.GetDynamicCodeDto;
import com.hust.service.UserService;
import com.hust.util.DynamicCodeUtil;
import com.hust.util.ErrorCodeEnum;
import com.hust.util.JedisUtils;
import com.hust.accountcommon.util.PublicUtil;
import com.hust.accountcommon.util.ciper.MD5Util;
import com.hust.entity.vo.DynamicCodeVo;
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

import java.util.Map;

/**
 * @author lw
 * @Title: DynamicCodeController
 * @Description: 邮件和手机动态验证码controller类
 * @date 2020/9/5 19:42
 */
@RestController
@Slf4j
public class DynamicCodeController {
    @Autowired
    private UserService userService;

    @Autowired
    private JedisUtils jedisUtils;


    @RequestMapping(value = "/hellocode", method = RequestMethod.POST)
    public int hello(){
        int a = RSAUtil.hello();
        System.out.println("var=");
        try {
            Map<String, String> keyMap = RSAUtil.createKeys(1024);
        }catch (Exception e){
            System.out.println("e="+e);
        }
        return 2;
    }

    /**
     * 不同的业务获取邮箱或手机动态验证码：登录、注册、修改密码、找回密码、微信登录后绑定手机号、终端绑定
     *
     * @param tdRequest 邮箱名或手机号
     * @return TDResponse<DynamicCodeVo>  RSA公钥
     */
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
                break;
            case 13:
                //微信登录绑定账号获取验证码
                rediskey = UserConstant.REDIS_WX_BIND_DYNAMIC_CODE;
                break;
            case 14:
                //终端绑定验证
                //根据输入的登录名和验证类型去获取 对应的验证登录名
                break;
            case 15:
                //注册时获取验证码
                int loginType = getDynamicCodeDto.getLoginType();
                String name = getDynamicCodeDto.getLoginName();
                boolean isExist = false;
                if (loginType == UserConstant.LOGIN_TYPE_MOBILE) {
                    isExist = userService.checkMobileExist(name, basicOutput);
                    if(basicOutput.getCode() != ErrorCodeEnum.TD200.code()){
                        return tdResponse;
                    }
                    else if (isExist) {
                        basicOutput.setCode(ErrorCodeEnum.TD7006.code());
                        basicOutput.setMsg(ErrorCodeEnum.TD7006.msg());
                        return tdResponse;
                    }
                } else if (loginType == UserConstant.LOGIN_TYPE_EMAIL) {
                    isExist = userService.checkMobileExist(name,basicOutput);
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
                DynamicCodeRedisDto dynamicCodeRedisDto = new DynamicCodeRedisDto();
                String dynamicCodeInput = DynamicCodeUtil.getDynamicCode();
                System.out.println("dynamicCodeInput is " + dynamicCodeInput);
                dynamicCodeRedisDto.setCode(dynamicCodeInput);
                dynamicCodeRedisDto.setTarget(getDynamicCodeDto.getLoginName());
                dynamicCodeRedisDto.setFailCount(0);
                String codeKey = UserConstant.REDIS_REGISTER_CODE + getDynamicCodeDto.getLoginName() + UserConstant.REDIS_CODE;
                jedisUtils.set(codeKey, dynamicCodeRedisDto);

                TDResponse<DynamicCodeVo> response = new TDResponse<>();
                basicOutput.setCode(ErrorCodeEnum.TD200.code());
                basicOutput.setMsg(ErrorCodeEnum.TD200.msg());
                response.setBasic(basicOutput);
                DynamicCodeVo vo = new DynamicCodeVo();
                vo.setIdCode("");
                vo.setImgData("");
                response.setData(vo);

                if (response.getBasic().getCode() == ErrorCodeEnum.TD200.code()) {
                    try {
                        //生成私钥、公钥对
                        Map<String, String> keyMap = RSAUtil.createKeys(1024);
                        String publicKey = keyMap.get("publicKey");
                        String privateKey = keyMap.get("privateKey");
                        DynamicCodeRedisDto dynamicCodeDto = (DynamicCodeRedisDto) jedisUtils.get(codeKey);
                        String dynamicCode = dynamicCodeDto.getCode();

                        //将私钥放入缓存
                        String keyKey = UserConstant.REDIS_REGISTER_PRIVATE_KEY + getDynamicCodeDto.getLoginName();
                        jedisUtils.setString(keyKey, privateKey);

                        basicOutput.setCode(ErrorCodeEnum.TD200.code());
                        basicOutput.setMsg(ErrorCodeEnum.TD200.msg());
                        //生成签名
                        String sign = MD5Util.encrypt(publicKey + "#" + dynamicCode);
                        basicOutput.setSign(sign);
                        response.getData().setPublicKey(publicKey);
                        response.setBasic(basicOutput);
                        return response;
                    } catch (Exception e) {
                        log.error("注册时生成私钥失败", "ms-user", e);
                        System.out.println("e="+e);
                        basicOutput.setCode(ErrorCodeEnum.TD9500.code());
                        basicOutput.setMsg(ErrorCodeEnum.TD9500.msg());
                        response.setBasic(basicOutput);
                        return response;
                    }
                } else {
                    return response;
                }
            } else {
                return null;
            }
        } else {
            basicOutput.setCode(ErrorCodeEnum.TD4500.code());
            basicOutput.setMsg(ErrorCodeEnum.TD4500.msg());
            return tdResponse;
        }
    }
}
