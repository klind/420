package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.SystemException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.ejb.model.SaCodeEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for SA Codes.
 */
public class SaCodeDao {
    private static final Logger LOG = LoggerFactory.getLogger(SaCodeDao.class);

    @PersistenceContext
    private EntityManager em;

    public List<SaCodeEN> getSaCodes()  {
        List<SaCodeEN> result = new ArrayList<SaCodeEN>();
        try {
            result = em.createQuery("SELECT s FROM SaCodeEN s ").getResultList();
        } catch (Exception e) {
            LOG.error("Could not get TravelerRelationshipTypes", e);
            throw new SystemException("Could not get SaCodes");
        }
        return result;
    }

    public boolean saCodeExists(long id) {
        Long count = (Long) em.createQuery("SELECT COUNT(a.id) FROM SaCodeEN a WHERE a.id = :id")
                .setParameter("id", id).getSingleResult();
        return count > 0;
    }

    /**
     * Get sa code by id.
     *
     * @param id SA Code id.
     * @return SaCodeEN
     */
    public SaCodeEN getSaCodeById(long id) throws NotFoundException {
        SaCodeEN result;
        try {
            result = em.find(SaCodeEN.class, id);
            if (result == null) {
                throw new NotFoundException("SA Code with id " + id + " does not exists");
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get SA Code with id {}", id, e);
            throw new SystemException("Could not get SA Code with id " + id);
        }
        return result;
    }

    /**
     * Get sa code by saCode.
     *
     * @param saCode
     * @return SaCodeEN
     */
    public SaCodeEN getSaCodeBySACode(String saCode)  {
        SaCodeEN result;
        try {
            result = em.createQuery("SELECT s FROM SaCodeEN s where saCode = :saCode", SaCodeEN.class)
                    .setParameter("saCode", saCode)
                    .getSingleResult();
        } catch (Exception e) {
            LOG.error("Could not get SA Code with sa code {}", saCode, e);
            throw new SystemException("Could not get SA Code with sa code " + saCode);
        }
        return result;
    }

}
