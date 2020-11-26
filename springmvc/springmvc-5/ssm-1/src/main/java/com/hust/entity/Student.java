package com.hust.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;

public class Student {

    //@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    @NumberFormat(pattern = "##,###.##")
    private Double price;

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate(){
        return date;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public Double getPrice(){
        return this.price;
    }
}
