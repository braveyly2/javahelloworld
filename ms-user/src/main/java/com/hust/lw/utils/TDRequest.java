package com.hust.lw.utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TDRequest<T> {

    private BasicInput basic;


    private T data;
}
