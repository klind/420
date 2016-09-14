package com.mmj.data.core.dto.entity;

import com.mmj.data.core.enums.EmployeeStatusEnum;
import com.mmj.data.core.enums.EmployeeTypeEnum;
import com.mmj.data.core.util.ToJson;

import java.io.Serializable;

public class AccessConfigurationDTO implements Serializable {

    private Long id;
    private EmployeeTypeEnum employeeType;
    private EmployeeStatusEnum employeeStatus;
    private boolean accessEPT;
    private boolean guestPass;
    private boolean vacationUpgrade;
    private boolean accessMyIdTravel;

    public AccessConfigurationDTO(Long id, EmployeeTypeEnum employeeType, EmployeeStatusEnum employeeStatus, boolean accessEPT, boolean guestPass, boolean vacationUpgrade, boolean accessMyIdTravel) {
        this.id = id;
        this.employeeType = employeeType;
        this.employeeStatus = employeeStatus;
        this.accessEPT = accessEPT;
        this.guestPass = guestPass;
        this.vacationUpgrade = vacationUpgrade;
        this.accessMyIdTravel = accessMyIdTravel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeTypeEnum getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeTypeEnum employeeType) {
        this.employeeType = employeeType;
    }

    public EmployeeStatusEnum getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatusEnum employeeStatus) {
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

    @Override
    public String toString() {
        return ToJson.toJson(this);
    }
}
