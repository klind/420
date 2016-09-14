package com.mmj.data.ejb.model;

import com.mmj.data.core.dto.entity.TravelerRelationshipDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "travelerrelationship")
public class TravelerRelationshipEN {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    @NotNull
    private String value;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public TravelerRelationshipDTO getTravelerRelationshipDTO() {
        return new TravelerRelationshipDTO(getId(), getValue(), getDescription());
    }
}
