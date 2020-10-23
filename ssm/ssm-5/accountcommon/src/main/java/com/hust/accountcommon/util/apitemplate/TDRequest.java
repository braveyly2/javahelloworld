package com.hust.accountcommon.util.apitemplate;
import com.hust.accountcommon.entity.dto.TokenDataDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lw
 * @Title: TDRequest
 * @Description: 通用请求报文类
 * @date 2018/9/10 10:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TDRequest<T> {

    private BasicInput basic;


    private T data;

    private TokenDataDto tokenDataDto;
}
