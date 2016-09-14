package com.mmj.data.core.dto.entity;

import java.io.Serializable;

/**
 *
 */
public class SuspensionTypeDTO implements Serializable {
    private Long id;
    private String value;
    private String description;

    public SuspensionTypeDTO() {
    }

    public SuspensionTypeDTO(Long id, String value, String description) {
        this.id = id;
        this.value = value;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
