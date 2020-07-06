package com.hust.lw.controller;

import com.hust.lw.model.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String Hello(){
        return "hello";
    }

    @RequestMapping("/user")
    public User Hello(@RequestBody User user){
        User userResult = new User();
        userResult.setId(user.getId());
        userResult.setUserName(user.getUserName()+"-hello");
        userResult.setPassword(user.getPassword());
        userResult.setAge(user.getAge());
        return userResult;
    }

}
