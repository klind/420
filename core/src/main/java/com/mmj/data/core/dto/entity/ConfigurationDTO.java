package com.mmj.data.core.dto.entity;

import java.io.Serializable;

/**
 *
 */
public class ConfigurationDTO implements Serializable {
    private Long id;
    private String category;
    private String identity;
    private String value;
    private String description;
    private Integer year;
    private String regex;

    public ConfigurationDTO() {
    }

    public ConfigurationDTO(Long id, String category, String identity, String value, String description, Integer year, String regex) {
        this.id = id;
        this.category = category;
        this.identity = identity;
        this.value = value;
        this.description = description;
        this.year = year;
        this.regex = regex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
