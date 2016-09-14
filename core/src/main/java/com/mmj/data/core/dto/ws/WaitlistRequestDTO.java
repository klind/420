package com.mmj.data.core.dto.ws;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WaitlistRequestDTO implements Serializable {

    private String flightNumber;
    private String leg;
    private String departDate;
    private String hireDate;
    private String employeeId;
    private boolean forecast;
    private String bookingType;
    private boolean isVacationUpgrade;
    private String itn;
    private List<WaitlistPassengerDTO> passengers = new ArrayList<WaitlistPassengerDTO>();

    /**
     * No args constructor for use in serialization
     */
    public WaitlistRequestDTO() {
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
    public boolean getForecast() {
        return forecast;
    }

    /**
     * @param forecast The forecast
     */
    public void setForecast(boolean forecast) {
        this.forecast = forecast;
    }

    public List<WaitlistPassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<WaitlistPassengerDTO> passengers) {
        this.passengers = passengers;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public boolean getVacationUpgrade() {
        return isVacationUpgrade;
    }

    public String getItn() {
        return itn;
    }

    public void setItn(String itn) {
        this.itn = itn;
    }

    public void setVacationUpgrade(boolean vacationUpgrade) {
        isVacationUpgrade = vacationUpgrade;
    }

}
