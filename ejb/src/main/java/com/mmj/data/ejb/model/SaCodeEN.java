package com.mmj.data.ejb.model;

import com.mmj.data.core.dto.entity.SaCodeDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sa_code")
public class SaCodeEN {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sa_code")
    @NotNull
    private String saCode;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "active", columnDefinition = "BIT", length = 1)
    @NotNull
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSaCode() {
        return saCode;
    }

    public void setSaCode(String saCode) {
        this.saCode = saCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public SaCodeDTO getSaCodeDTO() {
        return new SaCodeDTO(getId(), getSaCode(), getDescription(), isActive());
    }
}
