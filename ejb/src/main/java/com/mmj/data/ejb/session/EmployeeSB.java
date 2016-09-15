package com.mmj.data.ejb.session;

import com.mmj.data.client.dto.EmployeeMyIDTravelDTO;
import com.mmj.data.core.dto.entity.AccessConfigurationDTO;
import com.mmj.data.core.dto.entity.EmployeeAuditDTO;
import com.mmj.data.core.dto.entity.EmployeeBaseDTO;
import com.mmj.data.core.dto.entity.EmployeeFullDTO;
import com.mmj.data.core.dto.entity.EmployeePartialDTO;
import com.mmj.data.core.dto.entity.EmployeePatchDTO;
import com.mmj.data.core.dto.entity.PassengerDTO;
import com.mmj.data.core.dto.entity.SearchResultDTO;
import com.mmj.data.core.dto.entity.SystemConfigurationDTO;
import com.mmj.data.core.enums.ConfigurationCategoryEnum;
import com.mmj.data.core.enums.ConfigurationIdentityEnum;
import com.mmj.data.core.enums.EmployeeStatusEnum;
import com.mmj.data.core.enums.EmployeeTypeEnum;
import com.mmj.data.core.enums.SuspensionTypeEnum;
import com.mmj.data.core.enums.SystemConfigurationEnum;
import com.mmj.data.core.exception.AlreadyExistsException;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.extclient.dto.ReservationDto;
import com.mmj.data.core.flights.FlightsService;
import com.mmj.data.core.util.DateTimeUtil;
import com.mmj.data.ejb.cache.G4tcCache;
import com.mmj.data.ejb.dao.ConfigurationDao;
import com.mmj.data.ejb.dao.EmployeeDao;
import com.mmj.data.ejb.dao.SaCodeDao;
import com.mmj.data.ejb.dao.SuspensionDao;
import com.mmj.data.ejb.dao.SuspensionTypeDao;
import com.mmj.data.ejb.dao.TimerDao;
import com.mmj.data.ejb.dao.TravelerRelationshipDao;
import com.mmj.data.ejb.model.ConfigurationEN;
import com.mmj.data.ejb.model.EmployeeEN;
import com.mmj.data.ejb.model.SuspensionEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.mmj.data.core.constant.Constants.DATE_FORMATTER;
import static com.mmj.data.core.constant.Constants.DATE_REGEX;
import static com.mmj.data.core.constant.Constants.DOB_REGEX;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@LocalBean
public class EmployeeSB {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeSB.class);

    @Inject
    private ConfigurationDao configurationDao;

    @Inject
    private EmployeeAuditSB employeeAuditSB;

    @Inject
    private EmployeeDao employeeDao;

    @Inject
    private PassengerSB passengerSB;

    @Inject
    private SuspensionSB suspensionSB;

    @Inject
    private TravelerRelationshipDao travelerRelationshipDao;

    @Inject
    private SuspensionDao suspensionDao;

    @Inject
    private FlightsService flightsService;

    @Inject
    private SuspensionTypeDao suspensionTypeDao;

    @Inject
    private SaCodeDao saCodeDao;

    @Inject
    private TimerDao timerDao;

    @Inject
    private G4tcCache g4tcCache;

    /**
     * Create a new employee.
     *
     * @param employeePartialDTO employeeBaseDTO with all the details to add.
     * @return EmployeeBaseDTO           A copy of the employeeBaseDTO created.
     * @
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeePartialDTO createEmployee(EmployeePartialDTO employeePartialDTO) throws BusinessException {
        if (employeeDao.employeeExists(employeePartialDTO.getEmployeeId())) {
            throw new AlreadyExistsException("An employee with id " + employeePartialDTO.getEmployeeId() + " already exists");
        }
        validateEmployee(employeePartialDTO);
        EmployeeEN employeeEN = new EmployeeEN();
        setEmployeeData(employeePartialDTO, employeeEN);
        employeeEN.setGuestPassesAlloted(getGuestPasses(employeePartialDTO));
        employeeEN.setGuestPassesBooked(0);
        employeeEN.setVacationPassesAlloted(getVacationPasses(employeePartialDTO));
        employeeEN.setVacationPassesUsed(0);
        employeeEN.setSaCode(saCodeDao.getSaCodeBySACode(configurationDao.getConfigurationByCatIden(ConfigurationCategoryEnum.SACODE, ConfigurationIdentityEnum.DEFAULT_EMPLOYEE_SA_CODE).getValue()));
        // Add new hire suspension
        LocalDate lastHireDate = employeePartialDTO.getLastHireDate();
        SuspensionEN suspensionEN = createNewHireSuspension(lastHireDate);
        suspensionEN.setEmployee(employeeEN);
        employeeEN.getSuspensions().add(suspensionEN);
        employeeDao.saveNewEmployee(employeeEN);
        EmployeePartialDTO result = employeeEN.getEmployeePartialDTO();
        setManagerName(result);
        return result;
    }

    private void validateEmployee(EmployeePartialDTO employeePartialDTO) throws BusinessException {
        // javax.validation is done by resteasy. Here we do some business validation
        BusinessException businessException = new BusinessException();
        String managerId = employeePartialDTO.getManagerId();
        if (!employeeDao.employeeExists(managerId)) {
            businessException.addMessage("Employee ( manager id ) with id " + managerId + " could not be found");
        }
        // Validate java.time.LocalDate as the javax.validation does now support it at this time
        if (!employeePartialDTO.getDob().format(DATE_FORMATTER).matches(DOB_REGEX)) {
            businessException.addMessage("DOB ( " + employeePartialDTO.getDob() + " ) is not valid, must match regex " + DOB_REGEX);
        }
        if (!employeePartialDTO.getLastHireDate().format(DATE_FORMATTER).matches(DATE_REGEX)) {
            businessException.addMessage("Last hire date ( " + employeePartialDTO.getLastHireDate() + " ) is not valid, must match regex " + DATE_REGEX);
        }
        if (employeePartialDTO.getTermDate() != null && !employeePartialDTO.getTermDate().format(DATE_FORMATTER).matches(DATE_REGEX)) {
            businessException.addMessage("Term date ( " + employeePartialDTO.getTermDate() + " ) is not valid, must match regex " + DATE_REGEX);
        }
        if (!businessException.getMessages().isEmpty()) {
            throw businessException;
        }
    }

    /**
     * Get Employee by Employee Id.
     *
     * @param employeeId Id of the employee we want to get.
     * @return EmployeeFullDTO   Employee info in DTO form.
     * @
     */
    public EmployeeFullDTO getEmployeeById(String employeeId) throws NotFoundException {
        EmployeeEN employeeEN = employeeDao.getEmployeeById(employeeId);
        EmployeeFullDTO result = employeeEN.getEmployeeDTO();
        setManagerName(result);
        return result;
    }

    /**
     * Update an employee with the data in the specified EmployeePartialDTO.
     *
     * @param employeePartialDTO The DTO with updated values.
     * @return Updated employeeFullDTO. Good for testing if update was successful.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeePartialDTO updateEmployee(EmployeePartialDTO employeePartialDTO) throws BusinessException {
        if (!employeeDao.employeeExists(employeePartialDTO.getEmployeeId())) {
            throw new NotFoundException("Employee with id " + employeePartialDTO.getEmployeeId() + " does not exists");
        }
        validateEmployee(employeePartialDTO);
        EmployeeEN employeeEN = employeeDao.getEmployeeById(employeePartialDTO.getEmployeeId());
        LocalDate newLastHireDate = employeePartialDTO.getLastHireDate();
        LocalDate existingLastHireDate = DateTimeUtil.dateToLocalDate(employeeEN.getLastHireDate());
        Date termDate = employeeEN.getTermDate();
        if (!newLastHireDate.isEqual(existingLastHireDate)) {
            if (termDate == null) {
                suspensionDao.deleteActiveAndFutureSuspensionsByEmployeeIdAndType(employeePartialDTO.getEmployeeId(), SuspensionTypeEnum.NWH.toString());
            }
            employeeEN.setGuestPassesAlloted(getGuestPasses(employeePartialDTO));
            SuspensionEN suspensionEN = createNewHireSuspension(newLastHireDate);
            suspensionEN.setEmployee(employeeEN);
            employeeEN.getSuspensions().add(suspensionEN);
        } else {
            // Check that the employee have access to guest passes.
            if (!allowGuestPasses(EmployeeTypeEnum.getSingleByName(employeePartialDTO.getEmployeeType()), EmployeeStatusEnum.getSingleByName(employeePartialDTO.getStatus()))) {
                employeeEN.setGuestPassesAlloted(0);
                employeeEN.setGuestPassesBooked(0);
            }
        }
        // Check that the employee have access to vacation upgrades.
        if (!allowVacationUpgrades(EmployeeTypeEnum.getSingleByName(employeePartialDTO.getEmployeeType()), EmployeeStatusEnum.getSingleByName(employeePartialDTO.getStatus()))) {
            employeeEN.setVacationPassesAlloted(0);
            employeeEN.setVacationPassesUsed(0);
        }

        setEmployeeData(employeePartialDTO, employeeEN);
        EmployeePartialDTO result = employeeEN.getEmployeePartialDTO();
        setManagerName(result);
        return result;
    }

    /**
     *
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void resetPassAllotment() throws NotFoundException {
        ConfigurationEN configurationEN = configurationDao.getConfigurationByCatIden(ConfigurationCategoryEnum.PASS, ConfigurationIdentityEnum.DEFAULT_GUEST_PASS_ALLOTMENT);
        String numberOfGuestPasses = configurationEN.getValue();
        configurationEN = configurationDao.getConfigurationByCatIden(ConfigurationCategoryEnum.PASS, ConfigurationIdentityEnum.DEFAULT_VACATION_PASS_ALLOTMENT);
        String numberOfVacationPasses = configurationEN.getValue();
        List<AccessConfigurationDTO> accessConfigurations = g4tcCache.getAccessConfigurations();
        List<AccessConfigurationDTO> gp = accessConfigurations.stream().filter(a -> a.isGuestPass()).collect(Collectors.toList());
        List<AccessConfigurationDTO> va = accessConfigurations.stream().filter(a -> a.isVacationUpgrade()).collect(Collectors.toList());
        employeeDao.resetPassAllotment(Integer.parseInt(numberOfGuestPasses), Integer.parseInt(numberOfVacationPasses), gp, va);
    }

    /**
     * Search employees based on specified criteria.
     *
     * @param id               Employee id.
     * @param email            Employee email address.
     * @param firstName        Employee first name.
     * @param lastName         Employee last name.
     * @param offset           Pagination record offset.
     * @param pageSizeEmployee Size of pagination.
     * @return SearchResultDTO   List of employee DTOs that match the criteria.
     */
    public SearchResultDTO searchEmployees(String id, String email, String firstName, String lastName, Integer offset, int pageSizeEmployee) {
        SearchResultDTO searchResultDTO = new SearchResultDTO();
        List<EmployeeBaseDTO> result = new ArrayList<EmployeeBaseDTO>();
        Object[] o = employeeDao.searchEmployees(id, email, firstName, lastName, offset, pageSizeEmployee);
        List<EmployeeEN> employees = (List<EmployeeEN>) o[1];
        for (EmployeeEN employee : employees) {
            result.add(employee.getEmployeeBaseDTO());
        }
        searchResultDTO.setCount((Long) o[0]);
        searchResultDTO.setEmployeeBaseDTOs(result);
        return searchResultDTO;
    }

    /**
     * Set Employee Data.
     *
     * @param employeePartialDTO DTO of partial employee data.
     * @param employeeEN         Employee entity.
     * @ Any possible exception.
     */
    private void setEmployeeData(EmployeePartialDTO employeePartialDTO, EmployeeEN employeeEN) {
        employeeEN.setEmployeeId(employeePartialDTO.getEmployeeId());
        employeeEN.setSalutation(employeePartialDTO.getSalutation());
        employeeEN.setSuffix(employeePartialDTO.getSuffix());
        employeeEN.setFirstName(employeePartialDTO.getFirstName());
        employeeEN.setMiddleName(employeePartialDTO.getMiddleName());
        employeeEN.setLastName(employeePartialDTO.getLastName());
        employeeEN.setEmail(employeePartialDTO.getEmail());
        employeeEN.setTitle(employeePartialDTO.getTitle());
        employeeEN.setPhone(employeePartialDTO.getPhone());
        employeeEN.setLastHireDate(DateTimeUtil.localDateToDate(employeePartialDTO.getLastHireDate()));
        employeeEN.setTermDate(DateTimeUtil.localDateToDate(employeePartialDTO.getTermDate()));
        employeeEN.setDepartmentId(employeePartialDTO.getDepartmentId());
        employeeEN.setDepartmentName(employeePartialDTO.getDepartmentName());
        employeeEN.setDob(DateTimeUtil.localDateToDate(employeePartialDTO.getDob()));
        employeeEN.setEmployeeType(employeePartialDTO.getEmployeeType());
        employeeEN.setJobCode(employeePartialDTO.getJobCode());
        employeeEN.setLocation(employeePartialDTO.getLocation());
        employeeEN.setManagerId(employeePartialDTO.getManagerId());
        employeeEN.setStatus(employeePartialDTO.getStatus());
        String gender = employeePartialDTO.getGender();
        employeeEN.setGender(gender);
        switch (gender) {
            case "M":
                employeeEN.setSalutation("Mr.");
                break;
            case "F":
                employeeEN.setSalutation("Ms.");
                break;
            default:
                break;
        }
    }

    /**
     * Return the amount of guest passes a new employee will be alloted according to the date of hire.
     *
     * @param employeePartialDTO
     * @return
     * @throws NotFoundException
     */
    public int getGuestPasses(EmployeePartialDTO employeePartialDTO) throws NotFoundException {
        int result = 0;
        if (allowGuestPasses(EmployeeTypeEnum.getSingleByName(employeePartialDTO.getEmployeeType()), EmployeeStatusEnum.getSingleByName(employeePartialDTO.getStatus()))) {
            LocalDate lastHireDate = employeePartialDTO.getLastHireDate();
            if (DateTimeUtil.getLocalDateNowGMT().getYear() == lastHireDate.getYear()) {
                ConfigurationEN configuration = configurationDao.getConfigurationByCatIden(ConfigurationCategoryEnum.PASS, ConfigurationIdentityEnum.DEFAULT_GUEST_PASS_ALLOTMENT);
                int value = Integer.parseInt(configuration.getValue());
                int monthsLeft = 13 - lastHireDate.getMonthValue();
                result = (value / 12) * monthsLeft;
            }
        }
        return result;
    }

    /**
     * Return the amount of vacation passes a new employee will be alloted according to the date of hire.
     *
     * @return number if guest passes
     * @
     */
    public int getVacationPasses(EmployeePartialDTO employeePartialDTO) throws NotFoundException {
        int result = 0;
        if (allowVacationUpgrades(EmployeeTypeEnum.getSingleByName(employeePartialDTO.getEmployeeType()), EmployeeStatusEnum.getSingleByName(employeePartialDTO.getStatus()))) {
            ConfigurationEN configuration = configurationDao.getConfigurationByCatIden(ConfigurationCategoryEnum.PASS, ConfigurationIdentityEnum.DEFAULT_VACATION_PASS_ALLOTMENT);
            result = Integer.parseInt(configuration.getValue());
        }
        return result;
    }

    /**
     * Set the manager name.
     *
     * @param result Employee Partial DTO that we wish to update.
     * @throws NotFoundException Possible exception.
     */
    private void setManagerName(EmployeePartialDTO result) throws NotFoundException {
        result.setManagerName(employeeDao.getFirstAndLastName(result.getManagerId()));
    }

    /**
     * Patch employee.
     *
     * @param employeePatchDTO Employee Patch DTO that we'll use to update the employee record.
     * @ Possible exceptions.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void patchEmployee(EmployeePatchDTO employeePatchDTO, String userId) throws BusinessException {
        Timestamp nowGMT = DateTimeUtil.getNowGMT();
        String employeeId = employeePatchDTO.getEmployeeId();
        EmployeeEN employeeEN = employeeDao.getEmployeeById(employeeId);
        EmployeeAuditDTO employeeAuditDTO = new EmployeeAuditDTO();
        employeeAuditDTO.setEmployeeId(employeeEN.getEmployeeId());
        employeeAuditDTO.setPreviousGuestPassesAlloted(employeeEN.getGuestPassesAlloted());
        employeeAuditDTO.setPreviousVacationPassesAlloted(employeeEN.getVacationPassesAlloted());
        employeeAuditDTO.setPreviousSaCodeId(employeeEN.getSaCode().getSaCodeDTO().getId());
        employeeAuditDTO.setNewGuestPassesAlloted(employeePatchDTO.getGuestPassesAlloted());
        employeeAuditDTO.setNewVacationPassesAlloted(employeePatchDTO.getVacationPassesAlloted());
        employeeAuditDTO.setNewSaCodeId(employeePatchDTO.getSaCode().getId());
        employeeAuditDTO.setUserId(userId);
        employeeAuditDTO.setCreated(nowGMT);
        employeeDao.patchEmployee(employeePatchDTO);
        employeeAuditSB.createEmployeeAudit(employeeAuditDTO);
    }

    /**
     * Get employee details needed for MyIDTravel.
     *
     * @param empNumber EmployeeID
     * @return Employee Detail in json format.
     * @ Any possible exception.
     */
    public EmployeeMyIDTravelDTO getMyIDTravel(String empNumber) throws NotFoundException {
        EmployeeFullDTO employeeFullDTO = getEmployeeById(empNumber);

        // Build passengers in thin client dto.
        List<PassengerDTO> verifiedPassengers = passengerSB.getVerifiedPassengerByEmployeeId(empNumber);
        List<com.mmj.data.client.dto.PassengerDTO> clientVerifiedPassengers = new ArrayList<com.mmj.data.client.dto.PassengerDTO>();
        for (PassengerDTO passenger : verifiedPassengers) {
            clientVerifiedPassengers.add(new com.mmj.data.client.dto.PassengerDTO(
                    passenger.getId(),
                    passenger.getEmployeeId(),
                    passenger.getFirstName(),
                    passenger.getMiddleName(),
                    passenger.getLastName(),
                    passenger.getGender(),
                    passenger.getRelationship().getValue(),
                    passenger.getDob().toString(),
                    passenger.getPhone(),
                    passenger.getReAddress(),
                    passenger.getKnownTravelerNumber(),
                    passenger.isVerified()
            ));
        }

        EmployeeMyIDTravelDTO result = new EmployeeMyIDTravelDTO(
                employeeFullDTO.getEmployeeId(),
                employeeFullDTO.getSalutation(),
                employeeFullDTO.getFirstName(),
                employeeFullDTO.getMiddleName(),
                employeeFullDTO.getLastName(),
                employeeFullDTO.getEmail(),
                employeeFullDTO.getGender(),
                employeeFullDTO.getDob().toString(),
                employeeFullDTO.getLastHireDate().toString(),
                employeeFullDTO.getTermDate() != null ? employeeFullDTO.getTermDate().toString() : null,
                employeeFullDTO.getPhone(),
                employeeFullDTO.getTravelStatus(),
                employeeFullDTO.getGuestPassesAlloted(),
                employeeFullDTO.getGuestPassesBooked(),
                employeeFullDTO.getVacationPassesAlloted(),
                employeeFullDTO.getVacationPassesUsed(),
                employeeFullDTO.getSaCode().getSaCode(),
                clientVerifiedPassengers
        );

        return result;
    }

    /**
     * Returns a ReservationsResponseDto representing the guest pass bookings made for the specified employee
     *
     * @param employeeId
     * @return ReservationsResponseDto
     * @
     */
    public List<ReservationDto> getGuestPassBookingsByEmployeeId(String employeeId) throws BusinessException {
        int year = DateTimeUtil.getLocalDateNowGMT().getYear();
        LocalDate start = null;
        SystemConfigurationDTO systemConfiguration = g4tcCache.getSystemConfiguration(SystemConfigurationEnum.FIRST_SEARCH_DATE);
        LocalDate s = DateTimeUtil.stringToLocalDate(systemConfiguration.getValue());
        if (s != null) {
            start = s;
        } else {
            start = LocalDate.parse(year + "-01-01", DATE_FORMATTER);
        }
        return flightsService.getGuestPassBookingsByEmployeeId(employeeId, start, LocalDate.parse(year + "-12-31", DATE_FORMATTER), "BP");
    }

    private SuspensionEN createNewHireSuspension(LocalDate lastHireDate) throws NotFoundException {
        SuspensionEN suspensionEN = new SuspensionEN();
        suspensionEN.setCreated(DateTimeUtil.getNowGMT());
        suspensionEN.setUsername("System");
        suspensionEN.setUserid("System");
        suspensionEN.setBegin(DateTimeUtil.localDateToDate(lastHireDate));
        ConfigurationEN configurationEN = configurationDao.getConfigurationByCatIden(ConfigurationCategoryEnum.SUSPENSION, ConfigurationIdentityEnum.NEW_HIRE_SUSPENSION_LENGTH);
        suspensionEN.setEnd(DateTimeUtil.localDateToDate(lastHireDate.plusDays(Long.parseLong(configurationEN.getValue()))));
        suspensionEN.setSuspensionType(suspensionTypeDao.getSuspensionTypeByValue(SuspensionTypeEnum.NWH.toString()));
        return suspensionEN;
    }

    /**
     * Loads a new employee into the database. This endpoint is only supposed to be used byt pipeline to populate all employees
     *
     * @param employeePartialDTO employeeBaseDTO with all the details to add.
     * @return EmployeeBaseDTO           A copy of the employeeBaseDTO created.
     * @
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeePartialDTO loadEmployee(EmployeePartialDTO employeePartialDTO) throws BusinessException {
        if (employeeDao.employeeExists(employeePartialDTO.getEmployeeId())) {
            throw new AlreadyExistsException("An employee with id " + employeePartialDTO.getEmployeeId() + " already exists");
        }
        // javax.validation is done by resteasy. Here we do some business validation
        BusinessException businessException = new BusinessException();
        // Validate java.time.LocalDate as the javax.validation does now support it at this time
        if (!employeePartialDTO.getDob().format(DATE_FORMATTER).matches(DOB_REGEX)) {
            businessException.addMessage("DOB ( " + employeePartialDTO.getDob() + " ) is not valid, must match regex " + DOB_REGEX);
        }
        if (!employeePartialDTO.getLastHireDate().format(DATE_FORMATTER).matches(DATE_REGEX)) {
            businessException.addMessage("Last hire date ( " + employeePartialDTO.getLastHireDate() + " ) is not valid, must match regex " + DATE_REGEX);
        }
        if (employeePartialDTO.getTermDate() != null && !employeePartialDTO.getTermDate().format(DATE_FORMATTER).matches(DOB_REGEX)) {
            businessException.addMessage("Term date ( " + employeePartialDTO.getTermDate() + " ) is not valid, must match regex " + DOB_REGEX);
        }
        if (!businessException.getMessages().isEmpty()) {
            throw businessException;
        }
        EmployeeEN employeeEN = new EmployeeEN();
        setEmployeeData(employeePartialDTO, employeeEN);
        employeeEN.setGuestPassesAlloted(getGuestPasses(employeePartialDTO));
        employeeEN.setGuestPassesBooked(0);
        employeeEN.setVacationPassesAlloted(getVacationPasses(employeePartialDTO));
        employeeEN.setVacationPassesUsed(0);
        employeeEN.setSaCode(saCodeDao.getSaCodeBySACode(configurationDao.getConfigurationByCatIden(ConfigurationCategoryEnum.SACODE, ConfigurationIdentityEnum.DEFAULT_EMPLOYEE_SA_CODE).getValue()));
        employeeDao.saveNewEmployee(employeeEN);
        EmployeePartialDTO result = employeeEN.getEmployeePartialDTO();
        return result;
    }

    /**
     * Returns remaining number of guest passes for a given employee based on their employee id
     *
     * @param employeeId
     * @return Integer
     * @
     */
    public Integer getRemainingGuestPasses(String employeeId) throws NotFoundException {
        return employeeDao.getRemainingGuestPasses(employeeId);
    }

    /**
     * Increments the guest passes booked for a given employee based on their employee id
     *
     * @param employeeId
     * @
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void incrementGuestPassesBooked(String employeeId) {
        employeeDao.incrementGuestPassesBooked(employeeId);
    }

    /**
     * @param employeeId
     * @return
     * @throws NotFoundException
     */
    public Integer getRemainingVacationUpgrades(String employeeId) throws NotFoundException {
        return employeeDao.getRemainingVacationUpgrades(employeeId);
    }

    /**
     * Increments the vacation upgrades used for a given employee based on their employee id
     *
     * @param employeeId
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void incrementVacationUpgradesUsed(String employeeId) {
        employeeDao.incrementVacationUpgradesUsed(employeeId);
    }

    /**
     * @param employeeId
     * @return
     */
    public String[] getDOBFirstMiddleLast(String employeeId) throws NotFoundException {
        return employeeDao.getDOBFirstMiddleLast(employeeId);
    }

    /**
     * @param employeeId
     * @return
     * @throws BusinessException
     */
    public String getEmployeeSACode(String employeeId) throws BusinessException {
        return employeeDao.getEmployeeSACode(employeeId);
    }

    /**
     * @param employeeId
     * @return
     * @throws BusinessException
     */
    public String getEmployeeStatus(String employeeId) throws BusinessException {
        return employeeDao.getEmployeeStatus(employeeId);
    }

    /**
     * @param employeeId
     * @return
     */
    public boolean employeeExists(String employeeId) {
        return employeeDao.employeeExists(employeeId);
    }

    public String getEmployeeType(String employeeId) throws BusinessException {
        return employeeDao.getEmployeeType(employeeId);
    }

    /**
     * Returns true if the user with the specified employeeId can access EPT according to the employee type and status.
     *
     * @param employeeId
     * @return
     * @throws BusinessException
     */
    public boolean canAccessEPT(String employeeId) throws BusinessException {
        return check(employeeId, "EPT");
    }

    /**
     * Returns true if the user with the specified employeeId can access My ID travel according to the employee type and status.
     *
     * @param employeeId
     * @return
     * @throws BusinessException
     */
    public boolean canAccessMYIDTravel(String employeeId) throws BusinessException {
        return check(employeeId, "MYID");
    }

    public boolean allowGuestPasses(String employeeId) throws BusinessException {
        return check(employeeId, "GP");
    }

    public boolean allowGuestPasses(EmployeeTypeEnum employeeType, EmployeeStatusEnum employeeStatus) {
        return check(employeeType, employeeStatus, "GP");
    }

    public boolean allowVacationUpgrades(String employeeId) throws BusinessException {
        return check(employeeId, "VA");
    }

    public boolean allowVacationUpgrades(EmployeeTypeEnum employeeType, EmployeeStatusEnum employeeStatus) {
        return check(employeeType, employeeStatus, "VA");
    }

    private boolean check(String employeeId, String type) throws BusinessException {
        EmployeeTypeEnum employeeType = EmployeeTypeEnum.getSingleByName(employeeDao.getEmployeeType(employeeId));
        EmployeeStatusEnum employeeStatus = EmployeeStatusEnum.getSingleByName(employeeDao.getEmployeeStatus(employeeId));
        return check(employeeType, employeeStatus, type);
    }

    private boolean check(EmployeeTypeEnum employeeType, EmployeeStatusEnum employeeStatus, String type) {
        boolean result = false;
        List<AccessConfigurationDTO> accessConfigurations = g4tcCache.getAccessConfigurations();
        for (AccessConfigurationDTO accessConfiguration : accessConfigurations) {
            if (accessConfiguration.getEmployeeType() == employeeType && accessConfiguration.getEmployeeStatus() == employeeStatus) {
                switch (type) {
                    case "VA":
                        result = accessConfiguration.isVacationUpgrade();
                        break;
                    case "GP":
                        result = accessConfiguration.isGuestPass();
                        break;
                    case "EPT":
                        result = accessConfiguration.isAccessEPT();
                        break;
                    case "MYID":
                        result = accessConfiguration.isAccessMyIdTravel();
                        break;
                    default:
                        break;
                }
                break;
            }
        }
        return result;
    }
}
