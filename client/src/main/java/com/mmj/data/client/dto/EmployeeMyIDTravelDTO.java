package com.mmj.data.client.dto;

import java.util.List;

/**
 *
 */
public class EmployeeMyIDTravelDTO {

    private String employeeId;
    private String salutation;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String gender;
    private String dob;
    private String lastHireDate;
    private String termDate;
    private String phone;
    private boolean travelStatus;
    private Integer guestPassesAlloted;
    private Integer guestPassesBooked;
    private Integer vacationPassesAlloted;
    private Integer vacationPassesUsed;
    private String saCode;

    private List<PassengerDTO> verifiedPassengers;

    public EmployeeMyIDTravelDTO() {
        super();
    }

    public EmployeeMyIDTravelDTO(String employeeId, String salutation, String firstName, String middleName, String lastName, String email, String gender, String dob, String lastHireDate, String termDate,
                                 String phone, boolean travelStatus, Integer guestPassesAlloted, Integer guestPassesBooked, Integer vacationPassesAlloted, Integer vacationPassesUsed,
                                 String saCode, List<PassengerDTO> verifiedPassengers) {
        this.employeeId = employeeId;
        this.salutation = salutation;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;                       // TODO: Need to persist from pipeline.
        this.dob = dob;
        this.lastHireDate = lastHireDate;
        this.termDate = termDate;
        this.phone = phone;
        this.travelStatus = travelStatus;           // Suspensions can contain sensitive info, so instead of passing list of suspensions, we simply pass the employee's travel status.
        this.guestPassesAlloted = guestPassesAlloted;
        this.guestPassesBooked = guestPassesBooked;
        this.vacationPassesAlloted = vacationPassesAlloted;
        this.vacationPassesUsed = vacationPassesUsed;
        this.saCode = saCode;
        this.verifiedPassengers = verifiedPassengers;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PassengerDTO> getVerifiedPassengers() {
        return verifiedPassengers;
    }

    public void setVerifiedPassengers(List<PassengerDTO> verifiedPassengers) {
        this.verifiedPassengers = verifiedPassengers;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastHireDate() {
        return lastHireDate;
    }

    public void setLastHireDate(String lastHireDate) {
        this.lastHireDate = lastHireDate;
    }

    public String getTermDate() {
        return termDate;
    }

    public void setTermDate(String termDate) {
        this.termDate = termDate;
    }

    public boolean isTravelStatus() {
        return travelStatus;
    }

    public void setTravelStatus(boolean travelStatus) {
        this.travelStatus = travelStatus;
    }

    public Integer getGuestPassesAlloted() {
        return guestPassesAlloted;
    }

    public void setGuestPassesAlloted(Integer guestPassesAlloted) {
        this.guestPassesAlloted = guestPassesAlloted;
    }

    public Integer getGuestPassesBooked() {
        return guestPassesBooked;
    }

    public void setGuestPassesBooked(Integer guestPassesBooked) {
        this.guestPassesBooked = guestPassesBooked;
    }

    public Integer getVacationPassesAlloted() {
        return vacationPassesAlloted;
    }

    public void setVacationPassesAlloted(Integer vacationPassesAlloted) {
        this.vacationPassesAlloted = vacationPassesAlloted;
    }

    public Integer getVacationPassesUsed() {
        return vacationPassesUsed;
    }

    public void setVacationPassesUsed(Integer vacationPassesUsed) {
        this.vacationPassesUsed = vacationPassesUsed;
    }

    public String getSaCode() {
        return saCode;
    }

    public void setSaCode(String saCode) {
        this.saCode = saCode;
    }
}
