package com.mmj.data.core.extclient.dto;

import java.math.BigDecimal;

public class FlightCalendarInfoDto extends G4FlightDto{

    private static final long serialVersionUID = -2867478829108664245L;
    private String availableDate;
    private BigDecimal maxFare;
    private BigDecimal minFare;
    private String firstDepartureTime;
    private String lastDepartureTime;

    public String getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }

    public BigDecimal getMaxFare() {
        return maxFare;
    }

    public void setMaxFare(BigDecimal maxFare) {
        this.maxFare = maxFare;
    }

    public BigDecimal getMinFare() {
        return minFare;
    }

    public void setMinFare(BigDecimal minFare) {
        this.minFare = minFare;
    }

    public String getFirstDepartureTime() {
        return firstDepartureTime;
    }

    public void setFirstDepartureTime(String firstDepartureTime) {
        this.firstDepartureTime = firstDepartureTime;
    }

    public String getLastDepartureTime() {
        return lastDepartureTime;
    }

    public void setLastDepartureTime(String lastDepartureTime) {
        this.lastDepartureTime = lastDepartureTime;
    }

}
