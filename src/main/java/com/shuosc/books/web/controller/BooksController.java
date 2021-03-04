package com.shuosc.books.web.controller;

import com.shuosc.books.web.model.*;
import com.shuosc.books.web.service.BookService;
import com.shuosc.books.web.service.HoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
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
                .map(book -> new ListBookDto(
                        book.getId(),
                        book.getTitle(),
                        book.getParallelTitle(),
                        book.getAuthor(),
                        book.getSeriesTitle(),
                        book.getPublisher(),
                        book.getSubjects(),
                        book.getPublicationDate(),
                        book.getClcClassification(),
                        book.getIsbn(),
                        book.getLanguage()
                ))
                .collect(Collectors.toList());
        return Return.success("查询成功", books);
    }

    @GetMapping(path = "/books/{id}")
    public Return getBook(@PathVariable String id) {
        var book = bookService.findById(id);
        if (book == null || !book.getVisible())
            return Return.failure("图书不存在，获取失败");
        var holdings = holdingService.findByBook(book);

        return Return.success("获取成功", new GetBookDto(
                book.getId(),
                book.getTitle(),
                book.getParallelTitle(),
                book.getAuthor(),
                book.getSeriesTitle(),
                book.getSummary(),
                book.getPublisher(),
                book.getSubjects(),
                book.getPublicationDate(),
                book.getClcClassification(),
                book.getIsbn(),
                book.getLanguage(),
                book.getPages(),
                book.getPrice(),
                book.getDoubanId(),
                holdings.stream()
                        .sorted(Comparator.comparing(Holding::getBarcode))
                        .map(holding -> new GetBookDtoHolding(
                                holding.getId(),
                                holding.getBarcode(),
                                holding.getPlace(),
                                holding.getShelf(),
                                holding.getRow(),
                                holding.getCallNumber(),
                                holding.getState()
                        ))
                        .collect(Collectors.toList())
        ));
    }
}
