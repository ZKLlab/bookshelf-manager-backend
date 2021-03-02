package com.shuosc.books.web.model;

public class Return {
    private int returnCode;
    private String msg;
    private String identity;
    private Object o;

    public Return(int returnCode, String msg, String identity, Object o) {
        this.returnCode = returnCode;
        this.msg = msg;
        this.identity = identity;
        this.o = o;
    }

    public static Return success(String msg) {
        return new Return(200, msg, null, null);
    }

    public static Return success(String msg, String identity) {
        return new Return(200, msg, identity, null);
    }

    public static Return success(String msg, String identity, Object o) {

        return new Return(200, msg, identity, o);
    }

    public static Return success(String msg, Object o) {
        return new Return(200, msg, null, o);
    }

    public static Return failure(String msg) {
        return new Return(500, msg, null, null);
    }

    public static Return failure(String msg, Object o) {
        return new Return(500, msg, null, o);
    }

    public int getreturnCode() {
        return returnCode;
    }

    public void setRetCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
}
