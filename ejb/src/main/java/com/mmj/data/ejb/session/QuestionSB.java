package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.QuestionDTO;
import com.mmj.data.ejb.dao.QuestionDao;
import com.mmj.data.ejb.model.QuestionEN;
import com.mmj.data.ejb.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@LocalBean
public class QuestionSB {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionSB.class);

    @Inject
    private QuestionDao questionDao;

    @Inject
    private Transformer transformer;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<QuestionDTO> getQuestions() {
        List<QuestionDTO> result = new ArrayList<>();
        List<QuestionEN> questions = questionDao.getQuestions();
        for (QuestionEN question : questions) {
            result.add(transformer.getQuestionDTO(question));
        }
        return result;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<QuestionDTO> getQuestionsBySurveyId(Long surveyId) {
        List<QuestionDTO> result = new ArrayList<>();
        List<QuestionEN> questions = questionDao.getQuestionsBySurveyId(surveyId);
        for (QuestionEN question : questions) {
            result.add(transformer.getQuestionDTO(question));
        }
        return result;

    }
}
