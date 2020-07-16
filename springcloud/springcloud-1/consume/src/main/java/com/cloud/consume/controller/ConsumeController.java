package com.cloud.consume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumeController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String add(){
        return restTemplate.getForEntity("http://computer-service/add?a=10&b=20", String.class).getBody();
        //return "liwei";
    }

}
