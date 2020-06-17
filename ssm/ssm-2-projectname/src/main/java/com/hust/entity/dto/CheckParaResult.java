package com.hust.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CheckParaResult {
    private List<String> checkResults = new ArrayList<>();
}
