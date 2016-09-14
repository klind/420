package com.mmj.data.core.extclient.dto;

public class BookReservationResponseDto extends BaseResponseDto {

    private static final long serialVersionUID = 3761693211650601804L;
    private ReservationDto reservationDto;

    public ReservationDto getReservationDto() {
        return reservationDto;
    }

    public void setReservationDto(ReservationDto reservationDto) {
        this.reservationDto = reservationDto;
    }
}
