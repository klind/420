package com.mmj.data.core.extclient.dto;

import java.math.BigDecimal;
import java.util.List;

public class ReservationDto extends G4FlightDto {

    private static final long serialVersionUID = 8181682366491664388L;
    private String company;
    private String itinerary;
    private String departDate; // format yyyy-MM-dd
    private String returnDate; // format yyyy-MM-dd
    private String status;
    private String bookedBy;
    private String agent;
    private Integer paxCount;
    private String market;
    private String comments;
    private String bookingType;
    private String groupName;
    private String customerNumber;
    private BigDecimal paidAmount;
    private String paidDate;
    private String bookedDate;
    private String bookTime;
    private String bookStatus;
    private String cancelCode;
    private String cancelledBy;
    private String cancelDate;
    private String travelProtection;
    private BigDecimal travelProtectionTotal;
    private BigDecimal convenienceFeeTotal;
    private String fax;
    private String email;
    private String guestName;
    private Integer childCount;
    private BigDecimal itineraryTotal;
    private BigDecimal balanceDue;
    private String phone1;
    private String phone1Type;
    private String phone2;
    private String phone2Type;
    private String holdDate;
    private List<String> linkedReservations;
    private ReservationPaymentDto reservationPayment;
    private List<PassengerSegmentDto> passengerSegments;

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

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public Integer getPaxCount() {
        return paxCount;
    }

    public void setPaxCount(Integer paxCount) {
        this.paxCount = paxCount;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getCancelCode() {
        return cancelCode;
    }

    public void setCancelCode(String cancelCode) {
        this.cancelCode = cancelCode;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getTravelProtection() {
        return travelProtection;
    }

    public void setTravelProtection(String travelProtection) {
        this.travelProtection = travelProtection;
    }

    public BigDecimal getTravelProtectionTotal() {
        return travelProtectionTotal;
    }

    public void setTravelProtectionTotal(BigDecimal travelProtectionTotal) {
        this.travelProtectionTotal = travelProtectionTotal;
    }

    public BigDecimal getConvenienceFeeTotal() {
        return convenienceFeeTotal;
    }

    public void setConvenienceFeeTotal(BigDecimal convenienceFeeTotal) {
        this.convenienceFeeTotal = convenienceFeeTotal;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public BigDecimal getItineraryTotal() {
        return itineraryTotal;
    }

    public void setItineraryTotal(BigDecimal itineraryTotal) {
        this.itineraryTotal = itineraryTotal;
    }

    public BigDecimal getBalanceDue() {
        return balanceDue;
    }

    public void setBalanceDue(BigDecimal balanceDue) {
        this.balanceDue = balanceDue;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone1Type() {
        return phone1Type;
    }

    public void setPhone1Type(String phone1Type) {
        this.phone1Type = phone1Type;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone2Type() {
        return phone2Type;
    }

    public void setPhone2Type(String phone2Type) {
        this.phone2Type = phone2Type;
    }

    public String getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(String holdDate) {
        this.holdDate = holdDate;
    }

    public List<String> getLinkedReservations() {
        return linkedReservations;
    }

    public void setLinkedReservations(List<String> linkedReservations) {
        this.linkedReservations = linkedReservations;
    }

    public ReservationPaymentDto getReservationPayment() {
        return reservationPayment;
    }

    public void setReservationPayment(ReservationPaymentDto reservationPayment) {
        this.reservationPayment = reservationPayment;
    }

    public List<PassengerSegmentDto> getPassengerSegments() {
        return passengerSegments;
    }

    public void setPassengerSegments(List<PassengerSegmentDto> passengerSegments) {
        this.passengerSegments = passengerSegments;
    }

}
