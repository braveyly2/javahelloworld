package com.hust.lw.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Data
public class User {
    private Long id;

    private String userName;

    @JsonIgnore
    private String password;

    private String email;

    @JsonProperty("Address")
    private String Address;

    private LocalDateTime dateTime;

    @JsonProperty("dateTime")
    private String getDateTimeStr() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return df.format(dateTime);
    }

    @JsonProperty("dateTime")
    private void setDateTimeStr(String dateTimeStr) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTime = LocalDateTime.parse(dateTimeStr,df);
    }
}
