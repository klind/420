package com.mmj.data.core.dto.entity;

public class AnswerDTO {

    private Long id;
    private String answer;
    private Long profileId;
    private Long surveyId;
    private Long questionId;
    private Long questionRangeId;
    /*private ProfileDTO profile;
    private SurveyDTO survey;
    private QuestionDTO question;*/

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

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getQuestionRangeId() {
        return questionRangeId;
    }

    public void setQuestionRangeId(Long questionRangeId) {
        this.questionRangeId = questionRangeId;
    }
}
