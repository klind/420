package com.mmj.data.core.extclient.dto;

import java.math.BigDecimal;

public class PassengerPriorityAccessDto extends G4FlightDto {

    private BigDecimal feeAmount;

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }
}
