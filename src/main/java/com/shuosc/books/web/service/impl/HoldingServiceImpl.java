package com.shuosc.books.web.service.impl;

import com.shuosc.books.web.model.Book;
import com.shuosc.books.web.model.Holding;
import com.shuosc.books.web.enums.HoldingState;
import com.shuosc.books.web.service.HoldingService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoldingServiceImpl implements HoldingService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public HoldingServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Holding findByBarcode(String barcode) {
        return mongoTemplate
                .findOne(Query.query(Criteria.where("barcode").is(barcode)),
                        Holding.class);
    }

    public void update(String id, Holding holding) {
        Document doc = new Document();
        mongoTemplate.getConverter().write(holding, doc);
        Update update = Update.fromDocument(doc);
        mongoTemplate
                .updateFirst(Query.query(Criteria.where("id").is(id)),
                        update,
                        Holding.class);
    }

    @Override
    public Holding findById(String id) {
        return mongoTemplate
                .findOne(Query.query(Criteria.where("id").is(id)),
                        Holding.class);
    }

    @Override
    public List<Holding> findAll() {
        return mongoTemplate.findAll(Holding.class);
    }

    @Override
    public void save(Holding holding) {
        mongoTemplate.save(holding);
    }

    @Override
    public void updateSat(String id, HoldingState state) {
        mongoTemplate
                .updateFirst(Query.query(Criteria.where("id").is(id)),
                        Update.update("state", state),
                        Holding.class);
    }

    @Override
    public void deleteById(String id) {
        mongoTemplate
                .remove(Query.query(Criteria.where("id").is(id)),
                        Holding.class);
    }

    @Override
    public List<Holding> findByBook(Book book) {
        return mongoTemplate.find(Query.query(Criteria.where("book").is(book)),
                Holding.class);
    }

    @Override
    public void saveAll(List<Holding> holdings) {
        mongoTemplate.insertAll(holdings);
    }
}