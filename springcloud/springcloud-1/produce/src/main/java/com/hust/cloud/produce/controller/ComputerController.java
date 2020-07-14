package com.hust.cloud.produce.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComputerController {
    @PostMapping(value="/add")
    public Integer add(@RequestParam Integer a, @RequestParam Integer b){
        Integer r = a + b;
        return r;
    }
}
