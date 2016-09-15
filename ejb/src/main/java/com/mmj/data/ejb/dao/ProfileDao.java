package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.exception.SystemException;
import com.mmj.data.ejb.model2.ProfileEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 */
public class ProfileDao {
    private static final Logger LOG = LoggerFactory.getLogger(ProfileDao.class);

    @PersistenceContext()
    private EntityManager em;

    public void saveNewProfile(ProfileEN profileEN) {
        try {
            em.persist(profileEN);
        } catch (Exception e) {
            LOG.error("Could not save profile: {}", profileEN, e);
            throw new SystemException("Could not save profile: " + profileEN, e);
        }
    }

    public ProfileEN getProfileById(Long id) throws NotFoundException {
        ProfileEN result = null;
        try {
            result = em.find(ProfileEN.class, id);
            if (result == null) {
                throw new NotFoundException("ProfileEN with id " + id + " does not exists");
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get ProfileEN with id {}", id, e);
            throw new SystemException("Could not get ProfileEN with id " + id);
        }
        return result;
    }
}
