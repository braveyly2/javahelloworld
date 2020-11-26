package com.hust.controller;

import com.hust.Exceptions.BaseException;
import com.hust.Exceptions.MyException1;
import com.hust.Exceptions.MyException2;
import com.hust.entity.TTransaction;
import com.hust.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
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

    @RequestMapping("/ex1")
    public Object throwBaseException() throws Exception {
        throw new BaseException("This is BaseException.");
    }

    @RequestMapping("/ex2")
    public Object throwMyException1() throws Exception {
        throw new MyException1("This is MyException1.");
    }

    @RequestMapping("/ex3")
    public Object throwMyException2() throws Exception {
        throw new MyException2("This is MyException1.");
    }

    @RequestMapping("/ex4")
    public Object throwIOException() throws Exception {
        throw new IOException("This is IOException.");
    }

    @RequestMapping("/ex5")
    public Object throwNullPointerException() throws Exception {
        throw new NullPointerException("This is NullPointerException.");
    }

    @ExceptionHandler(NullPointerException.class)
    public String controllerExceptionHandler(HttpServletRequest req, Exception e) {
        System.out.println("---ControllerException Handler---Host {} invokes url {} ERROR: {}"+ e.getMessage());
        return e.getMessage();
    }
}
