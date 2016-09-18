package com.mmj.data.core.dto.entity;

import java.util.ArrayList;
import java.util.List;

public class QuestionDTO {
    private Long id;
    private String text;
    //private List<SurveyDTO> surveys = new ArrayList<>();
    //private List<AnswerDTO> answers = new ArrayList<>();
    private List<QuestionRangeDTO> questionRanges = new ArrayList<>();

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

    /*public List<SurveyDTO> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SurveyDTO> surveys) {
        this.surveys = surveys;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }*/

    public List<QuestionRangeDTO> getQuestionRanges() {
        return questionRanges;
    }

    public void setQuestionRanges(List<QuestionRangeDTO> questionRanges) {
        this.questionRanges = questionRanges;
    }
}
