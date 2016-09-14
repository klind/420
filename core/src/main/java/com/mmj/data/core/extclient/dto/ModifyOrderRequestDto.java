package com.mmj.data.core.extclient.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ModifyOrderRequestDto extends G4FlightDto {

    @NotNull(message = "Confirmation Number cannot be null")
    private String confirmationNumber;
    private String orderId;
    private List<ReservationChangeDto> reservationChanges;
    private List<ReservationPaymentDto> payments;

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

    public List<ReservationChangeDto> getReservationChanges() {
        return reservationChanges;
    }

    public void setReservationChanges(List<ReservationChangeDto> reservationChanges) {
        this.reservationChanges = reservationChanges;
    }

    public List<ReservationPaymentDto> getPayments() {
        return payments;
    }

    public void setPayments(List<ReservationPaymentDto> payments) {
        this.payments = payments;
    }
}

