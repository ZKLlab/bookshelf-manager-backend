package com.shuosc.books.web.model;

import java.util.List;


public class GetBookDto {
    private String id;
    private String title;
    private String parallelTitle;
    private String author;
    private String seriesTitle;
    private String summary;
    private String publisher;
    private List<String> subjects;
    private List<Integer> publicationDate;
    private String clcClassification;
    private String isbn;
    private String language;
    private Integer pages;
    private Integer price;
    private Integer doubanId;
    private List<GetBookDtoHolding> holdings;

    public GetBookDto() {
    }

    public GetBookDto(String id, String title, String parallelTitle, String author, String seriesTitle, String summary, String publisher, List<String> subjects, List<Integer> publicationDate, String clcClassification, String isbn, String language, Integer pages, Integer price, Integer doubanId, List<GetBookDtoHolding> holdings) {
        this.id = id;
        this.title = title;
        this.parallelTitle = parallelTitle;
        this.author = author;
        this.seriesTitle = seriesTitle;
        this.summary = summary;
        this.publisher = publisher;
        this.subjects = subjects;
        this.publicationDate = publicationDate;
        this.clcClassification = clcClassification;
        this.isbn = isbn;
        this.language = language;
        this.pages = pages;
        this.price = price;
        this.doubanId = doubanId;
        this.holdings = holdings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public List<Integer> getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(List<Integer> publicationDate) {
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

    public List<GetBookDtoHolding> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<GetBookDtoHolding> holdings) {
        this.holdings = holdings;
    }
}
