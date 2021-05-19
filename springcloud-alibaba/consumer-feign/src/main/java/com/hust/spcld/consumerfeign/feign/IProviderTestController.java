package com.hust.spcld.consumerfeign.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 类功能描述
 *
 * @author nick.liwei
 * @since 2021-05-18
 */
@FeignClient(name = "provider")
public interface IProviderTestController {
    @GetMapping("/serverhello")
    public String serverHello(@RequestParam String content);
}
