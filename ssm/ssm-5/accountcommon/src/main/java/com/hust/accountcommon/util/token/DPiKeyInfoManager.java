package com.hust.accountcommon.util.token;

import com.hust.accountcommon.entity.domain.DPiKeyInfo;
import com.hust.accountcommon.util.IdWorker;
import com.hust.accountcommon.util.ciper.MD5Util;
import com.hust.accountcommon.util.ciper.RSAUtil;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author lw
 * @Title: DPiKeyInfoManager
 * @Description: dpikeyinfo管理类，提供单实例dpikey
 * @date 2020/9/5 19:42
 */
public class DPiKeyInfoManager {
    private static DPiKeyInfoManager instance;
    private static DPiKeyInfo dpiKeyInfo;

    DPiKeyInfoManager(){
    }

    /**
     * 静态获取dpikeyinfomanager单实例
     *
     * @return
     */
    public static DPiKeyInfoManager getInstance(){
        if(null == instance){
            instance = new DPiKeyInfoManager();
        }
        return instance;
    }

    /**
     * 获取dpikeyinfo
     *
     * @return
     */
    public DPiKeyInfo getDPiKeyInfo(){
        if(null == dpiKeyInfo){
            dpiKeyInfo = new DPiKeyInfo();
            dpiKeyInfo.setDpiKey("swaottest");
            dpiKeyInfo.setId(IdWorker.getInstance().getId());
            dpiKeyInfo.setCreatedTime(LocalDateTime.now());
            dpiKeyInfo.setLastUpdateTime(LocalDateTime.now());
            dpiKeyInfo.setDpiKeyStartTime(LocalDateTime.now());
            try {
                Map<String, String> keyMap = RSAUtil.createKeys(1024);
                dpiKeyInfo.setPrivateKey(keyMap.get("privateKey"));
                dpiKeyInfo.setPublicKey(keyMap.get("publicKey"));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return dpiKeyInfo;
    }

    /**
     * 根据时间生成动态口令
     *
     * @param time token的签发时间
     * @return 动态口令
     */
    public static String getDPwd(long time) {
        DPiKeyInfo dPiKeyInfo = DPiKeyInfoManager.getInstance().getDPiKeyInfo();
        return MD5Util.encrypt(dPiKeyInfo.getDpiKey() + "#" + time);
    }
}
