package com.hust.controller;

import com.hust.accountcommon.entity.dto.TokenResultDto;
import com.hust.accountcommon.entity.dto.TokenDataDto;
import com.hust.constant.UserConstant;
import com.hust.entity.domain.User;
import com.hust.entity.dto.*;
import com.hust.service.UserService;
import com.hust.util.ErrorCodeEnum;
import com.hust.util.JedisUtils;
//import com.hust.accountcommon.util.LogUtil;
import com.hust.accountcommon.constant.GlobalConstant;
import com.hust.entity.RestBean;
import com.hust.accountcommon.util.PublicUtil;
import com.hust.entity.vo.LoginVo;
import com.hust.accountcommon.util.apitemplate.BasicOutput;
import com.hust.accountcommon.util.apitemplate.TDRequest;
import com.hust.accountcommon.util.apitemplate.TDResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.hust.constant.UserConstant.LOGIN_TYPE_EMAIL;
import static com.hust.constant.UserConstant.LOGIN_TYPE_MOBILE;

/**
 * @author lw
 * @Title: UserController
 * @Description: 用户登录/获取用户信息/刷新token的controller
 * @date 2020/9/5 19:42
 */
@Controller
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    JedisUtils jedisUtils;

    @RequestMapping(value="hello",method=RequestMethod.POST)
    @ResponseBody
    Map<String, Object> hello(@RequestBody User user){
        System.out.println("this is Hello value:");
        System.out.println(jedisUtils.getString("Hello"));
        Map<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("status","success");
        hashmap.put("code",200);
        hashmap.put("user",user);
        return hashmap;
    }

    @RequestMapping(value="hello2",method=RequestMethod.POST)
    @ResponseBody
    RestBean hello2(@RequestBody User user){
        RestBean restBean = RestBean.ok("this is ok");
        restBean.setObj((Object)user);
        return restBean;
    }

    @RequestMapping(value="/user/register1",method=RequestMethod.POST)
    @ResponseBody
    public String register(@RequestParam("userName") String name, @RequestParam("password") String password){
        User user = userService.getUserInfoByAccount(name,1);
        //cannot register for the same username
        if(null != user){
            return "fail";
        }

        User userAdd = new User();
        userAdd.setPhone(name);
        userAdd.setPassword(password);
        userAdd.setMark("thisisforregister");

        userService.insert(userAdd);
        return "ok";
    }

    @RequestMapping(value="/user/register2",method=RequestMethod.POST)
    @ResponseBody
    public String register2(@RequestBody User user){
        User userExist = userService.getUserInfoByAccount(user.getPhone(),1);
        //cannot register for the same username
        if(null != userExist){
            return "fail";
        }

        User userAdd = new User();
        userAdd.setId(user.getId());
        userAdd.setPhone(user.getPhone());
        userAdd.setPassword(user.getPassword());
        userAdd.setMark(user.getMark());

        userService.insert(userAdd);
        return "ok";
    }


    @RequestMapping(value="/user/register3",method=RequestMethod.POST)
    @ResponseBody
    public RestBean register3(@RequestBody User user){
        User userExist = userService.getUserInfoByAccount(user.getPhone(),1);
        //cannot register for the same username
        if(null != userExist){
            return RestBean.error("user:" + user.getPhone() + " is exist");
        }

        User userAdd = new User();
        userAdd.setId(user.getId());
        userAdd.setPhone(user.getPhone());
        userAdd.setPassword(user.getPassword());
        userAdd.setMark(user.getMark());

        userService.insert(userAdd);
        // model.addAttribute("status","0");
        return RestBean.ok("Succeed to add user",userAdd);
    }

    /**
     * 使用邮箱或手机号进行登录
     *
     * @param tdRequest 邮箱名或手机号
     * @return TDResponse<DynamicCodeVo>  RSA公钥
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public TDResponse<LoginVo> login(@RequestBody TDRequest<LoginDto> tdRequest, HttpServletRequest request) {
        TDResponse<LoginVo> tdResponse = new TDResponse<>();
        BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic());
        try {
            String clientType = "web";
            boolean isCheckImgCode = false;
            LoginDto loginDto = tdRequest.getData();
            int loginType = 0;
            if(PublicUtil.isEmail(loginDto.getUserName())){
                loginType = LOGIN_TYPE_EMAIL;
            }
            else if(PublicUtil.isPhone(loginDto.getUserName())){
                loginType = LOGIN_TYPE_MOBILE;
            }
            else{
                basicOutput.setCode(ErrorCodeEnum.TD1011.code());
                basicOutput.setMsg(ErrorCodeEnum.TD1011.msg());
                tdResponse.setBasic(basicOutput);
                return tdResponse;
            }

            log.info(String.format("收到登录信息: UserName: %s, ClientType: %s", tdRequest.getData().getUserName(), clientType), "Login");
            LoginResultDto dto = userService.login(tdRequest, clientType, loginType, true, isCheckImgCode);
            LoginVo vo = new LoginVo();
            vo.setToken(dto.getToken());
            vo.setRefreshToken(dto.getRefreshToken());
            vo.setSid(dto.getSid());
            vo.setIdCode(dto.getIdCode());
            vo.setImgData(dto.getImgData());
            vo.setP2pId(dto.getTid() == null ? "" : dto.getTid().toString());
            if (PublicUtil.isNotEmpty(dto.getResultCode())) {
                basicOutput.setCode(dto.getResultCode().code());
                basicOutput.setMsg(dto.getResultCode().msg());
            } else {
                //写入操作日志
                //OpLog opLog = new OpLog();
                //opLog.setIp(PublicUtil.getRemoteIp(request));
                //opLogUtil.logBus(dto.getUserId(), dto.getTid(), OpLogUtil.login, opLog);
            }
            tdResponse.setBasic(basicOutput);
            tdResponse.setData(vo);
        } catch (Exception ex) {
            log.error("登录出错" + ex.getMessage(), "Login", ex);
            basicOutput.setCode(ErrorCodeEnum.TD9500.code());
            basicOutput.setMsg(ex.getMessage());
        }
        return tdResponse;
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param  tdRequest 邮箱名或手机号
     * @return TDResponse<IdDto>  用户id和名称
     */
    @RequestMapping(value = "/user/info", method = RequestMethod.POST)
    @ResponseBody
    //@TDResult
    public TDResponse<IdDto> getUserInfoByName(@RequestBody TDRequest<GetUserInfoByNameDto> tdRequest) {
        TDResponse<IdDto> tdResponse = new TDResponse<>();
        BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic());
        try {
            User user = userService.getUserInfoByAccount(tdRequest.getData().getUserName(),1);
            if (PublicUtil.isNotEmpty(user)) {
                IdDto dto = new IdDto();
                dto.setId((long)user.getId());
                dto.setName(user.getPhone());
                dto.setMark(user.getMark());
                tdResponse.setData(dto);
            } else {
                basicOutput.setCode(ErrorCodeEnum.TD7001.code());
                basicOutput.setMsg(ErrorCodeEnum.TD7001.msg());
                log.error("用户信息为空", "ms-user");
            }
        } catch (Exception e) {
            log.error("获取用户信息异常", "ms-user", e);
            basicOutput.setCode(ErrorCodeEnum.TD9500.code());
            basicOutput.setMsg(ErrorCodeEnum.TD9500.msg());
        }
        tdResponse.setBasic(basicOutput);
        return tdResponse;
    }

    /**
     * 刷新token
     *
     * @param  tdRequest 头部含有token信息
     * @return TDResponse<TokenResultDto>  返回新的AT和RT
     */
    @RequestMapping(value = "/user/token/refresh", method = RequestMethod.POST)
    @ResponseBody
    public TDResponse<TokenResultDto> refeshToken(@RequestBody TDRequest tdRequest, HttpServletRequest request) {
        TDResponse<TokenResultDto> tdResponse = new TDResponse<>();
        BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic());
        String clientType = request.getHeader(GlobalConstant.ZUUL_HEADER_CLIENTTYPE);
        TokenResultDto tokenResultDto = null;
        try {
            TokenDataDto tokenDataDto = tdRequest.getTokenDataDto();
            User user = userService.selectByPrimaryKey(tokenDataDto.getUserId());

            tokenResultDto = userService.createToken(user.getId(), user.getPassword(), clientType, null,"customer",null);

            if (PublicUtil.isEmpty(tokenResultDto.getToken())) {
                basicOutput.setCode(ErrorCodeEnum.TD7004.code());
                basicOutput.setMsg(ErrorCodeEnum.TD7004.msg());
                tdResponse.setBasic(basicOutput);
                return tdResponse;
            }
        } catch (Exception e) {
            basicOutput.setCode(ErrorCodeEnum.TD9500.code());
            basicOutput.setMsg(ErrorCodeEnum.TD9500.msg());
            log.error("token create异常", "user", e);
        }
        tdResponse.setBasic(basicOutput);
        tdResponse.setData(tokenResultDto);
        return tdResponse;
    }

    /**
     * 修改密码
     *
     * @param  tdRequest 头部含有token信息
     * @return TDResponse<TokenResultDto>  返回新的AT和RT
     */
    @RequestMapping(value = "/user/password/update", method = RequestMethod.POST)
    @ResponseBody
    public TDResponse<TokenResultDto> updatePwd(@RequestBody TDRequest tdRequest, HttpServletRequest request) {
        TDResponse<TokenResultDto> tdResponse = new TDResponse<>();
        BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic());
        String clientType = request.getHeader(GlobalConstant.ZUUL_HEADER_CLIENTTYPE);
        TokenResultDto tokenResultDto = null;
        try {
            TokenDataDto tokenDataDto = tdRequest.getTokenDataDto();
            User user = userService.selectByPrimaryKey(tokenDataDto.getUserId());

            tokenResultDto = userService.createToken(user.getId(), user.getPassword(), clientType, null,"customer",null);

            if (PublicUtil.isEmpty(tokenResultDto.getToken())) {
                basicOutput.setCode(ErrorCodeEnum.TD7004.code());
                basicOutput.setMsg(ErrorCodeEnum.TD7004.msg());
                tdResponse.setBasic(basicOutput);
                return tdResponse;
            }
        } catch (Exception e) {
            basicOutput.setCode(ErrorCodeEnum.TD9500.code());
            basicOutput.setMsg(ErrorCodeEnum.TD9500.msg());
            log.error("token create异常", "user", e);
        }
        tdResponse.setBasic(basicOutput);
        tdResponse.setData(tokenResultDto);
        return tdResponse;
    }

    /**
     * 找回密码
     *
     * @param  tdRequest 头部含有token信息
     * @return TDResponse<TokenResultDto>  返回新的AT和RT
     */
    @RequestMapping(value = "/user/password/find", method = RequestMethod.POST)
    @ResponseBody
    public TDResponse findPwd(@RequestBody TDRequest<FindLoginPwdDto> tdRequest) {
        TDResponse tdResponse = new TDResponse<>();
        BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic());
        tdResponse.setBasic(basicOutput);

        FindLoginPwdDto findLoginPwdDto = tdRequest.getData();
        String loginName = findLoginPwdDto.getLoginName();
        int loginType = 0;
        if(PublicUtil.isEmail(loginName)){
            loginType = LOGIN_TYPE_EMAIL;
        } else if(PublicUtil.isPhone(loginName)){
            loginType = LOGIN_TYPE_MOBILE;
        } else{
            basicOutput.setCode(ErrorCodeEnum.TD9500.code());
            basicOutput.setMsg(ErrorCodeEnum.TD9500.msg());
            return tdResponse;
        }

        int retCode = userService.findPwd(findLoginPwdDto, loginType, tdRequest.getBasic());
        basicOutput.setCode(ErrorCodeEnum.getEnum(retCode).code());
        basicOutput.setMsg(ErrorCodeEnum.getEnum(retCode).msg());
        return tdResponse;
        }

    }
