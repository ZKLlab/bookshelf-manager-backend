package com.shuosc.books.web.controller;

import com.shuosc.books.web.model.Book;
import com.shuosc.books.web.model.Return;
import com.shuosc.books.web.service.BookService;
import com.shuosc.books.web.service.HoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.stream.Collectors;


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
        var books = bookService.findAll().stream()
                .filter(Book::getVisible)
                .sorted((o1, o2) -> {
                    var a = o1.getPublicationDate().isEmpty()
                            ? o1.getPublicationDate().get(0)
                            : 0;
                    var b = o2.getPublicationDate().isEmpty()
                            ? o2.getPublicationDate().get(0)
                            : 0;
                    return b - a;
                })
                .collect(Collectors.toCollection(ArrayList::new));
        return Return.success("查询成功", books);
    }

    @GetMapping(path = "/books/{id}")
    public Return getBook(@PathVariable String id) {
        var book = bookService.findById(id);
        if (book == null || !book.getVisible())
            return Return.failure("图书不存在，获取失败");

        return Return.success("获取成功", bookService.findById(id));
    }

    @GetMapping(path = "/books/{id}/holdings")
    public Return getBookHoldings(@PathVariable String id) {
        return Return.success("查询成功",
                holdingService.findByBook(bookService.findById(id)));
    }
}
