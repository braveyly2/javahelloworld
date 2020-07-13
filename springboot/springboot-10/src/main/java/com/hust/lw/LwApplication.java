package com.hust.lw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.UnsupportedEncodingException;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.hust.lw.*"})
public class LwApplication {

    static final Logger logger = LoggerFactory.getLogger(LwApplication.class);

    public static void main(String[] args) {
        logger.debug("Start process {}...", LwApplication.class.getName());
        try {
            "".getBytes("invalidCharsetName");
        } catch (UnsupportedEncodingException e) {
            // TODO: 使用logger.error(String, Throwable)打印异常
        }
        logger.debug("Process[debug] end.");
        logger.info("Process[info] end.");
        logger.error("Process[error] end.");
        SpringApplication.run(LwApplication.class, args);
    }

}
