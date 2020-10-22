package com.hust.accountcommon.util.ciper;




import com.hust.accountcommon.constant.GlobalConstant;
import com.hust.accountcommon.util.PublicUtil;

import java.security.MessageDigest;

/**
 * Created by Administrator on 2018/9/6.
 */
public class SHA256Util {
    /**
     * 利用java原生的摘要实现SHA256加密
     *
     * @param data 加密后的报文
     * @return
     */
    public static String encrypt(String data) {
        if (data == null) {
            return null;
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(data.getBytes(GlobalConstant.DEFAULT_CHARSET));
            return PublicUtil.byteArray2HexString(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException("SHA256加密失败", e);
        }
    }
}
