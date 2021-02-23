package com.shuosc.books.web.model;

public class BarcodeResult {
    private boolean horizontal; // false: the code is vertical, x values of the 2 points are the same
    private int resultPoint;
    private String text;

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public int getResultPoint() {
        return resultPoint;
    }

    public void setResultPoint(int resultPoint) {
        this.resultPoint = resultPoint;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
