package com.hust.lw.model.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class User {
    private Long id;
    private String userName;
    private String password;

}
