package com.shuosc.books.web.service;

import com.shuosc.books.web.model.Book;
import com.shuosc.books.web.model.Holding;
import com.shuosc.books.web.model.HoldingState;
import org.bson.types.ObjectId;

import java.util.List;

public interface HoldingService {
    Holding findByBarcode(Long barcode);

    void update(ObjectId id, Holding holding);

    Holding findById(ObjectId id);

    List<Holding> findAll();

    void save(Holding holding);

    void updateSat(ObjectId id, HoldingState state);

    void deleteById(ObjectId id);

    List<Holding> findByBookId(Book book);

    void saveAll(List<Holding> holdings);
}