package com.mmj.data.core.extclient.dto;

import java.util.ArrayList;
import java.util.List;

public class SegmentBookingRequestDto extends G4FlightDto{

    private static final long serialVersionUID = 3182079841007108708L;
    private ShopDto shopDto;
    private List<FlightPassengerDto> flightPassengers;

    public ShopDto getShopDto() {
        return shopDto;
    }

    public void setShopDto(ShopDto shopDto) {
        this.shopDto = shopDto;
    }

    public List<FlightPassengerDto> getFlightPassengers() {

        if(flightPassengers == null){
            flightPassengers = new ArrayList<FlightPassengerDto>();
        }

        return flightPassengers;
    }

    public void setFlightPassengers(List<FlightPassengerDto> flightPassengers) {
        this.flightPassengers = flightPassengers;
    }
}

