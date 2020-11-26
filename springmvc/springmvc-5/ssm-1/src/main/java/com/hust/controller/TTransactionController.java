package com.hust.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hust.entity.Employee;
import com.hust.entity.Student;
import com.hust.entity.TTransaction;
import com.hust.entity.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @RequestMapping(value="/transaction/testemployee",method= RequestMethod.GET)
    public String testEmployee(@RequestParam("employee") Employee employee){
        System.out.println(employee);
        return "userInfo";
    }

    @RequestMapping(value="/transaction/testemployeemany",method= RequestMethod.GET)
    public String testEmployeemany(@RequestParam("employee") Employee[] employeeArry){
        System.out.println(employeeArry);
        return "userInfo";
    }

    @RequestMapping(value="/transaction/testemployee2",method= RequestMethod.GET)
    public String testEmployee2(@RequestBody Employee employee){
        System.out.println(employee);
        return "userInfo";
    }

    @RequestMapping(value="/transaction/teststudent",method= RequestMethod.POST)
    public String testStudent(@RequestBody Student student){

        System.out.println(student);
        return "userInfo";
    }

    @RequestMapping(value="/transaction/testdateformat",method= RequestMethod.GET)
    public String testDateFormat(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") Date date){
        System.out.println(date);
        return "userInfo";
    }

    @RequestMapping(value="/transaction/testdateformat2",method= RequestMethod.GET)
    public String testDateFormat2(Student student){
        System.out.println(student);
        return "userInfo";
    }

    @RequestMapping(value="/transaction/testgetstudent",method= RequestMethod.GET)
    @ResponseBody
    public Student testGetStudent(){
        SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simFormat.parse("2020-11-11 11:11:11");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        Student student = new Student();
        student.setDate(date);
        student.setPrice(12311.11);
        return student;
    }
}
