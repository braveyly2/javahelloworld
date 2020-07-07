package com.hust.lw.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "com.didispace")
public class FooProperties {
    private String foo;
}
