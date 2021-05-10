package com.example.hust;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.example.hust.dao")
@SpringBootApplication
public class HustApplication {

    public static void main(String[] args) {
        SpringApplication.run(HustApplication.class, args);
    }

}
