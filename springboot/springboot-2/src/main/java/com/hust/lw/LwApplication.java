package com.hust.lw;

import com.hust.lw.utils.GlobalVariable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LwApplication {

    public static void main(String[] args) {

        GlobalVariable.context = SpringApplication.run(LwApplication.class, args);
        GlobalVariable.binder = Binder.get(GlobalVariable.context.getEnvironment());
    }
}
