package com.hust.lw.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserController {
    /**
     * 登录
     *
     * @param tdRequest
     * @return
     */
    /*
    //@TDResult(isNeedCheckToken = false)
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public TDResponse<LoginVo> login(@RequestBody TDRequest<LoginDto> tdRequest, HttpServletRequest request) {
        LogUtil.info("收到登录请求", "Login");
        TdResponse<LoginVo> tdResponse = new TdResponse<>();
        BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic());
        try {
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
            //判断是否开启终端校验 以及绑定设备是否含有本设备
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
            LogUtil.info(String.format("收到登录请求: UserName: %s, ClientType: %s", tdRequest.getData().getUserName(), clientType), "Login");
            LoginResultDto dto = userServiceImpl.login(tdRequest, clientType, true, isCheckImgCode);
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
                OpLog opLog = new OpLog();
                opLog.setIp(PublicUtil.getRemoteIp(request));
                opLogUtil.logBus(dto.getUserId(), dto.getTid(), OpLogUtil.login, opLog);
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
    */

}
