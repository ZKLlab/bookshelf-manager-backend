package com.shuosc.books.web.controller;

import com.shuosc.books.web.model.Return;
import com.shuosc.books.web.service.BookService;
import com.shuosc.books.web.service.HoldingService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api")
public class BooksController {
    private final HoldingService holdingService;
    private final BookService bookService;

    @Autowired
    public BooksController(HoldingService holdingService, BookService bookService) {
        this.holdingService = holdingService;
        this.bookService = bookService;
    }

    @GetMapping(path = "/books")
    public Return listBooks() {
        return Return.success("查询成功", bookService.findAll());
    }

    @GetMapping(path = "/books/{id}")
    public Return getBook(@PathVariable ObjectId id) {
        var book = bookService.findById(id);
        if (book == null)
            return Return.failure("图书不存在，获取失败");

        return Return.success("获取成功", bookService.findById(id));
    }

    @GetMapping(path = "/books/{id}/holdings")
    public Return getBookHoldings(@PathVariable ObjectId id) {
        return Return.success("查询成功",
                holdingService.findByBook(bookService.findById(id)));
    }
}
