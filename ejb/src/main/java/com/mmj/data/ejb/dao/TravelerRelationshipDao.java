package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.exception.SystemException;
import com.mmj.data.ejb.model.TravelerRelationshipEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

/**
 *
 */
public class TravelerRelationshipDao {
    private static final Logger LOG = LoggerFactory.getLogger(TravelerRelationshipDao.class);

    @PersistenceContext
    private EntityManager em;

    /**
     * @param id
     */
    public TravelerRelationshipEN getTravellerRelationshipById(Long id) throws BusinessException {
        try {
            TravelerRelationshipEN result = em.find(TravelerRelationshipEN.class, id);
            if (result == null) {
                throw new NotFoundException("TravelerRelationship with id " + id + " could not be found");
            }
            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get TravelerRelationship with id {}", id, e);
            throw new SystemException("Could not get TravelerRelationship with id " + id);
        }
    }

    /**
     *
     * @return List<TravelerRelationshipEN>
     * @
     */
    public List<TravelerRelationshipEN> getRelationshipTypes()  {
        List<TravelerRelationshipEN> result = null;
        try {
            result = em.createQuery("SELECT c FROM TravelerRelationshipEN c").getResultList();
        } catch (Exception e) {
            LOG.error("Could not get TravelerRelationshipTypes", e);
            throw new SystemException("Could not get TravelerRelationshipTypes");
        }
        return result;
    }

    /**
     *
     * @return Long
     * @
     */
    public Long getRelationshipTypeIdByValue(String value)  {
        Long result = null;
        try {
            result = ((BigInteger) em.createNativeQuery("SELECT id FROM travelerrelationship WHERE value = :value")
                    .setParameter("value", value).getSingleResult()).longValue();
        } catch (Exception e) {
            LOG.error("Could not get TravelerRelationship type id", e);
            throw new SystemException("Could not get TravelerRelationship type id");
        }
        return result;
    }
}
