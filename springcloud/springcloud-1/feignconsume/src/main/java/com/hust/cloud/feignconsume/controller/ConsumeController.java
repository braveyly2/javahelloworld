package com.hust.cloud.feignconsume.controller;

import com.hust.cloud.feignconsume.util.ComputerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumeController {

    @Autowired
    ComputerClient computerClient;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Integer add() {
            return computerClient.add(10, 20);
    }
}
