package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.SurveyDTO;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.ejb.dao.AnswerDao;
import com.mmj.data.ejb.dao.SurveyDao;
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
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@LocalBean
public class SurveySB {
    private static final Logger LOG = LoggerFactory.getLogger(SurveySB.class);

    @Inject
    private SurveyDao surveyDao;

    @Inject
    private Transformer transformer;

    @Inject
    private AnswerDao answerDao;

    public SurveyDTO createSurvey(SurveyDTO surveyDTO) {
        SurveyEN surveyEN = transformer.getSurveyEN(surveyDTO);
        surveyDao.createSurvey(surveyEN);
        return transformer.getSurveyDTO(surveyEN);
    }

    public SurveyDTO getSurveyById(Long id) throws NotFoundException {
        SurveyEN surveyEN = surveyDao.getSurveyById(id);
        return transformer.getSurveyDTO(surveyEN);
    }

    public List<SurveyDTO> getSurveys() {
        List<SurveyDTO> result = new ArrayList<>();
        List<SurveyEN> surveys = surveyDao.getSurveys();
        for (SurveyEN survey : surveys) {
            result.add(transformer.getSurveyDTO(survey));
        }
        return result;
    }

    public List<SurveyDTO> getMySurveys(Long userId) throws BusinessException {
        if (userId == null || userId < 1) {
            throw new BusinessException("userId missing or userId < 1");
        }
        List<SurveyDTO> result = new ArrayList<>();
        List<SurveyEN> surveys = surveyDao.getMySurveys(userId);
        for (SurveyEN survey : surveys) {
            SurveyDTO surveyDTO = transformer.getSurveyDTO(survey);
            surveyDTO.setAnswered(answerDao.answerExistForProfileAndSurvey(userId, survey.getId()));
            result.add(surveyDTO);
        }
        return result;
    }
}
