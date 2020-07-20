package com.cloud.msorder.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class OrderController {
    @Value("${from}")
    private String from;

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String add(){
        return this.from;
    }
}
