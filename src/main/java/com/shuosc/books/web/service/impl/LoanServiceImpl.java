package com.shuosc.books.web.service.impl;

import com.shuosc.books.web.constant.BooksConstant;
import com.shuosc.books.web.model.Holding;
import com.shuosc.books.web.model.Loan;
import com.shuosc.books.web.model.Renewal;
import com.shuosc.books.web.service.LoanService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@Service
public class LoanServiceImpl implements LoanService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public LoanServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Loan createLoanBySubAndHolding(String sub, Holding holding) {
        return new Loan(sub, holding, new Date(), getDueTime());
    }

    @Override
    public Date getDueTime() {
        var calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        calendar.add(Calendar.MILLISECOND, BooksConstant.BORROWING_TIME_MILLIS);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    @Override
    public void save(Loan loan) {
        mongoTemplate.save(loan);
    }

    @Override
    public void update(String id, Loan loan) {
        Document doc = new Document();
        mongoTemplate.getConverter().write(loan, doc);
        Update update = Update.fromDocument(doc);
        mongoTemplate
                .updateFirst(Query.query(Criteria.where("id").is(id)),
                        update,
                        Loan.class);
    }

    @Override
    public void updateReturnTime(String id) {
        Update update = Update.update("returnTime", new Date());
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
    public void updateRenewals(String id, Renewal renewal) {
        var update = new Update().push("renewals", renewal);
        mongoTemplate
                .updateFirst(Query.query(Criteria.where("id").is(id)),
                        update,
                        Loan.class);
    }

    @Override
    public Loan findById(String id) {
        return mongoTemplate
                .findOne(Query.query(Criteria.where("id").is(id)),
                        Loan.class);
    }

    @Override
    public Loan findByHolding(Holding holding) {
        var query = new Query();
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
    public List<Loan> findNotReturnedBySub(String sub) {
        var query = new Query();
        query.addCriteria(new Criteria()
                .andOperator(
                        Criteria.where("sub").is(sub),
                        Criteria.where("returnTime").is(null)
                ));
        return mongoTemplate
                .find(query, Loan.class);
    }

    @Override
    public void updateDueTime(String id, Date dueTime) {
        var update = Update.update("dueTime", dueTime);
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
