package com.mmj.data.ejb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "failed_payment_refunds")
public class FailedPaymentRefundsEN {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "itinerary")
    @NotNull
    private String itinerary;

    @Column(name = "paid_amount")
    private BigDecimal padAmount;

    @Column(name = "balance_due")
    private BigDecimal balancedue;

    @Column(name = "booked_date")
    @NotNull
    private String bookedDate;

    @Column(name = "employee_id")
    @NotNull
    private String enployeeId;

    @Column(name = "date_failed")
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dateFailed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public BigDecimal getPadAmount() {
        return padAmount;
    }

    public void setPadAmount(BigDecimal padAmount) {
        this.padAmount = padAmount;
    }

    public BigDecimal getBalancedue() {
        return balancedue;
    }

    public void setBalancedue(BigDecimal balancedue) {
        this.balancedue = balancedue;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getEnployeeId() {
        return enployeeId;
    }

    public void setEnployeeId(String enployeeId) {
        this.enployeeId = enployeeId;
    }

    public Date getDateFailed() {
        return dateFailed;
    }

    public void setDateFailed(Date dateFailed) {
        this.dateFailed = dateFailed;
    }
}
