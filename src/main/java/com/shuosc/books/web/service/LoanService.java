package com.shuosc.books.web.service;

import com.shuosc.books.web.model.Holding;
import com.shuosc.books.web.model.Loan;
import com.shuosc.books.web.model.Renewal;
import org.bson.types.ObjectId;

import java.util.List;

public interface LoanService {
    void save(String sub, Holding holding);

    void update(ObjectId id, Loan loan);

    List<Loan> findBySub(String sub);

    List<Loan> findAll();

    void updateDuetime(ObjectId id);

    void updateReturntime(ObjectId id);

    Loan findBySubHolding(String sub, Holding holding);

    void updateRenewals(ObjectId id, Renewal renewal);

    Loan findById(ObjectId id);

    void updateReturntime(String sub, Holding holding);

    Loan findByHolding(Holding holding);

    void deleteAll();
}
