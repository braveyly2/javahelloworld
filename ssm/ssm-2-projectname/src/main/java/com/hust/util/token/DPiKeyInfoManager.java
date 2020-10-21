package com.hust.util.token;

import com.hust.entity.domain.DPiKeyInfo;
import com.hust.util.IdWorker;
import com.hust.util.ciper.MD5Util;
import com.hust.util.ciper.RSAUtil;

import java.time.LocalDateTime;
import java.util.Map;

public class DPiKeyInfoManager {
    private static DPiKeyInfoManager instance;
    private static DPiKeyInfo dpiKeyInfo;

    DPiKeyInfoManager(){
    }

    public static DPiKeyInfoManager getInstance(){
        if(null == instance){
            instance = new DPiKeyInfoManager();
        }
        return instance;
    }

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

    public static String getDPwd(long time) {
        //获取离签发时间最近的dpikey
        //DPiKeyInfo dPiKeyInfo = getDpiObj(time);
        DPiKeyInfo dPiKeyInfo = DPiKeyInfoManager.getInstance().getDPiKeyInfo();
        return MD5Util.encrypt(dPiKeyInfo.getDpiKey() + "#" + time);
    }
}
