package com.mmj.data.core.extclient.dto.lookup;

import java.util.Date;

public class PaymentTypeDTO {
    private String company;
    private String idx;
    private String cflg;
    private Integer page;
    private Date dchg;
    private Date tchg;
    private String pchg;
    private String crt;
    private String description;
    private String cor1;
    private String cor2;
    private String validFor;
    private Long recordId;

    public PaymentTypeDTO() {
    }

    public PaymentTypeDTO(String company, String idx) {
        this.company = company;
        this.idx = idx;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        if(idx != null) {
            this.idx = idx.toUpperCase();
        }
    }

    public String getCflg() {
        return cflg;
    }

    public void setCflg(String cflg) {
        this.cflg = cflg;
    }

    public Date getDchg() {
        return dchg;
    }

    public void setDchg(Date dchg) {
        this.dchg = dchg;
    }

    public Date getTchg() {
        return tchg;
    }

    public void setTchg(Date tchg) {
        this.tchg = tchg;
    }

    public String getPchg() {
        return pchg;
    }

    public void setPchg(String pchg) {
        this.pchg = pchg;
    }

    public String getCrt() {
        return crt;
    }

    public void setCrt(String crt) {
        this.crt = crt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description != null) {
            this.description = description.toUpperCase();
        }
    }

    public String getCor1() {
        return cor1;
    }

    public void setCor1(String cor1) {
        if(cor1 != null) {
            this.cor1 = cor1.toUpperCase();
        }
    }

    public String getCor2() {
        return cor2;
    }

    public void setCor2(String cor2) {
        this.cor2 = cor2;
    }

    public String getValidFor() {
        return validFor;
    }

    public void setValidFor(String validFor) {
        if(validFor != null) {
            this.validFor = validFor.toUpperCase();
        }
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
}
