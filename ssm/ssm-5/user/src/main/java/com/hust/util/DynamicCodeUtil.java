package com.hust.util;

/**
 * All rights Reserved ,Designed by LW
 *
 * @author lw
 * @Description: 生成6位随机动态验证码
 * @date 2018/8/23 10:00
 */

public class DynamicCodeUtil
{
    private static int CODE_LENGTH = 6;

    /**
     * 获取手机动态验证码
     * @return
     */
    public static String getDynamicCode()
    {
        StringBuffer sb=new StringBuffer();

        for(int i=0;i<CODE_LENGTH;i++)
        {
            double d=Math.random();
            int n= (int)Math.floor(d * 10);
            sb.append(n);
        }

        return sb.toString();
    }

    public static void main(String [] args)
    {
        for(int i=0;i<10;i++)
        {
            System.out.println(getDynamicCode());
        }
    }
}
