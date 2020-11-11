package com.hust.lw.enums;

public enum Member {
    FATHER(1,"爸爸"), MOTHER(2,"妈妈");
    private int code;
    private String name;

    Member(int code, String name) {
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

    public static Member getMember(int code) {
        for(Member member : Member.values()) {
            if(member.getCode() == code) {
                return member;
            }
        }
        return null;
    }
}
