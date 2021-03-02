package com.shuosc.books.web.model;

import org.bson.BsonTimestamp;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Renewal {

    @Id
    private ObjectId id;
    private BsonTimestamp renewTime;
    private BsonTimestamp dueTime;
    private RenewalReason renewalReason;

    public Renewal() {
    }

    public ObjectId getId() {
        return id;
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
                "id=" + id +
                ", renewTime=" + renewTime +
                ", dueTime=" + dueTime +
                ", renewalReason" + renewalReason +
                '}';
    }
}
