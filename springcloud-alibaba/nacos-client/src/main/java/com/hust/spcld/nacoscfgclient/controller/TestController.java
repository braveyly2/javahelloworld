package com.hust.spcld.nacoscfgclient.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.hust.spcld.nacoscfgclient.config.AliyunApiConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 类功能描述
 *
 * @author nick.liwei
 * @since 2021-05-18
 */
@RestController
@RefreshScope
public class TestController {
    //@NacosValue(value = "${config.info}", autoRefreshed = true)
    @Value("${config.info}")
    private String configInfo;

    @Resource
    private AliyunApiConfig aliyunApiConfig;

    @GetMapping("/cfghello")
    public String cfgHello(@RequestParam String content){
        return "provider hello  " + content + configInfo + aliyunApiConfig.getServiceAppKey();
    }
}
