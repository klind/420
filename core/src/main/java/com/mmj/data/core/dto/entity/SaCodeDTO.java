package com.mmj.data.core.dto.entity;

import java.io.Serializable;

/**
 *
 */
public class SaCodeDTO implements Serializable {
    private Long id;
    private String saCode;
    private String description;
    private boolean active;

    public SaCodeDTO() {
    }

    public SaCodeDTO(Long id, String saCode, String description, boolean active) {
        this.id = id;
        this.saCode = saCode;
        this.description = description;
        this.active = active;
    }

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
}
