package com.shuosc.books.web.controller;

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
    public Return createBook(@RequestBody @Valid CreateOrUpdateBookDto dto) {
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

    @GetMapping(path = "/books/{id}/holdings")
    public Return listHoldings(@PathVariable String id) {
        var book = bookService.findById(id);

        if (book == null)
            return Return.failure("该书籍不存在");

        var holdings = holdingService.findByBook(book);
        holdings.forEach(holding -> holding.setBook(null));

        return Return.success("", holdings);
    }

    @PostMapping(path = "/books/{id}/holdings")
    public Return createHolding(@PathVariable String id, @RequestBody @Valid CreateOrUpdateHoldingDto dto) {
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

    @PatchMapping(path = "/books/{id}")
    public Return updateBook(@PathVariable String id,
                             @RequestBody CreateOrUpdateBookDto dto) {
        var book = bookService.findById(id);
        if (book == null)
            return Return.failure("书籍不存在, 修改失败");

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setClcClassification(dto.getClcClassification());
        book.setIsbn(dto.getIsbn());
        book.setLanguage(dto.getLanguage());
        book.setPages(dto.getPages());
        book.setPrice(dto.getPrice());
        book.setVisible(dto.getVisible());
        book.setSummary(dto.getSummary());
        book.setDoubanId(dto.getDoubanId());
        book.setParallelTitle(dto.getParallelTitle());
        book.setPublicationDate(dto.getPublicationDate());
        book.setPublisher(dto.getPublisher());
        book.setSeriesTitle(dto.getSeriesTitle());
        book.setSubjects(dto.getSubjects());

        bookService.save(book);
        return Return.success("修改成功");
    }

    @PatchMapping(path = "/holdings/{id}")
    public Return updateHolding(@PathVariable String id,
                                @RequestBody @Valid CreateOrUpdateHoldingDto createOrUpdateHoldingDto) {
        var holding = holdingService.findById(id);
        if (holding == null)
            return Return.failure("藏书不存在, 修改失败");

        holding.setCallNumber(createOrUpdateHoldingDto.getCallNumber());
        holding.setPlace(createOrUpdateHoldingDto.getPlace());
        holding.setRow(createOrUpdateHoldingDto.getRow());
        holding.setShelf(createOrUpdateHoldingDto.getShelf());
        holding.setState(createOrUpdateHoldingDto.getState());

        holdingService.save(holding);
        return Return.success("修改成功");
    }

    @PostMapping(path = "/loans/{id}/return")
    public Return returnLoan(@PathVariable String id) {
        var loan = loanService.findById(id);
        if (loan == null)
            return Return.failure("借阅记录不存在, 修改失败");

        loanService.updateReturnTime(id);

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