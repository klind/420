package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.exception.SystemException;
import com.mmj.data.ejb.model.QuestionRangeEN;
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
public class QuestionRangesDao {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionRangesDao.class);

    @PersistenceContext()
    private EntityManager em;

    public List<QuestionRangeEN> getQuestionRanges() {
        List<QuestionRangeEN> result = null;
        try {
            result = em.createQuery("SELECT c FROM QuestionRangeEN c").getResultList();
        } catch (Exception e) {
            LOG.error("Could not get question ranges", e);
            throw new SystemException("Could not get question ranges");
        }
        return result;
    }

    public QuestionRangeEN getQuestionRangeById(Long id) {
        QuestionRangeEN result = null;
        try {
            result = em.find(QuestionRangeEN.class, id);
            if (result == null) {
                throw new NotFoundException("QuestionRangeEN with id " + id + " could not be found");
            }
        } catch (Exception e) {
            LOG.error("Could not get QuestionRangeEN with id {}", id, e);
            throw new SystemException("Could not get QuestionRangeEN with id " + id);
        }
        return result;
    }

    public QuestionRangeEN getQuestionRangeByCatAndMark(String cat, Integer mark) throws NotFoundException {
        QuestionRangeEN result = null;
        try {
            result = em.createQuery("SELECT c FROM QuestionRangeEN c where c.category = :cat and c.mark = :mark", QuestionRangeEN.class)
                    .setParameter("cat", cat)
                    .setParameter("mark", mark)
                    .getSingleResult();
        } catch (NoResultException e) {
            String msg = "Could not find a QuestionRangeEN with cat " + cat + " and mark " + mark;
            LOG.error(msg);
            throw new NotFoundException(msg);
        } catch (NonUniqueResultException e) {
            String msg = "Did find more than one QuestionRangeEN with cat " + cat + " and mark " + mark;
            LOG.error(msg);
            throw new SystemException(msg);
        } catch (Exception e) {
            String msg = "Could not get a QuestionRangeEN with cat " + cat + " and mark " + mark;
            LOG.error(msg, e);
            throw new SystemException(msg);
        }
        return result;
    }
}
