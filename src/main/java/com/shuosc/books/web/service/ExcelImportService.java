package com.shuosc.books.web.service;

import com.shuosc.books.web.model.Return;
import org.springframework.web.multipart.MultipartFile;


public interface ExcelImportService {
    Return importBooksFromFile(MultipartFile file);

    Return importHoldingsFromFile(MultipartFile file);
}
