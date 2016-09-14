package com.mmj.data.core.dto.entity;

import com.mmj.data.core.constant.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 */
public class EmployeeBaseDTO implements Serializable {

    // employeeId is a string because of leading 0s.
    @NotNull(message = "employeeId may not be null")
    @Pattern(regexp= Constants.EMPLOYEE_ID_REGEX, message = "employeeId must match " + Constants.EMPLOYEE_ID_REGEX)
    private String employeeId;
    // Salutation will be set according to gender
    private String salutation;
    @Size(min = 1, max = 10, message = "suffix must be between 1 and 10 characters")
    private String suffix;
    @NotNull(message = "firstName may not be null")
    @Pattern(regexp=Constants.FIRST_NAME_REGEX, message = "firstName must match " + Constants.FIRST_NAME_REGEX)
    private String firstName;
    @Pattern(regexp=Constants.MIDDLE_NAME_REGEX, message = "middleName must match " + Constants.MIDDLE_NAME_REGEX)
    private String middleName;
    @NotNull(message = "lastName may not be null")
    @Pattern(regexp=Constants.LAST_NAME_REGEX, message = "lastName must match " + Constants.LAST_NAME_REGEX)
    private String lastName;
    @NotNull(message = "email may not be null")
    @Pattern(regexp=Constants.EMAIL_REGEX, message = "email must match " + Constants.EMAIL_REGEX)
    private String email;
    @NotNull(message = "title may not be null")
    @Size(min = 1, max = 50, message = "title must be between 1 and 50 characters")
    private String title;
    @NotNull(message = "status may not be null")
    @Pattern(regexp=Constants.STATUS_REGEX, message = "status must match " + Constants.EMAIL_REGEX)
    private String status;
    // TODO: Implement NotNull when we implement getting the data from pipeline.
    //@NotNull(message = "employee gender may not be null")
    //@Pattern(regexp=Constants.EMPLOYEE_GENDER_REGEX, message = "employee gender must match " + Constants.EMPLOYEE_GENDER_REGEX)
    private String gender;
    @NotNull(message = "location may not be null")
    @Pattern(regexp=Constants.LOCATION_REGEX, message = "location must match " + Constants.LOCATION_REGEX)
    private String location;

    public EmployeeBaseDTO(String employeeId, String salutation, String suffix, String firstName, String middleName, String lastName, String email, String title, String status, String gender, String location) {
        this.employeeId = employeeId;
        this.salutation = salutation;
        this.suffix = suffix;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.title = title;
        this.status = status;
        this.gender = gender;
        this.location = location;
    }

    public EmployeeBaseDTO() {

    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
