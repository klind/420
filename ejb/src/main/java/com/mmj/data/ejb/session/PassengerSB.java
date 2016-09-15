package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.PassengerDTO;
import com.mmj.data.core.enums.RelationshipTypeEnum;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.LimitReachException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.util.DateTimeUtil;
import com.mmj.data.ejb.dao.ConfigurationDao;
import com.mmj.data.ejb.dao.EmployeeDao;
import com.mmj.data.ejb.dao.PassengerDao;
import com.mmj.data.ejb.dao.TravelerRelationshipDao;
import com.mmj.data.ejb.model.ConfigurationEN;
import com.mmj.data.ejb.model.EmployeeEN;
import com.mmj.data.ejb.model.PassengerEN;
import com.mmj.data.ejb.model.TravelerRelationshipEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@LocalBean
public class PassengerSB {
    private static final Logger LOG = LoggerFactory.getLogger(PassengerSB.class);

    @Inject
    private PassengerDao passengerDao;

    @Inject
    private EmployeeDao employeeDao;

    @Inject
    private TravelerRelationshipDao travelerRelationshipDao;

    @Inject
    private ConfigurationDao configurationDao;

    /**
     * @param firstName
     * @param lastName
     * @return List<PassengerDTO>
     * @
     */
    public List<PassengerDTO> getPassengerByName(String firstName, String lastName) throws NotFoundException {
        List<PassengerEN> passengers = passengerDao.getPassengerByName(firstName, lastName);
        if (passengers.isEmpty()) {
            throw new NotFoundException("PassengerWaitlistDto with first name '" + firstName + "' and last name '" + lastName + "' not found");
        } else {
            List<PassengerDTO> passengerDTOs = new ArrayList<PassengerDTO>();
            for (PassengerEN passenger : passengers) {
                passengerDTOs.add(passenger.getPassengerDTO());
            }
            return passengerDTOs;
        }
    }

    /**
     * @param id
     * @return List<PassengerDTO>
     * @
     */
    public List<PassengerDTO> getPassengerByEmployeeId(String id)  {
        List<PassengerEN> passengers = passengerDao.getPassengersByEmployeeId(id);
        List<PassengerDTO> passengerDTOs = new ArrayList<PassengerDTO>();
        for (PassengerEN passenger : passengers) {
            passengerDTOs.add(passenger.getPassengerDTO());
        }
        Collections.sort(passengerDTOs, PassengerDTO.RELATIONSHIP_COMPARATOR);
        return passengerDTOs;
    }

    /**
     * @param id
     * @return List<PassengerDTO>
     * @
     */
    public List<PassengerDTO> getVerifiedPassengerByEmployeeId(String id)  {
        List<PassengerEN> passengers = passengerDao.getVerifiedPassengersByEmployeeId(id);
        List<PassengerDTO> passengerDTOs = new ArrayList<PassengerDTO>();
        for (PassengerEN passenger : passengers) {
            passengerDTOs.add(passenger.getPassengerDTO());
        }
        Collections.sort(passengerDTOs, PassengerDTO.RELATIONSHIP_COMPARATOR);
        return passengerDTOs;
    }


