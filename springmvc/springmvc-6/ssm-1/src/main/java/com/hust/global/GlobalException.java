package com.hust.global;

import com.hust.Exceptions.BaseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Object baseErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        System.out.println("---BaseException Handler---Host {} invokes url {} ERROR: {}"+ e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        System.out.println("---DefaultException Handler---Host {} invokes url {} ERROR: {}"+ e.getMessage());
        return e.getMessage();
    }
}
