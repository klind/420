package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.SystemException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.ejb.model.SuspensionCommentEN;
import com.mmj.data.ejb.model.SuspensionEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class SuspensionDao {
    private static final Logger LOG = LoggerFactory.getLogger(SuspensionDao.class);

    @PersistenceContext
    private EntityManager em;

    /**
     * Add a new suspension
     *
     * @param suspensionEN
     */
    public void saveNewSuspension(SuspensionEN suspensionEN)  {
        try {
            em.persist(suspensionEN);
        } catch (Exception e) {
            LOG.error("Could not save suspension: {}", suspensionEN, e);
            throw new SystemException("Could not save suspension: " + suspensionEN, e);
        }
    }

    /**
     * @param id
     * @return List<SuspensionEN>
     * @
     */
    public List<SuspensionEN> getSuspensionsByEmployeeId(String id)  {
        List<SuspensionEN> result = new ArrayList<SuspensionEN>();
        try {
            result = em.createQuery("SELECT s FROM SuspensionEN s WHERE s.employee.employeeId = :id")
                    .setParameter("id", id).getResultList();
        } catch (Exception e) {
            LOG.error("Could not get suspensions", e);
            throw new SystemException("Could not get suspensions");
        }
        return result;
    }

    /**
     * @param id
     * @return SuspensionEN
     * @
     */
    public SuspensionEN getSuspensionById(Long id) throws NotFoundException {
        SuspensionEN result = null;
        try {
            result = em.find(SuspensionEN.class, id);
            if (result == null) {
                throw new NotFoundException("Suspension with id " + id + " could not be found");
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get Suspension with id {}", id, e);
            throw new SystemException("Could not get Suspension with id " + id);
        }
        return result;
    }

    public void saveNewSuspensionComment(SuspensionCommentEN suspensionCommentEN)  {
        try {
            em.persist(suspensionCommentEN);
        } catch (Exception e) {
            LOG.error("Could not save suspension comment : {}", suspensionCommentEN, e);
            throw new SystemException("Could not save suspension comment : " + suspensionCommentEN, e);
        }
    }

    /**
     * @param id
     * @return List<SuspensionEN>
     * @
     */
    public List<SuspensionEN> getActiveSuspensionsByEmployeeId(String id)  {
        List<SuspensionEN> result = new ArrayList<SuspensionEN>();
        try {
            result = em.createQuery("SELECT s FROM SuspensionEN s WHERE s.employee.employeeId = :id AND s.begin <= :now AND (s.end IS NULL OR s.end >= :now)")
                    .setParameter("id", id)
                    .setParameter("now", new Date()).getResultList();
        } catch (Exception e) {
            LOG.error("Could not get suspensions", e);
            throw new SystemException("Could not get suspensions");
        }
        return result;
    }

    /**
     * @param id
     * @param suspensionType
     * @return List<SuspensionEN>
     * @
     */
    public List<SuspensionEN> getActiveSuspensionsByEmployeeIdAndType(String id, String suspensionType)  {
        List<SuspensionEN> result = new ArrayList<SuspensionEN>();
        try {
            result = em.createQuery("SELECT s FROM SuspensionEN s WHERE s.employee.employeeId = :id AND s.suspensionType.value = :suspensionType AND s.begin <= :now AND (s.end IS NULL OR s.end >= :now)")
                    .setParameter("id", id)
                    .setParameter("suspensionType", suspensionType)
                    .setParameter("now", new Date()).getResultList();
        } catch (Exception e) {
            LOG.error("Could not get suspensions", e);
            throw new SystemException("Could not get suspensions");
        }
        return result;
    }

    /**
     * @param id
     * @param suspensionType
     * @return List<SuspensionEN>
     * @
     */
    public List<SuspensionEN> getSuspensionsByEmployeeIdAndType(String id, String suspensionType)  {
        List<SuspensionEN> result = new ArrayList<SuspensionEN>();
        try {
            result = em.createQuery("SELECT s FROM SuspensionEN s WHERE s.employee.employeeId = :id AND s.suspensionType.value = :suspensionType")
                    .setParameter("id", id)
                    .setParameter("suspensionType", suspensionType).getResultList();
        } catch (Exception e) {
            LOG.error("Could not get suspensions", e);
            throw new SystemException("Could not get suspensions");
        }
        return result;
    }

    /**
     * @param id
     * @param suspensionType
     * @
     */
    public void deleteActiveAndFutureSuspensionsByEmployeeIdAndType(String id, String suspensionType)  {
        try {
            List ids = em.createQuery("SELECT s.id FROM SuspensionEN s WHERE s.employee.employeeId = :id " +
                    "AND s.suspensionType.value = :suspensionType " +
                    "AND s.username = :username " +
                    "AND (( s.begin <= :now AND (s.end IS NULL OR s.end >= :now)) OR s.begin >= :now )")
                    .setParameter("id", id)
                    .setParameter("suspensionType", suspensionType)
                    .setParameter("username", "System")
                    .setParameter("now", new Date()).getResultList();

            if (!ids.isEmpty()) {
                em.createQuery("DELETE FROM SuspensionEN WHERE id in :ids")
                        .setParameter("ids", ids).executeUpdate();
            }
        } catch (Exception e) {
            LOG.error("Could not delete suspensions", e);
            throw new SystemException("Could not delete suspensions");
        }
    }
}
