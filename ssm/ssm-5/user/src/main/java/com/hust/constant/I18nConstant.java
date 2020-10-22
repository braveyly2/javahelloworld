package com.hust.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 国际化常量
 * @author hht
 * @date 2019/11/12 15:10
 */
public class I18nConstant {

    private I18nConstant(){}
    public static final String DEFAULT = "default";
    private static final String ZH_CN = "zh-CN";
    private static final String EN_US = "en-US";
    public static final String DEFAULT_LANGUAGE = "zh-CN";


    /**
     * 默认动态码模板code 对应notice_info_template
     */
    public static final int DEFAULT_SMS_DYNAMIC_CODE = 1;

    /**
     * 中文简体短信动态码
     */
    public static final int ZH_CN_SMS_DYNAMIC_CODE = 1;

    /**
     * 英文短信动态码
     */
    public static final int EN_US_SMS_DYNAMIC_CODE = 6;



    /*-------------------------告警消息 短信-----------------*/

    /**
     * 默认告警消息code 对应notice_info_template
     */
    public static final int DEFAULT_SMS_ALARM_CODE = 2;

    /**
     * 中文简体告警消息code
     */
    public static final int ZH_CN_SMS_ALARM_CODE= 2;

    /**
     * 英文告警消息code
     */
    public static final int EN_US_SMS_ALARM_CODE = 7;

    /*-------------------------告警消息 邮件-----------------*/

    /**
     * 默认告警消息code 对应notice_email_template
     */
    public static final int DEFAULT_EMAIL_ALARM_CODE = 2;

    /**
     * 中文简体告警消息code
     */
    public static final int ZH_CN_EMAIL_ALARM_CODE= 2;

    /**
     * 英文告警消息code
     */
    public static final int EN_US_EMAIL_ALARM_CODE = 4;

    /*----------------------邮件--------------------------*/

    /**
     * 默认邮件动态码
     */
    public static final int DEFAULT_EMAIL_DYNAMIC_CODE = 1;

    /**
     * 中文简体邮件动态码
     */
    public static final int ZH_CN_EMAIL_DYNAMIC_CODE = 1;
    /**
     * 英文邮件动态码
     */
    public static final int EN_US_EMAIL_DYNAMIC_CODE = 3;

    /**
     * 默认邮件动态码标题
     */
    private static final String DEFAULT_EMAIL_DYNAMIC_CODE_TITLE = "Verification code";
    /**
     * 中文简体邮件动态码标题
     */
    private static final String ZH_CN_EMAIL_DYNAMIC_CODE_TITLE = "验证码";

    /**
     * 英文邮件动态码标题
     */
    private static final String EN_US_EMAIL_DYNAMIC_CODE_TITLE = "Verification code";

    /*----------------------场景--------------------------*/

    /**
     * 默认邮件动态码标题
     */
    private static final String DEFAULT_SCENES = "其他";
    /**
     * 中文简体邮件动态码标题
     */
    private static final String ZH_CN_DEFAULT_SCENES = "其他";

    /**
     * 英文邮件动态码标题
     */
    private static final String EN_US_DEFAULT_SCENES = "other";

    /*-------------------短信签名-----------------------*/

    private static final String ZH_CN_SIGN_NAME="星视云";
    private static final String EN_US_SIGN_NAME="StarVision";

    /**
     * 邮件动态码模板编号
     */
    public static final Map<String,Integer> EMAIL_DYNAMIC_CODE_MAP=new HashMap<>();

    /**
     * 告警消息短信模板编号
     */
    public static final Map<String,Integer> SMS_ALARM_CODE_MAP=new HashMap<>();

    /**
     * 告警消息邮件
     */
    public static final Map<String,Integer> EMAIL_ALARM_CODE_MAP=new HashMap<>();

    /**
     * 短信动态码模板编号
     */
    public static final Map<String,Integer> SMS_DYNAMIC_CODE_MAP=new HashMap<>();

    /**
     *   场景配置默认项
     */
    public static final Map<String,String> DEFAULT_SCENES_MAP=new HashMap<>();


    /**
     * 邮件动态码标题
     */
    public static final Map<String,String> EMAIL_DYNAMIC_CODE_TITLE_MAP=new HashMap<>();


    /**
     * 短信签名
     */
    public static final Map<String,String> SMS_SIGN_NAME_MAP=new HashMap<>();
    static {
        /*邮件动态码*/
        EMAIL_DYNAMIC_CODE_MAP.put(ZH_CN,ZH_CN_EMAIL_DYNAMIC_CODE);
        EMAIL_DYNAMIC_CODE_MAP.put(EN_US,EN_US_EMAIL_DYNAMIC_CODE);
        EMAIL_DYNAMIC_CODE_MAP.put(DEFAULT,DEFAULT_EMAIL_DYNAMIC_CODE);
        /*告警消息短信code*/
        SMS_ALARM_CODE_MAP.put(ZH_CN,ZH_CN_SMS_ALARM_CODE);
        SMS_ALARM_CODE_MAP.put(EN_US,EN_US_SMS_ALARM_CODE);
        SMS_ALARM_CODE_MAP.put(DEFAULT,DEFAULT_SMS_ALARM_CODE);
        /*告警消息邮件code*/
        EMAIL_ALARM_CODE_MAP.put(ZH_CN,ZH_CN_EMAIL_ALARM_CODE);
        EMAIL_ALARM_CODE_MAP.put(EN_US,EN_US_EMAIL_ALARM_CODE);
        EMAIL_ALARM_CODE_MAP.put(DEFAULT,DEFAULT_EMAIL_ALARM_CODE);
        /*短信动态码*/
        SMS_DYNAMIC_CODE_MAP.put(ZH_CN,ZH_CN_SMS_DYNAMIC_CODE);
        SMS_DYNAMIC_CODE_MAP.put(EN_US,EN_US_SMS_DYNAMIC_CODE);
        SMS_DYNAMIC_CODE_MAP.put(DEFAULT,DEFAULT_SMS_DYNAMIC_CODE);
        /*默认场景*/
        DEFAULT_SCENES_MAP.put(ZH_CN,ZH_CN_DEFAULT_SCENES);
        DEFAULT_SCENES_MAP.put(EN_US,EN_US_DEFAULT_SCENES);
        DEFAULT_SCENES_MAP.put(DEFAULT,DEFAULT_SCENES);
        /*邮件动态码标题*/
        EMAIL_DYNAMIC_CODE_TITLE_MAP.put(ZH_CN,ZH_CN_EMAIL_DYNAMIC_CODE_TITLE);
        EMAIL_DYNAMIC_CODE_TITLE_MAP.put(EN_US,EN_US_EMAIL_DYNAMIC_CODE_TITLE);
        EMAIL_DYNAMIC_CODE_TITLE_MAP.put(DEFAULT,DEFAULT_EMAIL_DYNAMIC_CODE_TITLE);
        /*短信签名*/
        SMS_SIGN_NAME_MAP.put(ZH_CN,ZH_CN_SIGN_NAME);
        SMS_SIGN_NAME_MAP.put(EN_US,EN_US_SIGN_NAME);
        SMS_SIGN_NAME_MAP.put(DEFAULT,EN_US_SIGN_NAME);
    }

}
