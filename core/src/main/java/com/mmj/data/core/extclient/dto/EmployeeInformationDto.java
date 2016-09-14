package com.mmj.data.core.extclient.dto;

import javax.validation.constraints.NotNull;

public class EmployeeInformationDto {

    @NotNull(message = "SaCode cannot be null")
    private String saCode;
    private String hireDate;
    @NotNull(message = "Employee Id cannot be null")
    private String employeeId;

    public String getSaCode() {
        return saCode;
    }

    public void setSaCode(String saCode) {
        this.saCode = saCode;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
