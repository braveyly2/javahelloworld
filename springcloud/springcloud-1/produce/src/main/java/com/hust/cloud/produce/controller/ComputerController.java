package com.hust.cloud.produce.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class ComputerController {
    @GetMapping(value="/add")
    public Integer add(@RequestParam Integer a, @RequestParam Integer b){
        Integer r = a + b;
        System.out.println("ComputerController:add");
        return r;
    }
}
