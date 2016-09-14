package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.SystemException;
import com.mmj.data.ejb.model.GuestEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 */
public class GuestDao {
    /**
     * Logger class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(GuestDao.class);

    @PersistenceContext()
    private EntityManager em;

    /**
     * Returns a list of GuestEN
     *
     * @param id
     * @return List<GuestEN>
     */
    public List<GuestEN> getGuestsByEmployeeId(String id) {
        List<GuestEN> guests = em.createQuery("SELECT p FROM GuestEN p WHERE p.employee.employeeId = :id")
                .setParameter("id", id).getResultList();
        return guests;
    }

    /**
     * Return the number of guests the specified employee has.
     *
     * @param employeeId
     * @return long
     */
    public long getNumberOfGuests(String employeeId) {
        return ((Number) em.createNativeQuery("select count(id) from guest where employee_id = :employeeId").setParameter("employeeId", employeeId).getSingleResult()).longValue();
    }

    /**
     * Deletes the oldest guest for the specified employee
     * @param employeeId
     */
    public void deleteOldestGuest(String employeeId) {
        em.createNativeQuery("delete from guest where id = (select x.id from (select min(id) as id from guest where employee_id = :employeeId) as x);")
                .setParameter("employeeId", employeeId)
                .executeUpdate();
    }

    /**
     * Persist the specified guest
     *
     * @param guestEN
     * @
     */
    public void saveNewGuest(GuestEN guestEN)  {
        try {
            em.persist(guestEN);
        } catch (Exception e) {
            LOG.error("Could not save guest: {}", guestEN, e);
            throw new SystemException("Could not save guest: " + guestEN, e);
        }
    }
}