    /**
     * @param passengerDTO
     * @return PassengerDTO
     * @
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PassengerDTO createPassenger(PassengerDTO passengerDTO) throws BusinessException {
        // Validate the limits
        validateLimits(passengerDTO, false);
        PassengerEN passengerEN = new PassengerEN();
        passengerEN.setDob(DateTimeUtil.localDateToDate(passengerDTO.getDob()));
        String employeeId = passengerDTO.getEmployeeId();
        EmployeeEN employeeEN = employeeDao.getEmployeeById(employeeId);
        passengerEN.setEmployee(employeeEN);
        passengerEN.setFirstName(passengerDTO.getFirstName());
        passengerEN.setGender(passengerDTO.getGender());
        passengerEN.setKnownTravelerNumber(passengerDTO.getKnownTravelerNumber());
        passengerEN.setLastName(passengerDTO.getLastName());
        passengerEN.setMiddleName(passengerDTO.getMiddleName());
        passengerEN.setPhone(passengerDTO.getPhone());
        passengerEN.setReAddress(passengerDTO.getReAddress());
        Long travelerRelationshipId = passengerDTO.getRelationship().getId();
        TravelerRelationshipEN travelerRelationshipEN = travelerRelationshipDao.getTravellerRelationshipById(travelerRelationshipId);
        passengerEN.setRelationship(travelerRelationshipEN);
        passengerEN.setVerified(passengerDTO.isVerified());
        passengerDao.saveNewPassenger(passengerEN);
        return passengerEN.getPassengerDTO();
    }

    /*private void createValidateLimits(PassengerDTO passengerVO) throws LimitReachException, SystemException, BusinessException {
        TravelerRelationshipEN newTravellerRelationship = travelerRelationshipDao.getTravellerRelationshipById(passengerVO.getRelationship().getId());

        String value = newTravellerRelationship.getValue();
        List<Long> relationshipTypeIds = new ArrayList<>();

        RelationshipTypeEnum relationshiptype = null;

        List<RelationshipTypeEnum> groupByValue = RelationshipTypeEnum.getGroupByValue(value);
        if (groupByValue != null && groupByValue.size() > 0) {
            relationshiptype = groupByValue.get(0);
            for (RelationshipTypeEnum relationshipTypeEnum : groupByValue) {
                Long id = travelerRelationshipDao.getRelationshipTypeIdByValue(relationshipTypeEnum.toString());
                relationshipTypeIds.add(id);
            }
        } else {
            relationshiptype = RelationshipTypeEnum.getSingleByValue(value);
            relationshipTypeIds.add(newTravellerRelationship.getId());
        }

        ConfigurationEN configurationEN = configurationDao.getConfigurationByCatIden(relationshiptype.getCategory(), relationshiptype.getIdentity());
        Long count = passengerDao.getCountOfRelationShipType(passengerVO.getEmployeeId(), relationshipTypeIds);
        if (!(count < Integer.parseInt(configurationEN.getValue()))) {
            throw new LimitReachException("You have reached the limit of eligible travellers for this relationship type");
        }
    }*/

    /*private void updateValidateLimits(PassengerDTO passengerVO)  {
        // We need to consider the previous state.
        //      E.g. Data entry mistake where they need to update child to parent, etc.
        //      Tricker example is spouse to travel companion or vice versa.
        boolean isSameRelationshipType = false;
        PassengerEN previousPassengerRecord = passengerDao.getPassengerById(passengerVO.getId());
        TravelerRelationshipEN previousRelationship = travelerRelationshipDao.getTravellerRelationshipById(previousPassengerRecord.getPassengerVO().getRelationship().getId());
        TravelerRelationshipEN newTravellerRelationship = travelerRelationshipDao.getTravellerRelationshipById(passengerVO.getRelationship().getId());

        // Compare previous relationship and new relationship to determine if category changed, as this can affect our unlying calculation.
        RelationshipTypeEnum prevGroup = RelationshipTypeEnum.getSingleByValue(previousRelationship.getValue());
        RelationshipTypeEnum newGroup = RelationshipTypeEnum.getSingleByValue(newTravellerRelationship.getValue());
        if (prevGroup.getIdentity().equals(newGroup.getIdentity()))
            isSameRelationshipType = true;
        LOG.info("isSameRelationshipType: " + isSameRelationshipType);

        String value = newTravellerRelationship.getValue();
        List<Long> relationshipTypeIds = new ArrayList<>();
        RelationshipTypeEnum relationshiptype = null;
        List<RelationshipTypeEnum> groupByValue = RelationshipTypeEnum.getGroupByValue(value);

        if (groupByValue != null && groupByValue.size() > 0) {
            relationshiptype = groupByValue.get(0);
            for (RelationshipTypeEnum relationshipTypeEnum : groupByValue) {
                Long id = travelerRelationshipDao.getRelationshipTypeIdByValue(relationshipTypeEnum.toString());
                relationshipTypeIds.add(id);
            }
        } else {
            relationshiptype = RelationshipTypeEnum.getSingleByValue(value);
            relationshipTypeIds.add(newTravellerRelationship.getId());
        }

        ConfigurationEN configurationEN = configurationDao.getConfigurationByCatIden(relationshiptype.getCategory(), relationshiptype.getIdentity());
        long limit = Integer.parseInt(configurationEN.getValue());
        Long currRecCount = passengerDao.getCountOfRelationShipType(passengerVO.getEmployeeId(), relationshipTypeIds);
        long newRecCount;
        if (isSameRelationshipType) {
            newRecCount = currRecCount;
        } else {
            newRecCount = currRecCount + 1;
        }

        LOG.info("newRecCount: " + newRecCount);
        LOG.info("limit: " + limit);
        if (newRecCount > limit) {
            throw new LimitReachException("You have reached the limit of eligible travellers for this relationship type");
        }
    }*/

