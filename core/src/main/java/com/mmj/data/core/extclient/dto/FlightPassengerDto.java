package com.mmj.data.core.extclient.dto;

import com.mmj.data.core.extclient.Passenger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FlightPassengerDto extends G4FlightDto implements Passenger {

    private static final long serialVersionUID = -7007786592365118788L;
    @NotNull(message = "First Name cannot be null")
    @Size(max = 15, message = "First Name cannot be more than 15 characters")
    private String firstName;
    @Size(max = 15, message = "Middle Name cannot be more than 15 characters")
    private String middleName;
    @NotNull(message = "Last Name cannot be null")
    @Size(max = 20, message = "Last Name cannot be more than 20 characters")
    private String lastName;
    @NotNull(message = "Date of birth cannot be null")
    private String dateOfBirth;
    @NotNull(message = "Gender cannot be null")
    private String gender;
    @Size(max = 3, message = "Seat cannot be more than 3 characters")
    private String seat;
    private BigDecimal seatFee;
    private List<AncillaryFeeDto> bags;
    private List<AncillaryFeeDto> ssrs;
    private AncillaryFeeDto priorityBoarding;
    private EmployeeInformationDto employeeInformation;
    private PassengerIdentificationDto passengerIdentification;
    private List<InfantTravelerDto> infantTravelerDtos;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public BigDecimal getSeatFee() {
        return seatFee;
    }

    public void setSeatFee(BigDecimal seatFee) {
        this.seatFee = seatFee;
    }

    public List<AncillaryFeeDto> getBags() {

        if(bags == null){
            bags = new ArrayList<AncillaryFeeDto>();
        }

        return bags;
    }

    public List<AncillaryFeeDto> getSsrs() {

        if(ssrs == null){
            ssrs = new ArrayList<AncillaryFeeDto>();
        }

        return ssrs;
    }

    public AncillaryFeeDto getPriorityBoarding() {
        return priorityBoarding;
    }

    public void setPriorityBoarding(AncillaryFeeDto priorityBoarding) {
        this.priorityBoarding = priorityBoarding;
    }

    public EmployeeInformationDto getEmployeeInformation() {
        return employeeInformation;
    }

    public void setEmployeeInformation(EmployeeInformationDto employeeInformation) {
        this.employeeInformation = employeeInformation;
    }

    public PassengerIdentificationDto getPassengerIdentification() {
        return passengerIdentification;
    }

    public void setPassengerIdentification(PassengerIdentificationDto passengerIdentification) {
        this.passengerIdentification = passengerIdentification;
    }

    public List<InfantTravelerDto> getInfantTravelerDtos() {

        if(infantTravelerDtos == null){
            infantTravelerDtos =  new ArrayList<InfantTravelerDto>();
        }

        return infantTravelerDtos;
    }
}
