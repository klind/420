package com.mmj.data.core.extclient.dto;

public class FareAndAvailDto extends G4FlightDto {

    private static final long serialVersionUID = -8590583864723796490L;
    private FareDto fare;
    private FareClassInventoryDto fareClassInventory;

    public FareDto getFare() {
        return fare;
    }

    public void setFare(FareDto fare) {
        this.fare = fare;
    }

    public FareClassInventoryDto getFareClassInventory() {
        return fareClassInventory;
    }

    public void setFareClassInventory(FareClassInventoryDto fareClassInventory) {
        this.fareClassInventory = fareClassInventory;
    }
}
