package com.mmj.data.core.dto.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;

/**
 *
 */
public class PassengerDTO implements Serializable {

    public static final Comparator<PassengerDTO> ID_COMPARATOR = new Comparator<PassengerDTO>() {
        @Override public int compare(PassengerDTO passengerDTO1, PassengerDTO passengerDTO2) {
            return passengerDTO1.getId().compareTo(passengerDTO2.getId());
        }
    };

    public static final Comparator<PassengerDTO> RELATIONSHIP_COMPARATOR = new Comparator<PassengerDTO>() {
        @Override public int compare(PassengerDTO passengerDTO1, PassengerDTO passengerDTO2) {
            return passengerDTO2.getRelationship().getValue().compareTo(passengerDTO1.getRelationship().getValue());
        }
    };

    private Long id;
    // Employee id is a string because of leading 0s.
    private String employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private TravelerRelationshipDTO relationship;
    private LocalDate dob;
    private Long phone;
    private boolean verified;
    private String reAddress;
    private String knownTravelerNumber;

    public PassengerDTO() {
    }

    public PassengerDTO(Long id, String employeeId, String firstName, String middleName, String lastName, String gender, TravelerRelationshipDTO relationship, LocalDate dob, Long phone, String reAddress, String knownTravelerNumber, boolean verified) {
        this.id = id;
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.relationship = relationship;
        this.dob = dob;
        this.phone = phone;
        this.reAddress = reAddress;
        this.knownTravelerNumber = knownTravelerNumber;
        this.verified = verified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public TravelerRelationshipDTO getRelationship() {
        return relationship;
    }

    public void setRelationship(TravelerRelationshipDTO relationship) {
        this.relationship = relationship;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getReAddress() {
        return reAddress;
    }

    public void setReAddress(String reAddress) {
        this.reAddress = reAddress;
    }

    public String getKnownTravelerNumber() {
        return knownTravelerNumber;
    }

    public void setKnownTravelerNumber(String knownTravelerNumber) {
        this.knownTravelerNumber = knownTravelerNumber;
    }
}
