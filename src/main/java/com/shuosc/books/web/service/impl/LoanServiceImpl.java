package com.shuosc.books.web.service.impl;

import com.shuosc.books.web.constant.BooksConstant;
import com.shuosc.books.web.model.Holding;
import com.shuosc.books.web.model.Loan;
import com.shuosc.books.web.model.Renewal;
import com.shuosc.books.web.service.LoanService;
import org.bson.BsonTimestamp;
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
public class LoanServiceImpl implements LoanService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public LoanServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Loan createLoanBySubAndHolding(String sub, Holding holding) {
        var loan = new Loan();
        loan.setSub(sub);
        loan.setHolding(holding);
        loan.setReturned(false);
        loan.setLendTime(new BsonTimestamp(System.currentTimeMillis()));
        loan.setDueTime(new BsonTimestamp(System.currentTimeMillis() + BooksConstant.BORROWING_TIME_MILLIS));
        return loan;
    }

    @Override
    public void save(Loan loan) {
        mongoTemplate.save(loan);
    }

    @Override
    public void update(ObjectId id, Loan loan) {
        Document doc = new Document();
        mongoTemplate.getConverter().write(loan, doc);
        Update update = Update.fromDocument(doc);
        mongoTemplate
                .updateFirst(Query.query(Criteria.where("id").is(id)),
                        update,
                        Loan.class);
    }

    @Override
    public void updateReturnTime(ObjectId id) {
        Update update = Update.update("returnTime", new BsonTimestamp(System.currentTimeMillis()));
        mongoTemplate
                .updateFirst(Query.query(Criteria.where("id").is(id)),
                        update,
                        Loan.class);
    }

    @Override
    public Loan findBySubHolding(String sub, Holding holding) {
        Query query = new Query();
        query.addCriteria(new Criteria()
                .andOperator(
                        Criteria.where("sub").is(sub),
                        Criteria.where("holding").is(holding)
                ));
        return mongoTemplate
                .findOne(query, Loan.class);
    }

    @Override
    public void updateRenewals(ObjectId id, Renewal renewal) {
        Loan loan = findById(id);
        List<Renewal> renewals = loan.getRenewals();
        renewals.add(renewal);
        Update update = Update.update("renewals", renewals);
        mongoTemplate
                .updateFirst(Query.query(Criteria.where("id").is(id)),
                        update,
                        Loan.class);
    }

    @Override
    public Loan findById(ObjectId id) {
        return mongoTemplate
                .findOne(Query.query(Criteria.where("id").is(id)),
                        Loan.class);
    }

    @Override
    public void updateReturnTime(String sub, Holding holding) {
        Update update = Update.update("returnTime", new BsonTimestamp(System.currentTimeMillis()));
        Query query = new Query();
        query.addCriteria(new Criteria()
                .andOperator(
                        Criteria.where("sub").is(sub),
                        Criteria.where("holding").is(holding)
                ));
        mongoTemplate
                .updateFirst(query,
                        update,
                        Loan.class);
    }

    @Override
    public Loan findByHolding(Holding holding) {
        Query query = new Query();
        query.addCriteria(new Criteria()
                .andOperator(
                        Criteria.where("returnTime").is(null),
                        Criteria.where("holding").is(holding)
                ));
        return mongoTemplate
                .findOne(query,
                        Loan.class);
    }

    @Override
    public List<Loan> findBySub(String sub) {
        return mongoTemplate
                .find(Query.query(Criteria.where("sub").is(sub)),
                        Loan.class);
    }

    @Override
    public void updateDueTime(ObjectId id) {
        Update update = Update.update("dueTime", new BsonTimestamp(System.currentTimeMillis() + BooksConstant.BORROWING_TIME_MILLIS));
        mongoTemplate
                .updateFirst(Query.query(Criteria.where("id").is(id)),
                        update,
                        Loan.class);
    }

    public List<Loan> findAll() {
        return mongoTemplate.findAll(Loan.class);
    }

    public void deleteAll() {
        mongoTemplate
                .remove(Query.query(Criteria.where("id").ne(null)),
                        Loan.class);
    }
}
