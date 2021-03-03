package com.shuosc.books.web.model;

import org.bson.BsonTimestamp;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;


@Document
public class Loan {
    @Id
    private ObjectId id;
    private String sub;
    private ArrayList<Renewal> renewals;
    private Boolean returned;
    private BsonTimestamp lendTime;
    private BsonTimestamp dueTime;
    private BsonTimestamp returnTime;

    @DBRef
    private Holding holding;

    public Loan() {
    }

    public Loan(Holding holding, String sub, ArrayList<Renewal> renewals, Boolean returned, BsonTimestamp lendTime, BsonTimestamp dueTime, BsonTimestamp returnTime) {
        this.holding = holding;
        this.sub = sub;
        this.renewals = renewals;
        this.returned = returned;
        this.lendTime = lendTime;
        this.dueTime = dueTime;
        this.returnTime = returnTime;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Holding getHolding() {
        return holding;
    }

    public void setHolding(Holding holding) {
        this.holding = holding;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public ArrayList<Renewal> getRenewals() {
        return renewals;
    }

    public void setRenewals(ArrayList<Renewal> renewals) {
        this.renewals = renewals;
    }

    public void addRenewal(Renewal renewal) {
        this.renewals.add(renewal);
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    public BsonTimestamp getLendTime() {
        return lendTime;
    }

    public void setLendTime(BsonTimestamp lendTime) {
        this.lendTime = lendTime;
    }

    public BsonTimestamp getDueTime() {
        return dueTime;
    }

    public void setDueTime(BsonTimestamp dueTime) {
        this.dueTime = dueTime;
    }

    public BsonTimestamp getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(BsonTimestamp returnTime) {
        this.returnTime = returnTime;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", holding=" + holding +
                ", sub='" + sub + '\'' +
                ", renewals=" + renewals +
                ", returned=" + returned +
                ", lendTime=" + lendTime +
                ", dueTime=" + dueTime +
                ", returnTime=" + returnTime +
                '}';
    }
}
