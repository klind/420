package com.mmj.data.core.dto.entity;

import java.time.LocalDate;

/**
 *
 */
public class SystemConfigurationDTO {

    private Long id;
    private String identity;
    private String value;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean required;
    private String regex;

    public SystemConfigurationDTO() {
    }

    public SystemConfigurationDTO(Long id, String identity, String value, String description, LocalDate startDate, LocalDate endDate, Boolean required, String regex) {
        this.id = id;
        this.identity = identity;
        this.value = value;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.required = required;
        this.regex = regex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
