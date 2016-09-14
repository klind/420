package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.SystemException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.ejb.model.PassengerEN;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 */
public class PassengerDao {
    private static final Logger LOG = LoggerFactory.getLogger(PassengerDao.class);

    @PersistenceContext
    private EntityManager em;

    /**
     * Add a new passenger
     *
     * @param passengerEN
     */
    public void saveNewPassenger(PassengerEN passengerEN)  {
        try {
            em.persist(passengerEN);
        } catch (Exception e) {
            LOG.error("Could not save passenger: {}", passengerEN, e);
            throw new SystemException("Could not save passenger: " + passengerEN, e);
        }
    }

    /**
     *
     * @param passengerEN
     * @return
     * @
     */
    public PassengerEN updatePassenger(PassengerEN passengerEN)  {
        try {
            em.merge(passengerEN);
            return passengerEN;
        } catch (Exception e) {
            LOG.error("Could not update PassengerWaitlistDto: {}", passengerEN, e);
            throw new SystemException("Could not update PassengerWaitlistDto: " + passengerEN, e);
        }
    }

    /**
     * @param id
     * @return List<PassengerEN>
     * @
     */
    public List<PassengerEN> getPassengersByEmployeeId(String id)  {
        List<PassengerEN> passengers = em.createQuery("SELECT p FROM PassengerEN p WHERE p.employee.employeeId = :id")
                .setParameter("id", id).getResultList();
        return passengers;
    }

    /**
     * @param id
     * @return PassengerEN
     * @
     */
    public PassengerEN getPassengerById(Long id) {
        PassengerEN result = null;
        try {
            result = em.find(PassengerEN.class, id);
            if (result == null) {
                throw new NotFoundException("PassengerWaitlistDto with id " + id + " could not be found");
            }
        } catch (Exception e) {
            LOG.error("Could not get PassengerWaitlistDto with id {}", id, e);
            throw new SystemException("Could not get PassengerWaitlistDto with id " + id);
        }
        return result;
    }

    /**
     * @param firstName
     * @param lastName
     * @return List<PassengerEN>
     * @
     */
    public List<PassengerEN> getPassengerByName(String firstName, String lastName)  {
        StringBuilder builder = new StringBuilder("SELECT p FROM PassengerEN p WHERE ");
        builder.append(StringUtils.isNoneBlank(firstName) ? "p.firstName = :firstName" : "");
        builder.append(StringUtils.isNoneBlank(firstName) && StringUtils.isNoneBlank(lastName) ? " AND " : "");
        builder.append(StringUtils.isNoneBlank(lastName) ? "p.lastName = :lastName" : "");
        String sql = builder.toString();
        Query query = em.createQuery(sql);
        if (StringUtils.isNoneBlank(firstName)) {
            query.setParameter("firstName", firstName);
        }
        if (StringUtils.isNoneBlank(lastName)) {
            query.setParameter("lastName", lastName);
        }
        List<PassengerEN> passengers = query.getResultList();
        return passengers;
    }

    /**
     * @param id
     * @
     */
    public void deletePassengerByPassengerId(Long id) throws NotFoundException {
        try {
            PassengerEN passengerEN = em.find(PassengerEN.class, id);
            if (passengerEN == null) {
                throw new NotFoundException("PassengerWaitlistDto with id " + id + " could not be deleted as it did not exists");
            }
            em.remove(passengerEN);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not delete passenger with id {}", id, e);
            throw new SystemException("Could not delete passenger with id " + id, e);
        }
    }

    /**
     * Returns the number of already existing relations of the specified relation type
     * @param employeeId
     * @param relationshipIds
     * @return Long
     */
    public Long getCountOfRelationShipType(String employeeId, List<Long> relationshipIds) {
        Long count = (Long) em.createQuery("SELECT COUNT(a.id) FROM PassengerEN a WHERE a.employeeId = :employeeId AND a.relationship.id IN :relationshipIds")
                .setParameter("employeeId", employeeId)
                .setParameter("relationshipIds", relationshipIds)
                .getSingleResult();
        return count;
    }

    /**
     * @param id
     * @return List<PassengerEN>
     * @
     */
    public List<PassengerEN> getVerifiedPassengersByEmployeeId(String id) {
        List<PassengerEN> passengers = em.createQuery("SELECT p FROM PassengerEN p WHERE p.employee.employeeId = :id AND p.verified = true")
                .setParameter("id", id).getResultList();
        return passengers;
    }

    /**
     *
     * @param employeeId
     * @return
     */
    public boolean employeeExists(String employeeId) {
        Long count = (Long) em.createQuery("select count(a.employeeId) from EmployeeEN a where a.employeeId = :employeeId")
                .setParameter("employeeId", employeeId).getSingleResult();
        return count > 0;
    }
}
