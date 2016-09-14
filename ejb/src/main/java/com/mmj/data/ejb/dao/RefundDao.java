package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.SystemException;
import com.mmj.data.ejb.model.RefundEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

/**
 *
 */
public class RefundDao {
    private static final Logger LOG = LoggerFactory.getLogger(RefundDao.class);

    @PersistenceContext()
    private EntityManager em;

    /**
     *
     * @param employeeId
     * @param itn
     * @return
     */
    public List<RefundEN> getRefund(String employeeId, String itn) {
        List<RefundEN> guests = em.createQuery("SELECT p FROM RefundEN p WHERE p.employeeId = :employeeId and p.itn = :itn")
                .setParameter("employeeId", employeeId).setParameter("itn", itn).getResultList();
        return guests;
    }

    /**
     * Persist the specified guest
     *
     * @param refundEN
     * @
     */
    public void saveRefund(RefundEN refundEN)  {
        try {
            em.persist(refundEN);
        } catch (Exception e) {
            LOG.error("Could not save refund: {}", refundEN, e);
            throw new SystemException("Could not save refund: " + refundEN, e);
        }
    }

    /**
     *
     * @param employeeId
     * @param itinerary
     * @param paxNum
     * @return boolean
     */
    public boolean refundExists(String employeeId, String itinerary, String paxNum) {
        BigInteger count = (BigInteger) em.createNativeQuery("SELECT COUNT(*) FROM refund WHERE employee_id = :employeeId and itn = :itinerary and pax = :paxNum")
                .setParameter("employeeId", employeeId)
                .setParameter("itinerary", itinerary)
                .setParameter("paxNum", paxNum)
                .getSingleResult();
        return count.longValue() > 0;
    }

    /**
     *
     * @param employeeId
     * @param itinerary
     * @return boolean
     */
    public boolean paymentRefundExists(String employeeId, String itinerary) {
        BigInteger count = (BigInteger) em.createNativeQuery("SELECT COUNT(*) FROM refund WHERE employee_id = :employeeId and itn = :itinerary and type = :type")
                .setParameter("employeeId", employeeId)
                .setParameter("itinerary", itinerary)
                .setParameter("type", "Payment")
                .getSingleResult();
        return count.longValue() > 0;
    }
}
