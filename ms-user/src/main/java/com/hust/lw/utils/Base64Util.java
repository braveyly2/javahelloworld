package com.hust.lw.utils;


import com.hust.lw.constant.GlobalConstant;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by Administrator on 2018/9/6.
 */
public class Base64Util {

    /**
     * baes64 加密为字符串
     *
     * @param data 要加密的数据
     * @return 加密后的字符串
     */
    public static String encryptBase64(byte[] data) {
//        BASE64Encoder base64Encoder = new BASE64Encoder();
//        return base64Encoder.encodeBuffer(data);
//        从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Encoder
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * baes64 加密为字符串
     *
     * @param data 要加密的UTF-8编码格式字符串
     * @return 加密后的字符串
     */
    public static String encryptBase64(String data) {
        return encryptBase64(data, GlobalConstant.DEFAULT_CHARSET);
    }

    /**
     * baes64 加密为字符串
     *
     * @param data 要加密的 {@code charset} 编码格式字符串
     * @return 加密后的字符串
     */
    public static String encryptBase64(String data, String charset) {
        try {
            return Base64.getEncoder().encodeToString(data.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Base64加密失败", e);
        }
    }

    /**
     * base64 解密
     *
     * @param data 要解密的字符串
     * @return 解密后的byte[]
     * @throws Exception
     */
    public static byte[] decryptBase64(String data) {
//        return base64Decoder.decodeBuffer(data);
//        从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Decoder
        return Base64.getDecoder().decode(data);
    }

    /**
     * base64 解密
     *
     * @param data 要解密的字符串
     * @return 解密后的UTF8字符串
     * @throws Exception
     */
    public static String decryptBase64ToString(String data) {
        byte[] result = Base64.getDecoder().decode(data);
        try {
            return new String(result, GlobalConstant.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Base64解密失败", e);
        }
    }

    /**
     * base64 解密
     *
     * @param data 要解密的字符串
     * @return 解密后的 {@code charset} 编码格式字符串
     * @throws Exception
     */
    public static String decryptBase64ToString(String data, String charset) {
        byte[] result = Base64.getDecoder().decode(data);
        try {
            return new String(result, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Base64解密失败", e);
        }
    }
}
