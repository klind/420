package com.mmj.data.core.dto.entity;

import com.mmj.data.core.constant.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

public class GuestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotNull(message = "employee id may not be null")
    @Size(min = 1, max = 6, message = "employeeId must be between 1 and 6 characters")
    private String employeeId;
    @NotNull(message = "firstName may not be null")
    @Pattern(regexp= Constants.FIRST_NAME_REGEX, message = "firstName must match " + Constants.FIRST_NAME_REGEX)
    private String firstName;
    private String middleName;
    @NotNull(message = "lastName may not be null")
    @Pattern(regexp=Constants.LAST_NAME_REGEX, message = "lastName must match " + Constants.LAST_NAME_REGEX)
    private String lastName;
    @NotNull(message = "dob may not be null")
    private LocalDate dob;
    @NotNull(message = "employee gender may not be null")
    @Pattern(regexp=Constants.EMPLOYEE_GENDER_REGEX, message = "employee gender must match " + Constants.EMPLOYEE_GENDER_REGEX)
    private String gender;
    private String knownTravelerNumber;

    public GuestDTO(Long id, String employeeId, String firstName, String middleName, String lastName, LocalDate dob, String gender, String knownTravelerNumber) {
        this.id = id;
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.knownTravelerNumber = knownTravelerNumber;
    }

    public GuestDTO() {

    }

    public Long getId() {
        return id;
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getKnownTravelerNumber() {
        return knownTravelerNumber;
    }

    public void setKnownTravelerNumber(String knownTravelerNumber) {
        this.knownTravelerNumber = knownTravelerNumber;
    }
}
