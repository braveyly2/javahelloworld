package com.hust.lw.controller;

import com.hust.lw.model.entity.User;
import com.hust.lw.utils.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HelloController {
    @Value("${test.user.username}")
    private String name;

    @Value("${test.user.desc}")
    private String password;

    @Autowired
    UserConfig userConfig;

    @RequestMapping(value="/hello", method= RequestMethod.POST)
    @ResponseBody
    public User Hello(){
        User user = new User();
        user.setId(new Long((long)1));
        user.setUserName(name);
        user.setPassword(password);
        return user;
    }

    @RequestMapping(value="/hello1", method= RequestMethod.POST)
    @ResponseBody
    public Map<String,String> Hello1(){
        return userConfig.getPerson();
    }

    @RequestMapping(value="/hello2", method= RequestMethod.POST)
    @ResponseBody
    public List<String> Hello2(){
        return userConfig.getList();
    }
}
