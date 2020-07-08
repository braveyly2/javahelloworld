package com.hust.lw;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2Doc
@SpringBootApplication
public class LwApplication {

    public static void main(String[] args) {
        SpringApplication.run(LwApplication.class, args);
    }

}
