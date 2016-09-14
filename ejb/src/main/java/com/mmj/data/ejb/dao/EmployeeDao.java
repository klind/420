package com.mmj.data.ejb.dao;

import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.SystemException;
import com.mmj.data.core.dto.entity.AccessConfigurationDTO;
import com.mmj.data.core.dto.entity.EmployeePatchDTO;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.ejb.model.EmployeeEN;
import com.mmj.data.ejb.model.EmployeeEN_;
import com.mmj.data.ejb.model.FailedPaymentRefundsEN;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import java.sql.Date;
import java.util.List;

import static com.mmj.data.core.constant.Constants.SIMPLE_DATE_FORMATTER;

/**
 *
 */
public class EmployeeDao {
    /**
     * Logger class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeDao.class);

    @PersistenceContext()
    private EntityManager em;

    @Inject
    private UserTransaction ut;

    /**
     * Add a new employee. (Way for pipeline to update our data)
     *
     * @param employeeEN
     */
    public void saveNewEmployee(EmployeeEN employeeEN) {
        try {
            em.persist(employeeEN);
        } catch (Exception e) {
            LOG.error("Could not save employee: {}", employeeEN, e);
            throw new SystemException("Could not save employee: " + employeeEN, e);
        }
    }

    /**
     * Get employee by employeeId.
     *
     * @param employeeId
     * @return EmployeeEN
     */
    public EmployeeEN getEmployeeById(String employeeId) throws NotFoundException {
        EmployeeEN result = null;
        try {
            result = em.find(EmployeeEN.class, employeeId);
            if (result == null) {
                throw new NotFoundException("Employee with id " + employeeId + " does not exists");
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get Employee with employeeId {}", employeeId, e);
            throw new SystemException("Could not get Employee with employeeId " + employeeId);
        }
        return result;
    }

    /**
     * @param numberOfGuestPasses
     * @param numberOfVacationPasses
     * @param gp
     * @param va
     */
    public void resetPassAllotment(Integer numberOfGuestPasses, Integer numberOfVacationPasses, List<AccessConfigurationDTO> gp, List<AccessConfigurationDTO> va) {
        Integer recordsUpdated = null;
        try {
            final StringBuilder gpSQLBuilder = new StringBuilder("UPDATE employee SET guest_passes_alloted = :numberOfGuestPasses, guest_passes_booked = 0 WHERE ");
            gp.forEach(a -> {
                gpSQLBuilder.append(" (employee_type = '" + a.getEmployeeType() + "' and status = '" + a.getEmployeeStatus() + "') ");
                gpSQLBuilder.append("OR");
            });
            String s = gpSQLBuilder.substring(0, gpSQLBuilder.length() - 2);
            recordsUpdated = em.createNativeQuery(s)
                    .setParameter("numberOfGuestPasses", numberOfGuestPasses)
                    .executeUpdate();
            LOG.debug("{} records had there guest pass allotment reset", recordsUpdated);

            final StringBuilder vaSQLBuilder = new StringBuilder("UPDATE employee SET vacation_passes_alloted = :numberOfVacationPasses, vacation_passes_used = 0 WHERE ");
            va.forEach(a -> {
                vaSQLBuilder.append(" (employee_type = '" + a.getEmployeeType() + "' and status = '" + a.getEmployeeStatus() + "') ");
                vaSQLBuilder.append("OR");
            });
            s = vaSQLBuilder.substring(0, vaSQLBuilder.length() - 2);
            recordsUpdated = em.createNativeQuery(s)
                    .setParameter("numberOfVacationPasses", numberOfVacationPasses)
                    .executeUpdate();
            LOG.debug("{} records had there vacation upgrade allotment reset", recordsUpdated);
        } catch (Exception e) {
            LOG.error("Could not update guest passes alloted", e);
            throw new SystemException("Could not update guest passes alloted");
        }
    }

    /**
     * Returns a search result.
     * Object[] that will contain the count in [0] and the search result in [1]
     *
     * @param id
     * @param email
     * @param firstName
     * @param lastName
     * @param offset
     * @param pageSizeEmployee
     * @return Object[]
     */
    public Object[] searchEmployees(String id, String email, String firstName, String lastName, Integer offset, Integer pageSizeEmployee) {
        Object[] result = new Object[2];
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<EmployeeEN> employees = null;
        try {
            CriteriaQuery<EmployeeEN> cq = cb.createQuery(EmployeeEN.class);
            Root<EmployeeEN> employee = cq.from(EmployeeEN.class);
            cq.select(employee);

            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<EmployeeEN> countEmployees = countQuery.from(EmployeeEN.class);
            countQuery.select(cb.count(countEmployees));

            Predicate predicateEmployeeId = cb.and(); // cb.and() default to true
            Predicate predicateEmail = cb.and();
            Predicate predicateFirstName = cb.and();
            Predicate predicateLastName = cb.and();

            Predicate cpredicateEmployeeId = cb.and();
            Predicate cpredicateEmail = cb.and();
            Predicate cpredicateFirstName = cb.and();
            Predicate cpredicateLastName = cb.and();

            if (StringUtils.isNotBlank(id)) {
                predicateEmployeeId = cb.and(cb.equal(employee.get(EmployeeEN_.employeeId), id));
                cpredicateEmployeeId = cb.and(cb.equal(countEmployees.get(EmployeeEN_.employeeId), id));
            }
            if (StringUtils.isNotBlank(email)) {
                predicateEmail = cb.and(cb.equal(employee.get(EmployeeEN_.email), email));
                cpredicateEmail = cb.and(cb.equal(countEmployees.get(EmployeeEN_.email), email));
            }
            if (StringUtils.isNotBlank(firstName)) {
                if (firstName.contains("*")) {
                    String f = firstName.replace("*", "%");
                    predicateFirstName = cb.and(cb.like(employee.get(EmployeeEN_.firstName), f));
                    cpredicateFirstName = cb.and(cb.like(countEmployees.get(EmployeeEN_.firstName), f));
                } else {
                    predicateFirstName = cb.and(cb.equal(employee.get(EmployeeEN_.firstName), firstName));
                    cpredicateFirstName = cb.and(cb.equal(countEmployees.get(EmployeeEN_.firstName), firstName));
                }
            }
            if (StringUtils.isNotBlank(lastName)) {
                if (lastName.contains("*")) {
                    String l = lastName.replace("*", "%");
                    predicateLastName = cb.and(cb.like(employee.get(EmployeeEN_.lastName), l));
                    cpredicateLastName = cb.and(cb.like(countEmployees.get(EmployeeEN_.lastName), l));
                } else {
                    predicateLastName = cb.and(cb.equal(employee.get(EmployeeEN_.lastName), lastName));
                    cpredicateLastName = cb.and(cb.equal(countEmployees.get(EmployeeEN_.lastName), lastName));
                }
            }

            countQuery.where(cpredicateEmployeeId, cpredicateEmail, cpredicateFirstName, cpredicateLastName);
            cq.where(predicateEmployeeId, predicateEmail, predicateFirstName, predicateLastName);

            cq.orderBy(cb.asc(employee.get(EmployeeEN_.employeeId)));

            TypedQuery<Long> typedQuery = em.createQuery(countQuery);
            typedQuery.setFirstResult(0);
            Long count = typedQuery.getSingleResult();

            TypedQuery<EmployeeEN> q = em.createQuery(cq).setFirstResult(offset).setMaxResults(pageSizeEmployee);
            employees = q.getResultList();

            result[0] = count;
            result[1] = employees;
        } catch (Exception e) {
            LOG.error("Could not search for employees", e);
            throw new SystemException("Could not search for employees");
        }
        return result;
    }

    /**
     * @param employeeId
     * @return true if the specified employeeId already exists, otherwise false.
     */
    public boolean employeeExists(String employeeId) {
        Long count = (Long) em.createQuery("SELECT COUNT(a.employeeId) FROM EmployeeEN a WHERE a.employeeId = :employeeId")
                .setParameter("employeeId", employeeId).getSingleResult();
        return count > 0;
    }

    /**
     * @param employeeId
     * @return Returns the full name of the specified employee id.
     */
    public String getFullName(String employeeId) throws NotFoundException {
        String result = null;
        if (StringUtils.isNotBlank(employeeId)) {
            Object[] data = null;
            try {
                data = (Object[]) em.createNativeQuery("SELECT first_name, middle_name, last_name FROM employee WHERE employee_id = :employeeId")
                        .setParameter("employeeId", employeeId).getSingleResult();
            } catch (NoResultException e) {
                throw new NotFoundException("Could not find employee with employee number: " + employeeId);
            }
            result = (String) data[0] + " " + (StringUtils.isNotBlank((String) data[1]) ? (String) data[1] + " " : "") + (String) data[2];
        }
        return result;
    }

    /**
     * @param employeeId
     * @return Returns the first and last name of the specified employee id.
     */
    public String getFirstAndLastName(String employeeId) throws NotFoundException {
        String result = null;
        if (StringUtils.isNotBlank(employeeId)) {
            Object[] data = null;
            try {
                data = (Object[]) em.createNativeQuery("SELECT first_name, last_name FROM employee WHERE employee_id = :employeeId")
                        .setParameter("employeeId", employeeId).getSingleResult();
            } catch (NoResultException e) {
                throw new NotFoundException("Could not find employee with employee number: " + employeeId);
            }
            result = (String) data[0] + " " + (String) data[1];
        }
        return result;
    }

    /**
     * @param employeeId
     * @return
     */
    public String[] getDOBFirstMiddleLast(String employeeId) throws NotFoundException {
        String[] result = new String[4];
        if (StringUtils.isNotBlank(employeeId)) {
            Object[] data = null;
            try {
                data = (Object[]) em.createNativeQuery("SELECT dob, first_name, middle_name, last_name FROM employee WHERE employee_id = :employeeId")
                        .setParameter("employeeId", employeeId).getSingleResult();
            } catch (NoResultException e) {
                throw new NotFoundException("Could not find employee with employee number: " + employeeId);
            }
            for (int i = 0; i < data.length; i++) {
                Object o = data[i];
                if (o instanceof Date) {
                    result[i] = SIMPLE_DATE_FORMATTER.format((Date) o);
                } else {
                    result[i] = (String) o;
                }
            }
        }
        return result;
    }

    /**
     * @param employeePatchDTO
     * @
     */
    public void patchEmployee(EmployeePatchDTO employeePatchDTO) {
        Query q = null;
        try {
            q = em.createNativeQuery("update employee set guest_passes_alloted = :guest_passes_alloted, vacation_passes_alloted = :vacation_passes_alloted, sa_code_id = :sa_code_id where employee_id = :employee_id");
            q.setParameter("guest_passes_alloted", employeePatchDTO.getGuestPassesAlloted());
            q.setParameter("vacation_passes_alloted", employeePatchDTO.getVacationPassesAlloted());
            q.setParameter("sa_code_id", employeePatchDTO.getSaCode().getId());
            q.setParameter("employee_id", employeePatchDTO.getEmployeeId());
            q.executeUpdate();
        } catch (Exception e) {
            LOG.error("Could not patch employee.", e);
            throw new SystemException("Could not patch employee");
        }
    }

    /**
     * @param employeeNumber
     * @return recordsUpdated
     * @
     */
    public Integer refundOneGuestPass(String employeeNumber) {
        Integer recordsUpdated = null;
        try {
            em.createNativeQuery(
                    "UPDATE employee SET guest_passes_booked = CASE WHEN guest_passes_booked = 0 THEN 0 ELSE (guest_passes_booked - 1) END WHERE employee_id = :employeeNumber")
                    .setParameter("employeeNumber", employeeNumber)
                    .executeUpdate();
        } catch (Exception e) {
            LOG.error("Could not update guest passes alloted", e);
            throw new SystemException("Could not update guest passes alloted");
        }
        return recordsUpdated;
    }

    /**
     * Returns the number of remaining guest passes.
     *
     * @param employeeId
     * @return Number of remaining guest passes.
     * @
     */
    public Integer getRemainingGuestPasses(String employeeId) throws NotFoundException {
        Integer guestPassCount = null;
        List<Number> data = null;
        try {
            data = em.createNativeQuery("SELECT (guest_passes_alloted - guest_passes_booked) FROM employee WHERE employee_id = :employeeId")
                    .setParameter("employeeId", employeeId)
                    .getResultList();
            if (data.isEmpty()) {
                throw new NotFoundException("Could not find employee with employee number: " + employeeId);
            }
            guestPassCount = data.get(0).intValue();
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not retrieve guest passes.", e);
            throw new SystemException("Could not retrieve guest passes");
        }
        return guestPassCount;
    }

    /**
     * Returns the number of remaining vacation upgrades.
     *
     * @param employeeId
     * @return Number of remaining vacation upgrades
     */
    public Integer getRemainingVacationUpgrades(String employeeId) throws NotFoundException {
        Integer vacationUpgrades = null;
        List<Number> data = null;
        try {
            data = em.createNativeQuery("SELECT (vacation_passes_alloted - vacation_passes_used) FROM employee WHERE employee_id = :employeeId")
                    .setParameter("employeeId", employeeId)
                    .getResultList();
            if (data.isEmpty()) {
                throw new NotFoundException("Could not find employee with employee number: " + employeeId);
            }
            vacationUpgrades = data.get(0).intValue();
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not retrieve vacation upgrades.", e);
            throw new SystemException("Could not retrieve vacation upgrades.");
        }
        return vacationUpgrades;
    }

    /**
     * @param employeeId
     * @return recordsUpdated
     */
    public void incrementGuestPassesBooked(String employeeId) {
        try {
            em.createNativeQuery("UPDATE employee SET guest_passes_booked = (guest_passes_booked + 1) WHERE employee_id = :employeeId")
                    .setParameter("employeeId", employeeId)
                    .executeUpdate();
        } catch (Exception e) {
            LOG.error("Could not update guest passes booked.", e);
            throw new SystemException("Could not update guest passes booked");
        }
    }

    /**
     * @param employeeId
     */
    public void incrementVacationUpgradesUsed(String employeeId) {
        try {
            em.createNativeQuery("UPDATE employee SET vacation_passes_used = (vacation_passes_used + 1) WHERE employee_id = :employeeId")
                    .setParameter("employeeId", employeeId)
                    .executeUpdate();
        } catch (Exception e) {
            LOG.error("Could not update vacation passes used.", e);
            throw new SystemException("Could not update vacation passes used");
        }
    }

    /**
     * @param employeeNumber
     * @
     */
    public void refundOneVacationUpgrade(String employeeNumber) {
        try {
            em.createNativeQuery(
                    "UPDATE employee SET vacation_passes_used = CASE WHEN vacation_passes_used = 0 THEN 0 ELSE (vacation_passes_used - 1) END WHERE employee_id = :employeeNumber")
                    .setParameter("employeeNumber", employeeNumber)
                    .executeUpdate();
        } catch (Exception e) {
            LOG.error("Could not update vacation passes when trying to return an unused one", e);
            throw new SystemException("Could not update vacation passes when trying to return an unused one");
        }
    }

    /**
     * @param employeeId
     */
    public String getEmployeeSACode(String employeeId) throws BusinessException {
        try {
            Object saCodeObject = em.createNativeQuery("SELECT sa_code FROM sa_code JOIN employee ON employee.sa_code_id = sa_code.id WHERE employee.employee_id = :employeeId")
                    .setParameter("employeeId", employeeId)
                    .getSingleResult();

            return (String) saCodeObject;
        } catch (NoResultException e) {
            throw new BusinessException("Could not retrieve SA code for employee " + employeeId, e);
        } catch (Exception e) {
            LOG.warn("Could not retrieve SA code for employee {}", employeeId, e);
            throw new SystemException("Could not retrieve SA code for employee " + employeeId, e);
        }
    }

    /**
     * @param failedPaymentRefundsEN
     */
    public void saveFailedPaymentRefunds(FailedPaymentRefundsEN failedPaymentRefundsEN) {
        try {
            em.persist(failedPaymentRefundsEN);
        } catch (Exception e) {
            LOG.error("Could not save failed payment refund: {}", failedPaymentRefundsEN, e);
            throw new SystemException("Could not save failed payment refund : " + failedPaymentRefundsEN, e);
        }
    }

    /**
     * @param employeeId
     * @return
     * @throws BusinessException
     */
    public String getEmployeeStatus(String employeeId) throws BusinessException {
        try {
            Object employeeType = em.createNativeQuery("SELECT status FROM employee WHERE employee_id = :employeeId")
                    .setParameter("employeeId", employeeId)
                    .getSingleResult();
            return (String) employeeType;
        } catch (NoResultException e) {
            throw new BusinessException("Could not retrieve employee status for employee " + employeeId, e);
        } catch (Exception e) {
            LOG.warn("Could not retrieve employee status for employee {}", employeeId, e);
            throw new SystemException("Could not retrieve employee status for employee " + employeeId, e);
        }
    }

    /**
     * @param employeeId
     * @return
     * @throws BusinessException
     */
    public String getEmployeeType(String employeeId) throws BusinessException {
        try {
            Object employeeType = em.createNativeQuery("SELECT employee_type FROM employee WHERE employee_id = :employeeId")
                    .setParameter("employeeId", employeeId)
                    .getSingleResult();
            return (String) employeeType;
        } catch (NoResultException e) {
            throw new BusinessException("Could not retrieve employee type for employee " + employeeId, e);
        } catch (Exception e) {
            LOG.warn("Could not retrieve employee type for employee {}", employeeId, e);
            throw new SystemException("Could not retrieve employee type for employee " + employeeId, e);
        }
    }
}
