package com.hust.util.ciper;


import java.security.MessageDigest;
import com.hust.constant.GlobalConstant;
/**
 * All rights Reserved ,Designed by TVT
 *
 * @author:
 * @Description:MD5加密
 * @date: 2018/8/30 14:58
 */
public class MD5Util {
    public static final String CHARSET = "UTF-8";

    /**
     * 生成32位md5码
     * @param data 待加密字符串
     * @return 加密后的结果
     */
    public static String encrypt(String data) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(data.getBytes(GlobalConstant.DEFAULT_CHARSET));
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (Exception e) {
            throw new RuntimeException(String.format("MD5加密失败"), e);
        }
    }

    public static String encryptWithByteArr(byte[] result) {

        try {
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (Exception e) {
            throw new RuntimeException(String.format("MD5加密字节失败"), e);
        }
    }

}
