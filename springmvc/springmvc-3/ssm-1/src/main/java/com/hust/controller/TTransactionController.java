package com.hust.controller;

import com.hust.entity.TTransaction;
import com.hust.entity.User;
import com.hust.validator.ValidatorGroup1;
import com.hust.validator.ValidatorGroup2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.groups.Default;

@Controller
public class TTransactionController {


    @RequestMapping(value="/transaction/testvalidator",method= RequestMethod.POST)
    @ResponseBody
    public String testValidator(@RequestBody @Validated({Default.class}) TTransaction transaction, BindingResult bindingResult){
        System.out.println(transaction);
        if(bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
        }
        return "userInfo";
    }
}
