package com.hust.accountcommon.util.token;

import com.hust.accountcommon.constant.GlobalConstant;
import com.hust.accountcommon.entity.domain.DPiKeyInfo;
import com.hust.accountcommon.entity.domain.TokenHeader;
import com.hust.accountcommon.entity.domain.TokenPayload;
import com.hust.accountcommon.entity.dto.TokenDataDto;
import com.hust.accountcommon.entity.dto.TokenResultDto;
import com.hust.accountcommon.util.IdWorker;
////import com.hust.accountcommon.util.LogUtil;
import com.hust.accountcommon.util.PublicUtil;
import com.hust.accountcommon.util.ciper.*;
import com.alibaba.fastjson.JSON;
import com.hust.accountcommon.util.ciper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lyh
 * @Title: DPwdUtil
 * @Description: token生成工具类
 * @date 2018/9/5 19:42
 */
@Component
@Slf4j
public class TokenUtil {
    /**
     * 产生token
     *
     * @param userId 用户ID
     * @param pwdMd5 用户密码M5D（或者短信验证码MD5或者旧SID）
     * @return
     */
    public TokenResultDto createToken(long userId, String pwdMd5, String clientType, String language, String role, List<String> autoList) {
        long tokenId = IdWorker.getInstance().getId();
        long refreshTokenId = IdWorker.getInstance().getId();
        TokenResultDto tokenResultDto = new TokenResultDto();
        tokenResultDto.setTokenId(tokenId);
        tokenResultDto.setRefreshTokenId(refreshTokenId);
        //授权中心ID
        String token;
        String refreshToken;
        try {
            //签发时间
            long createTime = PublicUtil.getUtcTimestampOfSecond();
            /*
            DPiKeyInfo dpiKeyObj = DpikeyUtil.getDpiObj(createTime);
            DPiKeyPackage dPiKeyPackage = MemoryDpikey.getDpiKeyPackage();

            //本次token有效时长（秒）
            long validTime = dPiKeyPackage.getWebTokenValidTime() * 60L;
            */
            /*
            DPiKeyInfo dpiKeyObj = new DPiKeyInfo();
            dpiKeyObj.setDpiKey("swaottest");
            dpiKeyObj.setId(100);
            dpiKeyObj.setCreatedTime(LocalDateTime.now());
            dpiKeyObj.setLastUpdateTime(LocalDateTime.now());
            dpiKeyObj.setDpiKeyStartTime(LocalDateTime.now());
            GlobalVariable.GDPIKEY_INFO = dpiKeyObj;
            */
            DPiKeyInfo dpiKeyObj = DPiKeyInfoManager.getInstance().getDPiKeyInfo();


            long validTime = 60L;

            //手机用户7天
            if (GlobalConstant.CLIENT_TYPE_MOBILE.equals(clientType)) {
                //validTime = dPiKeyPackage.getAppTokenValidTime() * 60L;
                validTime = 600 * 60L;
            }
            //过期时间
            long expireTime = createTime + validTime;
            //私钥
            String privateKey = dpiKeyObj.getPrivateKey();
            //动态口令
            String dpwd = MD5Util.encrypt(dpiKeyObj.getDpiKey() + "#" + createTime);

            //base64头
            TokenHeader header = new TokenHeader();
            header.setVer("1.0");
            header.setAlg("HMACSHA256");
            String base64Header = Base64Util.encryptBase64(JSON.toJSONString(header));

            {
                //base64负载
                TokenPayload payload = new TokenPayload();
                payload.setIat(createTime);
                payload.setExp(expireTime);
                payload.setDc(1);
                payload.setUid(userId);
                payload.setTid(tokenId);
                payload.setRole(role);
                payload.setType(GlobalConstant.TOKEN_TYPE_BUS);
                //userId小于10000位运维系统访问业务系统的内置账号
                if (userId < 10000) {
                    payload.setAuth(autoList);
                }
                String base64Payload = Base64Util.encryptBase64(JSON.toJSONString(payload));
                //生成签名
                String hashSign = HMACSHA256Util.encrypt(base64Header + "." + base64Payload, dpwd);
                String rsaSign = RSAUtil.privateEncrypt(hashSign, privateKey);
                String base64Sign = Base64Util.encryptBase64(rsaSign);

                token = base64Header + "." + base64Payload + "." + base64Sign;
            }

            {
                //base64负载
                TokenPayload refreshTokenPayload = new TokenPayload();
                refreshTokenPayload.setIat(createTime);
                refreshTokenPayload.setExp(expireTime*10);
                refreshTokenPayload.setDc(1);
                refreshTokenPayload.setUid(userId);
                refreshTokenPayload.setTid(refreshTokenId);
                refreshTokenPayload.setRole(role);
                refreshTokenPayload.setType(GlobalConstant.TOKEN_TYPE_BUS);
                //userId小于10000位运维系统访问业务系统的内置账号
                if (userId < 10000) {
                    refreshTokenPayload.setAuth(autoList);
                }
                String base64Payload = Base64Util.encryptBase64(JSON.toJSONString(refreshTokenPayload));
                //生成签名
                String hashSign = HMACSHA256Util.encrypt(base64Header + "." + base64Payload, dpwd);
                String rsaSign = RSAUtil.privateEncrypt(hashSign, privateKey);
                String base64Sign = Base64Util.encryptBase64(rsaSign);

                refreshToken = base64Header + "." + base64Payload + "." + base64Sign;
            }

            tokenResultDto.setToken(token);
            tokenResultDto.setRefreshToken(refreshToken);
            tokenResultDto.setSid(this.createSid(tokenId, userId, createTime, pwdMd5));
            tokenResultDto.setRefreshTokenId(refreshTokenId);
        } catch (Exception e) {
            log.error("产生token失败:" + e.getMessage(), 1, e);
            System.out.println("create token error");
        }
        return tokenResultDto;
    }



