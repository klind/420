package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.exception.SystemException;
import com.mmj.data.ejb.model2.QuestionRangesEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 */
public class QuestionRangesDao {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionRangesDao.class);

    @PersistenceContext()
    private EntityManager em;

    public List<QuestionRangesEN> getScoreRanges() {
        List<QuestionRangesEN> result = null;
        try {
            result = em.createQuery("SELECT c FROM QuestionRangesEN c").getResultList();
        } catch (Exception e) {
            LOG.error("Could not get score ranges", e);
            throw new SystemException("Could not get score ranges");
        }
        return result;
    }

    public QuestionRangesEN getQuestionRangeById(Long id) {
        QuestionRangesEN result = null;
        try {
            result = em.find(QuestionRangesEN.class, id);
            if (result == null) {
                throw new NotFoundException("QuestionRangesEN with id " + id + " could not be found");
            }
        } catch (Exception e) {
            LOG.error("Could not get QuestionRangesEN with id {}", id, e);
            throw new SystemException("Could not get QuestionRangesEN with id " + id);
        }
        return result;
    }
}
