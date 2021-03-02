package com.shuosc.books.web.dto;

import com.shuosc.books.web.model.HoldingState;

public class HoldingDto {

    private String place;
    private Integer shelf;
    private Integer row;
    private String callNumber;
    private HoldingState state;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getShelf() {
        return shelf;
    }

    public void setShelf(Integer shelf) {
        this.shelf = shelf;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public HoldingState getState() {
        return state;
    }

    public void setState(HoldingState state) {
        this.state = state;
    }
}
