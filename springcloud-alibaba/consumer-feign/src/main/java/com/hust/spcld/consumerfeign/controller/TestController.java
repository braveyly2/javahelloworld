package com.hust.spcld.consumerfeign.controller;

import com.hust.spcld.consumerfeign.feign.IProviderTestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
/**
 * 类功能描述
 *
 * @author nick.liwei
 * @since 2021-05-18
 */
@RestController
public class TestController {
    @Autowired
    IProviderTestController providerTestController;

    @GetMapping("/clienthello")
    public String clientHello(@RequestParam String content){
        String result = providerTestController.serverHello(content);
        return "Result:"+result;
    }
}
