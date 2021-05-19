package com.hust.spcld.consumerresttemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 类功能描述
 *
 * @author nick.liwei
 * @since 2021-05-18
 */
@RestController
public class TestController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;

    @GetMapping("/clienthello")
    public String clientHello(@RequestParam String content){
        String result = restTemplate.getForObject("http://provider/serverhello?content="+content, String.class);
        return "Return : " + result;
    }

    @GetMapping("/clienthello2")
    public Mono<String> clientHello2(@RequestParam String content){
        Mono<String> result = webClientBuilder.build()
                .get()
                .uri("http://provider/serverhello?content="+content)
                .retrieve()
                .bodyToMono(String.class);
        return result;
    }
}
