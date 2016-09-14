package com.mmj.data.core.extclient.dto;

public class FareClassInventoryDto extends G4FlightDto{

    private static final long serialVersionUID = -6401941268831149542L;
    private String fareclassCode;
    private int numberAvail;
    private int totalFlightSeats;
    private int waitListedCount;

    public String getFareclassCode() {
        return fareclassCode;
    }

    public void setFareclassCode(String fareclassCode) {
        this.fareclassCode = fareclassCode;
    }

    public int getNumberAvail() {
        return numberAvail;
    }

    public void setNumberAvail(int numberAvail) {
        this.numberAvail = numberAvail;
    }

    public int getTotalFlightSeats() {
        return totalFlightSeats;
    }

    public void setTotalFlightSeats(int totalFlightSeats) {
        this.totalFlightSeats = totalFlightSeats;
    }

    public int getWaitListedCount() {
        return waitListedCount;
    }

    public void setWaitListedCount(int waitListedCount) {
        this.waitListedCount = waitListedCount;
    }
}
