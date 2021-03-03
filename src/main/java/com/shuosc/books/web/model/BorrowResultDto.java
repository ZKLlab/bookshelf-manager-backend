package com.shuosc.books.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;


public class BorrowResultDto {
    private String barcode;
    private Boolean succeed;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bookName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String author;
    private String message;

    public BorrowResultDto(String barcode, String bookName, String author, String message) {
        this.barcode = barcode;
        this.succeed = true;
        this.bookName = bookName;
        this.author = author;
        this.message = message;
    }

    public BorrowResultDto(String barcode, String message) {
        this.barcode = barcode;
        this.succeed = false;
        this.message = message;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Boolean getSucceed() {
        return succeed;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
