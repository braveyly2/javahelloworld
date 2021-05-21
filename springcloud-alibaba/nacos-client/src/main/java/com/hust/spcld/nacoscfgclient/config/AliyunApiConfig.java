package com.hust.spcld.nacoscfgclient.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 类功能描述
 *
 * @author nick.liwei
 * @since 2021-05-21
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aliyun.living")
public class AliyunApiConfig {
    /**
     * 云端接口AppKey
     */
    private String serviceAppKey;

    /**
     * 云端接口AppSecret
     */
    private String serviceAppSecret;

    /**
     * 数据同步AppKey
     */
    private String amqpAppKey;

    /**
     * 数据同步AppSecret
     */
    private String amqpAppSecret;

    /**
     * 自有账号体系AppKey
     */
    private String openIdAppKey;
}
