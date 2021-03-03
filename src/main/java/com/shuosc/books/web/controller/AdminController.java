package com.shuosc.books.web.controller;

import com.shuosc.books.web.model.BookDto;
import com.shuosc.books.web.model.HoldingDto;
import com.shuosc.books.web.model.Book;
import com.shuosc.books.web.model.Holding;
import com.shuosc.books.web.enums.HoldingState;
import com.shuosc.books.web.model.Return;
import com.shuosc.books.web.service.BookService;
import com.shuosc.books.web.service.HoldingService;
import com.shuosc.books.web.service.LoanService;
import com.shuosc.books.web.util.ExcelImportUtil;
import org.bson.BsonTimestamp;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {
    private final LoanService loanService;
    private final HoldingService holdingService;
    private final BookService bookService;

    @Autowired
    public AdminController(LoanService loanService, HoldingService holdingService, BookService bookService) {
        this.loanService = loanService;
        this.holdingService = holdingService;
        this.bookService = bookService;
    }

    @GetMapping(path = "/holdings")
    public Return listHoldings() {
        return Return.success("查询成功", holdingService.findAll());
    }

    @PostMapping(path = "/books")
    public Return createBook(@RequestBody @Validated Book book) {
        bookService.save(book);
        return Return.success("添加成功");
    }

    @PostMapping(path = "/holdings")
    public Return saveHolding(@RequestBody @Validated Holding holding) {
        if (bookService.findById(holding.getBook().getId()) == null)
            return Return.failure("该书籍不存在, 添加失败");

        holdingService.save(holding);
        return Return.success("添加成功");
    }

    @DeleteMapping(path = "/books/{id}")
    public Return deleteBook(@PathVariable ObjectId id) {
        var book = bookService.findById(id);
        if (book == null)
            return Return.failure("该书籍不存在, 删除失败");

        var holdings = holdingService.findByBook(book);
        if (!holdings.isEmpty())
            return Return.failure("该书籍有藏书在馆中, 删除失败");

        bookService.deleteById(id);
        return Return.success("删除成功");
    }

    @DeleteMapping(path = "/holdings/{id}")
    public Return deleteHolding(@PathVariable ObjectId id) {
        var holding = holdingService.findById(id);
        if (holding == null)
            return Return.failure("该藏书不存在, 删除失败");

        if (holding.getState() == HoldingState.Lent)
            return Return.failure("该藏书外借中, 删除失败");

        holdingService.deleteById(id);
        return Return.success("删除成功");
    }

    @GetMapping(path = "/loans")
    public Return listLoans() {
        return Return.success("查询成功", loanService.findAll());
    }

    @PutMapping(path = "/books/{id}")
    public Return updateBook(@PathVariable ObjectId id,
                             @RequestBody BookDto bookDto) {
        var book = bookService.findById(id);
        if (book == null)
            return Return.failure("书籍不存在, 修改失败");

        if (book.getVisible() && !bookDto.getVisible()) {
            var books = holdingService.findByBook(book);
            for (var holding : books) {
                if (holding.getState() == HoldingState.Lent)
                    return Return.failure("该书籍有藏书已被借阅, 修改失败");
            }
        }

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
        book.setUpdatedTime(new BsonTimestamp(System.currentTimeMillis()));
        book.setUpdaterSub("");

        bookService.save(book);
        return Return.success("修改成功");
    }

    @PutMapping(path = "/holdings/{id}")
    public Return updateHolding(@PathVariable ObjectId id,
                                @RequestBody HoldingDto holdingDto) {
        var holding = holdingService.findById(id);
        if (holding == null)
            return Return.failure("藏书不存在, 修改失败");

        if (holdingDto.getState().equals(HoldingState.Lent))
            return Return.failure("不能将图书状态修改为已被借阅, 修改失败");

        if (holding.getState().equals(HoldingState.Lent))
            return Return.failure("该图书已被借阅, 修改失败");

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
        return ExcelImportUtil.importBooksFromFile(file, bookService);
    }

    @PostMapping(path = "/holdings/excel")
    public Return importHoldings(MultipartFile file) {
        return ExcelImportUtil.importHoldingsFromFile(file, holdingService, bookService);
    }
}