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
@Table(name = "survey")
public class SurveyEN {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "survey")
    private List<AnswerEN> answers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="survey_question",
            joinColumns=@JoinColumn(name="survey_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="question_id", referencedColumnName="id"))
    private List<QuestionEN> questions = new ArrayList<>();

    @ManyToMany(mappedBy="surveys")
    private List<ProfileEN> profiles = new ArrayList<>();

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

    public List<AnswerEN> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerEN> answers) {
        this.answers = answers;
    }

    public List<QuestionEN> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEN> questions) {
        this.questions = questions;
    }

    public List<ProfileEN> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfileEN> profiles) {
        this.profiles = profiles;
    }
}
