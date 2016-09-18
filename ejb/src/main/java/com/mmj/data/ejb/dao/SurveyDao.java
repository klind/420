package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.exception.SystemException;
import com.mmj.data.ejb.model.SurveyEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 */
public class SurveyDao {
    private static final Logger LOG = LoggerFactory.getLogger(SurveyDao.class);

    @PersistenceContext()
    private EntityManager em;

    public void createSurvey(SurveyEN surveyEN) {
        try {
            em.persist(surveyEN);
        } catch (Exception e) {
            LOG.error("Could not save surveysurveyEN: {}", surveyEN, e);
            throw new SystemException("Could not save survey: " + surveyEN, e);
        }
    }

    public SurveyEN getSurveyById(Long id) throws NotFoundException {
        SurveyEN result = null;
        try {
            result = em.find(SurveyEN.class, id);
            if (result == null) {
                throw new NotFoundException("SurveyEN with id " + id + " does not exists");
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get SurveyEN with id {}", id, e);
            throw new SystemException("Could not get SurveyEN with id " + id);
        }
        return result;
    }

    public List<SurveyEN> getSurveys() {
        List<SurveyEN> result = null;
        try {
            result = em.createQuery("SELECT c FROM SurveyEN c").getResultList();
        } catch (Exception e) {
            LOG.error("Could not get SurveyEN", e);
            throw new SystemException("Could not get SurveyEN");
        }
        return result;
    }
}
