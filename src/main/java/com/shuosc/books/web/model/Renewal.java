package com.shuosc.books.web.model;

import com.shuosc.books.web.enums.RenewalReason;
import org.bson.BsonTimestamp;


public class Renewal {
    private BsonTimestamp renewTime;
    private BsonTimestamp dueTime;
    private RenewalReason renewalReason;

    public Renewal() {
    }

    public BsonTimestamp getRenewTime() {
        return renewTime;
    }

    public void setRenewTime(BsonTimestamp renewTime) {
        this.renewTime = renewTime;
    }

    public BsonTimestamp getDueTime() {
        return dueTime;
    }

    public void setDueTime(BsonTimestamp dueTime) {
        this.dueTime = dueTime;
    }

    public RenewalReason getRenewalReason() {
        return renewalReason;
    }

    public void setRenewalReason(RenewalReason renewalReason) {
        this.renewalReason = renewalReason;
    }

    @Override
    public String toString() {
        return "Renewal{" +
                "renewTime=" + renewTime +
                ", dueTime=" + dueTime +
                ", renewalReason" + renewalReason +
                '}';
    }
}
