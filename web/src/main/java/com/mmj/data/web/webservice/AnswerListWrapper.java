package com.mmj.data.web.webservice;

import com.mmj.data.core.dto.entity.AnswerDTO;

import java.util.ArrayList;
import java.util.List;

public class AnswerListWrapper {

    List<AnswerDTO> answers = new ArrayList<>();

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }
}
