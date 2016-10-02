package com.mmj.data.core.dto.entity;

import java.io.Serializable;

public class SurveyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private boolean answered;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
