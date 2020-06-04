package com.hust.lw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController3 {
    //原生API完成
    @RequestMapping("value")
    public ModelAndView showValue(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        httpServletRequest.setAttribute("message","成功！");
        System.out.println(httpServletRequest.getContextPath());
        System.out.println(httpServletRequest.getServletPath());
        System.out.println(httpServletRequest.getRequestURI());
        System.out.println(httpServletRequest.getRealPath("/"));
        return new ModelAndView("show");
    }

    //使用modelandview
    @RequestMapping("value1")
    public ModelAndView showValue1(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mav = new ModelAndView("show");
        mav.addObject("message","succeed1");
        return mav;
    }

    //使用model
    @RequestMapping("value2")
    public String showValue2(Model model) throws Exception {
        model.addAttribute("message","succeed2");
        return "show";
    }

    //使用modelAttribute
    @ModelAttribute
    public void model(Model model) {
        model.addAttribute("message", "succeed3");
    }

    @RequestMapping("value3")
    public String showValue3() throws Exception {
        return "show";
    }
}
