package com.mmj.data.ejb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question_range")
public class QuestionRangeEN {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", length = 25)
    @NotNull
    private String category;

    @NotNull
    private int lower;

    @NotNull
    private int upper;

    @Column(name = "score")
    @NotNull
    private BigDecimal score;

    @ManyToMany(mappedBy = "questionRanges")
    private List<QuestionEN> questions = new ArrayList<>();

    @OneToMany(mappedBy = "answerQuestionRange")
    private List<AnswerEN> answers = new ArrayList<>();

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

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public List<QuestionEN> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEN> questions) {
        this.questions = questions;
    }

    public List<AnswerEN> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerEN> answers) {
        this.answers = answers;
    }
}
