package com.hust.service.impl;

import com.alibaba.fastjson.JSON;
import com.hust.constant.GlobalConstant;
import com.hust.constant.GlobalVariable;
import com.hust.entity.bo.DPiKeyInfo;
import com.hust.entity.bo.TokenHeader;
import com.hust.entity.bo.TokenPayload;
import com.hust.entity.dto.TokenDataDto;
import com.hust.service.TokenService;
import com.hust.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Auther: TX
 * @Description: Token操作服务实现类
 */
@Service
public class TokenServiceImpl implements TokenService {
    private static final String MOD_TOKEN = "TokenSvc";

    @Value("appliwei")
    private String appName;

    /**
     * Token验证
     *
     * @param requestInfo
     * @return
     */
    public TDResponse<TokenDataDto> isTokenValid(TDRequest requestInfo) {
        return tokenVerification(requestInfo, true, true);
    }


    /**
     * Token续签验证
     *
     * @param requestInfo
     * @return
     */
    public TDResponse<TokenDataDto> isAllowTokenRenew(TDRequest requestInfo) {
        return tokenVerification(requestInfo, false, true);
    }

    /**
     * 解析Token数据，不进行签名和过期校验
     *
     * @param requestInfo
     * @return
     */
    @Override
    public TDResponse<TokenDataDto> getTokenDataDto(TDRequest requestInfo) {
        return tokenVerification(requestInfo, false, false);
    }

    /**
     * 解析和验证Token
     *
     * @param requestInfo 带Token的请求信息
     * @param isCheckTimeout 是否验证Token过期
     * @param isCheckSignBlackList 是否验证签名及黑名单
     * @return
     */
    private TDResponse<TokenDataDto> tokenVerification(TDRequest requestInfo, boolean isCheckTimeout, boolean isCheckSignBlackList) {
        //当前时间戳
        long currentTimeStamp = PublicUtil.getUtcTimestampOfSecond();
        BasicInput basicInput = requestInfo.getBasic();

        BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(basicInput);
        //basicInput.setSn(basicInput.getSn());
        TDResponse<TokenDataDto> tdResponse = new TDResponse<>(basicOutput, null);

        try {
            String token = basicInput.getToken();
            if (PublicUtil.isEmpty(token)) {
                basicOutput.setCode(ErrorCodeEnum.TD7003.code());
                basicOutput.setMsg("Token为空");
                LogUtil.error("Token为空", MOD_TOKEN);
                return tdResponse;

            }
            String[] tokenEle = token.split("\\.");

            //Token由3段点分内容组成
            if (tokenEle.length != 3) {
                basicOutput.setCode(ErrorCodeEnum.TD7003.code());
                basicOutput.setMsg(ErrorCodeEnum.TD7003.msg());
                LogUtil.error(String.format("Token格式错误: [%s]", token), MOD_TOKEN);
                return tdResponse;
            }

            //头
            String stringHeader = Base64Util.decryptBase64ToString(tokenEle[0]);
            //负载
            String stringPayload = Base64Util.decryptBase64ToString(tokenEle[1]);
            //签名
            String stringRSASign = Base64Util.decryptBase64ToString(tokenEle[2]);

            TokenHeader tokenHeader = JSON.parseObject(stringHeader, TokenHeader.class);
            //说明Json转换失败，FastJson不报异常
            if (tokenHeader.getVer() == null) {
                basicOutput.setCode(ErrorCodeEnum.TD7003.code());
                basicOutput.setMsg(ErrorCodeEnum.TD7003.msg());
                LogUtil.error(String.format("TokenHeader转换错误: [%s]", token), MOD_TOKEN);
                return tdResponse;
            }

            TokenPayload tokenPayload = JSON.parseObject(stringPayload, TokenPayload.class);
            //运维API网关，拦截业务token
            if(appName.contains("-ops")){
                if(tokenPayload.getType() != GlobalConstant.TOKEN_TYPE_OPS){
                    basicOutput.setCode(ErrorCodeEnum.TD7003.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD7003.msg());
                    LogUtil.error(String.format("业务用户不允许访问运维: [%s]", token), MOD_TOKEN);
                    return tdResponse;
                }
            }
            //说明Json转换失败，FastJson不报异常
            if (tokenPayload.getIat() == 0 && tokenPayload.getExp() == 0) {
                basicOutput.setCode(ErrorCodeEnum.TD7003.code());
                basicOutput.setMsg(ErrorCodeEnum.TD7003.msg());
                LogUtil.error(String.format("TokenPayload转换错误: [%s]", token), MOD_TOKEN);
                return tdResponse;
            }

            //是否验证Token过期
            if (!isCheckTimeout) {
                //Token已过期
                if (tokenPayload.getExp() < currentTimeStamp) {
                    basicOutput.setCode(ErrorCodeEnum.TD7004.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD7004.msg());
                    LogUtil.error(String.format("Token已过期: [%s]", token), MOD_TOKEN);
                    return tdResponse;
                }
            }
            String sid = null;
            if (isCheckSignBlackList) {
                //验证签名
                //获取生成此Token使用的DPiKey
                //DPiKeyInfo dPiKeyInfo = DpikeyUtil.getDpiObj(tokenPayload.getIat());
                DPiKeyInfo dPiKeyInfo = GlobalVariable.GDPIKEY_INFO;
                if (dPiKeyInfo == null) {
                    basicOutput.setCode(ErrorCodeEnum.TD7005.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD7005.msg());
                    LogUtil.error(String.format("Dpikey为空: [%s]", token), MOD_TOKEN);
                    return tdResponse;
                }
                //动态口令生成
                String DPwd = MD5Util.encrypt(dPiKeyInfo.getDpiKey() + "#" + tokenPayload.getIat());
                //解析出Token中携带的签名
                String stringTokenSign = RSAUtil.publicDecrypt(stringRSASign, dPiKeyInfo.getPublicKey());
                //计算出Token中携带的签名应该有的值
                String stringHashSign = HMACSHA256Util.encrypt(tokenEle[0] + "." + tokenEle[1], DPwd);
                //签名错误
                if (!stringTokenSign.equals(stringHashSign)) {
                    basicOutput.setCode(ErrorCodeEnum.TD7005.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD7005.msg());
                    LogUtil.error(String.format("签名验证失败，请求中的签名：[%s]，计算出的签名：[%s]", stringTokenSign, stringHashSign), MOD_TOKEN);
                    return tdResponse;
                }

                //判断Token是否在黑名单
                //计算服务识别码
                sid = MD5Util.encrypt(DPwd + "#" + tokenPayload.getTid() + "#" + tokenPayload.getUid());
            }

            // TODO 需要明确哪些负载需要加密，什么方式加密
            //Token解析后的数据
            TokenDataDto tokenDataDto = new TokenDataDto(tokenPayload.getUid(), tokenPayload.getRole(), tokenPayload.getAuth(), sid, tokenPayload.getTid(), null,null);
            if (!isCheckTimeout) {
                tokenDataDto.setTokenPayload(tokenPayload);
            }

            basicOutput.setCode(ErrorCodeEnum.TD200.code());
            basicOutput.setMsg(ErrorCodeEnum.TD200.msg());
            tdResponse.setData(tokenDataDto);
            return tdResponse;
        }
        catch (Exception ex){
            LogUtil.error("解析Token异常:" + ex.getMessage(), MOD_TOKEN);
            basicOutput.setCode(ErrorCodeEnum.TD9500.code());
            basicOutput.setMsg(ex.getMessage());
            return tdResponse;
        }
    }
}
