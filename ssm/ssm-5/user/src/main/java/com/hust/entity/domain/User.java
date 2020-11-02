package com.hust.entity.domain;

public class User {
    private Long id;

    private String phone;

    private String email;

    private String vxId;

    private String vxNickname;

    private String password;

    private String mark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVxId() { return vxId; }

    public void setVxId(String vxId) { this.vxId = vxId; }

    public String getVxNickname() { return vxNickname; }

    public void setVxNickname(String vxNickname) { this.vxNickname = vxNickname; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}