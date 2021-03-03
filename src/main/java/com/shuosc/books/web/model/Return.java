package com.shuosc.books.web.model;

public class Return {
    private int returnCode;
    private String msg;
    private Object o;

    public Return(int returnCode, String msg, Object o) {
        this.returnCode = returnCode;
        this.msg = msg;
        this.o = o;
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

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
}
