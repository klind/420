package com.mmj.data.core.dto.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "question_ranges")
public class QuestionRangesDTO {

    private Long id;
    private String category;
    private int lower;
    private int upper;

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

    public int getLower() {
        return lower;
    }

    public void setLower(int lower) {
        this.lower = lower;
    }

    public int getUpper() {
        return upper;
    }

    public void setUpper(int upper) {
        this.upper = upper;
    }
}
