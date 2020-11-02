package com.hust.controller;

import com.hust.accountcommon.entity.dto.TokenDataDto;
import com.hust.constant.UserConstant;
import com.hust.entity.dto.DynamicCodeRedisDto;
import com.hust.entity.dto.GetDynamicCodeDto;
import com.hust.entity.dto.PictureCodeDto;
import com.hust.service.CodeGenerateService;
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

import static com.hust.constant.UserConstant.REDIS_SEND;
import static com.hust.constant.UserConstant.REDIS_UPDATEPWD_PRIVATE_KEY;

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
    private CodeGenerateService codeGenerateService;

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
     * 在未登录时，不同的业务获取邮箱或手机动态验证码：登录、注册、修改密码、找回密码、微信登录后绑定手机号、终端绑定
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
        DynamicCodeVo vo = new DynamicCodeVo();
        tdResponse.setData(vo);
        //账号类型判断
        int loginType = getDynamicCodeDto.getLoginType();
        String name = getDynamicCodeDto.getLoginName();
        boolean isExist = false;
        boolean isAccountValid = true;
        if (loginType == UserConstant.LOGIN_TYPE_MOBILE) {
            if(!PublicUtil.isPhone(name)){
                isAccountValid = false;
            }
        } else if (loginType == UserConstant.LOGIN_TYPE_EMAIL) {
            if(!PublicUtil.isEmail(name)){
                isAccountValid = false;
            }
        }
        if(!isAccountValid){
            basicOutput.setCode(ErrorCodeEnum.TD1011.code());
            basicOutput.setMsg(ErrorCodeEnum.TD1011.msg());
            return tdResponse;
        }

        //账号是否存在判断
        isExist = userService.checkAccountExist(name, loginType);

        //图形验证码
        //1. 用户24小时内的SMS使用总条数，如果超过，则返回失败，不让使用了
        String sms24HourCountKey = UserConstant.REDIS_SMS_24HOUR_COUNT + name + REDIS_SEND;
        String sms24HourCount = null;
        if(jedisUtils.exists(sms24HourCountKey)){
            sms24HourCount = jedisUtils.getString(sms24HourCountKey);
            if(Integer.valueOf(sms24HourCount).intValue() > 20){
                basicOutput.setCode(ErrorCodeEnum.TD9500.code());
                basicOutput.setMsg(ErrorCodeEnum.TD9500.msg());
                return tdResponse;
            }
        }

        //2. 判断用户使用SMS的5分钟总条数，如果超过，则校验图形验证码，校验失败，则返回图形验证码，其中图形验证码有效期为1分钟
        String sms5MinCountKey = UserConstant.REDIS_SMS_5MINUTE_COUNT + name + REDIS_SEND;
        String sms5MinCount = null;
        if(jedisUtils.exists(sms5MinCountKey)){
            sms5MinCount = jedisUtils.getString(sms5MinCountKey);
            if(Integer.valueOf(sms5MinCount).intValue() > 10){
                //2.1 校验图形验证码
                log.info("待校验的图形验证码 idCode" + getDynamicCodeDto.getIdCode() + " imgCode" + getDynamicCodeDto.getImgCode());
                boolean checkResult = codeGenerateService.checkPictureCode(getDynamicCodeDto.getIdCode(),getDynamicCodeDto.getImgCode());
                if(!checkResult){
                    //2.2 失败，则继续返回图形验证码
                    PictureCodeDto pictureCodeDto = codeGenerateService.generatePictureCode();
                    tdResponse.getData().setIdCode(pictureCodeDto.getIdCode());
                    tdResponse.getData().setImgData(pictureCodeDto.getImgCodeImgData());
                    log.info("生成图形验证码 idCode" + pictureCodeDto.getIdCode() + " imgCode");
                    basicOutput.setCode(ErrorCodeEnum.TD1007.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD1007.msg());
                    return tdResponse;
                }
                //2.3 成功，走后面的流程，发送验证码
            }
        }

        switch (type) {
            case 11:
                //登录, 验证码直接登录
                rediskey = UserConstant.REDIS_LOGIN_DYNAMIC_CODE;
                break;
            case 12:
                //找回密码
                rediskey = UserConstant.REDIS_FINDPWD_DYNAMIC_CODE;
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
                rediskey = UserConstant.REDIS_REGISTER_CODE;
                break;
            default:
        }

        if (PublicUtil.isNotEmpty(rediskey)) {
            if (15 == type) {
                if(basicOutput.getCode() != ErrorCodeEnum.TD200.code()){
                    return tdResponse;
                }
                else if (isExist && loginType == UserConstant.LOGIN_TYPE_MOBILE) {
                    basicOutput.setCode(ErrorCodeEnum.TD7006.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD7006.msg());
                    return tdResponse;
                }
                else if (isExist && loginType == UserConstant.LOGIN_TYPE_EMAIL) {
                    basicOutput.setCode(ErrorCodeEnum.TD7023.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD7023.msg());
                    return tdResponse;
                }

                //注册发送成功后，生成publicKey
                DynamicCodeRedisDto dynamicCodeRedisDto = new DynamicCodeRedisDto();
                String dynamicCodeInput = DynamicCodeUtil.getDynamicCode();
                System.out.println("dynamicCodeInput is " + dynamicCodeInput);
                dynamicCodeRedisDto.setCode(dynamicCodeInput);
                dynamicCodeRedisDto.setTarget(getDynamicCodeDto.getLoginName());
                dynamicCodeRedisDto.setFailCount(0);
                String codeKey = UserConstant.REDIS_REGISTER_CODE + getDynamicCodeDto.getLoginName() + UserConstant.REDIS_CODE;
                jedisUtils.set(codeKey, dynamicCodeRedisDto);

                basicOutput.setCode(ErrorCodeEnum.TD200.code());
                basicOutput.setMsg(ErrorCodeEnum.TD200.msg());
                tdResponse.setBasic(basicOutput);
                vo.setIdCode("");
                vo.setImgData("");
                tdResponse.setData(vo);

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
                    tdResponse.getData().setPublicKey(publicKey);
                    tdResponse.setBasic(basicOutput);

                    if(sms24HourCount==null){
                        sms24HourCount = Integer.toString(1);
                    }else{
                        sms24HourCount = Integer.toString(Integer.valueOf(sms24HourCount).intValue() + 1);
                    }
                    jedisUtils.setString(sms24HourCountKey,sms24HourCount);
                    //return tdResponse;
                } catch (Exception e) {
                    log.error("注册时生成私钥失败", "ms-user", e);
                    System.out.println("e="+e);
                    basicOutput.setCode(ErrorCodeEnum.TD9500.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD9500.msg());
                    tdResponse.setBasic(basicOutput);
                    return tdResponse;
                }
            }
            else if(11 == type){
                DynamicCodeRedisDto dynamicCodeRedisDto = new DynamicCodeRedisDto();
                String dynamicCodeInput = DynamicCodeUtil.getDynamicCode();
                System.out.println("dynamicCodeInput is " + dynamicCodeInput);
                dynamicCodeRedisDto.setCode(dynamicCodeInput);
                dynamicCodeRedisDto.setTarget(getDynamicCodeDto.getLoginName());
                dynamicCodeRedisDto.setFailCount(0);
                String codeKey = UserConstant.REDIS_LOGIN_DYNAMIC_CODE + getDynamicCodeDto.getLoginName() + UserConstant.REDIS_CODE;
                jedisUtils.set(codeKey, dynamicCodeRedisDto);

                basicOutput.setCode(ErrorCodeEnum.TD200.code());
                basicOutput.setMsg(ErrorCodeEnum.TD200.msg());
                tdResponse.setBasic(basicOutput);
                vo.setIdCode("");
                vo.setImgData("");
                tdResponse.setData(vo);

                //return tdResponse;
            }
            else if(12 == type){
                if(!isExist){
                    basicOutput.setCode(ErrorCodeEnum.TD7001.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD7001.msg());
                    return tdResponse;
                }

                DynamicCodeRedisDto dynamicCodeRedisDto = new DynamicCodeRedisDto();
                String dynamicCodeInput = DynamicCodeUtil.getDynamicCode();
                System.out.println("dynamicCodeInput is " + dynamicCodeInput);
                dynamicCodeRedisDto.setCode(dynamicCodeInput);
                dynamicCodeRedisDto.setTarget(getDynamicCodeDto.getLoginName());
                dynamicCodeRedisDto.setFailCount(0);
                String codeKey = UserConstant.REDIS_FINDPWD_DYNAMIC_CODE + getDynamicCodeDto.getLoginName() + UserConstant.REDIS_CODE;
                jedisUtils.set(codeKey, dynamicCodeRedisDto);

                basicOutput.setCode(ErrorCodeEnum.TD200.code());
                basicOutput.setMsg(ErrorCodeEnum.TD200.msg());
                tdResponse.setBasic(basicOutput);
                tdResponse.setData(vo);

                try{
                    //生成私钥、公钥对
                    Map<String, String> keyMap = RSAUtil.createKeys(1024);
                    String publicKey = keyMap.get("publicKey");
                    String privateKey = keyMap.get("privateKey");
                    DynamicCodeRedisDto dynamicCodeDto = (DynamicCodeRedisDto) jedisUtils.get(codeKey);
                    String dynamicCode = dynamicCodeDto.getCode();

                    //将私钥放入缓存
                    String keyKey = UserConstant.REDIS_FINDPWD_PRIVATE_KEY + getDynamicCodeDto.getLoginName();
                    jedisUtils.setString(keyKey, privateKey);

                    basicOutput.setCode(ErrorCodeEnum.TD200.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD200.msg());
                    //生成签名
                    String sign = MD5Util.encrypt(publicKey + "#" + dynamicCode);
                    basicOutput.setSign(sign);
                    tdResponse.getData().setPublicKey(publicKey);
                    tdResponse.setBasic(basicOutput);
                    //return tdResponse;
                } catch (Exception e) {
                    log.error("找回密码时生成私钥失败", "ms-user", e);
                    System.out.println("e="+e);
                    basicOutput.setCode(ErrorCodeEnum.TD9500.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD9500.msg());
                    tdResponse.setBasic(basicOutput);
                    return tdResponse;
                }
            }
            else {
                return null;
            }
        } else {
            basicOutput.setCode(ErrorCodeEnum.TD4500.code());
            basicOutput.setMsg(ErrorCodeEnum.TD4500.msg());
            return tdResponse;
        }

        //更新用户24小时总的发送短信数量
        if(!jedisUtils.exists(sms24HourCountKey)){
            sms24HourCount = Integer.toString(1);
            jedisUtils.setString(sms24HourCountKey,sms24HourCount, 2*60L);
        }else{
            sms24HourCount = Integer.toString(Integer.valueOf(sms24HourCount).intValue() + 1);
        }
        jedisUtils.setString(sms24HourCountKey,sms24HourCount );

        //更新用户5分钟总的发送短信数量
        if(!jedisUtils.exists(sms5MinCountKey)){
            sms5MinCount = Integer.toString(1);
            jedisUtils.setString(sms5MinCountKey,sms5MinCount, 60L);
        }else{
            sms5MinCount = Integer.toString(Integer.valueOf(sms5MinCount).intValue() + 1);
        }
        jedisUtils.setString(sms5MinCountKey,sms5MinCount);

        return tdResponse;

    }

    /**
     * 在登录时，不同的业务获取邮箱或手机动态验证码：修改密码，更换账户信息
     *
     * @param tdRequest 邮箱名或手机号
     * @return TDResponse<DynamicCodeVo>
     */
    @RequestMapping(value = "/user/sms-code/get", method = RequestMethod.POST)
    public TDResponse<DynamicCodeVo> getTokenDynamicCode(@RequestBody TDRequest<GetDynamicCodeDto> tdRequest) {
        GetDynamicCodeDto getDynamicCodeDto = tdRequest.getData();
        TokenDataDto tokenDataDto = tdRequest.getTokenDataDto();
        int businessType = getDynamicCodeDto.getBusinessType();
        String loginName = getDynamicCodeDto.getLoginName();
        String redisKey = null;
        TDResponse<DynamicCodeVo> tdResponse = new TDResponse<>();
        tdResponse.setBasic(PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic()));
        DynamicCodeVo dynamicCodeVo = new DynamicCodeVo();
        tdResponse.setData(dynamicCodeVo);
        //1. 根据businesstype设置redisKey
        switch(businessType){
            case 16:
                //登录后修改密码
                redisKey = REDIS_UPDATEPWD_PRIVATE_KEY;
                break;
            case 17:
                //登录后更换账户
                //redisKey =
                break;
            default:
                break;
        }

        if(16 == businessType){
            //2. 生成验证码并存储到redis中
            String codeKey = UserConstant.REDIS_UPDATEPWD_DYNAMIC_CODE + loginName + UserConstant.REDIS_CODE;
            String priKeyKey = UserConstant.REDIS_UPDATEPWD_PRIVATE_KEY + loginName;
            DynamicCodeRedisDto dynamicCodeRedisDto = new DynamicCodeRedisDto();
            String dynamicCodeInput = DynamicCodeUtil.getDynamicCode();
            System.out.println("dynamicCodeInput is " + dynamicCodeInput);
            dynamicCodeRedisDto.setCode(dynamicCodeInput);
            dynamicCodeRedisDto.setTarget(getDynamicCodeDto.getLoginName());
            dynamicCodeRedisDto.setFailCount(0);
            jedisUtils.set(codeKey,dynamicCodeRedisDto);
            //3. 生成SA私钥并存储到redis中
            try{
            Map<String, String> keyMap = RSAUtil.createKeys(1024);
            String publicKey = keyMap.get("publicKey");
            String privateKey = keyMap.get("privateKey");
            jedisUtils.setString(priKeyKey,privateKey);
            tdResponse.getData().setPublicKey(publicKey);
            }catch(Exception e){
                log.error("生成rsa公私钥失败");
                tdResponse.getBasic().setCode(ErrorCodeEnum.TD9500.code());
                tdResponse.getBasic().setMsg(ErrorCodeEnum.TD9500.msg());
                return tdResponse;
            }
            //4. 返回RSA公钥
            return tdResponse;
        }

        return null;
    }
}
