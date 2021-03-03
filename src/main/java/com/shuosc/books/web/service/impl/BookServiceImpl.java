package com.shuosc.books.web.service.impl;

import com.shuosc.books.web.model.Book;
import com.shuosc.books.web.service.BookService;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookServiceImpl implements BookService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public BookServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Book> findAll() {
        return mongoTemplate.findAll(Book.class);
    }

    public void save(Book book) {
        mongoTemplate.save(book);
    }

    public void deleteAll() {
        mongoTemplate.dropCollection(Book.class);
    }

    public void update(ObjectId id, Book book) {
        Document doc = new Document();
        mongoTemplate.getConverter().write(book, doc);
        Update update = Update.fromDocument(doc);
        mongoTemplate
                .updateFirst(Query.query(Criteria.where("id").is(id)),
                        update,
                        Book.class);
    }

    @Override
    public Book findById(ObjectId id) {
        return mongoTemplate
                .findOne(Query.query(Criteria.where("id").is(id)),
                        Book.class);
    }

    @Override
    public void deleteById(ObjectId id) {
        mongoTemplate
                .remove(Query.query(Criteria.where("id").is(id)),
                        Book.class);
    }

    @Override
    public void saveAll(List<Book> books) {
        mongoTemplate.insertAll(books);
    }
}