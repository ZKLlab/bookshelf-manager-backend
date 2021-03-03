package com.shuosc.books.web.model;

public class Return {
    private Integer code;
    private String msg;
    private Object data;

    public Return(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Return success(String msg) {
        return new Return(200, msg, null);
    }

    public static Return success(String msg, Object o) {
        return new Return(200, msg, o);
    }

    public static Return failure(String msg) {
        return new Return(500, msg, null);
    }

    public static Return failure(String msg, Object o) {
        return new Return(500, msg, o);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
