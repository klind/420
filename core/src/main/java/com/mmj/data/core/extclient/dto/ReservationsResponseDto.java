package com.mmj.data.core.extclient.dto;

import java.util.List;

public class ReservationsResponseDto extends BaseResponseDto {

    private static final long serialVersionUID = -1396966585849514473L;
    private List<ReservationDto> reservations;

    public List<ReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDto> reservations) {
        this.reservations = reservations;
    }

}
