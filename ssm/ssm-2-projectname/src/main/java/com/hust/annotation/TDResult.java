package com.hust.annotation;

import java.lang.annotation.*;

/**
 * @author Lyh
 * @Title: result
 * @Description: 自定义参数验证注解
 * @date 2018/9/13 16:57
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TDResult {

    /** 使用需要验证TOKEN */
    boolean isNeedCheckToken() default true;

    String authorityCode() default "";

    boolean isNeedCheckApiVersion() default false;

}