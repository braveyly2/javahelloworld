package com.hust.lw.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "data")
@PropertySource("classpath:application.properties")
@Data
public class UserConfig {

    /*
    变量名必须与配置文件中保持一致
    */
    private Map<String, String> person = new HashMap<>();
    /*
   变量名必须与配置文件中保持一致
   */
    private List<String> list = new ArrayList<>();
}
