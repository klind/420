package com.mmj.data.core.extclient.dto;

import java.math.BigDecimal;

public class CancelOrderResponseDto extends BaseResponseDto {

    private ReservationDto reservationDto;
    private BigDecimal refundedAmount;
    private BigDecimal nonRefundedAmount;

    public ReservationDto getReservationDto() {
        return reservationDto;
    }

    public void setReservationDto(ReservationDto reservationDto) {
        this.reservationDto = reservationDto;
    }

    public BigDecimal getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(BigDecimal refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    public BigDecimal getNonRefundedAmount() {
        return nonRefundedAmount;
    }

    public void setNonRefundedAmount(BigDecimal nonRefundedAmount) {
        this.nonRefundedAmount = nonRefundedAmount;
    }
}
