package com.hust.util;

import com.alibaba.fastjson.JSON;
import com.hust.constant.GlobalConstant;
import com.hust.entity.bo.DPiKeyInfo;
import com.hust.entity.bo.TokenHeader;
import com.hust.entity.bo.TokenPayload;
import com.hust.entity.dto.TokenResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Lyh
 * @Title: DPwdUtil
 * @Description: token生成工具类
 * @date 2018/9/5 19:42
 */
@Component
public class TokenUtil {



    private List<String> opsAuthList = new ArrayList<String>(){{
        add("ops");
    }};

    /**
     * 产生token
     *
     * @param userId 用户ID
     * @param pwdMd5 用户密码M5D（或者短信验证码MD5或者旧SID）
     * @return
     */
    public TokenResultDto createToken(long userId, String pwdMd5, String clientType,String language) {
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


            long validTime = 600 * 60L;

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
                payload.setType(GlobalConstant.TOKEN_TYPE_BUS);
                //userId小于10000位运维系统访问业务系统的内置账号
                if (userId < 10000) {
                    payload.setAuth(opsAuthList);
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
                refreshTokenPayload.setType(GlobalConstant.TOKEN_TYPE_BUS);
                //userId小于10000位运维系统访问业务系统的内置账号
                if (userId < 10000) {
                    refreshTokenPayload.setAuth(opsAuthList);
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
            //LogUtil.error("产生token失败:" + e.getMessage(), 1, e);
            System.out.println("create token error");
        }
        return tokenResultDto;
    }

    /**
     * 续签TOKEN
     *
     * @param oldPlayload 旧的token 负载
     * @param oldSid      旧的sid
     * @param lang redis中的语言
     * @return
     */
    public TokenResultDto renewToken(TokenPayload oldPlayload, String oldSid, String clientType, String lang) {
        /*
        TokenResultDto tokenResultDto = new TokenResultDto();
        long oldExpireTime = oldPlayload.getExp();
        long oldCreateTime = oldPlayload.getIat();
        long curTime = PublicUtil.getUtcTimestampOfSecond();
        DPiKeyInfo dpiKeyObj = DpikeyUtil.getDpiObj(oldCreateTime);
        DPiKeyInfo newDpiKeyObj = DpikeyUtil.getDpiObj(curTime);
        DPiKeyPackage dPiKeyPackage = MemoryDpikey.getDpiKeyPackage();
        boolean isDpikeyChanged = false;
        if (dpiKeyObj.getId() != newDpiKeyObj.getId()) {
            isDpikeyChanged = true;
        }
        //续签token有效时长（秒）
        long validTime = dPiKeyPackage.getWebTokenValidTime() * 60L;
        //手机用户7天
        if (GlobalConstant.CLIENT_TYPE_MOBILE.equals(clientType)) {
            validTime = dPiKeyPackage.getAppTokenValidTime() * 60L;
        }
        //过期时间+签发时长 > 当前时间
        if (oldExpireTime + validTime > curTime) {
            //如何生成了新的DpiKey（即原Token使用的Dpikey和当前生效的Dpikey不同），则生成新的Token
            if (isDpikeyChanged) {
                tokenResultDto = createToken(oldPlayload.getUid(), oldSid, clientType,lang);
            } else {
                //续签
                //私钥
                String privateKey = dpiKeyObj.getPrivateKey();
                //动态口令
                String dpwd = MD5Util.encrypt(dpiKeyObj.getDpiKey() + "#" + oldCreateTime);
                //base64头
                TokenHeader header = new TokenHeader();
                header.setVer("1.0");
                header.setAlg("HMACSHA256");
                String base64Header = Base64Util.encryptBase64(JSON.toJSONString(header));
                //base64负载
                oldPlayload.setExp(curTime + validTime);
                String base64Payload = Base64Util.encryptBase64(JSON.toJSONString(oldPlayload));
                //生成签名
                String hashSign = HMACSHA256Util.encrypt(base64Header + "." + base64Payload, dpwd);
                String rsaSign = RSAUtil.privateEncrypt(hashSign, privateKey);
                String base64Sign = Base64Util.encryptBase64(rsaSign);

                String token = base64Header + "." + base64Payload + "." + base64Sign;
                //产生token 后存储redis

                HashMap<String, RedisTokenInfo> map = new HashMap<>();
                if (jedisUtils.exists(UserConstant.REDIS_USER_TOKEN + oldPlayload.getUid())) {
                    map = (HashMap<String, RedisTokenInfo>) jedisUtils.hgetAll(UserConstant.REDIS_USER_TOKEN + oldPlayload.getUid());
                }
                //redis中存储token的createTime和客户端类型
                RedisTokenInfo redisTokenInfo = new RedisTokenInfo();
                redisTokenInfo.setCt(oldCreateTime);
                redisTokenInfo.setEt(curTime + validTime);
                redisTokenInfo.setCtp(clientType);
                redisTokenInfo.setLang(lang);
                map.put(String.valueOf(oldPlayload.getTid()), redisTokenInfo);
                jedisUtils.hset(UserConstant.REDIS_USER_TOKEN + oldPlayload.getUid(), String.valueOf(oldPlayload.getTid()), redisTokenInfo);
                //遍历map，将map中已过期的token清除
                Iterator<Map.Entry<String, RedisTokenInfo>> it = map.entrySet().iterator();
                List<Map.Entry<String, RedisTokenInfo>> webList = new ArrayList<>();
                List<Map.Entry<String, RedisTokenInfo>> appList = new ArrayList<>();
                while (it.hasNext()) {
                    Map.Entry<String, RedisTokenInfo> entry = it.next();
                    RedisTokenInfo tokenInfo = entry.getValue();
                    if (tokenInfo.getEt() <= curTime) {
                        it.remove();
                        jedisUtils.hmDelete(UserConstant.REDIS_USER_TOKEN + oldPlayload.getUid(), entry.getKey());
                        //同时删除和websocket 映射
                        jedisUtils.hmDelete(UserConstant.REDIS_WEBSOCKET_UID_PRE + oldPlayload.getUid(), entry.getKey());
                        jedisUtils.remove(UserConstant.REDIS_WEBSOCKET_TID_PRE + entry.getKey());
                    }else {
                        if (GlobalConstant.CLIENT_TYPE_MOBILE.equals(tokenInfo.getCtp())) {
                            appList.add(entry);
                        } else {
                            webList.add(entry);
                        }
                    }
                }
                //遍历map，踢除超过token数量限制的最早访问的token
                doDelToken(map, webList, userRemoteConfigProUtil.getMaxWebTokenNum(), oldPlayload.getUid());
                doDelToken(map, appList, userRemoteConfigProUtil.getMaxAppTokenNum(), oldPlayload.getUid());
                //获取map中过期时间最晚的token,将其过期时间设为用户key过期时间
                long maxExpireTime = getKeyExpireTime(map);
                //最大时间-当前时间，为redis过期时间(秒)
                long redisExpireTime = maxExpireTime - curTime;
                jedisUtils.setExpire(UserConstant.REDIS_USER_TOKEN + oldPlayload.getUid(), (int) redisExpireTime);
                User user = new User();
                user.setId(oldPlayload.getUid());
                user.setOnlineStatus(UserConstant.USER_ONLINE);
                user.setExpireTime(PublicUtil.getUTCDate(maxExpireTime * 1000));
                userMapper.updateUser(user);

                tokenResultDto.setToken(token);
                tokenResultDto.setSid(oldSid);
            }
        }
        return tokenResultDto;
        */
        return null;
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
            String data = DpikeyUtil.getDPwd(time) + "#" + tokenId + "#" + userId;
            String sid = MD5Util.encrypt(data);
            sidStr = AESUtil.aesEncrypt(sid, pwdMd5);
        } catch (Exception e) {
            //LogUtil.error("产生服务器器识别码sid加密串失败:" + e.getMessage(), MOD_LOGIN);
            System.out.println("产生服务器器识别码sid加密串失败:" + e.getMessage());
        }
        return sidStr;
    }




}
