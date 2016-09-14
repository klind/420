package com.mmj.data.core.dto.entity;

import java.io.Serializable;

/**
 *
 */
public class TravelTypesDTO implements Serializable {
    private Long id;
    private String value;
    private String description;

    public TravelTypesDTO() {
    }

    public TravelTypesDTO(Long id, String value, String description) {
        this.id = id;
        this.value = value;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
