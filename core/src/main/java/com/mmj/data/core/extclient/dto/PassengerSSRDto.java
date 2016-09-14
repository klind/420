package com.mmj.data.core.extclient.dto;

import java.math.BigDecimal;

public class PassengerSSRDto extends G4FlightDto {

    private static final long serialVersionUID = -6879293506765293703L;
    private String code;
    private BigDecimal feeAmount;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

}
