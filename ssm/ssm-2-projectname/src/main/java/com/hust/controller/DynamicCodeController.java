package com.hust.controller;

import com.hust.constant.GlobalVariable;
import com.hust.constant.UserConstant;
import com.hust.entity.dto.DynamicCodeRedisDto;
import com.hust.entity.dto.GetDynamicCodeDto;
import com.hust.entity.vo.DynamicCodeVo;
import com.hust.service.UserService;
import com.hust.util.*;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
/*
/user/sms-code/no-token/get
{
	"basic": {
		"ver": "1.0",
		"time": 1592399555986,
		"id": 30,
		"nonce": 1567246549,
		"token": null,
	},
	"data": {
		"businessType": 15,
		"loginName": "admin",
		"loginType": 1,
		"idCode": "",
		"imgCode": "",
        "lang":""
	}
}


{
    "basic": {
        "id": "30",
        "time": 1592399555986,
        "code": 200,
        "msg": "operate successfully",
        "sign": "d19d689aae5377db54829eb6d9f620b1"
    },
    "data": {
        "idCode": "",
        "imgData": "",
        "publicKey": "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDX0SlB08yDcr4Ifz0dmvykLu9cnMyoxMVtX2oFfPIb5FJKIuEHQ/0lmsU38uqU/FoFu68Pk+rzGdZWQq3AK+v29jX9sYp/mzHczf0dPczBp2OowoYWEaWl481/UtH+6J3V849jA93X+y/HsBW5DB0gmkCJLRl41D8z1X7E6yBrWQIDAQAB"
    }
}
*/
/*
{
	"basic": {
		"id": "2",
		"time": "1592398739",
		"code": 200,
		"msg": "operate successfully"
	},
	"data": {
		"token": "eyJhbGciOiJITUFDU0hBMjU2IiwidmVyIjoiMS4wIn0=.eyJhdXRoIjpbInVzZXJfYWRkIiwidXNlcl91cGRhdGUiLCJ1c2VyX2RlbGV0ZSIsInVzZXJfbG9jayIsInVzZXJfdW5sb2NrIiwiY21kYl9xdWVyeSIsImNtZGJfb3BlcmF0ZSIsIm1vbml0b3JfcXVlcnkiLCJtb25pdG9yX29wZXJhdGUiLCJvcmRlcl9vcGVyYXRlIiwibWVzc2FnZV9hZGQiLCJtZXNzYWdlX3F1ZXJ5IiwibWVzc2FnZV9kZWxldGUiLCJzZWN1cml0eV9zdGFydCIsInNlY3VyaXR5X3Jlc3VsdCIsInNlY3VyaXR5X2luZm8iLCJzZWN1cml0eV9yZXBhaXIiLCJzbl9pbXBvcnQiLCJ1cGdyYWRlX3F1ZXJ5IiwidXBncmFkZV9vcGVyYXRlIiwib3BzIl0sImRjIjowLCJleHAiOjE1OTI0MDU5MzksImlhdCI6MTU5MjM5ODczOSwicm9sZSI6IjEiLCJ0aWQiOjc0MDI2ODI3NTI0MTMyMTI5NiwidHlwZSI6MiwidWlkIjoxfQ==.xW6Zq78f3yQI4wk3I/WG6WCoRWURZlH2gCHuddD3ALff5FOlYtRivNCauqoCSHq9Om7dGK0tgXyAHL5tk2JutRbhxxNmNY51B+s/S6Riaos//urDtrcH5wWzlNiIqyw7bbZgRm8FW+fc7Uyr7P2l9vvM4syp6QWNFcTOFtQt1Ks=",
		"sid": "0lwCo1+eErigemiKRIxfvSIphZXc7IR40BL9FparIdQ=",
		"idCode": null,
		"imgData": null
		}
}

{
    "basic": {
        "time": 1592401387,
        "code": 200,
        "msg": "operate successfully",
        "sign": "f06dd2ad0a03b2e97be78376cbf074dd"
    },
    "data": {
        "idCode": "",
        "imgData": "",
        "publicKey": "publicKey"
    }
}
 */
@RestController
public class DynamicCodeController {
    @Autowired
    private UserService userService;

    @Autowired
    private JedisUtils jedisUtils;

    //@Autowired
    //private RedissonClient redissonClient;

    @RequestMapping(value = "/hellocode", method = RequestMethod.POST)
    public int hello(){

        //RBucket<String> rBucket = redissonClient.getBucket("redisson");
        //rBucket.set("firstValue");

        int a = RSAUtil.hello();
        System.out.println("var=");
        try {
            Map<String, String> keyMap = RSAUtil.createKeys(1024);
        }catch (Exception e){
            System.out.println("e="+e);
        }
        return 2;
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
                //TDResponse<DynamicCodeVo> response = sendDynamicCode(tdRequest.getData(), null, rediskey, tdRequest.getBasic());
                DynamicCodeRedisDto dynamicCodeRedisDto = new DynamicCodeRedisDto();
                dynamicCodeRedisDto.setCode(DynamicCodeUtil.getDynamicCode());
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
                /////end

                if (response.getBasic().getCode() == ErrorCodeEnum.TD200.code()) {
                    try {
                        //生成私钥、公钥对
                        Map<String, String> keyMap = RSAUtil.createKeys(1024);
                        String publicKey = keyMap.get("publicKey");
                        String privateKey = keyMap.get("privateKey");
                        System.out.println("publicKey=" + publicKey);
                        System.out.println("privateKey=" + privateKey);
                        //将私钥，验证码放入缓存
                        //String codeKey = UserConstant.REDIS_REGISTER_CODE + getDynamicCodeDto.getLoginName() + UserConstant.REDIS_CODE;
                        DynamicCodeRedisDto dynamicCodeDto = (DynamicCodeRedisDto) jedisUtils.get(codeKey);
                        String dynamicCode = dynamicCodeDto.getCode();
                        //String dynamicCode = "888888";
                        //end

                        String keyKey = UserConstant.REDIS_REGISTER_PRIVATE_KEY + getDynamicCodeDto.getLoginName();
                        jedisUtils.setString(keyKey, privateKey, 5 * 60L);
                        //GlobalVariable.PRIVATE_KEY = privateKey;
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
                        //LogUtil.error("注册时生成私钥失败", "ms-user", e);
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
