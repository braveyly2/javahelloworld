package com.hust.controller;

import com.hust.entity.RestBean;
import com.hust.entity.User;
import com.hust.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="hello",method=RequestMethod.POST)
    @ResponseBody
    Map<String, Object> hello(@RequestBody User user){
        Map<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("status","success");
        hashmap.put("code",200);
        hashmap.put("user",user);
        return hashmap;
    }

    @RequestMapping(value="hello2",method=RequestMethod.POST)
    @ResponseBody
    RestBean hello2(@RequestBody User user){
        RestBean restBean = RestBean.ok("this is ok");
        restBean.setObj((Object)user);
        return restBean;
    }
}
