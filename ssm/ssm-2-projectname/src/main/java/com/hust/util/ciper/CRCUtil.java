package com.hust.util.ciper;


import com.hust.constant.GlobalConstant;

import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;

/**
 * Created by Administrator on 2018/9/6.
 */
public class CRCUtil {
    public static final CRC32 crc32 = new CRC32();

    /**
     * 获取CRC32校验值
     *
     * @param data
     * @return
     */
    public static long getCRC32(String data) throws UnsupportedEncodingException {
        crc32.reset();
        crc32.update(data.getBytes(GlobalConstant.DEFAULT_CHARSET));
        return crc32.getValue();
    }

    public static int getCRC16(String data) throws UnsupportedEncodingException {
        byte[] bytes = data.getBytes(GlobalConstant.DEFAULT_CHARSET);
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;

        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return CRC;
    }

    public static int getCRC8(String data) throws UnsupportedEncodingException {
        byte[] bytes = data.getBytes(GlobalConstant.DEFAULT_CHARSET);
        int CRC = 0;
        int genPoly = 0x8C;
        for (int i = 0; i < bytes.length; i++) {
            CRC ^= bytes[i];
            CRC &= 0xff;//保证CRC余码输出为1字节。
            for (int j = 0; j < 8; j++) {
                if ((CRC & 0x01) != 0) {
                    CRC = (CRC >> 1) ^ genPoly;
                    CRC &= 0xff;//保证CRC余码输出为1字节。
                } else {
                    CRC >>= 1;
                }
            }
        }
        CRC &= 0xff;//保证CRC余码输出为1字节。
        return CRC;
    }
}
