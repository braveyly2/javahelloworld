package com.hust.spcld.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类功能描述
 *
 * @author nick.liwei
 * @since 2021-05-18
 */
@RestController
public class TestController {
    @GetMapping("/serverhello")
    public String serverHello(@RequestParam String content){
        return "provider hello  " + content;
    }
}
