package com.mmj.data.core.dto.entity;

import com.mmj.data.core.constant.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 *
 */
public class EmployeePartialDTO extends EmployeeBaseDTO {

    @NotNull(message = "dob may not be null")
    //@Pattern(regexp=Constants.DOB_REGEX)
    private LocalDate dob;
    @NotNull(message = "lastHireDate may not be null")
    //@Pattern(regexp=Constants.DATE_REGEX)
    private LocalDate lastHireDate;
    //@Pattern(regexp=Constants.DATE_REGEX)
    private LocalDate termDate;
    //@NotNull(message = "phone may not be null") // Allow null for for now as there are many employess in ultipro that have no phone number.
    @Pattern(regexp = Constants.PHONE_REGEX, message = "phone must match " + Constants.PHONE_REGEX)
    private String phone;
    @NotNull(message = "jobCode may not be null")
    @Pattern(regexp = Constants.JOB_CODE_REGEX, message = "jobCode must match " + Constants.JOB_CODE_REGEX)
    private String jobCode;
    @NotNull(message = "departmentId may not be null")
    @Pattern(regexp = Constants.DEPARTMENT_ID_REGEX, message = "departmentId must match " + Constants.DEPARTMENT_ID_REGEX)
    private String departmentId;
    @NotNull(message = "departmentName may not be null")
    @Size(min = 1, max = 50, message = "departmentName must be between 1 and 50 characters")
    private String departmentName;
    @NotNull(message = "managerId may not be null")
    @Pattern(regexp = Constants.EMPLOYEE_ID_REGEX, message = "managerId must match " + Constants.EMPLOYEE_ID_REGEX)
    private String managerId;
    private String managerName;
    @NotNull(message = "employeeType may not be null")
    @Pattern(regexp = Constants.EMPLOYEE_TYPE_REGEX, message = "employeeType must match " + Constants.EMPLOYEE_TYPE_REGEX)
    private String employeeType;

    public EmployeePartialDTO() {
        super();
    }

    public EmployeePartialDTO(String employeeId, String salutation, String suffix, String firstName, String middleName, String lastName, String email, String title, String status, LocalDate dob, LocalDate lastHireDate, LocalDate termDate, String phone, String jobCode, String location, String departmentId, String departmentName, String managerId, String managerName, String employeeType, String gender) {
        super(employeeId, salutation, suffix, firstName, middleName, lastName, email, title, status, gender, location);
        this.dob = dob;
        this.lastHireDate = lastHireDate;
        this.termDate = termDate;
        this.phone = phone;
        this.jobCode = jobCode;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.managerId = managerId;
        this.managerName = managerName;
        this.employeeType = employeeType;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getLastHireDate() {
        return lastHireDate;
    }

    public void setLastHireDate(LocalDate lastHireDate) {
        this.lastHireDate = lastHireDate;
    }

    public LocalDate getTermDate() {
        return termDate;
    }

    public void setTermDate(LocalDate termDate) {
        this.termDate = termDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }
}
