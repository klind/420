package com.mmj.data.core.extclient.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FlightBookingRequestDto extends G4FlightDto {

    private static final long serialVersionUID = 6198315885369315817L;
    @NotNull(message = "Market cannot be null")
    @Size(min = 6, max = 6, message = "Market must be 6 characters")
    private String market;
    @NotNull(message = "Booked By cannot be null")
    private String bookedBy;
    @NotNull(message = "Customer Number cannot be null")
    private String customerNumber;
    @NotNull(message = "Booking Type cannot be null")
    @Size(min = 2, max = 2, message = "Booking Type must be 2 characters")
    private String bookingType;
    private BigDecimal convenienceFeeTotal;
    @Email(message = "Email is invalid")
    private String primaryEmail;
    @Size(max = 10, message = "Fax Number cannot be more than 10 digits")
    private String fax;
    @Size(max = 250, message = "Comments cannot be more than 250 characters")
    private String comments;
    private String agent;
    @Size(max = 25, message = "Group Name cannot be more than 25 characters")
    private String groupName;
    private String travelProtection;
    private BigDecimal travelProtectionTotal;
    private Integer childCount;
    private BigDecimal itineraryTotal;
    @Size(max = 1, message = "Phone Number type cannot be more than 1 character")
    private String phone1Type;
    @Size(max = 10, message = "Phone Number cannot be more than 10 digits")
    private String phone1;
    @Size(max = 1, message = "Phone Number 2 type cannot be more than 1 character")
    private String phone2Type;
    @Size(max = 10, message = "Phone Number 2 cannot be more than 10 digits")
    private String phone2;
    private List<String> linkedReservations;

    @NotNull(message = "Segments cannot be null")
    private List<SegmentBookingRequestDto> segments;
    private List<ReservationPaymentDto> payments;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public BigDecimal getConvenienceFeeTotal() {
        return convenienceFeeTotal;
    }

    public void setConvenienceFeeTotal(BigDecimal convenienceFeeTotal) {
        this.convenienceFeeTotal = convenienceFeeTotal;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getPhone1Type() {
        return phone1Type;
    }

    public void setPhone1Type(String phone1Type) {
        this.phone1Type = phone1Type;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2Type() {
        return phone2Type;
    }

    public void setPhone2Type(String phone2Type) {
        this.phone2Type = phone2Type;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public List<String> getLinkedReservations() {

        if (linkedReservations == null) {
            linkedReservations = new ArrayList<String>();
        }

        return linkedReservations;
    }

    public List<SegmentBookingRequestDto> getSegments() {

        if (segments == null) {
            segments = new ArrayList<SegmentBookingRequestDto>();
        }

        return segments;
    }

    public List<ReservationPaymentDto> getPayments() {

        if (payments == null) {
            payments = new ArrayList<ReservationPaymentDto>();
        }

        return payments;
    }

    public void setPayments(List<ReservationPaymentDto> payments) {
        this.payments = payments;
    }
}