    /**
     * @param passengerId
     * @return PassengerDTO
     * @
     */
    public PassengerDTO getPassengerByPassengerId(Long passengerId)  {
        PassengerEN passengerEN = passengerDao.getPassengerById(passengerId);
        return passengerEN.getPassengerDTO();
    }

    /**
     * @param id
     * @return PassengerDTO
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deletePassengerByPassengerId(Long id) throws NotFoundException {
        passengerDao.deletePassengerByPassengerId(id);
    }

    /**
     * Update a passenger based on its id.
     *
     * @param passengerDTO The DTO with updated values.
     * @return Updated passengerDTO. Good for testing if update was successful.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PassengerDTO updatePassenger(PassengerDTO passengerDTO) throws BusinessException {
        validateLimits(passengerDTO, true);
        PassengerEN passengerEN = passengerDao.getPassengerById(passengerDTO.getId());
        passengerEN.setFirstName(passengerDTO.getFirstName());
        passengerEN.setMiddleName(passengerDTO.getMiddleName());
        passengerEN.setLastName(passengerDTO.getLastName());
        passengerEN.setGender(passengerDTO.getGender());
        passengerEN.setDob(DateTimeUtil.localDateToDate(passengerDTO.getDob()));
        passengerEN.setPhone(passengerDTO.getPhone());
        passengerEN.setVerified(passengerDTO.isVerified());
        passengerEN.setReAddress(passengerDTO.getReAddress());
        passengerEN.setKnownTravelerNumber(passengerDTO.getKnownTravelerNumber());
        passengerEN.setRelationship(travelerRelationshipDao.getTravellerRelationshipById(passengerDTO.getRelationship().getId()));
        passengerEN = passengerDao.updatePassenger(passengerEN);
        return passengerEN.getPassengerDTO();
    }

    /**
     * Valdiates if a new eligible traveller can be created with the specified relationship type, or
     * a eligible traveller can be updated to the specified relationship type.
     *
     * Limits on relationship types is specifed in the configuration table.
     *
     * @param passengerDTO
     * @param update true if this is an update, false otherwise
     * @throws LimitReachException
     * @
     */
    private void validateLimits(PassengerDTO passengerDTO, boolean update) throws BusinessException {
        TravelerRelationshipEN newRelationship = travelerRelationshipDao.getTravellerRelationshipById(passengerDTO.getRelationship().getId());
        String newValue = newRelationship.getValue();
        if (!update) {
            LOG.debug("Validating create eligible traveller with relationship {}", newValue);
        }
        List<RelationshipTypeEnum> groupByValue = RelationshipTypeEnum.getGroupByValue(newValue);
        if (update) {
            PassengerEN passengerEN = passengerDao.getPassengerById(passengerDTO.getId());
            TravelerRelationshipEN currentRelationship = passengerEN.getRelationship();
            LOG.debug("Validating update eligible traveller relationship type from {} to {}", currentRelationship.getValue(), newValue);
            if (newRelationship.getId().equals(currentRelationship.getId())) {
                // OK we are not updating the relationship type
                return;
            }
            RelationshipTypeEnum newRelationshipTypeEnum = RelationshipTypeEnum.getSingleByValue(newValue);
            RelationshipTypeEnum currentRelationshipTypeEnum = RelationshipTypeEnum.getSingleByValue(currentRelationship.getValue());
            if (groupByValue != null && groupByValue.size() > 0 && groupByValue.contains(newRelationshipTypeEnum) && groupByValue.contains(currentRelationshipTypeEnum)) {
                // OK we are in the same group
                return;
            }
        }
        List<Long> relationshipTypeIds = new ArrayList<>();
        RelationshipTypeEnum relationshiptype = null;
        if (groupByValue != null && groupByValue.size() > 0) {
            relationshiptype = groupByValue.get(0);
            for (RelationshipTypeEnum relationshipTypeEnum : groupByValue) {
                Long id = travelerRelationshipDao.getRelationshipTypeIdByValue(relationshipTypeEnum.toString());
                relationshipTypeIds.add(id);
            }
        } else {
            relationshiptype = RelationshipTypeEnum.getSingleByValue(newValue);
            relationshipTypeIds.add(newRelationship.getId());
        }
        ConfigurationEN configurationEN = configurationDao.getConfigurationByCatIden(relationshiptype.getCategory(), relationshiptype.getIdentity());
        Long count = passengerDao.getCountOfRelationShipType(passengerDTO.getEmployeeId(), relationshipTypeIds);
        if (!(count < Integer.parseInt(configurationEN.getValue()))) {
            throw new LimitReachException("You have reached the limit of eligible travellers you can add with this relation ship type");
        }
    }
}
