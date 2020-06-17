package com.hust.constant;

/**
 * All rights Reserved ,Designed by TVT
 *
 * @author Yanjj
 * @Description:
 * @date 2018/8/23 10:00
 */

public interface GlobalConstant {
    String DEFAULT_CHARSET = "UTF-8";

    /**
     * 客户端类型：手机APP
     */
    String CLIENT_TYPE_MOBILE = "mobile";
    /**
     * 客户端类型：WEB
     */
    String CLIENT_TYPE_WEB = "web";
    /**
     * 客户端类型：WEB-OPS
     */
    String CLIENT_TYPE_WEB_OPS = "web-ops";

    /**
     * API网关自定义头
     */
    String ZUUL_HEADER_CLIENTTYPE = "client-type";

    String CLOUDSTORAGE_ALI_OSS = "ALI_OSS";
    String CLOUDSTORAGE_AWS_S3 = "AWS_S3";
    String CLOUDSTORAGE_QINIU_OSS = "QINIU_KODO";


    String CLOUDSTORAGE_BUSINESS_CLOUD_RECORD = "cloudRecord";
    String CLOUDSTORAGE_BUSINESS_ALARM_IMG = "alarmImg";
    String CLOUDSTORAGE_BUSINESS_CHANNEL_COVER = "channelCover";
    String CLOUDSTORAGE_BUSINESS_PRESET_IMG = "presetImg";
    String CLOUDSTORAGE_BUSINESS_CHANNEL_LIVE_IMG = "channelLiveImg";
    String CLOUDSTORAGE_BUSINESS_DEV_UPGRADE = "devUpgrade";
    String CLOUDSTORAGE_BUSINESS_DEV_UP_NET_SPEED = "devUpNetSpeed";

    String CLOUDSTORAGE_STORAGETYPE = "storageType";
    String CLOUDSTORAGE_STORAGEINFO = "storageInfo";

    String DC_TYPE_RDC = "rdc";
    String DC_TYPE_DDC = "ddc";
;
    String WEBSOCKET_SYSMSG_PREFIX = "[$new_msg$]";
    /**
     * 用户token 映射表
     */
    String REDIS_USER_TOKEN = "user_token:";
    /**
     * token类型--业务
     */
    int TOKEN_TYPE_BUS = 1;
    /**
     * token类型--运维
     */
    int TOKEN_TYPE_OPS = 2;

    /**
     * 版本管理
     */
    String VER_1_0 = "1.0";

    String GENERAL_ALARM_DPIKEY_NOTICE = "DpikeyNotice";
    String GENERAL_ALARM_DPIKEY_GET = "DpikeyGet";
    String GENERAL_ALARM_RELAY_OFFLINE = "RelayOffline";
    String GENERAL_ALARM_RELAY_OFFLINE_ALL = "AllRelayOffline";

    //通用告警状态：告警
    int GENERALALARM_STATUS_ALARM = 1;
    //通用告警状态：恢复
    int GENERALALARM_STATUS_RECOVER = 0;
    //通用告警Redis数据前缀
    String GENERALALARM_REDIS_KEY = "GeneralAlarm";


}
