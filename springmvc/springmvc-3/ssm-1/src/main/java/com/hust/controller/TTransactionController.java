package com.hust.controller;

import com.hust.entity.TTransaction;
import com.hust.entity.User;
import com.hust.validator.ValidatorGroup1;
import com.hust.validator.ValidatorGroup2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.servlet.http.HttpSession;
import javax.validation.groups.Default;
import java.util.Date;
import java.util.Set;

@Controller
public class TTransactionController {

    @Autowired
    Validator validator;

    @RequestMapping(value="/transaction/testvalidator",method= RequestMethod.POST)
    @ResponseBody
    public String testValidator(@RequestBody @Validated({ValidatorGroup2.class}) TTransaction transaction, BindingResult bindingResult){
        System.out.println(transaction);
        if(bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());

            }
            //TODO:进一步
            throw new ConstraintViolationException("testvalidator",null);
        }
        //Validation.byProvider()
        return "userInfo";
    }

    @RequestMapping(value="/transaction/testbarevalidator",method= RequestMethod.POST)
    @ResponseBody
    public String testBareValidator(){
        TTransaction tTransaction = new TTransaction();
        tTransaction.setProductId(1L);
        tTransaction.setUserId(1L);
        tTransaction.setAmount(99.0);
        Date date = new Date();

        tTransaction.setDate(date);
        tTransaction.setEmail("111@111.com");
        tTransaction.setPrice(22.2);
        tTransaction.setNote("liwei");
        tTransaction.setQuantity(150);
        Set<ConstraintViolation<TTransaction>> violationSet = validator.validate(tTransaction);
        for (ConstraintViolation<TTransaction> model : violationSet) {
            System.out.println(model.getMessage());
        }
        return null;
    }
}
