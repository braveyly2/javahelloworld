package com.hust.entity;

public class RestBean {
    private Integer status;
    private String msg;
    private Object obj;

    public static RestBean ok(String msg){
        return new RestBean(200, msg, null);
    }

    public static RestBean ok(String msg, Object obj){
        return new RestBean(200, msg, obj);
    }

    public static RestBean error(String msg){
        return new RestBean(500, msg, null);
    }

    public static RestBean error(String msg, Object obj){
        return new RestBean(500, msg, obj);
    }

    private RestBean() {

    }

    private RestBean(Integer status, String msg, Object obj){
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    public static RestBean build(){
        return new RestBean();
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }

    public void setObj(Object obj){
        this.obj = obj;
    }

    public Object getObj(){
        return obj;
    }
}
