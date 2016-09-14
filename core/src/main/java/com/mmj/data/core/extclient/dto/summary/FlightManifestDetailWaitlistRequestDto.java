package com.mmj.data.core.extclient.dto.summary;

import java.util.ArrayList;
import java.util.List;

public class FlightManifestDetailWaitlistRequestDto {

    private String flightNumber;
    private String leg;
    private String departDate;
    private String hireDate;
    private String employeeId;
    private Boolean forecast;
    private List<PassengerWaitlistDto> passengers = new ArrayList<PassengerWaitlistDto>();

    /**
     * No args constructor for use in serialization
     */
    public FlightManifestDetailWaitlistRequestDto() {
    }

    /**
     * @return The flightNumber
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * @param flightNumber The flightNumber
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * @return The leg
     */
    public String getLeg() {
        return leg;
    }

    /**
     * @param leg The leg
     */
    public void setLeg(String leg) {
        this.leg = leg;
    }

    /**
     * @return The departDate
     */
    public String getDepartDate() {
        return departDate;
    }

    /**
     * @param departDate The departDate
     */
    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    /**
     * @return The hireDate
     */
    public String getHireDate() {
        return hireDate;
    }

    /**
     * @param hireDate The hireDate
     */
    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    /**
     * @return The employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId The employeeId
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return The forecast
     */
    public Boolean getForecast() {
        return forecast;
    }

    /**
     * @param forecast The forecast
     */
    public void setForecast(Boolean forecast) {
        this.forecast = forecast;
    }

    public List<PassengerWaitlistDto> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerWaitlistDto> passengers) {
        this.passengers = passengers;
    }
}
