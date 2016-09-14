package com.mmj.data.core.dto.entity;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class SearchResultDTO implements Serializable {

    private Long count;
    private List<EmployeeBaseDTO> employeeBaseDTOs;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<EmployeeBaseDTO> getEmployeeBaseDTOs() {
        return employeeBaseDTOs;
    }

    public void setEmployeeBaseDTOs(List<EmployeeBaseDTO> employeeBaseDTOs) {
        this.employeeBaseDTOs = employeeBaseDTOs;
    }
}
