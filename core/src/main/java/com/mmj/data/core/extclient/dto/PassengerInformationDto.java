package com.mmj.data.core.extclient.dto;

import java.util.List;

public class PassengerInformationDto {

    private String firstName;
    private String middleName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private PassengerIdentificationDto passengerIdentification;
    private List<InfantTravelerDto> infantTravelers;

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

    public PassengerIdentificationDto getPassengerIdentification() {
        return passengerIdentification;
    }

    public void setPassengerIdentification(PassengerIdentificationDto passengerIdentificationDto) {
        this.passengerIdentification = passengerIdentificationDto;
    }

    public List<InfantTravelerDto> getInfantTravelers() {
        return infantTravelers;
    }

    public void setInfantTravelers(List<InfantTravelerDto> infantTravelerDtos) {
        this.infantTravelers = infantTravelerDtos;
    }
}
