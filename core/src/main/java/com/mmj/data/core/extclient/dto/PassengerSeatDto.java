package com.mmj.data.core.extclient.dto;

import java.math.BigDecimal;

public class PassengerSeatDto extends G4FlightDto {

    private String seat;
    private BigDecimal seatFee;

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public BigDecimal getSeatFee() {
        return seatFee;
    }

    public void setSeatFee(BigDecimal seatFee) {
        this.seatFee = seatFee;
    }
}
