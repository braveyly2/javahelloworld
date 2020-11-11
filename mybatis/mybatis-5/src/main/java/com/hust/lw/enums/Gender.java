package com.hust.lw.enums;

public enum Gender {
    MALE(1, "男性"), FEMALE(2, "女性");
    private int code;
    private String name;

    Gender(int code, String name) {
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

    public void setName(String name) {
        this.name = name;
    }

    public static Gender getGender(int code) {
        for(Gender gender : Gender.values()) {
            if(gender.getCode() == code) {
                return gender;
            }
        }
        return null;
    }
}
