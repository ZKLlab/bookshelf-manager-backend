package com.shuosc.books.web.controller;

import com.shuosc.books.web.enums.HoldingState;
import com.shuosc.books.web.model.*;
import com.shuosc.books.web.service.BookService;
import com.shuosc.books.web.service.ExcelImportService;
import com.shuosc.books.web.service.HoldingService;
import com.shuosc.books.web.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Random;


@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {
    private final LoanService loanService;
    private final HoldingService holdingService;
    private final BookService bookService;
    private final ExcelImportService excelImportService;

    @Autowired
    public AdminController(LoanService loanService, HoldingService holdingService, BookService bookService, ExcelImportService excelImportService) {
        this.loanService = loanService;
        this.holdingService = holdingService;
        this.bookService = bookService;
        this.excelImportService = excelImportService;
    }

    @GetMapping(path = "/books")
    public Return listBooks() {
        return Return.success("", bookService.findAll());
    }

    @PostMapping(path = "/books")
    public Return createBook(@RequestBody @Valid BookDto dto) {
        var book = new Book(
                dto.getTitle(),
                dto.getParallelTitle(),
                dto.getAuthor(),
                dto.getSeriesTitle(),
                dto.getSummary(),
                dto.getPublisher(),
                dto.getSubjects(),
                dto.getPublicationDate(),
                dto.getClcClassification(),
                dto.getIsbn(),
                dto.getLanguage(),
                dto.getPages(),
                dto.getPrice(),
                dto.getDoubanId(),
                dto.getVisible()
        );
        bookService.save(book);
        return Return.success("创建成功", book);
    }

    @PostMapping(path = "/books/{id}/holdings")
    public Return saveHolding(@PathVariable String id, @RequestBody @Valid HoldingDto dto) {
        var book = bookService.findById(id);

        if (book == null)
            return Return.failure("该书籍不存在, 添加失败");

        var holding = new Holding(
                book,
                String.format("%07d", new Random().nextInt(10000000)),
                dto.getPlace(),
                dto.getShelf(),
                dto.getRow(),
                dto.getCallNumber(),
                dto.getState()
        );
        holdingService.save(holding);

        return Return.success("添加成功", holding);
    }

    @GetMapping(path = "/loans")
    public Return listLoans() {
        return Return.success("查询成功", loanService.findAll());
    }

    @PutMapping(path = "/books/{id}")
    public Return updateBook(@PathVariable String id,
                             @RequestBody BookDto bookDto) {
        var book = bookService.findById(id);
        if (book == null)
            return Return.failure("书籍不存在, 修改失败");

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setClcClassification(bookDto.getClcClassification());
        book.setIsbn(bookDto.getIsbn());
        book.setLanguage(bookDto.getLanguage());
        book.setPages(bookDto.getPages());
        book.setPrice(bookDto.getPrice());
        book.setVisible(bookDto.getVisible());
        book.setSummary(bookDto.getSummary());
        book.setDoubanId(bookDto.getDoubanId());
        book.setParallelTitle(bookDto.getParallelTitle());
        book.setPublicationDate(bookDto.getPublicationDate());
        book.setPublisher(bookDto.getPublisher());
        book.setSeriesTitle(bookDto.getSeriesTitle());
        book.setSubjects(bookDto.getSubjects());

        bookService.save(book);
        return Return.success("修改成功");
    }

    @PutMapping(path = "/holdings/{id}")
    public Return updateHolding(@PathVariable String id,
                                @RequestBody @Valid HoldingDto holdingDto) {
        var holding = holdingService.findById(id);
        if (holding == null)
            return Return.failure("藏书不存在, 修改失败");

        holding.setCallNumber(holdingDto.getCallNumber());
        holding.setPlace(holdingDto.getPlace());
        holding.setRow(holdingDto.getRow());
        holding.setShelf(holdingDto.getShelf());
        holding.setState(holdingDto.getState());

        holdingService.save(holding);
        return Return.success("修改成功");
    }

    @PostMapping(path = "/books/excel")
    public Return importBooks(MultipartFile file) {
        return excelImportService.importBooksFromFile(file);
    }

    @PostMapping(path = "/holdings/excel")
    public Return importHoldings(MultipartFile file) {
        return excelImportService.importHoldingsFromFile(file);
    }
}