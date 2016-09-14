package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.SystemException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.ejb.model.EmployeeAuditEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 */
public class EmployeeAuditDao {
    /**
     * Logger class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeAuditDao.class);

    @PersistenceContext()
    private EntityManager em;

    /**
     * Add a new employee audit.
     *
     * @param employeeAuditEN
     */
    public void saveNewEmployeeAudit(EmployeeAuditEN employeeAuditEN)  {
        try {
            em.persist(employeeAuditEN);
        } catch (Exception e) {
            LOG.error("Could not save employee audit: {}", employeeAuditEN, e);
            throw new SystemException("Could not save employee audit: " + employeeAuditEN, e);
        }
    }

    /**
     * Get employee audit by employeeAuditId.
     *
     * @param employeeAuditId
     * @return EmployeeAuditEN
     */
    public EmployeeAuditEN getEmployeeAuditById(Long employeeAuditId) throws NotFoundException {
        EmployeeAuditEN result = null;
        try {
            result = em.find(EmployeeAuditEN.class, employeeAuditId);
            if (result == null) {
                throw new NotFoundException("Employee Audit with id " + employeeAuditId + " does not exists");
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get Employee Audit with employeeId {}", employeeAuditId, e);
            throw new SystemException("Could not get Employee Audit with employeeAuditId " + employeeAuditId);
        }
        return result;
    }

}
