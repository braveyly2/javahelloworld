package com.hust.lw.controller;

import com.hust.lw.model.entity.User;
import com.hust.lw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping(value="/add", method = RequestMethod.POST)
    public boolean add(@RequestBody User user) {
        System.out.println("开始新增...");
        userService.create(user.getUserName(), user.getAge());
        return true;
    }

    @RequestMapping(value="/delete", method = RequestMethod.POST)
    public boolean delete(@RequestBody User user) {
        System.out.println("开始删除...");
        userService.deleteByName(user.getUserName());
        return true;
    }
}
