package com.hust.util;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TDResponse<T> {
    private BasicOutput basic;

    @JsonSerialize
    private T data;
}
