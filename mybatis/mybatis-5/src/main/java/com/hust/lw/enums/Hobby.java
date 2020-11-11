package com.hust.lw.enums;

public enum Hobby {
    FOOTBALL(1,"足球"), BASKETBALL(2, "篮球");
    private int code;
    private String name;

    Hobby(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public static Hobby getHobby(int code) {
        for(Hobby h : Hobby.values()) {
            if(h.getCode() == code) {
                return h;
            }
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }
}
