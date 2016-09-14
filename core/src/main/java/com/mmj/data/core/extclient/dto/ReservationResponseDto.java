package com.mmj.data.core.extclient.dto;

public class ReservationResponseDto extends BaseResponseDto {

    private ReservationDto reservation;

    public ReservationDto getReservation() {
        return reservation;
    }

    public void setReservation(ReservationDto reservation) {
        this.reservation = reservation;
    }

}
