
package com.hust.spcld.consumerresttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ConsumerResttemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerResttemplateApplication.class, args);
    }

}
