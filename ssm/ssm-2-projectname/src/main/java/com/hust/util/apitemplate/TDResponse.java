package com.hust.util.apitemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TDResponse<T> {
    private BasicOutput basic;

    //@JsonSerialize
    private T data;
}
