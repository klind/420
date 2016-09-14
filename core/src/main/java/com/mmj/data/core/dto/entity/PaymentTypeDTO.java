package com.mmj.data.core.dto.entity;

import java.io.Serializable;

/**
 *
 */
public class PaymentTypeDTO implements Serializable {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String category;

    public PaymentTypeDTO() {
    }

    public PaymentTypeDTO(Long id, String code, String name, String description, String category) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
