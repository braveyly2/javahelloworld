package com.hust.lw.controller;

import com.hust.lw.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController2 {

    @RequestMapping("hello2")
    public ModelAndView handleRequest2(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("message", "Hello Spring MVC2");
        mav.addObject("title", "LW");
        return mav;
    }

    @RequestMapping("param")
    public ModelAndView getParam(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        System.out.println("this is para");
        String userName = httpServletRequest.getParameter("userName");
        String password = httpServletRequest.getParameter("password");

        System.out.println(userName);
        System.out.println(password);
        return null;
    }

    @RequestMapping("param1")
    //同名匹配原则，方法形参名与前端传入参数名相同
    //缺陷是与前端耦合了
    public ModelAndView getParam1(String userName, String password) throws Exception {
        System.out.println("this is para1");
        System.out.println(userName);
        System.out.println(password);
        return null;
    }

    @RequestMapping("param2")
    //使用 @RequestParam("前台参数名") 来注入
    public ModelAndView getParam2(@RequestParam("userName") String name, @RequestParam("password") String pass) throws Exception {
        System.out.println("this is para2");
        System.out.println(name);
        System.out.println(pass);
        return null;
    }

    @RequestMapping("param3")
    //使用模型传入,模型的字段名必须与前端传入参数名保持一致
    public ModelAndView getParam3(User user) throws Exception {
        System.out.println("this is getpara3");
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        return null;
    }

    @RequestMapping(value="param/param4/{id}", method= RequestMethod.GET)
    public ModelAndView getParam4(@PathVariable("id") Integer id) throws Exception {
        System.out.println("this is id=" + id);
        return null;
    }
}