    /**
     * 产生服务器识别码加密串
     *
     * @param tokenId tokenId
     * @param userId  用户id
     * @param time    时间（UTC 秒数）
     * @param pwdMd5  用户密码MD5
     * @return
     */
    public String createSid(long tokenId, long userId, long time, String pwdMd5) {
        String sidStr = "";
        try {
            String data = DPiKeyInfoManager.getInstance().getDPwd(time) + "#" + tokenId + "#" + userId;
            String sid = MD5Util.encrypt(data);
            sidStr = AESUtil.aesEncrypt(sid, pwdMd5);
        } catch (Exception e) {
            log.error("产生服务器器识别码sid加密串失败:" + e.getMessage(), "MOD_LOGIN");
            System.out.println("产生服务器器识别码sid加密串失败:" + e.getMessage());
        }
        return sidStr;
    }

    public TokenDataDto verify(String token, boolean isCheckTimeout, boolean isCheckSignBlackList) {
        //当前时间戳
        long currentTimeStamp = PublicUtil.getUtcTimestampOfSecond();
        //BasicInput basicInput = requestInfo.getBasic();

        //BasicOutput basicOutput = PublicUtil.getDefaultBasicOutputByInput(basicInput);
        //basicInput.setSn(basicInput.getSn());
        //TDResponse<TokenDataDto> tdResponse = new TDResponse<>(basicOutput, null);
        TokenDataDto tokenDataDto = null;

        try {
            //String token = basicInput.getToken();
            if (PublicUtil.isEmpty(token)) {
                return null;

            }
            String[] tokenEle = token.split("\\.");

            //Token由3段点分内容组成
            if (tokenEle.length != 3) {
                log.error(String.format("Token格式错误: [%s]", token), "Token Module");
                return null;
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
                log.error(String.format("TokenHeader转换错误: [%s]", token), "Token Module");
                return null;
            }

            TokenPayload tokenPayload = JSON.parseObject(stringPayload, TokenPayload.class);
            /*
            //运维API网关，拦截业务token
            if(appName.contains("-ops")){
                if(tokenPayload.getType() != GlobalConstant.TOKEN_TYPE_OPS){
                    basicOutput.setCode(ErrorCodeEnum.TD7003.code());
                    basicOutput.setMsg(ErrorCodeEnum.TD7003.msg());
                    log.error(String.format("业务用户不允许访问运维: [%s]", token), MOD_TOKEN);
                    return tdResponse;
                }
            }
            */
            //说明Json转换失败，FastJson不报异常
            if (tokenPayload.getIat() == 0 && tokenPayload.getExp() == 0) {
                log.error(String.format("TokenPayload转换错误: [%s]", token), "Token Module");
                return null;
            }

            //是否验证Token过期
            if (isCheckTimeout) {
                //Token已过期
                if (tokenPayload.getExp() < currentTimeStamp) {
                    log.error(String.format("Token已过期: [%s]", token), "Token Module");
                    return null;
                }
            }
            String sid = null;
            if (isCheckSignBlackList) {
                //验证签名
                //获取生成此Token使用的DPiKey
                DPiKeyInfo dPiKeyInfo = DPiKeyInfoManager.getInstance().getDPiKeyInfo();
                //DPiKeyInfo dPiKeyInfo = GlobalVariable.GDPIKEY_INFO;
                if (dPiKeyInfo == null) {
                    log.error(String.format("Dpikey为空: [%s]", token), "Token Module");
                    return null;
                }
                //动态口令生成
                String DPwd = MD5Util.encrypt(dPiKeyInfo.getDpiKey() + "#" + tokenPayload.getIat());
                //解析出Token中携带的签名
                String stringTokenSign = RSAUtil.publicDecrypt(stringRSASign, dPiKeyInfo.getPublicKey());
                //计算出Token中携带的签名应该有的值
                String stringHashSign = HMACSHA256Util.encrypt(tokenEle[0] + "." + tokenEle[1], DPwd);
                //签名错误
                if (!stringTokenSign.equals(stringHashSign)) {
                    log.error(String.format("签名验证失败，请求中的签名：[%s]，计算出的签名：[%s]",
                                                stringTokenSign, stringHashSign), "Token Module");
                    return null;
                }

                //判断Token是否在黑名单
                //计算服务识别码
                sid = MD5Util.encrypt(DPwd + "#" + tokenPayload.getTid() + "#" + tokenPayload.getUid());
            }

            // TODO 需要明确哪些负载需要加密，什么方式加密
            //Token解析后的数据
            tokenDataDto = new TokenDataDto(tokenPayload.getUid(), tokenPayload.getRole(), tokenPayload.getAuth(), sid,
                                            tokenPayload.getTid(), null);
            //if (!isCheckTimeout) {
            //    tokenDataDto.setTokenPayload(tokenPayload);
            //}

            return tokenDataDto;
        }
        catch (Exception ex){
            log.error("解析Token异常:" + ex.getMessage(), "Token Module");
            return null;
        }
    }


}
