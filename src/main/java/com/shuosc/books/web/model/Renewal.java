package com.shuosc.books.web.model;

import com.shuosc.books.web.enums.RenewalReason;

import java.util.Date;


public class Renewal {
    private Date renewTime;
    private Date dueTime;
    private RenewalReason renewalReason;

    public Renewal() {
    }

    public Renewal(Date renewTime, Date dueTime, RenewalReason renewalReason) {
        this.renewTime = renewTime;
        this.dueTime = dueTime;
        this.renewalReason = renewalReason;
    }

    public Date getRenewTime() {
        return renewTime;
    }

    public void setRenewTime(Date renewTime) {
        this.renewTime = renewTime;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public RenewalReason getRenewalReason() {
        return renewalReason;
    }

    public void setRenewalReason(RenewalReason renewalReason) {
        this.renewalReason = renewalReason;
    }
}
