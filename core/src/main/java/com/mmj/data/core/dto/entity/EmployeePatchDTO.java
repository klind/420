package com.mmj.data.core.dto.entity;

/**
 * DTO used to help update existing employees with only infomration needed in the patch.
 */
public class EmployeePatchDTO {

    private String employeeId;
    private Integer guestPassesAlloted;
    private Integer vacationPassesAlloted;
    private SaCodeDTO saCode;

    public EmployeePatchDTO() { }

    public EmployeePatchDTO(String employeeId, Integer guestPassesAlloted, Integer vacationPassesAlloted, SaCodeDTO saCode) {
        this.employeeId = employeeId;
        this.guestPassesAlloted = guestPassesAlloted;
        this.vacationPassesAlloted = vacationPassesAlloted;
        this.saCode = saCode;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getGuestPassesAlloted() {
        return guestPassesAlloted;
    }

    public void setGuestPassesAlloted(Integer guestPassesAlloted) {
        this.guestPassesAlloted = guestPassesAlloted;
    }

    public Integer getVacationPassesAlloted() {
        return vacationPassesAlloted;
    }

    public void setVacationPassesAlloted(Integer vacationPassesAlloted) {
        this.vacationPassesAlloted = vacationPassesAlloted;
    }

    public SaCodeDTO getSaCode() {
        return saCode;
    }

    public void setSaCode(SaCodeDTO saCode) {
        this.saCode = saCode;
    }
}
