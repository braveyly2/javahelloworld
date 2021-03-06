package com.hust.lw.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicOutput {
    private String id;

    private long time = PublicUtil.getUtcTimestampOfSecond();

    private int code = 200;

    private String msg;

    private String sign;
}
