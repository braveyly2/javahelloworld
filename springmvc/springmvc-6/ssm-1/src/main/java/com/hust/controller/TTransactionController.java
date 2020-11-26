package com.hust.controller;

import com.hust.entity.TTransaction;
import com.hust.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class TTransactionController {


    @RequestMapping(value="/transaction/testvalidator",method= RequestMethod.POST)
    @ResponseBody
    public String testValidator(@Valid @RequestBody TTransaction transaction, BindingResult bindingResult){
        System.out.println(transaction);
        if(bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
        }
        return "userInfo";
    }

    @RequestMapping(value="/transaction/testmodelattribute",method= RequestMethod.GET)
    public String testModelAttribute(Model model){
        Map<String, Object> map = model.asMap();
        String msg = map.get("msg").toString();
        System.out.println(msg);
        return "userInfo";
    }
}