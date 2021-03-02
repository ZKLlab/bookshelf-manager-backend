package com.shuosc.books.web.controller;

import com.shuosc.books.web.constant.BooksConstant;
import com.shuosc.books.web.model.*;
import com.shuosc.books.web.service.HoldingService;
import com.shuosc.books.web.service.LoanService;
import com.shuosc.books.web.service.RenewalService;
import org.bson.BsonTimestamp;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/loans")
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

    @PostMapping(path = "/borrow")
    public Return borrowBook(String sub, Long barcode) {
        Holding holding = holdingService.findByBarcode(barcode);

        if (holding == null || !holding.getBook().getVisible())
            return Return.failure("没有这本书, 借阅失败");

        if (!parseState(sub, holding))
            return Return.failure("图书状态为" + holding.getState() + ", 借阅失败");

        holding.setState(HoldingState.Lent);
        holdingService.save(holding);

        loanService.save(sub, holding);

        return Return.success("图书借阅成功");
    }

    @PostMapping(path = "/return")
    public Return returnBook(String sub, ObjectId holdingId) {
        Holding holding = holdingService.findById(holdingId);
        if (holding == null)
            return Return.failure("图书不存在, 还书失败");

        Loan loan = loanService.findBySubHolding(sub, holding);
        if (loan == null || loan.getHolding().getState() != HoldingState.Lent)
            return Return.failure("你没有借这本书，还书失败");

        holding.setState(HoldingState.Lending);
        loanService.updateReturntime(sub, holding);
        holdingService.update(holdingId, holding);
        return Return.success("还书成功");
    }

    private boolean parseState(String sub, Holding holding) {
        switch (holding.getState()) {
            case Unlisted:
            case Reference:
            case Closed:
            case Damaged:
            case Lost:
            case Lent:
                return false;
            default:
                return true;
        }
    }

    @GetMapping(path = "")
    public Return showLoans(String sub) {
        List<Loan> loans = loanService.findBySub(sub);
        return Return.success("查询成功", loans);
    }

    @PostMapping(path = "/renew/{id}")
    public Return renew(String sub, @PathVariable ObjectId id, RenewalReason reason) {
        Loan loan = loanService.findById(id);
        if (loan == null || !loan.getSub().equals(sub))
            return Return.failure("记录不存在");

        Renewal renewal = new Renewal();
        renewal.setRenewalReason(reason);
        renewal.setRenewTime(new BsonTimestamp(System.currentTimeMillis()));
        renewal.setDueTime(new BsonTimestamp(renewal.getRenewTime().getTime() + BooksConstant.BORROWING_TIME_MILLIS));

        loanService.updateDuetime(loan.getId());
        loanService.updateRenewals(loan.getId(), renewal);
        renewalService.save(renewal);
        return Return.success("续借成功");
    }
}
