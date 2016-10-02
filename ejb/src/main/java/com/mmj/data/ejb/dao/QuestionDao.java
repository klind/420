package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.exception.SystemException;
import com.mmj.data.ejb.model.QuestionEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 */
public class QuestionDao {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionDao.class);

    @PersistenceContext()
    private EntityManager em;

    public QuestionEN getQuestionById(Long id) throws NotFoundException {
        QuestionEN result = null;
        try {
            result = em.find(QuestionEN.class, id);
            if (result == null) {
                throw new NotFoundException("QuestionEN with id " + id + " does not exists");
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get QuestionEN with id {}", id, e);
            throw new SystemException("Could not get QuestionEN with id " + id);
        }
        return result;
    }

    public QuestionEN getQuestionByText(String text) throws NotFoundException {
        QuestionEN result = null;
        try {
            result = em.createQuery("SELECT c FROM QuestionEN c WHERE c.text = :text", QuestionEN.class)
                    .setParameter("text", text)
                    .getSingleResult();
        } catch (NoResultException e) {
            String msg = "QuestionEN with text " + text + " does not exists";
            LOG.warn(msg);
            throw new NotFoundException(msg);
        } catch (NonUniqueResultException e) {
            String msg = "Did find more than one QuestionEN with text " + text;
            LOG.error(msg);
            throw new SystemException(msg);
        }
        return result;
    }

    public List<QuestionEN> getQuestions() {
        List<QuestionEN> result = null;
        try {
            result = em.createQuery("SELECT c FROM QuestionEN c").getResultList();
        } catch (Exception e) {
            LOG.error("Could not get QuestionEN", e);
            throw new SystemException("Could not get QuestionEN");
        }
        return result;
    }

    public List<QuestionEN> getQuestionsBySurveyId(Long surveyId) {
        List<QuestionEN> result = null;
        try {
            result = em.createQuery("SELECT a FROM QuestionEN a join a.surveys survey where survey.id = :surveyId").setParameter("surveyId", surveyId).getResultList();
        } catch (Exception e) {
            LOG.error("Could not get QuestionEN", e);
            throw new SystemException("Could not get QuestionEN", e);
        }
        return result;
    }
}
