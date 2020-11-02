package com.hust.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hust.accountcommon.entity.dto.TokenResultDto;
import com.hust.accountcommon.util.IdWorker;
import com.hust.accountcommon.util.PublicUtil;
import com.hust.accountcommon.util.token.TokenUtil;
import com.hust.constant.ErrorCodeEnum;
import com.hust.constant.UserConstant;
import com.hust.dao.UserMapper;
import com.hust.entity.domain.User;
import com.hust.entity.domain.WeixinUserInfo;
import com.hust.entity.dto.DynamicCodeRedisDto;
import com.hust.entity.dto.LoginResultDto;
import com.hust.service.VxUserService;
import com.hust.util.HttpUtil;
import com.hust.util.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class VxUserServiceImpl implements VxUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    JedisUtils jedisUtils;

    @Override
    public LoginResultDto vxLogin(String code){
        String appId = "appid";
        String appSecret = "appsecret";
        LoginResultDto loginResultDto = new LoginResultDto();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code";
        try {
            /*
            String response = HttpUtil.sendGet(url);
            Map<String, Object> result = new ObjectMapper().readValue(response, Map.class);
            if (PublicUtil.isNotEmpty(result.get("errcode"))) {
                log.error("获取access_token失败,错误为：" + result.get("errmsg").toString(), "ms-user");
                loginResultDto.setResultCode(ErrorCodeEnum.TD9500);
                return loginResultDto;
            }
            String accessToken = result.get("access_token").toString();
            String openid = result.get("openid").toString();
            //通过access_token调用接口,获取用户信息
            String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openid;
            String resUserInfo = HttpUtil.sendGet(getUserInfoUrl);
            if (StringUtils.contains(resUserInfo, "errcode")) {
                log.error("获取微信用户信息失败,返回字符串为：" + resUserInfo, "ms-user");
                loginResultDto.setResultCode(ErrorCodeEnum.TD9500);
                return loginResultDto;
            }
            WeixinUserInfo weixinUserInfo = JSON.parseObject(resUserInfo, WeixinUserInfo.class);
            */
            //微信登录打桩
            WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
            weixinUserInfo.setNickname("lwnickname");
            weixinUserInfo.setOpenid("123oi");
            weixinUserInfo.setUnionid("123ui");
            //1.判断微信号是否能匹配上账号
            User user = userMapper.selectByVxId(weixinUserInfo.getUnionid());
            //1.1 未匹配上
            if(PublicUtil.isEmpty(user)){
                loginResultDto.setResultCode(ErrorCodeEnum.TD7011);
                user = new User();
                user.setId(IdWorker.getInstance().getId());
                user.setVxId(weixinUserInfo.getUnionid());
                user.setVxNickname(weixinUserInfo.getNickname());
                userMapper.insert(user);
                jedisUtils.set(UserConstant.REDIS_CODE+code, user.getId(),60*5L);
                return loginResultDto;
            }
            else if((user.getPhone()==null || user.getPhone()=="")&&
                    (user.getEmail()==null || user.getEmail()=="")){
                loginResultDto.setResultCode(ErrorCodeEnum.TD7011);//微信登录后，用户不进行账户绑定，则有这样的记录
                user.setVxId(weixinUserInfo.getUnionid());
                user.setVxNickname(weixinUserInfo.getNickname());
                userMapper.updateByPrimaryKey(user);
                jedisUtils.set(UserConstant.REDIS_CODE+code, user.getId(),60*5L);
                return loginResultDto;
            }

            //1.2 匹配上了
            TokenResultDto tokenResultDto = tokenUtil.createToken(user.getId(), user.getPassword(), "web", "","customer",null);
            loginResultDto.setToken(tokenResultDto.getToken());
            loginResultDto.setRefreshToken(tokenResultDto.getRefreshToken());
            loginResultDto.setSid(tokenResultDto.getSid());
            loginResultDto.setTid(tokenResultDto.getTokenId());
            loginResultDto.setRefreshTokenId(tokenResultDto.getRefreshTokenId());
            loginResultDto.setUserId((long)user.getId());

            return loginResultDto;

        }
        catch(Exception e){
            log.error("请求微信用code换access_token失败");
        }
        return null;
    }

    @Override
    public LoginResultDto vxBindAccount(String code, String loginName, String dynCode){
        LoginResultDto loginResultDto = new LoginResultDto();

        //1.判断用户在微信登录后提示其绑定手机号/邮箱，此操作是否有超过5分钟
        if(!jedisUtils.exists(UserConstant.REDIS_CODE+code)){
            loginResultDto.setResultCode(ErrorCodeEnum.TD9500);
            log.error("用户在微信登录后提示绑定账户，此操作超时");
            return loginResultDto;
        }
        //2.判断绑定的账户是否输入正确的验证码
        DynamicCodeRedisDto dynamicCodeRedisDto = (DynamicCodeRedisDto)jedisUtils.get(UserConstant.REDIS_LOGIN_DYNAMIC_CODE+loginName+UserConstant.REDIS_CODE);
        if(!dynCode.equals(dynamicCodeRedisDto.getCode())){
            loginResultDto.setResultCode(ErrorCodeEnum.TD9500);
            log.error("用户在微信登录后绑定账户输入验证码有误");
            return loginResultDto;
        }
        //3. 判断此loginname在user表中是否存在相应的记录，也就是是否注册过了的
        User user = null;
        int loginType = 0;
        if(PublicUtil.isEmail(loginName)){
            user = userMapper.selectByEmail(loginName);
            loginType = UserConstant.LOGIN_TYPE_EMAIL;
        }
        else if(PublicUtil.isPhone(loginName)){
            user = userMapper.selectByPhone(loginName);
            loginType = UserConstant.LOGIN_TYPE_MOBILE;
        }

        //3.1 绑定的账号不存在
        String userId = jedisUtils.getString(UserConstant.REDIS_CODE+code);
        User vxUser = userMapper.selectByPrimaryKey(Long.parseLong(userId));
        if(PublicUtil.isEmpty(user)){
            user = userMapper.selectByPrimaryKey(Long.parseLong(userId));
            if(UserConstant.LOGIN_TYPE_EMAIL == loginType){
                user.setEmail(loginName);
            }
            if(UserConstant.LOGIN_TYPE_MOBILE == loginType){
                user.setPhone(loginName);
            }
            userMapper.updateByPrimaryKey(user);
        } else{
        //3.2 绑定的账号存在
            //3.2.1 同步wx_unionid
            user.setVxId(vxUser.getVxId());
            user.setVxNickname(vxUser.getVxNickname());
            userMapper.updateByPrimaryKeySelective(user);
            //3.2.2 删除wx账号记录
            userMapper.deleteByPrimaryKey(Long.parseLong(userId));
            userId = String.valueOf(user.getId());
        }

        //4.上面通过了， 则生成token
        TokenResultDto tokenResultDto = tokenUtil.createToken(user.getId(), dynCode, "web", "","customer",null);
        loginResultDto.setToken(tokenResultDto.getToken());
        loginResultDto.setRefreshToken(tokenResultDto.getRefreshToken());
        loginResultDto.setSid(tokenResultDto.getSid());
        loginResultDto.setTid(tokenResultDto.getTokenId());
        loginResultDto.setRefreshTokenId(tokenResultDto.getRefreshTokenId());
        loginResultDto.setUserId((long)user.getId());
        loginResultDto.setResultCode(ErrorCodeEnum.TD200);
        return loginResultDto;
    }

    public int accountBindVx(String code, long userId){
        WeixinUserInfo weixinUserInfo = null;
        {
            String appId = "appid";
            String appSecret = "appsecret";
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code";
            try {
                String response = HttpUtil.sendGet(url);
                Map<String, Object> result = new ObjectMapper().readValue(response, Map.class);
                if (PublicUtil.isNotEmpty(result.get("errcode"))) {
                    log.error("获取access_token失败,错误为：" + result.get("errmsg").toString(), "ms-user");
                    return ErrorCodeEnum.TD9500.code();
                }
                String accessToken = result.get("access_token").toString();
                String openid = result.get("openid").toString();
                //通过access_token调用接口,获取用户信息
                String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openid;
                String resUserInfo = HttpUtil.sendGet(getUserInfoUrl);
                if (StringUtils.contains(resUserInfo, "errcode")) {
                    log.error("获取微信用户信息失败,返回字符串为：" + resUserInfo, "ms-user");
                    return ErrorCodeEnum.TD9500.code();
                }
                weixinUserInfo = JSON.parseObject(resUserInfo, WeixinUserInfo.class);
            } catch (Exception e) {
                log.error("向微信服务器获取用户信息失败");
                return ErrorCodeEnum.TD9500.code();
            }
        }
        User vxUser = userMapper.selectByVxId(weixinUserInfo.getUnionid());
        User user = userMapper.selectByPrimaryKey(userId);
        //1. wx unionid在表中不存在相应的记录
        if(PublicUtil.isEmpty(vxUser)){
            user.setVxId(weixinUserInfo.getUnionid());
            user.setVxNickname(weixinUserInfo.getNickname());
            userMapper.updateByPrimaryKeySelective(user);
        //2. 存在记录但是phone和email为空
        }else if((vxUser.getPhone()==null || vxUser.getPhone()=="")&&
                 (vxUser.getEmail()==null || vxUser.getEmail()=="")){
            user.setVxId(weixinUserInfo.getUnionid());
            user.setVxNickname(weixinUserInfo.getNickname());
            userMapper.updateByPrimaryKeySelective(user);
            userMapper.deleteByPrimaryKey(vxUser.getId());
        }else{
        //3. 存在记录但是phone或email不为空
            log.error("此微信已被绑定，不支持此账号绑定");
            return ErrorCodeEnum.TD1014.code();
        }

        return ErrorCodeEnum.TD200.code();
    }

    public int accountUnBindVx(long userId){
        User user = userMapper.selectByPrimaryKey(userId);
        user.setVxId("");
        user.setVxNickname("");
        int n = userMapper.updateByPrimaryKeySelective(user);
        if(n<1){
            return ErrorCodeEnum.TD7011.code();
        }else{
            return ErrorCodeEnum.TD200.code();
        }
    }
}
