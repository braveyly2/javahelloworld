package com.hust.lw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.hust.lw.mapper")
@SpringBootApplication
public class LwApplication {

    public static void main(String[] args) {
        SpringApplication.run(LwApplication.class, args);
    }

}
