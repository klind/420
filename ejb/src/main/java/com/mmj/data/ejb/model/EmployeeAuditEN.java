package com.mmj.data.ejb.model;

import com.mmj.data.core.dto.entity.EmployeeAuditDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Employee Audit Entity.
 */
@Entity
@Table(name = "employee_audit")
public class EmployeeAuditEN implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", length = 6)
    @NotNull
    private String employeeId;

    @Column(name = "prv_guest_passes_alloted")
    private Integer previousGuestPassesAlloted;

    @Column(name = "new_guest_passes_alloted")
    private Integer newGuestPassesAlloted;

    @Column(name = "prv_vacation_passes_alloted")
    private Integer previousVacationPassesAlloted;

    @Column(name = "new_vacation_passes_alloted")
    private Integer newVacationPassesAlloted;

    @Column(name = "prv_sa_code_id")
    private Long previousSaCodeId;

    @Column(name = "new_sa_code_id")
    private Long newSaCodeId;

    @Column(name = "userid", length = 6)
    @NotNull
    private String userid;

    @Column(name = "created", columnDefinition = "TimeStamp")
    @NotNull
    private Timestamp created;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    /**
     * Returns an instance of EmployeeAuditDTO.
     *
     * @return EmployeePartialDTO
     */
    public EmployeeAuditDTO getEmployeeAuditDTO() {
        return new EmployeeAuditDTO(this.getId(), this.getEmployeeId(), this.getPreviousGuestPassesAlloted(), this.getNewGuestPassesAlloted(), this.getPreviousVacationPassesAlloted(), this.getNewVacationPassesAlloted(), this.getPreviousSaCodeId(), this.getNewSaCodeId(), this.getUserid(), this.getCreated());
    }

}
