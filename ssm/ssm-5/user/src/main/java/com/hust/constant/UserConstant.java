package com.hust.constant;

/**
 * user常量
 * @author lw
 * @date 2019/11/12 15:10
 */
public interface UserConstant {
    String REDIS_PICTURE_CODE = "userService:pictureCode:";

    String REDIS_REGISTER_CODE = "userService:registerDynamicCode:";

    String REDIS_REGISTER_PRIVATE_KEY = "userService:register:PRIVATE_KEY:";

    /**
     * redis中登录短信动态码缓存前缀
     */
    String REDIS_LOGIN_DYNAMIC_CODE = "userService:loginDynamicCode:";
    /**
     * redis中登录失败信息缓存前缀
     */
    String REDIS_LOGIN_FAIL = "LoginFail:";
    /**
     * redis中短信登录发送短信总数
     */
    String REDIS_SMS_COUNT = "userService:smsCount:";

    /**
     * 用户token 映射表
     */
    String REDIS_USER_TOKEN = "user_token:";
    /**
     * 登录类型 -密码登录
     */
    String LOGIN_TYPE_PWD = "1";

    /**
     * 用户锁定状态(运维锁定)
     */
    int USER_LOCK_STATE = 1;

    /**
     * 用户锁定状态（登录次数过多锁定，24小时后解禁）
     */
    int USER_LOCK_STATE_LOGIN = 2;
    /**
     * 用户连续登录失败次数（锁定账号）
     */
    int USER_LOCK_NUM = 20;

    /**
     * 用户被加入黑名单的tokenId 前缀
     */
    String BLACK_LIST_IN_REDIS = "TokenBlack:";

    /**
     * redis中更改手机短信动态码缓存前缀
     */
    String REDIS_CHANGE_MOBILE_DYNAMIC_CODE = "userService:changeMobileDynamicCode:";

    /**
     * redis中重置密码动态码缓存前缀
     */
    String REDIS_RESETPWD_DYNAMIC_CODE = "userService:resetPwdDynamicCode:";

    /**
     * redis中注销账户动态码缓存前缀
     */
    String REDIS_DELUSER_DYNAMIC_CODE = "userService:delUserDynamicCode:";

    /**
     * redis中找回密码手机短信动态码缓存前缀
     */
    String REDIS_FINDPWD_DYNAMIC_CODE = "userService:findPwdDynamicCode:";

    /**
     * redis中终端  自动绑定的验证码前缀
     */
    String REDIS_TERMINAL_BIND_DYNAMIC_CODE_AUTO = "userService:autoBindTerminalDynamicCode:";
    /**
     * redis中终端  手动绑定的验证码前缀
     */
    String REDIS_TERMINAL_BIND_DYNAMIC_CODE_MANUAL = "userService:manualBindTerminalDynamicCode:";
    /**
     * redis中 微信绑定账号的验证码前缀
     */
    String REDIS_WX_BIND_DYNAMIC_CODE = "userService:wxBindDynamicCode:";

    /**
     * redis中 用户一天内的头像上传次数
     */
    String REDIS_HEAD_IMG_UPLOAD_COUNT = "userService:headImgUploadCount:";

    String MOD_LOGIN = "Login";

    int USER_ONLINE = 1;

    int USER_OFFLINE = 0;

    String REDIS_WX_CODE = "wxCode:";
    /**
     * 发送动态码类型--手机
     */
    int DYNAMIC_CODE_MOBILE = 1;
    /**
     * 发送动态码类型--邮件
     */
    int DYNAMIC_CODE_EMAIL = 2;

    /**
     * redis中更改邮箱动态码缓存前缀
     */
    String REDIS_CHANGE_EMAIL_DYNAMIC_CODE = "userService:changeEmailDynamicCode:";

    //登录账号类型--手机
    int LOGIN_TYPE_MOBILE = 1;
    //登录账号类型--邮箱
    int LOGIN_TYPE_EMAIL = 2;
    //登录账号类型--微信
    int LOGIN_TYPE_WX = 3;

    //终端绑定的操作方式  直接登录  仅登录一次
    int TERMINAL_ONCE_LOGIN = 1;

    //终端绑定的操作方式  立即验证  即此次就绑定终端
    int TERMINAL_BIND_LOGIN = 2;

    //是否需要终端绑定弹框
    int TERMINAL_FRAME_POP = 1;//弹框
    int TERMINAL_FRAME_NOT_POP = 2;//未开启绑定开关不需要弹框
    int TERMINAL_REFUSE_WEB_LOGIN = 3;//开启了绑定开关  web不允许登录

    //终端绑定的开关 1：开启  0：关闭 1:已绑定 0：未绑定
    int TERMINAL_BIND_ON = 1;
    int TERMINAL_BIND_OFF = 0;

    //移动端/pc端
    int TERMINAL_DEVICE_PHONE = 1;
    int TERMINAL_DEVICE_PC = 2;

    /**
     * redis中用户一小时内(web)登录成功次数缓存前缀
     */
    String REDIS_LOGIN_NUM_WEB = "LoginNumWeb:";
    /**
     * redis中用户一小时内(app)登录成功次数缓存前缀
     */
    String REDIS_LOGIN_NUM_APP = "LoginNumApp:";

    /**
     * Redis websocket uid前缀
     */
    String REDIS_WEBSOCKET_UID_PRE = "WsU:";

    /**
     * Redis websocket tid前缀
     */
    String REDIS_WEBSOCKET_TID_PRE = "WsT:";
    /**
     * 发送短信码redisKey 结构为：redisKey + target + ":send"
     */
    String REDIS_SEND = ":send";
    /**
     * 短信码实体redisKey 结构为：redisKey + target + ":code"
     */
    String REDIS_CODE = ":code";

    String LogUserUp = "L$UserUp";
}
