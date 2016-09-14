package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.SuspensionCommentDTO;
import com.mmj.data.core.dto.entity.SuspensionDTO;
import com.mmj.data.core.dto.entity.SuspensionSummaryDTO;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.util.DateTimeUtil;
import com.mmj.data.ejb.dao.EmployeeDao;
import com.mmj.data.ejb.dao.SuspensionCommentDao;
import com.mmj.data.ejb.dao.SuspensionDao;
import com.mmj.data.ejb.dao.SuspensionTypeDao;
import com.mmj.data.ejb.model.EmployeeEN;
import com.mmj.data.ejb.model.SuspensionCommentEN;
import com.mmj.data.ejb.model.SuspensionEN;
import com.mmj.data.ejb.model.SuspensionTypeEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Suspension Session Bean.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@LocalBean
public class SuspensionSB {
    /**
     * Logger class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SuspensionSB.class);

    @Inject
    private SuspensionDao suspensionDao;

    @Inject
    private SuspensionTypeDao suspensionTypeDao;

    @Inject
    private EmployeeDao employeeDao;

    @Inject
    private SuspensionCommentDao suspensionCommentDao;

    @Resource
    private SessionContext ctx;

    /**
     * Get suspension by id.
     *
     * @param suspensionId Id of the suspension we want to get.
     * @return Suspension as a DTO.
     * @
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SuspensionDTO getSuspensionById(Long suspensionId) throws NotFoundException {
        SuspensionEN suspensionEN = suspensionDao.getSuspensionById(suspensionId);
        return suspensionEN.getSuspensionDTO();
    }

    /**
     * Get all suspensions that an employee has.
     *
     * @param id Id of the employee
     * @return List of suspensions.
     * @
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SuspensionDTO> getSuspensionByEmployeeId(String id)  {
        List<SuspensionEN> suspensions = suspensionDao.getSuspensionsByEmployeeId(id);
        List<SuspensionDTO> suspensionDTOs = new ArrayList<SuspensionDTO>();
        for (SuspensionEN suspension : suspensions) {
            suspensionDTOs.add(suspension.getSuspensionDTO());
        }
        return suspensionDTOs;
    }

    /**
     * Get's the employee's suspension summary.
     *
     * @param id Id of the employee
     * @return Suspension Summary.
     * @
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SuspensionSummaryDTO getSuspensionSummaryByEmployeeId(String id) throws NotFoundException {
        // Get the list of suspensions, so can pass to the suspension summary constructor.
        List<SuspensionEN> suspensions = suspensionDao.getSuspensionsByEmployeeId(id);
        List<SuspensionDTO> suspensionDTOs = new ArrayList<SuspensionDTO>();
        for (SuspensionEN suspension : suspensions) {
            suspensionDTOs.add(suspension.getSuspensionDTO());
        }

        // Get the employee status.
        String employeeStatusLetter = employeeDao.getEmployeeById(id).getStatus();

        // Calculate/create suspension summary.
        SuspensionSummaryDTO result = new SuspensionSummaryDTO(suspensionDTOs, employeeStatusLetter);
        return result;
    }

    /**
     * Create a suspension.
     *
     * @param suspensionDTO Suspension DTO
     * @param userId
     * @param userName
     * @return Copy of the DTO that was created.
     * @
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SuspensionDTO createSuspension(SuspensionDTO suspensionDTO, String userId, String userName) throws NotFoundException {
        Timestamp nowGMT = DateTimeUtil.getNowGMT();
        Long suspensionTypeId = suspensionDTO.getSuspensionType().getId();
        SuspensionTypeEN suspensionType = suspensionTypeDao.getSuspensionTypeById(suspensionTypeId);
        String employeeId = suspensionDTO.getEmployeeId();
        EmployeeEN employeeEN = employeeDao.getEmployeeById(employeeId);

        SuspensionEN suspensionEN = new SuspensionEN();
        suspensionEN.setBegin(DateTimeUtil.localDateToDate(suspensionDTO.getBegin()));
        suspensionEN.setEnd(DateTimeUtil.localDateToDate(suspensionDTO.getEnd()));
        suspensionEN.setSuspensionType(suspensionType);
        suspensionEN.setEmployee(employeeEN);
        suspensionEN.setCreated(nowGMT);
        suspensionEN.setUserid(userId);
        suspensionEN.setUsername(userName);
        List<SuspensionCommentDTO> suspensionCommentDTOs = suspensionDTO.getComments();
        for (SuspensionCommentDTO suspensionCommentDTO : suspensionCommentDTOs) {
            SuspensionCommentEN suspensionCommentEN = new SuspensionCommentEN();
            suspensionCommentEN.setComment(suspensionCommentDTO.getComment());
            suspensionCommentEN.setCreated(nowGMT);
            suspensionCommentEN.setSuspension(suspensionEN);
            suspensionCommentEN.setUserid(userId);
            suspensionCommentEN.setUsername(userName);
            suspensionEN.addSuspensionComment(suspensionCommentEN);
        }
        suspensionDao.saveNewSuspension(suspensionEN);
        return suspensionEN.getSuspensionDTO();
    }

    /**
     * Update a suspension.
     *
     * @param suspensionDTO The suspension we are updating.
     * @param userId
     * @param userName
     * @return A copy of the updated suspension.
     * @
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SuspensionDTO updateSuspension(SuspensionDTO suspensionDTO, String userId, String userName) throws NotFoundException {
        Timestamp nowGMT = DateTimeUtil.getNowGMT();
        SuspensionEN suspensionEN = suspensionDao.getSuspensionById(suspensionDTO.getId());
        Long suspensionTypeId = suspensionDTO.getSuspensionType().getId();
        SuspensionTypeEN suspensionType = suspensionTypeDao.getSuspensionTypeById(suspensionTypeId);

        suspensionEN.setSuspensionType(suspensionType);
        suspensionEN.setBegin(DateTimeUtil.localDateToDate(suspensionDTO.getBegin()));
        suspensionEN.setEnd(DateTimeUtil.localDateToDate(suspensionDTO.getEnd()));

        List<SuspensionCommentEN> suspensionCommentENs = new ArrayList<>();
        List<SuspensionCommentDTO> suspensionCommentDTOs = suspensionDTO.getComments();
        for (SuspensionCommentDTO suspensionCommentDTO : suspensionCommentDTOs) {
            if (suspensionCommentDTO.getId() == null) {
                SuspensionCommentEN suspensionCommentEN = new SuspensionCommentEN();
                suspensionCommentEN.setComment(suspensionCommentDTO.getComment());
                suspensionCommentEN.setCreated(nowGMT);
                suspensionCommentEN.setSuspension(suspensionEN);
                suspensionCommentEN.setUserid(userId);
                suspensionCommentEN.setUsername(userName);
                suspensionCommentDao.saveNewSuspensionComment(suspensionCommentEN);
            }
        }
        return suspensionEN.getSuspensionDTO();
    }

    /**
     * Get all active suspensions that an employee has.
     *
     * @param id Id of the employee
     * @return List of suspensions.
     * @
     */
    public List<SuspensionDTO> getActiveSuspensions(String id)  {
        List<SuspensionEN> suspensions = suspensionDao.getActiveSuspensionsByEmployeeId(id);
        List<SuspensionDTO> suspensionDTOs = new ArrayList<SuspensionDTO>();
        for (SuspensionEN suspension : suspensions) {
            suspensionDTOs.add(suspension.getSuspensionDTOWithoutComments(id));
        }
        return suspensionDTOs;
    }
}
