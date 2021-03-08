package com.shuosc.books.web.service;

import com.shuosc.books.web.enums.HoldingState;
import com.shuosc.books.web.model.Book;
import com.shuosc.books.web.model.Holding;

import java.util.List;


public interface HoldingService {
    Holding findByBarcode(String barcode);

    void update(String id, Holding holding);

    Holding findById(String id);

    List<Holding> findAll();

    void save(Holding holding);

    void updateSat(String id, HoldingState state);

    void deleteById(String id);

    List<Holding> findByBook(Book book);

    void saveAll(List<Holding> holdings);

    List<Holding> findBy(String place, Integer shelf, Integer row, HoldingState[] holdingStates);
}