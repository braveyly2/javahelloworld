package com.hust.lw.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class ControllerLogAspect {
    //private Logger logger = Logger.getLogger(getClass());

    @Pointcut("execution(public * com.hust.lw.controller.UserController.*(..))")
    public void ControllerLog(){

    }

    @Before("ControllerLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        //接收到请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("IP : " + request.getRemoteAddr());
        //请求的信息
        //log.info("URL : " + request.getRequestURL().toString());
        //log.info("HTTP_METHOD : " + request.getMethod());
        //log.info("IP : " + request.getRemoteAddr());
        //方法的信息
        //log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning="ret", pointcut="ControllerLog()")
    public void doAfterReturning(Object ret) throws Throwable{
        //log.info("Response :" + ret);
    }
}
