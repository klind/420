package com.mmj.data.core.extclient.dto;

import com.mmj.data.core.serializer.BooleanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.util.List;

public class PassengerSegmentDto extends G4FlightDto {

    private static final long serialVersionUID = 1L;
    private String company;
    private String itinerary;
    private String segment;
    private String ind;
    private String paxNum;
    private String departureDate;
    private String origin;
    private String destination;
    private String flightNumber;
    private String leg;
    private String finalDestination;
    private String paxType;
    private String status;
    private String revenueType;
    private String fareClassCode;
    private BigDecimal fare;
    private List<FeeDto> flightFees;
    private String country;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean checkedIn;
    private String bookTime;
    private String checkInTime;
    private String departDateAndTime;
    private String arriveDateAndTime;
    private String boardType;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean onboard;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean hasFlown;
    private String dhsTransaction;
    private PassengerInformationDto passengerInformation;
    private EmployeeInformationDto employeeInformation;
    private PassengerSeatDto passengerSeat;
    private PassengerPriorityAccessDto passengerPriorityAccess;
    private List<PassengerSSRDto> passengerSSRs;
    private List<PassengerBagDto> passengerBags;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }

    public String getPaxNum() {
        return paxNum;
    }

    public void setPaxNum(String paxNum) {
        this.paxNum = paxNum;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getLeg() {
        return leg;
    }

    public void setLeg(String leg) {
        this.leg = leg;
    }

    public String getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
    }

    public String getPaxType() {
        return paxType;
    }

    public void setPaxType(String paxType) {
        this.paxType = paxType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRevenueType() {
        return revenueType;
    }

    public void setRevenueType(String revenueType) {
        this.revenueType = revenueType;
    }

    public String getFareClassCode() {
        return fareClassCode;
    }

    public void setFareClassCode(String fareClassCode) {
        this.fareClassCode = fareClassCode;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public List<FeeDto> getFlightFees() {
        return flightFees;
    }

    public void setFlightFees(List<FeeDto> flightFees) {
        this.flightFees = flightFees;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isCheckedIn() {
        return checkedIn == null ? false : checkedIn.booleanValue();
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getDepartDateAndTime() {
        return departDateAndTime;
    }

    public void setDepartDateAndTime(String departDateAndTime) {
        this.departDateAndTime = departDateAndTime;
    }

    public String getArriveDateAndTime() {
        return arriveDateAndTime;
    }

    public void setArriveDateAndTime(String arriveDateAndTime) {
        this.arriveDateAndTime = arriveDateAndTime;
    }

    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }

    public boolean isOnboard() {
        return onboard == null ? false : onboard.booleanValue();
    }

    public void setOnboard(boolean onboard) {
        this.onboard = onboard;
    }

    public boolean isHasFlown() {
        return hasFlown == null ? false : hasFlown.booleanValue();
    }

    public void setHasFlown(boolean hasFlown) {
        this.hasFlown = hasFlown;
    }

    public String getDhsTransaction() {
        return dhsTransaction;
    }

    public void setDhsTransaction(String dhsTransaction) {
        this.dhsTransaction = dhsTransaction;
    }

    public PassengerInformationDto getPassengerInformation() {
        return passengerInformation;
    }

    public void setPassengerInformation(PassengerInformationDto passengerInformation) {
        this.passengerInformation = passengerInformation;
    }

    public EmployeeInformationDto getEmployeeInformation() {
        return employeeInformation;
    }

    public void setEmployeeInformation(EmployeeInformationDto employeeInformation) {
        this.employeeInformation = employeeInformation;
    }

    public PassengerSeatDto getPassengerSeat() {
        return passengerSeat;
    }

    public void setPassengerSeat(PassengerSeatDto passengerSeat) {
        this.passengerSeat = passengerSeat;
    }

    public PassengerPriorityAccessDto getPassengerPriorityAccess() {
        return passengerPriorityAccess;
    }

    public void setPassengerPriorityAccess(PassengerPriorityAccessDto passengerPriorityAccess) {
        this.passengerPriorityAccess = passengerPriorityAccess;
    }

    public List<PassengerSSRDto> getPassengerSSRs() {
        return passengerSSRs;
    }

    public void setPassengerSSRs(List<PassengerSSRDto> passengerSSRs) {
        this.passengerSSRs = passengerSSRs;
    }

    public List<PassengerBagDto> getPassengerBags() {
        return passengerBags;
    }

    public void setPassengerBags(List<PassengerBagDto> passengerBags) {
        this.passengerBags = passengerBags;
    }


}
