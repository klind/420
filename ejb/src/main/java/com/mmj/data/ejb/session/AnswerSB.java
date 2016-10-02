package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.AnswerDTO;
import com.mmj.data.core.dto.entity.SurveyAnswerDTO;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.ejb.dao.AnswerDao;
import com.mmj.data.ejb.dao.ProfileDao;
import com.mmj.data.ejb.dao.QuestionDao;
import com.mmj.data.ejb.dao.SurveyDao;
import com.mmj.data.ejb.model.AnswerEN;
import com.mmj.data.ejb.model.QuestionEN;
import com.mmj.data.ejb.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.time.LocalTime;
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
    public void createAnswer(AnswerDTO answerDTO) throws BusinessException {

        if (answerDao.answerExistForProfileAndSurvey(answerDTO.getProfileId(), answerDTO.getSurveyId())) {
            throw new BusinessException("This survey was already answered");
        }

        AnswerEN answerEN = transformer.getAnswerEN(answerDTO);

        SurveyAnswerDTO surveyAnswerDTO = answerDTO.getSurveyAnswerDTO();
        Integer peakLast = surveyAnswerDTO.getPeakLast();
        answerEN.setAnswer(peakLast.toString());
        QuestionEN questionEN = questionDao.getQuestionByText("peakLast");
        answerEN.setQuestion(questionEN);
        answerDao.saveAnswer(answerEN);

        answerEN = transformer.getAnswerEN(answerDTO);
        surveyAnswerDTO = answerDTO.getSurveyAnswerDTO();
        LocalTime peakTime = surveyAnswerDTO.getPeakTime();
        answerEN.setAnswer(peakTime.toString());
        questionEN = questionDao.getQuestionByText("peakTime");
        answerEN.setQuestion(questionEN);
        answerDao.saveAnswer(answerEN);

        answerEN = transformer.getAnswerEN(answerDTO);
        surveyAnswerDTO = answerDTO.getSurveyAnswerDTO();
        Integer potency = surveyAnswerDTO.getPotency();
        answerEN.setAnswer(potency.toString());
        questionEN = questionDao.getQuestionByText("potency");
        answerEN.setQuestion(questionEN);
        answerDao.saveAnswer(answerEN);

        answerEN = transformer.getAnswerEN(answerDTO);
        surveyAnswerDTO = answerDTO.getSurveyAnswerDTO();
        Integer potencyStrength = surveyAnswerDTO.getPotencyStrength();
        answerEN.setAnswer(potencyStrength.toString());
        questionEN = questionDao.getQuestionByText("potencyStrength");
        answerEN.setQuestion(questionEN);
        answerDao.saveAnswer(answerEN);

        answerEN = transformer.getAnswerEN(answerDTO);
        surveyAnswerDTO = answerDTO.getSurveyAnswerDTO();
        LocalTime startTime = surveyAnswerDTO.getStartTime();
        answerEN.setAnswer(startTime.toString());
        questionEN = questionDao.getQuestionByText("startTime");
        answerEN.setQuestion(questionEN);
        answerDao.saveAnswer(answerEN);

        answerEN = transformer.getAnswerEN(answerDTO);
        surveyAnswerDTO = answerDTO.getSurveyAnswerDTO();
        LocalTime startTimeEffect = surveyAnswerDTO.getStartTimeEffect();
        answerEN.setAnswer(startTimeEffect.toString());
        questionEN = questionDao.getQuestionByText("startTimeEffect");
        answerEN.setQuestion(questionEN);
        answerDao.saveAnswer(answerEN);

        answerEN = transformer.getAnswerEN(answerDTO);
        surveyAnswerDTO = answerDTO.getSurveyAnswerDTO();
        LocalTime timeEffectEnd = surveyAnswerDTO.getTimeEffectEnd();
        answerEN.setAnswer(timeEffectEnd.toString());
        questionEN = questionDao.getQuestionByText("timeEffectEnd");
        answerEN.setQuestion(questionEN);
        answerDao.saveAnswer(answerEN);

        answerEN = transformer.getAnswerEN(answerDTO);
        surveyAnswerDTO = answerDTO.getSurveyAnswerDTO();
        boolean eat = surveyAnswerDTO.getEat();
        answerEN.setAnswer(eat ? "true" : "false");
        questionEN = questionDao.getQuestionByText("eat");
        answerEN.setQuestion(questionEN);
        answerDao.saveAnswer(answerEN);
    }
}
