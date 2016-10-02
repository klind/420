package com.mmj.data.core.dto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AnswerDTO {

    private Long id;
    private LocalDate date;
    @NotNull
    private Long profileId;
    @NotNull
    private Long surveyId;
    private Long questionId;
    @JsonProperty(value = "surveyAnswer")
    private SurveyAnswerDTO surveyAnswerDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public SurveyAnswerDTO getSurveyAnswerDTO() {
        return surveyAnswerDTO;
    }

    public void setSurveyAnswerDTO(SurveyAnswerDTO surveyAnswerDTO) {
        this.surveyAnswerDTO = surveyAnswerDTO;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

