package com.shuosc.books.web.dto;

import org.bson.BsonTimestamp;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class BookDto {

    private String title;
    private String parallelTitle;
    private String author;
    private String seriesTitle;
    private String summary;
    private String publisher;
    private String[] subjects;
    private Integer[] publicationDate;
    private String clcClassification;
    private String isbn;
    private String language;
    private Integer pages;
    private Integer price;
    private Integer doubanId;
    private Boolean visible;
    private String updaterSub;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParallelTitle() {
        return parallelTitle;
    }

    public void setParallelTitle(String parallelTitle) {
        this.parallelTitle = parallelTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }

    public Integer[] getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Integer[] publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getClcClassification() {
        return clcClassification;
    }

    public void setClcClassification(String clcClassification) {
        this.clcClassification = clcClassification;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDoubanId() {
        return doubanId;
    }

    public void setDoubanId(Integer doubanId) {
        this.doubanId = doubanId;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getUpdaterSub() {
        return updaterSub;
    }

    public void setUpdaterSub(String updaterSub) {
        this.updaterSub = updaterSub;
    }
}
