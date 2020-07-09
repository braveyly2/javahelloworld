package com.hust.lw.controller;

import com.hust.lw.model.entity.User;
import org.springframework.web.bind.annotation.*;


@RestController
public class HelloController {

    @RequestMapping(value="/hello", method= RequestMethod.POST)
    @ResponseBody
    public User Hello(){
        User user = new User();
        user.setId(new Long((long)1));
        user.setUserName("liwei");
        user.setPassword("123456");
        user.setEmail("sjliwei2002@126.com");
        user.setAddress("china guangdong province shenzhen city");
        return user;
    }

    @RequestMapping(value="/hello1", method= RequestMethod.POST)
    @ResponseBody
    public User Hello1(@RequestBody User user){
        User userResult = new User();
        userResult.setId(user.getId());
        userResult.setUserName(user.getUserName());
        userResult.setPassword(user.getPassword());
        userResult.setEmail(user.getEmail());
        userResult.setAddress(user.getAddress());
        userResult.setDateTime(user.getDateTime());
        return userResult;
    }

    @RequestMapping(value="/hello2", method= RequestMethod.POST)
    @ResponseBody
    public User Hello2(@RequestBody User user){
        return user;
    }
}
