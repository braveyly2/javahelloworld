package com.hust.converter;


import com.hust.entity.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//@Component("employeeConverter")
public class EmployeeConverter implements Converter<String, Employee> {

    @Override
    public Employee convert(String str) {
        Employee emp=null;
        //字符串格式 name-age-gender
        if(str!=null && str.split("-").length==3){
            emp=new Employee();
            String[] arr=str.split("-");
            emp.setName(arr[0]);
            emp.setAge(Integer.parseInt(arr[1]));
            emp.setGender(arr[2]);
        }
        return emp;
    }
}