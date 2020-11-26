package com.hust.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalConfig {
    @ModelAttribute(value = "msg")
    public String message() {
        return "欢迎访问 hangge.com";
    }

    @ModelAttribute(value = "info")
    public Map<String, String> userinfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "hangge");
        map.put("age", "100");
        return map;
    }
}
