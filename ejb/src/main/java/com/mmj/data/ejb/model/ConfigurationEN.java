package com.mmj.data.ejb.model;

import com.mmj.data.core.dto.entity.ConfigurationDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "configuration")
public class ConfigurationEN {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    @NotNull
    private String category;

    @Column(name = "identity")
    @NotNull
    private String identity;

    @Column(name = "value")
    @NotNull
    private String value;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "year")
    @NotNull
    private Integer year;

    @Column(name = "reg_ex")
    @NotNull
    private String regex;

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

    public ConfigurationDTO getConfigurationDTO() {
        return new ConfigurationDTO(getId(), getCategory(), getIdentity(), getValue(), getDescription(), getYear(), getRegex());
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
