package com.mmj.data.core.dto.entity;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

public class SurveyAnswerDTO {
    @NotNull
    private Long surveyId;
    @NotNull
    private String name;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime startTimeEffect;
    @NotNull
    private LocalTime peakTime;
    @NotNull
    private Integer peakLast;
    @NotNull
    private LocalTime timeEffectEnd;
    @NotNull
    private Integer potency;
    @NotNull
    private Integer potencyStrength;
    @NotNull
    private Boolean eat;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getStartTimeEffect() {
        return startTimeEffect;
    }

    public void setStartTimeEffect(LocalTime startTimeEffect) {
        this.startTimeEffect = startTimeEffect;
    }

    public LocalTime getPeakTime() {
        return peakTime;
    }

    public void setPeakTime(LocalTime peakTime) {
        this.peakTime = peakTime;
    }

    public Integer getPeakLast() {
        return peakLast;
    }

    public void setPeakLast(Integer peakLast) {
        this.peakLast = peakLast;
    }

    public LocalTime getTimeEffectEnd() {
        return timeEffectEnd;
    }

    public void setTimeEffectEnd(LocalTime timeEffectEnd) {
        this.timeEffectEnd = timeEffectEnd;
    }

    public Integer getPotency() {
        return potency;
    }

    public void setPotency(Integer potency) {
        this.potency = potency;
    }

    public Integer getPotencyStrength() {
        return potencyStrength;
    }

    public void setPotencyStrength(Integer potencyStrength) {
        this.potencyStrength = potencyStrength;
    }

    public boolean getEat() {
        return eat;
    }

    public void setEat(boolean eat) {
        this.eat = eat;
    }
}
