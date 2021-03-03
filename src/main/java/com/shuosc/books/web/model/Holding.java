package com.shuosc.books.web.model;

import com.shuosc.books.web.enums.HoldingState;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Holding {
    @Id
    private ObjectId id;
    @DBRef
    private Book book;
    private Long barcode;
    private String place;
    private Integer shelf;
    private Integer row;
    private String callNumber;
    private HoldingState state;
    @Version
    private Long version;

    public Holding(Book book, Long barcode, String place, Integer shelf, Integer row, String callNumber, HoldingState state) {
        this.book = book;
        this.barcode = barcode;
        this.place = place;
        this.shelf = shelf;
        this.row = row;
        this.callNumber = callNumber;
        this.state = state;
    }

    public Holding() {
    }

    public ObjectId getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getBarcode() {
        return barcode;
    }

    public void setBarcode(Long barcode) {
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Holding{" +
                "id=" + id +
                ", book=" + book +
                ", barcode=" + barcode +
                ", place='" + place + '\'' +
                ", shelf=" + shelf +
                ", row=" + row +
                ", callNumber='" + callNumber + '\'' +
                ", state=" + state +
                '}';
    }
}
