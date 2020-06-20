package com.hust.controller;

import com.hust.constant.GlobalConstant;
import com.hust.constant.UserConstant;
import com.hust.entity.RestBean;
import com.hust.entity.domain.User;
import com.hust.entity.domain.UserLogin;
import com.hust.entity.dto.GetUserInfoByNameDto;
import com.hust.entity.dto.IdDto;
import com.hust.entity.dto.LoginDto;
import com.hust.entity.dto.LoginResultDto;
import com.hust.entity.vo.LoginVo;
import com.hust.service.UserService;
import com.hust.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="hello",method=RequestMethod.POST)
    @ResponseBody
    Map<String, Object> hello(@RequestBody User user){
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
        User user = userService.selectByName(name);
        //cannot register for the same username
        if(null != user){
            return "fail";
        }

        User userAdd = new User();
        userAdd.setName(name);
        userAdd.setPassword(password);
        userAdd.setMark("thisisforregister");

        userService.insert(userAdd);
        // model.addAttribute("status","0");
        return "ok";
    }

    @RequestMapping(value="/user/register2",method=RequestMethod.POST)
    @ResponseBody
    public String register2(@RequestBody User user){
        User userExist = userService.selectByName(user.getName());
        //cannot register for the same username
        if(null != userExist){
            return "fail";
        }

        User userAdd = new User();
        userAdd.setId(user.getId());
        userAdd.setName(user.getName());
        userAdd.setPassword(user.getPassword());
        userAdd.setMark(user.getMark());

        userService.insert(userAdd);
        // model.addAttribute("status","0");
        return "ok";
    }

    @RequestMapping(value="/user/register3",method=RequestMethod.POST)
    @ResponseBody
    public RestBean register3(@RequestBody User user){
        User userExist = userService.selectByName(user.getName());
        //cannot register for the same username
        if(null != userExist){
            return RestBean.error("user:" + user.getName() + " is exist");
        }

        User userAdd = new User();
        userAdd.setId(user.getId());
        userAdd.setName(user.getName());
        userAdd.setPassword(user.getPassword());
        userAdd.setMark(user.getMark());

        userService.insert(userAdd);
        // model.addAttribute("status","0");
        return RestBean.ok("Succeed to add user",userAdd);
    }

    /*
http://localhost:8080/user/login
{
	"basic": {
		"ver": "1.0",
		"time": 1592399555986,
		"id": 30,
		"nonce": 1567246549
	},
	"data": {
		"userName": "admin",
		"type": "1",
		"password": "8f137f2c4574c86221295af9c4e83b26",
		"idCode": "",
		"imgCode": "",
		"uuid": "",
		"lang": "zh-CN2"
	}
}


{
    "basic": {
        "id": "30",
        "time": 1592399555986,
        "code": 200,
        "msg": "ok"
    },
    "data": {
        "token": "eyJhbGciOiJITUFDU0hBMjU2IiwidmVyIjoiMS4wIn0=.eyJhdXRoIjpbIm9wcyJdLCJkYyI6MSwiZXhwIjoxNTkyNjYyMTY4LCJpYXQiOjE1OTI2MjYxNjgsInRpZCI6MTE5LCJ0eXBlIjoxLCJ1aWQiOjIyfQ==.WGJ0N09CMm13MSt1eXVyc0dxaFIxa0l4Q0UydzAyNlRuRVFxeVRnVHJhQitHclBKRGo3RTZ3Vmd5Ui8rQm1rL0creWJ3WURnNWVZMnRXMTJ2dUVzaDI5RFZsd1ZIZXEyTTMvUTdyNGxvMm1ZK0gzRURaemRITXV2YTlpMkQ2SmVESDNCRTJ2c1FvdFNpbzJ3dS9QY1ZWTmJHRHBPZHNwTzZuNzd3cCszK1dNPQ==",
        "sid": "zMEEdN5vPCd/Fee638ZcFaStOka45NYER+5v1h/5FCU=",
        "idCode": null,
        "imgData": null,
        "p2pId": "119",
        "userLoginList": null
    }
}
    */

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public TDResponse<LoginVo> login(@RequestBody TDRequest<LoginDto> tdRequest, HttpServletRequest request) {
        LogUtil.info("收到登录请求", "Login");
        TDResponse<LoginVo> tdResponse = new TDResponse<>();
        BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic());
        try {
            /*
            String clientType = request.getHeader(GlobalConstant.ZUUL_HEADER_CLIENTTYPE);
            boolean isCheckImgCode = true;
            //判断之前需要判断 密码校验是否正确(仅限于密码登录)
            if (UserConstant.LOGIN_TYPE_PWD.equals(tdRequest.getData().getType())) {
                LoginResultDto dtoFormer = userServiceImpl.login(tdRequest, clientType, false, true);
                if (PublicUtil.isNotEmpty(dtoFormer.getResultCode())) {
                    basicOutput.setCode(dtoFormer.getResultCode().code());
                    basicOutput.setMsg(dtoFormer.getResultCode().msg());
                    tdResponse.setBasic(basicOutput);
                    LoginVo vo = new LoginVo();
                    vo.setToken(dtoFormer.getToken());
                    vo.setSid(dtoFormer.getSid());
                    vo.setIdCode(dtoFormer.getIdCode());
                    vo.setImgData(dtoFormer.getImgData());
                    vo.setP2pId(dtoFormer.getTid() == null ? "" : dtoFormer.getTid().toString());
                    tdResponse.setData(vo);
                    return tdResponse;
                }
                isCheckImgCode = false;
            }
            */
            //start
            String clientType = "web";
            boolean isCheckImgCode = false;
            //end
            //判断是否开启终端校验 以及绑定设备是否含有本设备
            /*
            int allowLogin = userServiceImpl.checkBindTerminal(tdRequest, clientType);
            if (allowLogin != UserConstant.TERMINAL_FRAME_NOT_POP) {
                basicOutput.setCode(ErrorCodeEnum.TD7025.code());
                basicOutput.setMsg(ErrorCodeEnum.TD7025.msg());
                tdResponse.setBasic(basicOutput);
                if (allowLogin == UserConstant.TERMINAL_FRAME_POP) {
                    //需要返回脱密后的用户的信息
                    List<UserLogin> userLoginList = userLoginService.getLoginListByUserName(tdRequest.getData().getUserName());
                    //脱敏处理
                    for (UserLogin userLogin : userLoginList) {
                        if (userLogin.getLoginType() < 3) {
                            String loginName = LoginNameUtil.getSafeLoginName(userLogin.getLoginName());
                            userLogin.setLoginName(loginName);
                        } else {
                            userLogin.setLoginName("" + "");
                        }
                    }
                    LoginVo loginVo = new LoginVo();
                    loginVo.setUserLoginList(userLoginList);
                    tdResponse.setData(loginVo);
                }

                return tdResponse;
            }
            */

            LogUtil.info(String.format("收到登录请求: UserName: %s, ClientType: %s", tdRequest.getData().getUserName(), clientType), "Login");
            LoginResultDto dto = userService.login(tdRequest, clientType, true, isCheckImgCode);
            LoginVo vo = new LoginVo();
            vo.setToken(dto.getToken());
            vo.setSid(dto.getSid());
            vo.setIdCode(dto.getIdCode());
            vo.setImgData(dto.getImgData());
            vo.setP2pId(dto.getTid() == null ? "" : dto.getTid().toString());
            if (PublicUtil.isNotEmpty(dto.getResultCode())) {
                basicOutput.setCode(dto.getResultCode().code());
                basicOutput.setMsg(dto.getResultCode().msg());
            } else {
                //记录操作日志
                //OpLog opLog = new OpLog();
                //opLog.setIp(PublicUtil.getRemoteIp(request));
                //opLogUtil.logBus(dto.getUserId(), dto.getTid(), OpLogUtil.login, opLog);
            }
            tdResponse.setBasic(basicOutput);
            tdResponse.setData(vo);
        } catch (Exception ex) {
            LogUtil.error("登录失败异常：" + ex.getMessage(), "Login", ex);
            basicOutput.setCode(ErrorCodeEnum.TD9500.code());
            basicOutput.setMsg(ex.getMessage());
        }
        return tdResponse;
    }

    @RequestMapping(value = "/innerapi/userInfoByName/get", method = RequestMethod.POST)
    public TDResponse<IdDto> getUserInfoByName(@RequestBody TDRequest<GetUserInfoByNameDto> tdRequest) {
        TDResponse<IdDto> tdResponse = new TDResponse<>();
        BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic());
        try {
            //User user = userServiceImpl.getUserInfoByName(tdRequest.getData().getUserName());
            User user = new User();
            if (PublicUtil.isNotEmpty(user)) {
                IdDto dto = new IdDto();
                dto.setId((long)user.getId());
                tdResponse.setData(dto);
            } else {
                basicOutput.setCode(ErrorCodeEnum.TD7001.code());
                basicOutput.setMsg(ErrorCodeEnum.TD7001.msg());
                LogUtil.error("用户不存在", "ms-user");
            }
        } catch (Exception e) {
            LogUtil.error("查询用户信息失败", "ms-user", e);
            basicOutput.setCode(ErrorCodeEnum.TD9500.code());
            basicOutput.setMsg(ErrorCodeEnum.TD9500.msg());
        }
        tdResponse.setBasic(basicOutput);
        return tdResponse;
    }

}
