package com.hust.accountcommon.util.apitemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lw
 * @Title: TDResponse
 * @Description: 通用响应报文类
 * @date 2018/9/10 10:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TDResponse<T> {
    private BasicOutput basic;

    //@JsonSerialize
    private T data;
}
