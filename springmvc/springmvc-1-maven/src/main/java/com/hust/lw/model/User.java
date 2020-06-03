package com.hust.lw.model;

public class User {
    private String userName;
    private String password;

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }
    // 注意set一定要写this.
    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
