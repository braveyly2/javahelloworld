
package com.hust.spcld.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication2 {

    public static void main(String[] args) {
        System.out.println("gatewayapplication 2");
        SpringApplication.run(GatewayApplication2.class, args);
    }

}
