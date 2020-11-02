package com.hust.controller;


import com.hust.accountcommon.util.PublicUtil;
import com.hust.accountcommon.util.apitemplate.TDRequest;
import com.hust.accountcommon.util.apitemplate.TDResponse;
import com.hust.entity.dto.LoginResultDto;
import com.hust.entity.dto.WxBindAccountDto;
import com.hust.entity.dto.WxLoginDto;
import com.hust.entity.vo.LoginVo;
import com.hust.service.VxUserService;
import com.hust.constant.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class VxUserController {
    @Autowired
    VxUserService vxUserServiceImpl;

    /**
     * 微信登录
     *
     * @param  tdRequest
     * @return TDResponse<>
     */
    @RequestMapping(value = "/user/vxlogin", method = RequestMethod.POST)
    @ResponseBody
    public TDResponse<LoginVo> vxLogin(@RequestBody TDRequest<WxLoginDto> tdRequest){
        TDResponse<LoginVo> tdResponse = new TDResponse<>();
        tdResponse.setBasic(PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic()));
        LoginVo loginVo = new LoginVo();
        tdResponse.setData(loginVo);

        String code = tdRequest.getData().getCode();

        if(code==null || code==""){
            tdResponse.getBasic().setCode(ErrorCodeEnum.TD9500.code());
            tdResponse.getBasic().setMsg(ErrorCodeEnum.TD9500.msg());
            log.error("微信登录授权码错误");
            return tdResponse;
        }

        LoginResultDto loginResultDto = vxUserServiceImpl.vxLogin(code);
        if(loginResultDto.getResultCode().code()!= ErrorCodeEnum.TD200.code()){
            tdResponse.getBasic().setCode(loginResultDto.getResultCode().code());
            //tdResponse.getBasic().setMsg(loginResultDto.getResultCode().msg());
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
    @RequestMapping(value = "/user/vxBindAccount", method = RequestMethod.POST)
    @ResponseBody
    public TDResponse<LoginVo> vxBindAccount(@RequestBody TDRequest<WxBindAccountDto> tdRequest){
        TDResponse<LoginVo> tdResponse = new TDResponse<>();
        tdResponse.setBasic(PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic()));
        LoginVo loginVo = new LoginVo();
        tdResponse.setData(loginVo);
        WxBindAccountDto wxBindAccountDto = tdRequest.getData();
        LoginResultDto loginResultDto= vxUserServiceImpl.vxBindAccount(wxBindAccountDto.getCode(),wxBindAccountDto.getLoginName(),wxBindAccountDto.getDynCode());

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
    @RequestMapping(value = "/user/accountBindVx", method = RequestMethod.POST)
    @ResponseBody
    public TDResponse accountBindVx(@RequestBody TDRequest<WxLoginDto> tdRequest){
        TDResponse tdResponse = new TDResponse();
        tdResponse.setBasic(PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic()));
        Long userId = tdRequest.getTokenDataDto().getUserId();
        WxLoginDto wxLoginDto = tdRequest.getData();
        int retCode = vxUserServiceImpl.accountBindVx(wxLoginDto.getCode(), userId);
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
    @RequestMapping(value = "/user/accountUnbindVx", method = RequestMethod.POST)
    @ResponseBody
    public TDResponse accountUnbindVx(@RequestBody TDRequest tdRequest){
        TDResponse tdResponse = new TDResponse();
        tdResponse.setBasic(PublicUtil.getDefaultBasicOutputByInput(tdRequest.getBasic()));
        int retCode = vxUserServiceImpl.accountUnBindVx(tdRequest.getTokenDataDto().getUserId());
        tdResponse.getBasic().setCode(retCode);
        tdResponse.getBasic().setMsg(ErrorCodeEnum.getEnum(retCode).msg());
        return tdResponse;
    }
}
