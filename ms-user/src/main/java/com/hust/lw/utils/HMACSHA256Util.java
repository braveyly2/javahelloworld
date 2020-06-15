package com.hust.lw.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2018/9/6.
 */
public class HMACSHA256Util {
    public static String encrypt(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            return PublicUtil.byteArray2HexString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("HMACSHA256加密失败", e);
        }
    }
}
