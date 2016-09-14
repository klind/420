package com.mmj.data.ejb.model;

import com.mmj.data.core.dto.entity.AccessConfigurationDTO;
import com.mmj.data.core.enums.EmployeeStatusEnum;
import com.mmj.data.core.enums.EmployeeTypeEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "access_configuration")
public class AccessConfigurationEN {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_type")
    @NotNull
    private String employeeType;

    @Column(name = "employee_status")
    @NotNull
    private String employeeStatus;

    @Column(name = "access_ept", columnDefinition = "BIT", length = 1)
    @NotNull
    private boolean accessEPT;

    @Column(name = "guest_pass", columnDefinition = "BIT", length = 1)
    @NotNull
    private boolean guestPass;

    @Column(name = "vacation_upgrade", columnDefinition = "BIT", length = 1)
    @NotNull
    private boolean vacationUpgrade;

    @Column(name = "access_myid_travel", columnDefinition = "BIT", length = 1)
    @NotNull
    private boolean accessMyIdTravel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public boolean isAccessEPT() {
        return accessEPT;
    }

    public void setAccessEPT(boolean accessEPT) {
        this.accessEPT = accessEPT;
    }

    public boolean isGuestPass() {
        return guestPass;
    }

    public void setGuestPass(boolean guestPass) {
        this.guestPass = guestPass;
    }

    public boolean isVacationUpgrade() {
        return vacationUpgrade;
    }

    public void setVacationUpgrade(boolean vacationUpgrade) {
        this.vacationUpgrade = vacationUpgrade;
    }

    public boolean isAccessMyIdTravel() {
        return accessMyIdTravel;
    }

    public void setAccessMyIdTravel(boolean accessMyIdTravel) {
        this.accessMyIdTravel = accessMyIdTravel;
    }

    /**
     * Returns an instance of AccessConfigurationDTO.
     *
     * @return AccessConfigurationDTO
     */
    public AccessConfigurationDTO getAccessConfigurationDTO() {
        return new AccessConfigurationDTO(this.getId(), EmployeeTypeEnum.getSingleByValue(getEmployeeType()), EmployeeStatusEnum.getSingleByValue(this.getEmployeeStatus()), this.isAccessEPT(), this.isGuestPass(), this.isVacationUpgrade(), this.isAccessMyIdTravel());
    }
}
