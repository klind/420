package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.exception.SystemException;
import com.mmj.data.ejb.model.SuspensionCommentEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 */
public class SuspensionCommentDao {
    private static final Logger LOG = LoggerFactory.getLogger(SuspensionCommentDao.class);

    @PersistenceContext
    private EntityManager em;

    /**
     * @param id
     * @return
     * @throws NotFoundException
     * @
     */
    public SuspensionCommentEN getSuspensionCommentById(Long id) throws NotFoundException {
        SuspensionCommentEN result = null;
        try {
            result = em.find(SuspensionCommentEN.class, id);
            if (result == null) {
                throw new NotFoundException("SuspensionComment with id " + id + " could not be found");
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get SuspensionComment with id {}", id, e);
            throw new SystemException("Could not get SuspensionComment with id " + id);
        }
        return result;
    }

    /**
     * @param identity
     * @return SuspensionCommentEN
     * @throws NotFoundException
     * @
     */
    public SuspensionCommentEN getSuspensionCommentIdentity(String identity) throws NotFoundException {
        SuspensionCommentEN result = null;
        try {
            List<SuspensionCommentEN> resultList = em.createQuery("SELECT c FROM SuspensionCommentEN c WHERE c.identity = :identity", SuspensionCommentEN.class)
                    .setParameter("identity", identity)
                    .getResultList();
            if (resultList.isEmpty()) {
                throw new NotFoundException("SuspensionComment with identity " + identity + " could not be found");
            }
            result = resultList.get(0);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get SuspensionComment with identity {}", identity, e);
            throw new SystemException("Could not get SuspensionComment with identity " + identity);
        }
        return result;
    }

    /**
     * @return
     * @
     */
    public List<SuspensionCommentEN> getSuspensionComments()  {
        List<SuspensionCommentEN> result = null;
        try {
            result = em.createQuery("SELECT c FROM SuspensionCommentEN c").getResultList();
        } catch (Exception e) {
            LOG.error("Could not get SuspensionComments", e);
            throw new SystemException("Could not get SuspensionComments");
        }
        return result;
    }

    /**
     *
     * @param suspensionCommentEN
     * @return
     * @
     */
    public SuspensionCommentEN updateSuspensionComment(SuspensionCommentEN suspensionCommentEN)  {
        try {
            em.merge(suspensionCommentEN);
            return suspensionCommentEN;
        } catch (Exception e) {
            LOG.error("Could not update SuspensionComment: {}", suspensionCommentEN, e);
            throw new SystemException("Could not update SuspensionComment: " + suspensionCommentEN, e);
        }
    }

    /**
     *
     * @param suspensionCommentEN
     * @
     */
    public void saveNewSuspensionComment(SuspensionCommentEN suspensionCommentEN)  {
        try {
            em.persist(suspensionCommentEN);
        } catch (Exception e) {
            LOG.error("Could not save SuspensionComment: {}", suspensionCommentEN, e);
            throw new SystemException("Could not save SuspensionComment: " + suspensionCommentEN, e);
        }
    }

}
