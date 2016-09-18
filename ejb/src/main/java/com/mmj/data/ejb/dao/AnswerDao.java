package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.SystemException;
import com.mmj.data.ejb.model.AnswerEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 */
public class AnswerDao {
    private static final Logger LOG = LoggerFactory.getLogger(AnswerDao.class);

    @PersistenceContext()
    private EntityManager em;

    public void createAnswer(AnswerEN answerEN) {
        try {
            em.persist(answerEN);
        } catch (Exception e) {
            LOG.error("Could not save AnswerEN: {}", answerEN, e);
            throw new SystemException("Could not save AnswerEN: " + answerEN, e);
        }
    }

    public List<AnswerEN> getAnswersBySurveyId(Long surveyId) {
        List<AnswerEN> result = null;
        try {
            result = em.createQuery("SELECT a FROM AnswerEN a where a.survey.id = :surveyId").setParameter("surveyId", surveyId).getResultList();
        } catch (Exception e) {
            LOG.error("Could not get AnswerEN", e);
            throw new SystemException("Could not get AnswerEN", e);
        }
        return result;
    }
}
