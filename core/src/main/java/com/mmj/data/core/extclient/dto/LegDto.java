package com.mmj.data.core.extclient.dto;

public class LegDto extends G4FlightDto {
    private static final long serialVersionUID = 1L;
    private String departAirport;
    private String arriveAirport;
    private String scheduledDepartDateTime;
    private String estimatedDepartDateTime;
    private String actualDepartDateTime;
    private String scheduledArriveDateTime;
    private String estimatedArriveDateTime;
    private String actualArriveDateTime;
    private String gmtScheduledDepartDateTime;
    private String gmtEstimatedDepartDateTime;
    private String gmtActualDepartDateTime;
    private String gmtScheduledArriveDateTime;
    private String gmtEstimatedArriveDateTime;
    private String gmtActualArriveDateTime;
    private int routeMiles;
    private String divertStatus;
    private String actualDepartAirport;
    private String actualArriveAirport;
    private String gmtActualOffUTC;
    private String gmtActualOnUTC;
    private String leg;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDepartAirport() {
        return departAirport;
    }

    public void setDepartAirport(String departAirport) {
        this.departAirport = departAirport;
    }

    public String getArriveAirport() {
        return arriveAirport;
    }

    public void setArriveAirport(String arriveAirport) {
        this.arriveAirport = arriveAirport;
    }

    public String getScheduledDepartDateTime() {
        return scheduledDepartDateTime;
    }

    public void setScheduledDepartDateTime(String scheduledDepartDateTime) {
        this.scheduledDepartDateTime = scheduledDepartDateTime;
    }

    public String getEstimatedDepartDateTime() {
        return estimatedDepartDateTime;
    }

    public void setEstimatedDepartDateTime(String estimatedDepartDateTime) {
        this.estimatedDepartDateTime = estimatedDepartDateTime;
    }

    public String getActualDepartDateTime() {
        return actualDepartDateTime;
    }

    public void setActualDepartDateTime(String actualDepartDateTime) {
        this.actualDepartDateTime = actualDepartDateTime;
    }

    public String getScheduledArriveDateTime() {
        return scheduledArriveDateTime;
    }

    public void setScheduledArriveDateTime(String scheduledArriveDateTime) {
        this.scheduledArriveDateTime = scheduledArriveDateTime;
    }

    public String getEstimatedArriveDateTime() {
        return estimatedArriveDateTime;
    }

    public void setEstimatedArriveDateTime(String estimatedArriveDateTime) {
        this.estimatedArriveDateTime = estimatedArriveDateTime;
    }

    public String getActualArriveDateTime() {
        return actualArriveDateTime;
    }

    public void setActualArriveDateTime(String actualArriveDateTime) {
        this.actualArriveDateTime = actualArriveDateTime;
    }

    public String getGmtScheduledDepartDateTime() {
        return gmtScheduledDepartDateTime;
    }

    public void setGmtScheduledDepartDateTime(String gmtScheduledDepartDateTime) {
        this.gmtScheduledDepartDateTime = gmtScheduledDepartDateTime;
    }

    public String getGmtEstimatedDepartDateTime() {
        return gmtEstimatedDepartDateTime;
    }

    public void setGmtEstimatedDepartDateTime(String gmtEstimatedDepartDateTime) {
        this.gmtEstimatedDepartDateTime = gmtEstimatedDepartDateTime;
    }

    public String getGmtActualDepartDateTime() {
        return gmtActualDepartDateTime;
    }

    public void setGmtActualDepartDateTime(String gmtActualDepartDateTime) {
        this.gmtActualDepartDateTime = gmtActualDepartDateTime;
    }

    public String getGmtScheduledArriveDateTime() {
        return gmtScheduledArriveDateTime;
    }

    public void setGmtScheduledArriveDateTime(String gmtScheduledArriveDateTime) {
        this.gmtScheduledArriveDateTime = gmtScheduledArriveDateTime;
    }

    public String getGmtEstimatedArriveDateTime() {
        return gmtEstimatedArriveDateTime;
    }

    public void setGmtEstimatedArriveDateTime(String gmtEstimatedArriveDateTime) {
        this.gmtEstimatedArriveDateTime = gmtEstimatedArriveDateTime;
    }

    public String getGmtActualArriveDateTime() {
        return gmtActualArriveDateTime;
    }

    public void setGmtActualArriveDateTime(String gmtActualArriveDateTime) {
        this.gmtActualArriveDateTime = gmtActualArriveDateTime;
    }

    public int getRouteMiles() {
        return routeMiles;
    }

    public void setRouteMiles(int routeMiles) {
        this.routeMiles = routeMiles;
    }

    public String getDivertStatus() {
        return divertStatus;
    }

    public void setDivertStatus(String divertStatus) {
        this.divertStatus = divertStatus;
    }

    public String getActualDepartAirport() {
        return actualDepartAirport;
    }

    public void setActualDepartAirport(String actualDepartAirport) {
        this.actualDepartAirport = actualDepartAirport;
    }

    public String getActualArriveAirport() {
        return actualArriveAirport;
    }

    public void setActualArriveAirport(String actualArriveAirport) {
        this.actualArriveAirport = actualArriveAirport;
    }

    public String getGmtActualOffUTC() {
        return gmtActualOffUTC;
    }

    public void setGmtActualOffUTC(String gmtActualOffUTC) {
        this.gmtActualOffUTC = gmtActualOffUTC;
    }

    public String getGmtActualOnUTC() {
        return gmtActualOnUTC;
    }

    public void setGmtActualOnUTC(String gmtActualOnUTC) {
        this.gmtActualOnUTC = gmtActualOnUTC;
    }

    public String getLeg() {
        return leg;
    }

    public void setLeg(String leg) {
        this.leg = leg;
    }
}
