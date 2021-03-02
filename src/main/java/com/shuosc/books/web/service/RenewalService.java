package com.shuosc.books.web.service;

import com.shuosc.books.web.model.Renewal;
import org.bson.types.ObjectId;

public interface RenewalService {
    void save(Renewal renewal);
}
