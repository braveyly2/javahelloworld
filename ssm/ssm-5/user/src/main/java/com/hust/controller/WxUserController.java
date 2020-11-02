package com.hust.controller;


import com.hust.accountcommon.util.PublicUtil;
import com.hust.accountcommon.util.apitemplate.TDRequest;
import com.hust.accountcommon.util.apitemplate.TDResponse;
import com.hust.entity.dto.LoginResultDto;
import com.hust.entity.dto.WxBindAccountDto;
import com.hust.entity.dto.WxLoginDto;
import com.hust.entity.vo.LoginVo;
import com.hust.service.WxUserService;
import com.hust.constant.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
public class WxUserController {
    @Autowired
    WxUserService wxUserServiceImpl;
    /**
     * 微信登录
     *
     * @param  tdRequest
     * @return TDResponse<>
     */
    @RequestMapping(value = "/user/wxlogin", method = RequestMethod.POST)
    public TDResponse<LoginVo> wxLogin(TDRequest<WxLoginDto> tdRequest){
        TDResponse<LoginVo> tdResponse = new TDResponse<>();
        LoginVo loginVo = new LoginVo();
        tdResponse.setData(loginVo);

        String code = tdRequest.getData().getCode();

        if(code==null || code==""){
            tdResponse.getBasic().setCode(ErrorCodeEnum.TD9500.code());
            tdResponse.getBasic().setMsg(ErrorCodeEnum.TD9500.msg());
            log.error("微信登录授权码错误");
            return tdResponse;
        }

        LoginResultDto loginResultDto = wxUserServiceImpl.wxLogin(code);
        if(loginResultDto.getResultCode().code()!= ErrorCodeEnum.TD200.code()){
            tdResponse.getBasic().setCode(loginResultDto.getResultCode().code());
            tdResponse.getBasic().setMsg(loginResultDto.getResultCode().msg());
            return tdResponse;
        }

        loginVo.setToken(loginResultDto.getToken());
        loginVo.setRefreshToken(loginResultDto.getRefreshToken());
        loginVo.setSid(loginResultDto.getSid());

        return tdResponse;
    }


    /**
     * 微信登录过程中绑定手机号或邮箱
     *
     * @param  tdRequest
     * @return TDResponse<>
     */
    @RequestMapping(value = "/user/wxBindAccount", method = RequestMethod.POST)
    public TDResponse<LoginVo> wxBindAccount(TDRequest<WxBindAccountDto> tdRequest){
        TDResponse<LoginVo> tdResponse = new TDResponse<>();
        tdResponse.setBasic(PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic()));
        LoginVo loginVo = new LoginVo();
        tdResponse.setData(loginVo);
        WxBindAccountDto wxBindAccountDto = tdRequest.getData();
        LoginResultDto loginResultDto= wxUserServiceImpl.wxBindAccount(wxBindAccountDto.getCode(),wxBindAccountDto.getLoginName(),wxBindAccountDto.getDynCode());

        if(loginResultDto.getResultCode().code()!= ErrorCodeEnum.TD200.code()){
            tdResponse.getBasic().setCode(loginResultDto.getResultCode().code());
            tdResponse.getBasic().setMsg(loginResultDto.getResultCode().msg());
            return tdResponse;
        }

        loginVo.setToken(loginResultDto.getToken());
        loginVo.setRefreshToken(loginResultDto.getRefreshToken());
        loginVo.setSid(loginResultDto.getSid());

        return tdResponse;

    }

    /**
     * 手机号或邮箱登录进去后，绑定微信
     *
     * @param  tdRequest
     * @return TDResponse<>
     */
    @RequestMapping(value = "/user/accountBindWx", method = RequestMethod.POST)
    public TDResponse accountBindWx(TDRequest<WxLoginDto> tdRequest){
        TDResponse tdResponse = new TDResponse();
        tdResponse.setBasic(PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic()));
        Long userId = tdRequest.getTokenDataDto().getUserId();
        WxLoginDto wxLoginDto = tdRequest.getData();
        int retCode = wxUserServiceImpl.accountBindWx(wxLoginDto.getCode(), userId);
        tdResponse.getBasic().setCode(ErrorCodeEnum.getEnum(retCode).code());
        tdResponse.getBasic().setMsg(ErrorCodeEnum.getEnum(retCode).msg());
        return tdResponse;
    }

    /**
     * 手机号或邮箱登录进去后，解除对微信的绑定
     *
     * @param  tdRequest
     * @return TDResponse<>
     */
    public TDResponse accountUnbindWx(TDRequest tdRequest){
        TDResponse tdResponse = new TDResponse();
        tdResponse.setBasic(PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic()));
        int retCode = wxUserServiceImpl.accountUnBindWx(tdRequest.getTokenDataDto().getUserId());
        tdResponse.getBasic().setCode(retCode);
        tdResponse.getBasic().setMsg(ErrorCodeEnum.getEnum(retCode).msg());
        return tdResponse;
    }
}
