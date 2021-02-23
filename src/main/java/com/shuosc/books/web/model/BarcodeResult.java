package com.shuosc.books.web.model;

public class BarcodeResult{
    public boolean isHorizontal() {
        return isHorizontal;
    }

    public int getResultPoint() {
        return resultPoint;
    }

    public String getText() {
        return text;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    public void setResultPoint(int resultPoint) {
        this.resultPoint = resultPoint;
    }

    public void setText(String text) {
        this.text = text;
    }
    private static boolean isHorizontal;//false: the code is vertical,x values of the 2 points are the same
    private int resultPoint;
    private String text;

}
