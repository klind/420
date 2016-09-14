package com.mmj.data.core.dto.entity;

import java.io.Serializable;

/**
 *
 */
public class ConfigurationSummaryDTO implements Serializable {

    private Long idNextYear;
    private String category;
    private String identity;
    private String description;
    private String valueCurrentYear;
    private String valueNextYear;
    private String regex;

    public ConfigurationSummaryDTO() {
    }

    public ConfigurationSummaryDTO(Long idNextYear, String category, String identity, String description, String valueCurrentYear, String valueNextYear, String regex) {
        this.idNextYear = idNextYear;
        this.category = category;
        this.identity = identity;
        this.description = description;
        this.valueCurrentYear = valueCurrentYear;
        this.valueNextYear = valueNextYear;
        this.regex = regex;
    }

    public Long getIdNextYear() {
        return idNextYear;
    }

    public void setIdNextYear(Long idNextYear) {
        this.idNextYear = idNextYear;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getValueCurrentYear() {
        return valueCurrentYear;
    }

    public void setValueCurrentYear(String valueCurrentYear) {
        this.valueCurrentYear = valueCurrentYear;
    }

    public String getValueNextYear() {
        return valueNextYear;
    }

    public void setValueNextYear(String valueNextYear) {
        this.valueNextYear = valueNextYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
