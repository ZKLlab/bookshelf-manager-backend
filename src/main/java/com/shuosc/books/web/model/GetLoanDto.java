package com.shuosc.books.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;
import java.util.List;

public class GetLoanDto {
    private String id;
    private String sub;
    private Holding holding;
    private List<Renewal> renewals;
    private Date lendTime;
    private Date dueTime;
    private Date returnTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Holding> relatedHoldings;

    public GetLoanDto() {
    }

    public GetLoanDto(String id, String sub,
                      Holding holding, List<Renewal> renewals,
                      Date lendTime, Date dueTime, Date returnTime,
                      List<Holding> relatedHoldings) {
        this.id = id;
        this.sub = sub;
        this.holding = holding;
        this.renewals = renewals;
        this.lendTime = lendTime;
        this.dueTime = dueTime;
        this.returnTime = returnTime;
        this.relatedHoldings = relatedHoldings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Holding getHolding() {
        return holding;
    }

    public void setHolding(Holding holding) {
        this.holding = holding;
    }

    public List<Renewal> getRenewals() {
        return renewals;
    }

    public void setRenewals(List<Renewal> renewals) {
        this.renewals = renewals;
    }

    public Date getLendTime() {
        return lendTime;
    }

    public void setLendTime(Date lendTime) {
        this.lendTime = lendTime;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public List<Holding> getRelatedHoldings() {
        return relatedHoldings;
    }

    public void setRelatedHoldings(List<Holding> relatedHoldings) {
        this.relatedHoldings = relatedHoldings;
    }
}
