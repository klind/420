package com.mmj.data.core.extclient.dto;

import java.math.BigDecimal;
import java.util.List;

public class ModifyOrderResponseDto extends BaseResponseDto {

    private String confirmationNumber;
    private String orderId;
    private ReservationDto updatedReservation;
    private List<ReservationChangeDto> reservationChanges;
    private List<FeeDto> changeFees;
    private BigDecimal refundableTotal;
    private BigDecimal nonRefundableTotal;

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ReservationDto getUpdatedReservation() {
        return updatedReservation;
    }

    public void setUpdatedReservation(ReservationDto updatedReservation) {
        this.updatedReservation = updatedReservation;
    }

    public List<ReservationChangeDto> getReservationChanges() {
        return reservationChanges;
    }

    public void setReservationChanges(List<ReservationChangeDto> reservationChanges) {
        this.reservationChanges = reservationChanges;
    }

    public List<FeeDto> getChangeFees() {
        return changeFees;
    }

    public void setChangeFees(List<FeeDto> changeFees) {
        this.changeFees = changeFees;
    }

    public BigDecimal getRefundableTotal() {
        return refundableTotal;
    }

    public void setRefundableTotal(BigDecimal refundableTotal) {
        this.refundableTotal = refundableTotal;
    }

    public BigDecimal getNonRefundableTotal() {
        return nonRefundableTotal;
    }

    public void setNonRefundableTotal(BigDecimal nonRefundableTotal) {
        this.nonRefundableTotal = nonRefundableTotal;
    }
}
