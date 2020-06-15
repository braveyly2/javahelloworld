package com.hust.lw.model.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class CheckParaResult {
    private List<String> checkResults = new ArrayList<>();
}
