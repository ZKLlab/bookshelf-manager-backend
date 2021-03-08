package com.shuosc.books.web.controller;

import com.shuosc.books.web.enums.HoldingState;
import com.shuosc.books.web.enums.RenewalReason;
import com.shuosc.books.web.model.*;
import com.shuosc.books.web.service.HoldingService;
import com.shuosc.books.web.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


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
                    "请于" + formatter.format(loan.getDueTime()) + "前归还或续借"
            ));
        }

        return Return.success("", results);
    }

    @GetMapping(path = "/loans")
    public Return listLoans(Principal principal, @RequestParam(required = false) String mode) {
        List<Loan> loans;
        if (mode != null && mode.equalsIgnoreCase("all")) {
            loans = loanService.findBySub(principal.getName());
        } else {
            loans = loanService.findNotReturnedBySub(principal.getName());
        }
        Collections.reverse(loans);
        return Return.success("查询成功", loans);
    }

    @PostMapping(path = "/loans/{id}/return")
    public Return returnHolding(Principal principal, @PathVariable String id) {
        var loan = loanService.findById(id);
        if (loan == null || !loan.getSub().equals(principal.getName()))
            return Return.failure("找不到记录");

        if (loan.getReturnTime() != null)
            return Return.failure("你已经还过这本书了");

        var holding = loan.getHolding();
        holding.setState(HoldingState.Lending);

        loanService.updateReturnTime(loan.getId());
        holdingService.save(holding);

        return Return.success("还书成功");
    }

    @PostMapping(path = "/loans/{id}/renew")
    public Return renew(Principal principal, @PathVariable String id) {
        var loan = loanService.findById(id);
        if (loan == null || !loan.getSub().equals(principal.getName()))
            return Return.failure("记录不存在");

        if (loan.getReturnTime() != null)
            return Return.failure("你已经归还了这本书");

        if (loan.getRenewals().stream().anyMatch(renewal -> renewal.getRenewalReason() == RenewalReason.Unconditional))
            return Return.failure("你已经续借过一次了");

        var dueTime = loanService.getDueTime();
        var renewal = new Renewal(new Date(), dueTime, RenewalReason.Unconditional);

        loanService.updateDueTime(loan.getId(), dueTime);
        loanService.updateRenewals(loan.getId(), renewal);
        return Return.success("续借成功");
    }

    @GetMapping(path = "/loans/{id}")
    public Return getLoan(Principal principal, @PathVariable String id) {
        var loan = loanService.findById(id);
        if (loan == null)
            return Return.failure("记录不存在");
        var holding = loan.getHolding();

        var place = holding.getPlace();
        var shelf = holding.getShelf();
        var row = holding.getRow();

        if (loan.getReturnTime() != null)
            return Return.success("查询成功",
                    new GetLoanDto(loan.getId(),
                            loan.getSub(), loan.getHolding(),
                            loan.getRenewals(), loan.getLendTime(),
                            loan.getDueTime(), loan.getReturnTime(),
                            null));

        var holdings = holdingService
                .findBy(place, shelf, row,
                        new HoldingState[]{HoldingState.Lost,
                                HoldingState.Unlisted,
                                HoldingState.Damaged});

        return Return.success("查询成功",
                new GetLoanDto(loan.getId(),
                        loan.getSub(), loan.getHolding(),
                        loan.getRenewals(), loan.getLendTime(),
                        loan.getDueTime(), loan.getReturnTime(),
                        holdings));

    }
}
