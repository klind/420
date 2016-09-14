package com.mmj.data.client.dto;

import java.io.Serializable;

/**
 *
 */
public class PassengerDTO implements Serializable {

    private Long id;
    private String employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String relationship;
    private String dob;
    private Long phone;
    private Boolean verified;
    private String reAddress;
    private String knownTravelerNumber;

    public PassengerDTO() {
    }

    public PassengerDTO(Long id, String employeeId, String firstName, String middleName, String lastName, String gender, String relationship, String dob, Long phone, String reAddress, String knownTravelerNumber, Boolean verified) {
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

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Boolean isVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getReAddress() {
        return reAddress;
    }

    public void setReAddress(String reAddress) {
        this.reAddress = reAddress;
    }

    public String getknownTravelerNumber() {
        return knownTravelerNumber;
    }

    public void setknownTravelerNumber(String knownTravelerNumber) {
        this.knownTravelerNumber = knownTravelerNumber;
    }
}
