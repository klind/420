package com.mmj.data.ejb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class QuestionEN {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    @NotNull
    private String text;

    @ManyToMany(mappedBy = "questions")
    private List<SurveyEN> surveys = new ArrayList<>();

    @OneToMany(mappedBy = "question")
    private List<AnswerEN> answers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="question_question_ranges",
            joinColumns=@JoinColumn(name="question_id", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="question_range_id", referencedColumnName="ID"))
    private List<QuestionRangeEN> questionRanges = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<SurveyEN> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SurveyEN> surveys) {
        this.surveys = surveys;
    }

    public List<AnswerEN> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerEN> answers) {
        this.answers = answers;
    }

    public List<QuestionRangeEN> getQuestionRanges() {
        return questionRanges;
    }

    public void setQuestionRanges(List<QuestionRangeEN> questionRanges) {
        this.questionRanges = questionRanges;
    }
}
