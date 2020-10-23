package com.hust.accountcommon.util.apitemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hust.accountcommon.util.PublicUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lw
 * @Title: BasicOutput
 * @Description: 通用响应报文头部类
 * @date 2018/9/10 10:26
 */
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
