package com.shuosc.books.web.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document("books")
public class Book {
    @Id
    private String id;
    @TextIndexed
    private String title;
    @TextIndexed(weight = 0.9f)
    private String parallelTitle;
    @TextIndexed(weight = 0.9f)
    private String author;
    @TextIndexed(weight = 0.8f)
    private String seriesTitle;
    @TextIndexed(weight = 0.6f)
    private String summary;
    @TextIndexed(weight = 0.5f)
    private String publisher;
    @TextIndexed
    private List<String> subjects;
    private List<Integer> publicationDate;
    @TextIndexed
    private String clcClassification;
    @TextIndexed
    private String isbn;
    private String language;
    private Integer pages;
    private Integer price;
    private Integer doubanId;
    private Boolean visible;
    @Version
    private Long version;

    public Book(String title, String parallelTitle,
                String author, String seriesTitle,
                String summary, String publisher,
                List<String> subjects, List<Integer> publicationDate,
                String clcClassification, String isbn,
                String language, Integer pages,
                Integer price, Integer doubanId,
                Boolean visible) {
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
        this.visible = visible;
    }

    public Book() {
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

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
