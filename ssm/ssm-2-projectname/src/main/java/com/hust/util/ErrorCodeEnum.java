package com.hust.util;

public enum ErrorCodeEnum {
    TD200(200, "operate successfully"),//请求成功
    TD201(201, "operate partial failure"),//部分成功,部分失败

    TD1000(1000,"Parameter class error starting code"),//参数类错误起值
    TD1001(1001,"Protocol version is required"),//协议版本参数缺失
    TD1002(1002,"Message id is required"),//消息ID参数缺失
    TD1003(1003,"UTCTime is required"),//UTCTime参数缺失
    TD1004(1004,"Nonce is required"),//Nonce参数缺失
    TD1005(1005, "Image verification code is error"),//图片验证码错误
    TD1006(1006, "Id of image verification code is error"),//图片验证码标识码错误
    TD1007(1007, "Image verification code is required"),//需要图片验证码
    TD1008(1008, "SMS dynamic code time out"),//短信动态码已过期
    TD1009(1009, "SMS dynamic code is error"),//短信动态码错误
    TD1010(1010, "Data contains sensitive words"),//包含敏感信息
    TD1011(1011, "Parameter is invalid"),//参数无效，如缺少必填字段，或类型不匹配
    TD1012(1012, "API is not recognized"),//API无法识别
    TD1013(1013, "Send dynamic code error"),//发送动态码异常日志上传接口url
    TD1014(1014, "WeChat code error"),//微信code错误
    TD1015(1015, "name is duplicate"),//名称重复
    TD1016(1016, "No binding phone number"),//没有绑定的手机号
    TD1017(1017, "WeChat account does not exist"),//微信账号不存在
    TD1018(1018, "No binding Email"),//没有绑定邮箱
    TD1019(1019, "The phone is not bound, so the E-mail cannot be unbind"),//没有绑定手机号，所以邮箱不能解绑
    TD1020(1020, "No Email is bound, so the phone cannot be unbundled"),//没有绑定邮箱，因此不能解绑该手机
    TD1021(1021, "Application record does not exist"),//申请记录不存在
    TD1022(1022, "Group does not exist"),//分组不存在

    TD3000(3000, "WeChat errors"),//第三方微信接口调用失败

    TD4500(4500,"Parameter class customer error starting code"),//参数类错误自定义区

    TD5000(5000,"Permission class error starting code"),//权限类错误起值

    TD6000(6000,"Operation step class error starting code"),//操作步骤类错误起值
    TD6001(6001,"Too frequent operation"),//操作过于频繁
    TD6002(6002,"Not support the api version"),//api版本不支持
    TD6003(6003,"Api input data structure error"),//api入参数据结构错误
    TD6004(6004,"Write file fail"),//写文件失败

    TD6500(6500,"Operation step class customer error starting code"),//操作步骤类错误自定义区,根据具体业务操作步骤错误定义

    TD7000(7000,"Verification class error starting code"),//密码、Token、签名、CRC等错误
    TD7001(7001,"User is not exist"),//用户不存在
    TD7002(7002,"Password error"),//密码错误
    TD7003(7003,"Token invalid"),//Token不合法
    TD7004(7004,"Token time out"),//Token过期
    TD7005(7005,"Signature invalid"),//签名错误
    TD7006(7006,"Mobile already exist"),//手机号码已存在
    TD7007(7007,"User is locked"),//用户被锁定
    TD7008(7008,"DC id is not exist"),//数据中心ID不存在
    TD7009(7009,"DC id is not exist"),//Token在黑名单中
    TD7010(7010,"Admin invalid"),//管理员账号未激活
    TD7011(7011,"User invalid"),//账号未激活
    TD7012(7012,"DC ip invalid"),//数据中心ip不匹配
    TD7013(7013,"DPikey list is null or empty"),//DPikey列表为空
    TD7014(7014,"Security code is invalid"),//安全码无效
    TD7015(7015,"SN is not exist or is already bind"),//安全码无效
    TD7016(7016,"Group name is existed"),//分组名已经存在
    TD7017(7017,"Already friend"),//已经是好友
    TD7018(7018,"Already send adding friend request"),//已经发送过好友申请请求
    TD7019(7019,"User already exist"),//用户已存在
    TD7020(7020,"User also own devices"),
    TD7021(7021,"Some hosts in hostGroup，delete failed"),
    TD7022(7022,"Group name is repeat"),
    TD7023(7023,"Email already exist"),//邮箱已存在
    TD7024(7024,"Can not send friend request to yourself"),//不能向自己发送好友申请
    TD7025(7025,"Terminal binding is used.Unsafe Terminal Address"),//
    TD7026(7026,"Over maximum share"),//超过最大分享数
    TD7027(7027,"Channel is not exist"),//通道不存在
    TD7028(7028,"Module has been used"),
    TD7029(7029,"Module name is repeated"),//模块名出现重复
    TD7030(7030,"APP Config  is repeated"),//项目配置出现重复配置
    TD7031(7031,"Upgrade task is not exist"),//云升级任务不存在
    TD7032(7032,"Upgrade package already exist"),//升级包已存在
    TD7033(7033,"Cloud file is not exist"),//云存储文件不存在
    TD7034(7034,"Can not delete package"),//存在云升级任务，不允许删除
    TD7035(7035,"Status is wrong"),//不符合条件
    TD7036(7036,"Upgrade strategy is not exist"),//策略不存在
    TD7037(7037,"Status is wrong"),//待审核状态下不允许申请
    TD7038(7038,"Inconsistent renewal types"),//续费类型不一致
    TD7039(7039,"It can only be superimposed once at most"),//最多只能叠加一次
    TD7040(7040,"Device does not exist or is not online"),//设备不存在或不在线
    TD7041(7041,"Can't transfer device to oneself"),//不能转移设备给自己
    TD7042(7042,"Other tasks are in startup"),//有其他任务处于启动状态
    TD7043(7043,"Task not audited"),//任务未审核
    TD7044(7044,"No device eligible for upgrade"),//没有符合升级条件的设备
    TD7045(7045,"Task not passed"),//任务未审核通过
    TD7046(7046,"The number of STS resource requests exceeds the limit"),//请求STS资源次数超出限制
    TD7047(7047,"Target is not a friend"),//转移目标不是好友
    TD7048(7048,"Device does not exist"),//设备不存在
    TD7049(7049,"Channel not shared with this user"),//通道没有分享给此用户
    TD7050(7050,"The number of batch deletions cannot exceed 1000"),//批量删除个数不能超过1000
    TD7051(7051,"Channel grouping does not exist"),//通道分组不存在
    TD7052(7052,"Channel grouping does not exist"),//默认分组不允许删除
    TD7053(7053,"Channel does not exist"),//通道不存在
    TD7054(7054,"Unrecognized cloud storage business type"),//无法识别的云存储业务类型
    TD7055(7055,"No permission to get the channel token"),//没有获取该通道令牌的权限
    TD7056(7056, "Release Version has been used"),//兼容结果列表中已经使用到该版本信息
    TD7057(7057, "Issuing document cannot be empty"),//签发单不能为空
    TD7058(7058, "Hash check fail"),//文件hash值校验失败
    TD7059(7059, "Data check fail"),//文件hash值校验失败
    TD7060(7060, "Encrypt or decrypt exception"),//文件hash值校验失败

