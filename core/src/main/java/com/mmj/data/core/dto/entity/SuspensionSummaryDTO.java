package com.mmj.data.core.dto.entity;

import com.mmj.data.core.enums.EmployeeStatusEnum;
import com.mmj.data.core.util.DateTimeUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SuspensionSummaryDTO implements Serializable {

    /**
     * true if can travel, false if cannot.
     */
    private boolean canTravelStatus;
    /**
     * Employee status as text. e.g. Active
     */
    private EmployeeStatusEnum employeeStatus;

    public SuspensionSummaryDTO(List<SuspensionDTO> suspensionDTOs, String employeeStatusLetter) {
        // Initial values
        canTravelStatus = true;

        // Calculagte if there are any active suspensions and if so, update travel status.
        if (suspensionDTOs != null) {
            for (SuspensionDTO suspension : suspensionDTOs) {
                Date begin = DateTimeUtil.localDateToDate(suspension.getBegin());
                Date end = DateTimeUtil.localDateToDate(suspension.getEnd());
                if (DateTimeUtil.isNowBetween(begin, end)) {
                    canTravelStatus = false;
                    break;
                }
            }
        }

        // Set Employee Status
        employeeStatus = EmployeeStatusEnum.getSingleByName(employeeStatusLetter);
    }

    public void setCanTravelStatus(boolean canTravelStatus) {
        this.canTravelStatus = canTravelStatus;
    }

    public boolean isCanTravelStatus() {
        return canTravelStatus;
    }

    public EmployeeStatusEnum getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatusEnum employeeStatus) {
        this.employeeStatus = employeeStatus;
    }
}
