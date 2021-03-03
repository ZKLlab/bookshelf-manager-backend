package com.shuosc.books.web.service;

import com.shuosc.books.web.model.Holding;
import com.shuosc.books.web.model.Loan;
import com.shuosc.books.web.model.Renewal;
import org.bson.types.ObjectId;

import java.util.List;


public interface LoanService {
    Loan createLoanBySubAndHolding(String sub, Holding holding);

    void save(Loan loan);

    void update(String id, Loan loan);

    List<Loan> findBySub(String sub);

    List<Loan> findAll();

    void updateDueTime(String id);

    void updateReturnTime(String id);

    Loan findBySubHolding(String sub, Holding holding);

    void updateRenewals(String id, Renewal renewal);

    Loan findById(String id);

    void updateReturnTime(String sub, Holding holding);

    Loan findByHolding(Holding holding);

    void deleteAll();
}
