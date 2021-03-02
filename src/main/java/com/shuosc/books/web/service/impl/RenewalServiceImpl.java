package com.shuosc.books.web.service.impl;

import com.shuosc.books.web.model.Loan;
import com.shuosc.books.web.model.Renewal;
import com.shuosc.books.web.service.RenewalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class RenewalServiceImpl implements RenewalService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public RenewalServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void save(Renewal renewal) {
        mongoTemplate.save(renewal);
    }
}
