package com.mmj.data.core.dto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 *
 */
public class EmployeeAuditDTO {

    private Long id;
    private String employeeId;
    private Integer previousGuestPassesAlloted;
    private Integer newGuestPassesAlloted;
    private Integer previousVacationPassesAlloted;
    private Integer newVacationPassesAlloted;
    private Long previousSaCodeId;
    private Long newSaCodeId;
    private String userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Timestamp created;

    public EmployeeAuditDTO() {

    }

    public EmployeeAuditDTO(final Long id, final String employeeId, final Integer previousGuestPassesAlloted, final Integer newGuestPassesAlloted, final Integer previousVacationPassesAlloted, final Integer newVacationPassesAlloted, final Long previousSaCodeId, final Long newSaCodeId, final String userId, final Timestamp created) {
        this.id = id;
        this.employeeId = employeeId;
        this.previousGuestPassesAlloted = previousGuestPassesAlloted;
        this.newGuestPassesAlloted = newGuestPassesAlloted;
        this.previousVacationPassesAlloted = previousVacationPassesAlloted;
        this.newVacationPassesAlloted = newVacationPassesAlloted;
        this.previousSaCodeId = previousSaCodeId;
        this.newSaCodeId = newSaCodeId;
        this.userId = userId;
        this.created = created;
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

    public Integer getPreviousGuestPassesAlloted() {
        return previousGuestPassesAlloted;
    }

    public void setPreviousGuestPassesAlloted(Integer previousGuestPassesAlloted) {
        this.previousGuestPassesAlloted = previousGuestPassesAlloted;
    }

    public Integer getNewGuestPassesAlloted() {
        return newGuestPassesAlloted;
    }

    public void setNewGuestPassesAlloted(Integer newGuestPassesAlloted) {
        this.newGuestPassesAlloted = newGuestPassesAlloted;
    }

    public Integer getPreviousVacationPassesAlloted() {
        return previousVacationPassesAlloted;
    }

    public void setPreviousVacationPassesAlloted(Integer previousVacationPassesAlloted) {
        this.previousVacationPassesAlloted = previousVacationPassesAlloted;
    }

    public Integer getNewVacationPassesAlloted() {
        return newVacationPassesAlloted;
    }

    public void setNewVacationPassesAlloted(Integer newVacationPassesAlloted) {
        this.newVacationPassesAlloted = newVacationPassesAlloted;
    }

    public Long getPreviousSaCodeId() {
        return previousSaCodeId;
    }

    public void setPreviousSaCodeId(Long previousSaCodeId) {
        this.previousSaCodeId = previousSaCodeId;
    }

    public Long getNewSaCodeId() {
        return newSaCodeId;
    }

    public void setNewSaCodeId(Long newSaCodeId) {
        this.newSaCodeId = newSaCodeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
