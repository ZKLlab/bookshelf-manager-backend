package com.shuosc.books.web.model;

import com.shuosc.books.web.enums.HoldingState;


public class GetBookDtoHolding {
    private String id;
    private String barcode;
    private String place;
    private Integer shelf;
    private Integer row;
    private String callNumber;
    private HoldingState state;

    public GetBookDtoHolding() {
    }

    public GetBookDtoHolding(String id, String barcode, String place, Integer shelf, Integer row, String callNumber, HoldingState state) {
        this.id = id;
        this.barcode = barcode;
        this.place = place;
        this.shelf = shelf;
        this.row = row;
        this.callNumber = callNumber;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

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
