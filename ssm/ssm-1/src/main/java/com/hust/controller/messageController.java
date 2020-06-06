package com.hust.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
    @RequestMapping("/message/go")
    public String redirect(){
        return "reach";
    }

    @RequestMapping("/message/detail/data={uname}")
    public String searchUser(@PathVariable("uname")String data, Model model){
        model.addAttribute("data", data);
        return "detail";
    }

    @RequestMapping("/message/report1")
    public String report1(String begin, String end, Model model){
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        return "report1";
    }

    @RequestMapping("/message/report2")
    public String report2(@RequestParam("begin") String Nbegin, @RequestParam("end") String Nend, Model model){
        model.addAttribute("begin", Nbegin);
        model.addAttribute("end", Nend);
        return "report1";
    }


}
