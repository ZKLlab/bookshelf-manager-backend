package com.shuosc.books.web.controller;

import com.shuosc.books.web.constant.BooksConstant;
import com.shuosc.books.web.enums.HoldingState;
import com.shuosc.books.web.enums.RenewalReason;
import com.shuosc.books.web.model.Holding;
import com.shuosc.books.web.model.Loan;
import com.shuosc.books.web.model.Renewal;
import com.shuosc.books.web.model.Return;
import com.shuosc.books.web.service.HoldingService;
import com.shuosc.books.web.service.LoanService;
import com.shuosc.books.web.service.RenewalService;
import org.bson.BsonTimestamp;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/api")
public class LoanController {
    private final LoanService loanService;
    private final HoldingService holdingService;
    private final RenewalService renewalService;

    @Autowired
    public LoanController(LoanService loanService, HoldingService holdingService, RenewalService renewalService) {
        this.loanService = loanService;
        this.holdingService = holdingService;
        this.renewalService = renewalService;
    }

    @PostMapping(path = "/loans/borrow")
    public Return borrowBook(String sub, @RequestBody Long[] barcodes) {
        var results = new ArrayList<String[]>();
        for (var barcode : barcodes) {
            var holding = holdingService.findByBarcode(barcode);

            if (holding == null || !holding.getBook().getVisible()) {
                results.add(new String[]{"借阅失败", "没有这本书"});
                continue;
            }

            if (holding.getState() != HoldingState.Lending) {
                results.add(new String[]{"借阅失败", "此图书已被借出或不能外借"});
                continue;
            }

            holding.setState(HoldingState.Lent);
            holdingService.save(holding);

            var loan = loanService.createLoanBySubAndHolding(sub, holding);
            loanService.save(loan);

            var formatter = new SimpleDateFormat("yyyy年M月d日");
            results.add(new String[]{"借阅成功", "请于" + formatter.format(new Date(loan.getDueTime().getValue())) + "前归还"});
        }

        return Return.success("图书借阅结果已返回", results);
    }

    @PostMapping(path = "/loans/return")
    public Return returnBook(String sub, ObjectId holdingId) {
        Holding holding = holdingService.findById(holdingId);
        if (holding == null)
            return Return.failure("图书不存在, 还书失败");

        Loan loan = loanService.findBySubHolding(sub, holding);
        if (loan == null || loan.getHolding().getState() != HoldingState.Lent)
            return Return.failure("你没有借这本书，还书失败");

        holding.setState(HoldingState.Lending);
        loanService.updateReturnTime(sub, holding);
        holdingService.update(holdingId, holding);
        return Return.success("还书成功");
    }

    @GetMapping(path = "/loans")
    public Return showLoans(String sub) {
        List<Loan> loans = loanService.findBySub(sub);
        return Return.success("查询成功", loans);
    }

    @PostMapping(path = "/loans/renew/{id}")
    public Return renew(String sub, @PathVariable ObjectId id, RenewalReason reason) {
        Loan loan = loanService.findById(id);
        if (loan == null || !loan.getSub().equals(sub))
            return Return.failure("记录不存在");

        Renewal renewal = new Renewal();
        renewal.setRenewalReason(reason);
        renewal.setRenewTime(new BsonTimestamp(System.currentTimeMillis()));
        renewal.setDueTime(new BsonTimestamp(renewal.getRenewTime().getTime() + BooksConstant.BORROWING_TIME_MILLIS));

        loanService.updateDueTime(loan.getId());
        loanService.updateRenewals(loan.getId(), renewal);
        renewalService.save(renewal);
        return Return.success("续借成功");
    }
}