    TD9000(9000, "Service exception class error starting code"),//服务异常类错误起值
    TD9001(9001,"Protocol version too old"),//协议版本过低,老版本不再兼容，需升级
    TD9002(9002,"Protocol version error"),//协议版本错误，版本字段无法识别或错误信息
    TD9003(9003, "SMS dynamic code send error"),//短信动态码发送失败
    TD9004(9004, "Data base error"),//数据库操作失败
    TD9005(9005, "Data to be updated is not exist"),//更新失败，数据库中已不存在此数据
    TD9006(9006, "Data to be inserted is exist"),//新增失败，数据库中已存在此数据
    TD9007(9007, "Data to be view is not exist"),//查看失败，数据库中不存在此数据
    TD9008(9008, "Operation is not exist"),//数据库中不存在此操作
    TD9009(9009, "Script is not exist"),//数据库中不存在此脚本
    TD9010(9010, "Execute system script error"),//执行系统脚本错误
    TD9011(9011, "Generate srid error"),//生成srid失败
    TD9012(9012, "Action name is exis"),//Action name is exis
    TD9013(9013, "Hostname is not correct character"),//主机名非法
    TD9014(9014, "Error trigger expression"),//触发器表达式非法
    TD9015(9015, "No item is chosen"),//触发器表达式没有指定监控项
    TD9016(9016, "Repeat trigger name"),//触发器重名
    TD9017(9017, "The number of applications has reached the limit"), //今日申请次数已达上限
    TD9018(9018, "The number of friend group has reached the limit"), //好友群组数目已达上限
    TD9019(9019, "The number of uploading head image has reached the limit"), //今日上传头像数目已达上限
    TD9020(9020, "The number of channel group has reached the limit"), //通道分组数目已达上限
    TD9021(9021, "The number of generating sub report has reached the limit"), //今日生成安全子报告已达上限
    TD9022(9022, "The number of generating health report has reached the limit"), //今日生成健康报告已达上限
    TD9023(9023, "The number of submitting feedback has reached the limit"), //今日意见反馈已达上限
    TD9024(9024, "Download cloud file error"), //云存储文件失败
    TD9025(9025, "Download new cloud file"), //新的云存储文件下载信息


    TD9500(9500, "Service exception"),//比较通用服务的异常，当异常原因不需要告知调用者时，可使用此错误码，比如Redis中记录的RSAKey字符串转对象失败等，只需要在服务记录具体原因的日志供调试分析即可


    TD9600(9600, "Service degradation, interface does not provide service");//服务降级，接口不提供服务
    private int code;
    private String msg;

    /**
     * Msg string.
     *
     * @return the string
     */
    public String msg() {
        return msg;
    }

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Code int.
     *
     * @return the int
     */
    public int code() {
        return code;
    }

    /**
     * Gets enum.
     *
     * @param code the code
     *
     * @return the enum
     */
    public static ErrorCodeEnum getEnum(int code) {
        for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
            if (ele.code() == code) {
                return ele;
            }
        }
        return null;
    }
}
