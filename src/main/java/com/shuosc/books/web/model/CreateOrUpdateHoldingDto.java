package com.shuosc.books.web.model;

import com.shuosc.books.web.enums.HoldingState;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class CreateOrUpdateHoldingDto {
    @NotNull
    private String place;
    @NotNull
    @Min(1)
    private Integer shelf;
    @NotNull
    @Min(1)
    private Integer row;
    @NotNull
    @NotBlank
    private String callNumber;
    @NotNull
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
