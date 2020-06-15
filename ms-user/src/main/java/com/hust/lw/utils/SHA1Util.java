package com.hust.lw.utils;


import com.hust.lw.constant.GlobalConstant;

import java.security.MessageDigest;

/**
 * Created by Administrator on 2018/9/6.
 */
public class SHA1Util {
    /**
     * 使用SHA1加密字符串
     *
     * @param data 待加密字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String data) {
        if (data == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(data.getBytes(GlobalConstant.DEFAULT_CHARSET));
            return PublicUtil.byteArray2HexString(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException("SHA1加密失败", e);
        }
    }
}
