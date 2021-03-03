package com.shuosc.books.web.service;

import com.shuosc.books.web.model.Book;
import org.bson.types.ObjectId;

import java.util.List;


public interface BookService {
    List<Book> findAll();

    void save(Book book);

    void deleteAll();

    void update(ObjectId id, Book book);

    Book findById(String id);

    void deleteById(String id);

    void saveAll(List<Book> books);
}