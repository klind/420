package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.SystemException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.ejb.model.SuspensionTypeEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SuspensionTypeDao {
    private static final Logger LOG = LoggerFactory.getLogger(SuspensionTypeDao.class);

    @PersistenceContext
    private EntityManager em;

    /**
     * @return
     * @
     */
    public List<SuspensionTypeEN> getSuspensionTypes()  {
        List<SuspensionTypeEN> result = new ArrayList<SuspensionTypeEN>();
        try {
            result = em.createQuery("SELECT s FROM SuspensionTypeEN s ").getResultList();
        } catch (Exception e) {
            LOG.error("Could not get TravelerRelationshipTypes", e);
            throw new SystemException("Could not get TravelerRelationshipTypes");
        }
        return result;
    }

    /**
     * @param id
     * @return SuspensionEN
     * @
     */
    public SuspensionTypeEN getSuspensionTypeById(Long id) throws NotFoundException {
        SuspensionTypeEN result = null;
        try {
            result = em.find(SuspensionTypeEN.class, id);
            if (result == null) {
                throw new NotFoundException("Suspension Type with id " + id + " could not be found");
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get Suspension Type with id {}", id, e);
            throw new SystemException("Could not get Suspension Type with id " + id);
        }
        return result;
    }

    /**
     *
     * @param value
     * @return SuspensionTypeEN
     * @
     */
    public SuspensionTypeEN getSuspensionTypeByValue(String value)  {
        SuspensionTypeEN suspensionTypeEN = null;
        try {
            suspensionTypeEN = em.createQuery("SELECT s FROM SuspensionTypeEN s WHERE s.value = :value", SuspensionTypeEN.class)
                    .setParameter("value", value)
                    .getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            LOG.error("Could not get Suspension Type with value {}", value, e);
            throw new SystemException("Could not get Suspension Type with value " + value);
        }
        return suspensionTypeEN;
    }
}
