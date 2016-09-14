package com.mmj.data.ejb.model;

import com.mmj.data.core.dto.entity.SystemConfigurationDTO;
import com.mmj.data.core.util.DateTimeUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "system_configuration")
public class SystemConfigurationEN {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identity")
    @NotNull
    private String identity;

    @Column(name = "value")
    @NotNull
    private String value;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "start_date")
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "required", columnDefinition = "BIT", length = 1)
    @NotNull
    private Boolean required;

    @Column(name = "reg_ex")
    @NotNull
    private String regex;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public SystemConfigurationDTO getSystemConfigurationDTO() {
        return new SystemConfigurationDTO(getId(), getIdentity(), getValue(), getDescription(), DateTimeUtil.dateToLocalDate(getStartDate()), DateTimeUtil.dateToLocalDate(getEndDate()), getRequired(), getRegex());
    }
}
