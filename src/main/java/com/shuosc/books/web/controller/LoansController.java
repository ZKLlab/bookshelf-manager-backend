package com.shuosc.books.web.controller;

import com.shuosc.books.web.constant.BooksConstant;
import com.shuosc.books.web.enums.HoldingState;
import com.shuosc.books.web.enums.RenewalReason;
import com.shuosc.books.web.model.BorrowResultDto;
import com.shuosc.books.web.model.Renewal;
import com.shuosc.books.web.model.Return;
import com.shuosc.books.web.service.HoldingService;
import com.shuosc.books.web.service.LoanService;
import org.bson.BsonTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@RestController
@RequestMapping(value = "/api")
public class LoansController {
    private final LoanService loanService;
    private final HoldingService holdingService;

    @Autowired
    public LoansController(LoanService loanService, HoldingService holdingService) {
        this.loanService = loanService;
        this.holdingService = holdingService;
    }

    @PostMapping(path = "/borrow")
    public Return borrow(Principal principal, @RequestBody String[] barcodes) {
        var results = new ArrayList<BorrowResultDto>();
        for (var barcode : barcodes) {
            var holding = holdingService.findByBarcode(barcode);

            if (holding == null || !holding.getBook().getVisible()) {
                results.add(new BorrowResultDto(barcode, "找不到这本书"));
                continue;
            }

            if (holding.getState() != HoldingState.Lending) {
                results.add(new BorrowResultDto(barcode, "此图书已被借出或不能外借"));
                continue;
            }

            holding.setState(HoldingState.Lent);
            holdingService.save(holding);

            var loan = loanService.createLoanBySubAndHolding(principal.getName(), holding);
            loanService.save(loan);

            var formatter = new SimpleDateFormat("yyyy年M月d日");
            results.add(new BorrowResultDto(
                    barcode,
                    holding.getBook().getTitle(),
                    holding.getBook().getAuthor(),
                    "请于" + formatter.format(new Date(loan.getDueTime().getValue())) + "前归还"
            ));
        }

        return Return.success("", results);
    }

    @PostMapping(path = "/loans/{id}/return")
    public Return returnHolding(Principal principal, @PathVariable String id) {
        var loan = loanService.findById(id);
        if (loan == null || !loan.getSub().equals(principal.getName()))
            return Return.failure("你没有借这本书，还书失败");

        if (loan.getReturned())
            return Return.failure("你已经还过这本书了");

        var holding = loan.getHolding();
        holding.setState(HoldingState.Lending);

        loanService.updateReturnTime(loan.getId(), holding);
        holdingService.save(holding);

        return Return.success("还书成功");
    }

    @GetMapping(path = "/loans")
    public Return listLoans(Principal principal) {
        var loans = loanService.findBySub(principal.getName());
        return Return.success("查询成功", loans);
    }

    @PostMapping(path = "/loans/{id}/renew")
    public Return renew(String sub, @PathVariable String id) {
        var loan = loanService.findById(id);
        if (loan == null || !loan.getSub().equals(sub))
            return Return.failure("记录不存在");

        if (loan.getRenewals().stream().anyMatch(renewal -> renewal.getRenewalReason() == RenewalReason.Unconditional))
            return Return.failure("你已经续借过一次了");

        var renewal = new Renewal();
        renewal.setRenewalReason(RenewalReason.Unconditional);
        renewal.setRenewTime(new BsonTimestamp(System.currentTimeMillis()));
        renewal.setDueTime(new BsonTimestamp(renewal.getRenewTime().getTime() + BooksConstant.BORROWING_TIME_MILLIS));

        loanService.updateDueTime(loan.getId());
        loanService.updateRenewals(loan.getId(), renewal);
        return Return.success("续借成功");
    }
}
