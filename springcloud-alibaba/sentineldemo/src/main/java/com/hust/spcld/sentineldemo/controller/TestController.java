package com.hust.spcld.sentineldemo.controller;

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
    @GetMapping("/sentinelhello")
    public String sentinelHello(@RequestParam String content){
        try{
        Thread.sleep(600);
        }catch(Exception e){
            System.out.println(e.getStackTrace());
        }
        return "sentinel hello  " + content;
    }
}
