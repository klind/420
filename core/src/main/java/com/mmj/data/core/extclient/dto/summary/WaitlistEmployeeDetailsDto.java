package com.mmj.data.core.extclient.dto.summary;

import com.mmj.data.core.extclient.dto.G4FlightDto;

public class WaitlistEmployeeDetailsDto extends G4FlightDto {

    private static final long serialVersionUID = 1L;
    private Integer ranking;
    private String priorityCode;
    private String dateOfHire;
    private String employeeId;

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public String getPriorityCode() {
        return priorityCode;
    }

    public void setPriorityCode(String priorityCode) {
        this.priorityCode = priorityCode;
    }

    public String getDateOfHire() {
        return dateOfHire;
    }

    public void setDateOfHire(String dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
