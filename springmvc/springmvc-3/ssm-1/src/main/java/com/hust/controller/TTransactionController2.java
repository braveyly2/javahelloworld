package com.hust.controller;

import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Validated
@Controller
public class TTransactionController2 {
    @RequestMapping(value = "/transaction2/demo3", method = RequestMethod.GET)
    @ResponseBody
    public void demo3(@Range(min = 1, max = 9, message = "年级只能从1-9")
                      @RequestParam(value = "grade", required = true)
                              int grade,
                      @Min(value = 1, message = "班级最小只能1")
                      @Max(value = 99, message = "班级最大只能99")
                      @RequestParam(value = "classroom", required = true)
                              int classroom) {

        System.out.println(grade + "," + classroom);
    }
}
