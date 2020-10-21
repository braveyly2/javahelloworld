package com.hust.util.apitemplate;
import com.hust.entity.dto.TokenDataDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TDRequest<T> {

    private BasicInput basic;


    private T data;

    private TokenDataDto tokenDataDto;
}
