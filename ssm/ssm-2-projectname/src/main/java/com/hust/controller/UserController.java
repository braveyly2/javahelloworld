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

    @RequestMapping(value="/user/register",method=RequestMethod.POST)
    @ResponseBody
    public String register(@RequestParam("userName") String name, @RequestParam("password") String password){
        User user = userService.selectByName(name);
        //cannot register for the same username
        if(null != user){
            return "fail";
        }

        User userAdd = new User();
        userAdd.setName(name);
        userAdd.setPassword(password);
        userAdd.setMark("thisisforregister");

        userService.insert(userAdd);
        // model.addAttribute("status","0");
        return "ok";
    }

    @RequestMapping(value="/user/register2",method=RequestMethod.POST)
    @ResponseBody
    public String register2(@RequestBody User user){
        User userExist = userService.selectByName(user.getName());
        //cannot register for the same username
        if(null != userExist){
            return "fail";
        }

        User userAdd = new User();
        userAdd.setId(user.getId());
        userAdd.setName(user.getName());
        userAdd.setPassword(user.getPassword());
        userAdd.setMark(user.getMark());

        userService.insert(userAdd);
        // model.addAttribute("status","0");
        return "ok";
    }

    @RequestMapping(value="/user/register3",method=RequestMethod.POST)
    @ResponseBody
    public RestBean register3(@RequestBody User user){
        User userExist = userService.selectByName(user.getName());
        //cannot register for the same username
        if(null != userExist){
            return RestBean.error("user:" + user.getName() + " is exist");
        }

        User userAdd = new User();
        userAdd.setId(user.getId());
        userAdd.setName(user.getName());
        userAdd.setPassword(user.getPassword());
        userAdd.setMark(user.getMark());

        userService.insert(userAdd);
        // model.addAttribute("status","0");
        return RestBean.ok("Succeed to add user",userAdd);
    }
}
