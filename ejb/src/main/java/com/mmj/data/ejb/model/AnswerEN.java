package com.mmj.data.ejb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
public class AnswerEN {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(length = 50)
    private String answer;

    @ManyToOne
    private ProfileEN profile;

    @ManyToOne
    private SurveyEN survey;

    @ManyToOne
    private QuestionEN question;

    @ManyToOne
    @JoinColumn(name= "questionRange_id")
    private QuestionRangeEN answerQuestionRange;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ProfileEN getProfile() {
        return profile;
    }

    public void setProfile(ProfileEN profile) {
        this.profile = profile;
    }

    public SurveyEN getSurvey() {
        return survey;
    }

    public void setSurvey(SurveyEN survey) {
        this.survey = survey;
    }

    public QuestionEN getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEN question) {
        this.question = question;
    }

    public QuestionRangeEN getAnswerQuestionRange() {
        return answerQuestionRange;
    }

    public void setAnswerQuestionRange(QuestionRangeEN answerQuestionRange) {
        this.answerQuestionRange = answerQuestionRange;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
