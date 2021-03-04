package com.shuosc.books.web.model;

import java.util.List;


public class ListBookDto {
    private String id;
    private String title;
    private String parallelTitle;
    private String author;
    private String seriesTitle;
    private String publisher;
    private List<String> subjects;
    private List<Integer> publicationDate;
    private String clcClassification;
    private String isbn;
    private String language;

    public ListBookDto() {
    }

    public ListBookDto(String id, String title, String parallelTitle, String author, String seriesTitle, String publisher, List<String> subjects, List<Integer> publicationDate, String clcClassification, String isbn, String language) {
        this.id = id;
        this.title = title;
        this.parallelTitle = parallelTitle;
        this.author = author;
        this.seriesTitle = seriesTitle;
        this.publisher = publisher;
        this.subjects = subjects;
        this.publicationDate = publicationDate;
        this.clcClassification = clcClassification;
        this.isbn = isbn;
        this.language = language;
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
}
