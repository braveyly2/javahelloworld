package com.cloud.consume.controller;

import com.cloud.consume.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumeController {
    @Autowired
    ComputerService computerService;

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String add(){
        return computerService.add();
        //return "liwei";
    }

}
