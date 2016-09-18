package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.AnswerDTO;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.ejb.dao.AnswerDao;
import com.mmj.data.ejb.dao.ProfileDao;
import com.mmj.data.ejb.dao.QuestionDao;
import com.mmj.data.ejb.dao.SurveyDao;
import com.mmj.data.ejb.model.AnswerEN;
import com.mmj.data.ejb.model.ProfileEN;
import com.mmj.data.ejb.model.QuestionEN;
import com.mmj.data.ejb.model.SurveyEN;
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
public class AnswerSB {
    private static final Logger LOG = LoggerFactory.getLogger(AnswerSB.class);

    @Inject
    private AnswerDao answerDao;

    @Inject
    private SurveyDao surveyDao;


    @Inject
    private ProfileDao profileDao;

    @Inject
    private QuestionDao questionDao;



    @Inject
    private Transformer transformer;

    public List<AnswerDTO> getAnswersBySurveyId(Long surveyId) {
        List<AnswerDTO> result = new ArrayList<>();
        List<AnswerEN> answers = answerDao.getAnswersBySurveyId(surveyId);
        for (AnswerEN answer : answers) {
            result.add(transformer.getAnswerDTO(answer));
        }
        return result;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AnswerDTO createAnswer(AnswerDTO answerDTO) throws NotFoundException {
        AnswerEN answerEN = transformer.getAnswerEN(answerDTO);
        ProfileEN profileEN = profileDao.getProfileById(answerDTO.getProfileId());
        answerEN.setProfile(profileEN);
        SurveyEN surveyEN = surveyDao.getSurveyById(answerDTO.getSurveyId());
        answerEN.setSurvey(surveyEN);
        QuestionEN questionEN = questionDao.getQuestionById(answerDTO.getQuestionId());
        answerEN.setQuestion(questionEN);
        answerDao.createAnswer(answerEN);
        return transformer.getAnswerDTO(answerEN);
    }
}
