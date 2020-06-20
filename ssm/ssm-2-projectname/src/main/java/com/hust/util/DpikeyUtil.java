package com.hust.util;

import com.alibaba.fastjson.JSON;
import com.hust.constant.GlobalVariable;
import com.hust.entity.bo.DPiKeyInfo;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: TX
 * @Description:
 */
public class DpikeyUtil {

    /**
     * 获取动态口令
     *
     * @param time 签发时间
     * @return
     */
    public static String getDPwd(long time) {
        //获取离签发时间最近的dpikey
        DPiKeyInfo dPiKeyInfo = getDpiObj(time);
        return MD5Util.encrypt(dPiKeyInfo.getDpiKey() + "#" + time);
    }

    /**
     * 获取离签发时间最近的dpikey
     *
     * @param time 签发时间
     * @return
     */
    public static DPiKeyInfo getDpiObj(long time) {
        return GlobalVariable.GDPIKEY_INFO;
    }

    public static void main_bak(String[] args) throws ParseException {


    }

    public static LocalDateTime tf(String str) throws ParseException {
//        String str="Thu May 07 14:33:19 CST 2015";
//        str="Fri Jul 26 09:09:43 CST 2019";
//        String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
//        SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.US);
//        Date date = df.parse(str);
        System.out.println("=========" + PublicUtil.getUTCDate());

//        DateTimeFormatter df = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy");
//        LocalDateTime date = LocalDateTime.parse(str, df);
        LocalDateTime date = PublicUtil.String2LocalDateTime(str);
        System.out.println("转换后的值：" + date);
        return date;
    }

}
