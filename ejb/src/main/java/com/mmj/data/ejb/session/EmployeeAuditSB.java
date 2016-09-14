package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.EmployeeAuditDTO;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.ejb.dao.EmployeeAuditDao;
import com.mmj.data.ejb.model.EmployeeAuditEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@LocalBean
public class EmployeeAuditSB {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeAuditSB.class);

    @Inject
    private EmployeeAuditDao employeeAuditDao;

    @Inject
    private SaCodeSB saCodeSB;

    /**
     * Create a new employee audit.
     *
     * @param employeeAuditDTO employeeAuditDTO with all the details to add.
     * @return EmployeeAuditDTO  A copy of the employeeAuditDTO created.
     * @
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeeAuditDTO createEmployeeAudit(EmployeeAuditDTO employeeAuditDTO) {
        EmployeeAuditEN employeeAuditEN = new EmployeeAuditEN();
        employeeAuditEN.setEmployeeId(employeeAuditDTO.getEmployeeId());
        employeeAuditEN.setPreviousGuestPassesAlloted(employeeAuditDTO.getPreviousGuestPassesAlloted());
        employeeAuditEN.setPreviousVacationPassesAlloted(employeeAuditDTO.getPreviousVacationPassesAlloted());
        employeeAuditEN.setPreviousSaCodeId(employeeAuditDTO.getPreviousSaCodeId());
        employeeAuditEN.setNewGuestPassesAlloted(employeeAuditDTO.getNewGuestPassesAlloted());
        employeeAuditEN.setNewVacationPassesAlloted(employeeAuditDTO.getNewVacationPassesAlloted());
        employeeAuditEN.setNewSaCodeId(employeeAuditDTO.getNewSaCodeId());
        employeeAuditEN.setUserid(employeeAuditDTO.getUserId());
        employeeAuditEN.setCreated(employeeAuditDTO.getCreated());
        employeeAuditDao.saveNewEmployeeAudit(employeeAuditEN);
        EmployeeAuditDTO result = employeeAuditEN.getEmployeeAuditDTO();
        return result;
    }

    /**
     * Get Employee Audit by Employee Audit Id.
     *
     * @param employeeAuditId Id of the employee audit we want to get.
     * @return EmployeeAuditDTO      Employee Audit in DTO form.
     * @
     */
    public EmployeeAuditDTO getEmployeeAuditById(Long employeeAuditId) throws NotFoundException {
        EmployeeAuditEN employeeAuditEN = employeeAuditDao.getEmployeeAuditById(employeeAuditId);
        EmployeeAuditDTO result = employeeAuditEN.getEmployeeAuditDTO();
        return result;
    }
}
