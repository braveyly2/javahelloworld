package com.hust.controller;

import com.hust.annotation.TDAuth;
import com.hust.entity.User;
import com.hust.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="login",method= RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value="userInfo",method= RequestMethod.GET)
    public String userinfo(){
        return "userInfo";
    }

    @RequestMapping(value="register",method= RequestMethod.GET)
    public String register(){
        return "register";
    }

    @RequestMapping(value="/user/login",method=RequestMethod.POST)
    public String login(HttpSession session, Model model, @RequestParam("userName") String name, @RequestParam("password") String password){
        User user = userService.selectByName(name);
        if(null == user){
            model.addAttribute("status","1");
        }
        else{
            if(user.getName().equals(name) && user.getPassword().equals(password)) {
                model.addAttribute("status", "0");
                session.setAttribute("user", user);
                return "userInfo";
            }
            else if(user.getName().equals(name) && !user.getPassword().equals(password)) {
                model.addAttribute("status", "2");
                session.setAttribute("user", user);
            }
        }

        return "login";
    }

    @RequestMapping(value="/user/sessionInfo",method=RequestMethod.GET)
    public String sessionInfo(HttpSession session){
        System.out.println("session.id=" + session.getId());
        User user = (User) session.getAttribute("user");
        System.out.println("username =" + (String) user.getName());
        return "userInfo";
    }
    @RequestMapping(value="/user/logout",method=RequestMethod.GET)
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @RequestMapping(value="/user/register",method=RequestMethod.POST)
    public String register(Model model, @RequestParam("userName") String name, @RequestParam("password") String password){
        User user = userService.selectByName(name);
        //cannot register for the same username
        if(null != user){
            model.addAttribute("status","1");
            return "register";
        }

        User userAdd = new User();
        userAdd.setName(name);
        userAdd.setPassword(password);
        userAdd.setMark("thisisforregister");

        userService.insert(userAdd);
       // model.addAttribute("status","0");
        return "register";
    }

    @TDAuth(isCheckToken = false, value = "admin")
    @RequestMapping(value="/authannotest",method= RequestMethod.POST)
    public String authannotest(String name){
        return "login";
    }

    @RequestMapping(value="/authannotest2",method= RequestMethod.POST)
    private String authannotest2(String name){

        return "login";
    }

    @RequestMapping(value="/authannotest3",method= RequestMethod.POST)
    public String authannotest3(String name){

        return "login";
    }

    @RequestMapping(value="/modifyuser",method= RequestMethod.POST)
    @ResponseBody
    public User modifyUser(@RequestBody User user){
        return user;
    }
}
