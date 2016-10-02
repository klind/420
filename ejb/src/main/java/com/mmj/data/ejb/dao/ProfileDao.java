package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.exception.SystemException;
import com.mmj.data.ejb.model.ProfileEN;
import com.mmj.data.ejb.model.ProfilePasswordEN;
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

    public boolean profileExistsByEmail(String email) {
        Long count = (Long) em.createQuery("SELECT COUNT(p.id) FROM ProfileEN p WHERE p.email = :email")
                .setParameter("email", email).getSingleResult();
        return count > 0;
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

    public ProfileEN getProfileByEmail(String email) throws NotFoundException {
        ProfileEN result = null;
        try {
            result = em.createQuery("SELECT p FROM ProfileEN p WHERE p.email = :email", ProfileEN.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            String msg = "Could not find a profile with email " + email;
            LOG.warn(msg);
            throw new NotFoundException(msg);
        } catch (NonUniqueResultException e) {
            String msg = "Did find more than one profile with email " + email;
            LOG.error(msg);
            throw new SystemException(msg);
        }
        return result;
    }

    public List<ProfileEN> getProfiles() {
        List<ProfileEN> result = null;
        try {
            result = em.createQuery("SELECT c FROM ProfileEN c").getResultList();
        } catch (Exception e) {
            LOG.error("Could not get ProfileEN", e);
            throw new SystemException("Could not get ProfileEN");
        }
        return result;
    }

    public void saveNewProfilePassword(ProfilePasswordEN profilePasswordEN) {
        try {
            em.persist(profilePasswordEN);
        } catch (Exception e) {
            LOG.error("Could not save profilePasswordEN: {}", profilePasswordEN, e);
            throw new SystemException("Could not save profilePasswordEN: " + profilePasswordEN, e);
        }
    }

    public String getProfilePassword(String email) throws NotFoundException {
        String hashedPassword = null;
        try {
            hashedPassword = em.createQuery("SELECT p.password FROM ProfilePasswordEN p WHERE p.email = :email", String.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            String msg = "Could not find a ProfilePasswordEN with email " + email;
            LOG.error(msg);
            throw new NotFoundException(msg);
        } catch (NonUniqueResultException e) {
            String msg = "Did find more than one ProfilePasswordEN with email " + email;
            LOG.error(msg);
            throw new SystemException(msg);
        }
        return hashedPassword;
    }

    public ProfilePasswordEN getProfilePasswordeByEmail(String email) throws NotFoundException {
        ProfilePasswordEN result = null;
        try {
            result = em.createQuery("SELECT p FROM ProfilePasswordEN p WHERE p.email = :email", ProfilePasswordEN.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            String msg = "Could not find a ProfilePasswordEN with email " + email;
            LOG.error(msg);
            throw new NotFoundException(msg);
        } catch (NonUniqueResultException e) {
            String msg = "Did find more than one ProfilePasswordEN with email " + email;
            LOG.error(msg);
            throw new SystemException(msg);
        }
        return result;
    }
}
