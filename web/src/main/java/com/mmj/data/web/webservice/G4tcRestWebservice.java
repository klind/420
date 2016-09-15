package com.mmj.data.web.webservice;

import com.mmj.data.client.dto.EmployeeMyIDTravelDTO;
import com.mmj.data.core.dto.entity.AccessConfigurationDTO;
import com.mmj.data.core.dto.entity.ConfigurationDTO;
import com.mmj.data.core.dto.entity.ConfigurationSummaryDTO;
import com.mmj.data.core.dto.entity.EmployeeFullDTO;
import com.mmj.data.core.dto.entity.EmployeePartialDTO;
import com.mmj.data.core.dto.entity.EmployeePatchDTO;
import com.mmj.data.core.dto.entity.GuestDTO;
import com.mmj.data.core.dto.entity.PassengerDTO;
import com.mmj.data.core.dto.entity.PaymentTypeDTO;
import com.mmj.data.core.dto.entity.SaCodeDTO;
import com.mmj.data.core.dto.entity.SearchResultDTO;
import com.mmj.data.core.dto.entity.SuspensionDTO;
import com.mmj.data.core.dto.entity.SuspensionSummaryDTO;
import com.mmj.data.core.dto.entity.SuspensionTypeDTO;
import com.mmj.data.core.dto.entity.SystemConfigurationDTO;
import com.mmj.data.core.dto.entity.TravelerRelationshipDTO;
import com.mmj.data.core.dto.ws.WaitlistPassengerDTO;
import com.mmj.data.core.dto.ws.WaitlistRequestDTO;
import com.mmj.data.core.enums.SACodeEnum;
import com.mmj.data.core.enums.SystemConfigurationEnum;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.LimitReachException;
import com.mmj.data.core.exception.NotAuthorizedTravelException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.extclient.Passenger;
import com.mmj.data.core.extclient.dto.AncillaryFeeDto;
import com.mmj.data.core.extclient.dto.EmployeeInformationDto;
import com.mmj.data.core.extclient.dto.FlightBookingRequestDto;
import com.mmj.data.core.extclient.dto.FlightCalendarInfoDto;
import com.mmj.data.core.extclient.dto.FlightPassengerDto;
import com.mmj.data.core.extclient.dto.ModifyOrderRequestDto;
import com.mmj.data.core.extclient.dto.PassengerInformationDto;
import com.mmj.data.core.extclient.dto.PassengerSegmentDto;
import com.mmj.data.core.extclient.dto.ReservationChangeDto;
import com.mmj.data.core.extclient.dto.ReservationDto;
import com.mmj.data.core.extclient.dto.ReservationPaymentDto;
import com.mmj.data.core.extclient.dto.RouteDto;
import com.mmj.data.core.extclient.dto.SegmentBookingRequestDto;
import com.mmj.data.core.extclient.dto.ShopDto;
import com.mmj.data.core.extclient.dto.summary.FlightManifestDetailWaitlistRequestDto;
import com.mmj.data.core.extclient.dto.summary.FlightManifestDetailWaitlistResponseDto;
import com.mmj.data.core.extclient.dto.summary.PassengerWaitlistDto;
import com.mmj.data.core.extclient.dto.summary.WaitlistDetailsDto;
import com.mmj.data.core.interceptors.TimerInterceptor;
import com.mmj.data.core.util.DateTimeUtil;
import com.mmj.data.core.util.ToJson;
import com.mmj.data.ejb.cache.G4tcCache;
import com.mmj.data.ejb.model.RefundEN;
import com.mmj.data.ejb.session.ConfigurationSB;
import com.mmj.data.ejb.session.EmployeeSB;
import com.mmj.data.ejb.session.ExternalServiceSB;
import com.mmj.data.ejb.session.GuestSB;
import com.mmj.data.ejb.session.HealthCheckSB;
import com.mmj.data.ejb.session.PassengerSB;
import com.mmj.data.ejb.session.RefundSB;
import com.mmj.data.ejb.session.SaCodeSB;
import com.mmj.data.ejb.session.SuspensionSB;
import com.mmj.data.ejb.session.SuspensionTypeSB;
import com.mmj.data.ejb.session.TravellerRelationshipsSB;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static com.mmj.data.core.constant.Constants.DATE_FORMATTER;
import static com.mmj.data.core.constant.Constants.HEADER_USERNAME;
import static com.mmj.data.core.constant.Constants.HEADER_USER_ID;
import static com.mmj.data.core.constant.Constants.PAGE_SIZE_EMPLOYEE;
import static com.mmj.data.core.constant.Constants.SA_CODE_RETIREE;

/**
 * Rest Service for handling employee data.
 * <p>
 * All exception handling is done in ExceptionHttpMapper.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class G4tcRestWebservice implements G4tcRestWebserviceI {
    private static final Logger LOG = LoggerFactory.getLogger(G4tcRestWebservice.class);

    @Inject
    private ConfigurationSB configurationSB;

    @Inject
    private EmployeeSB employeeSB;

    @Inject
    private ExternalServiceSB externalServiceSB;

    @Inject
    private G4tcCache g4tcCache;

    @Inject
    private GuestSB guestSB;

    @Inject
    private HealthCheckSB healthCheckSB;

    @Inject
    private PassengerSB passengerSB;

    @Inject
    private SaCodeSB saCodeSB;

    @Inject
    private SuspensionSB suspensionSB;

    @Inject
    private SuspensionTypeSB suspensionTypeSB;

    @Inject
    private TravellerRelationshipsSB travellerRelationshipsSB;

    @Inject
    private RefundSB refundSB;

    /**
     * Searches for employee id are exact matches. If user does not add prepending 0s, then we prepend them until the string is size 6.
     *
     * @param employeeId The employee id in its current state.
     * @return The employee id with added zeros if applicable. Limits size of employee id to 6 as well.
     */
    private String prependZerosForEmployeeId(String employeeId) {
        if (employeeId != null) {
            return ("000000" + employeeId).substring((employeeId.length() + 6) - 6);
        } else {
            return null;
        }
    }

    @Override
    @Interceptors({TimerInterceptor.class})
    public Response getEmployeeById(String empNumber, HttpServletRequest servletRequest) throws NotFoundException {
        empNumber = prependZerosForEmployeeId(empNumber);
        LOG.info("Looking up employee with empNumber {}", empNumber);
        EmployeeFullDTO employeeFullDTO = employeeSB.getEmployeeById(empNumber);
        LOG.debug("Result : {}", ToJson.toJson(employeeFullDTO));
        return Response.status(Response.Status.OK).entity(employeeFullDTO).build();
    }

    @Override
    public Response getEmployeeById(@Context HttpServletRequest servletRequest) throws BusinessException {
        validateUser(servletRequest);
        return getEmployeeById(servletRequest.getHeader(HEADER_USER_ID), servletRequest);
    }

    @Override
    public Response searchEmployees(String id, String email, String firstName, String lastName, Integer offset, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        LOG.info("Searching for employee with criteria {}, {}, {}, {}", id, email, firstName, lastName);
        id = prependZerosForEmployeeId(id);
        SearchResultDTO result = employeeSB.searchEmployees(id, email, firstName, lastName, offset, PAGE_SIZE_EMPLOYEE + 1);
        if (offset >= PAGE_SIZE_EMPLOYEE) {
            servletResponse.addHeader("previousoffset", "" + (offset - PAGE_SIZE_EMPLOYEE));
        }
        if (result.getEmployeeBaseDTOs().size() > PAGE_SIZE_EMPLOYEE) {
            servletResponse.addHeader("nextoffset", "" + (offset + PAGE_SIZE_EMPLOYEE));
            result.getEmployeeBaseDTOs().remove(result.getEmployeeBaseDTOs().size() - 1);
        }
        servletResponse.addHeader("totalresultcount", result.getCount().toString());
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getPassengerByName(String firstName, String lastName, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Looking up passenger with firstname {} and lastname {}", firstName, lastName);
        if (StringUtils.isBlank(firstName) && StringUtils.isBlank(lastName)) {
            throw new BusinessException("First name and/or last name must be specified.");
        }
        List<PassengerDTO> passengers = passengerSB.getPassengerByName(firstName, lastName);
        LOG.debug("Result : {}", ToJson.toJson(passengers));
        return Response.status(Response.Status.OK).entity(passengers).build();
    }

    @Override
    public Response getPassengersByEmployeeId(HttpServletRequest servletRequest) throws BusinessException {
        validateUser(servletRequest);
        String id = servletRequest.getHeader(HEADER_USER_ID);
        if (StringUtils.isBlank(id)) {
            throw new BusinessException("Missing employee id in header");
        }
        id = prependZerosForEmployeeId(id);
        LOG.info("Looking up passengers for employee with id {}", id);
        List<PassengerDTO> passengers = passengerSB.getPassengerByEmployeeId(id);
        LOG.debug("Result : {}", ToJson.toJson(passengers));
        return Response.status(Response.Status.OK).entity(passengers).build();
    }

    @Override
    public Response getPassengersByEmployeeId(String id, HttpServletRequest servletRequest) {
        LOG.info("Looking up passengers for employee with id {}", id);
        List<PassengerDTO> passengers = passengerSB.getPassengerByEmployeeId(id);
        LOG.debug("Result : {}", ToJson.toJson(passengers));
        return Response.status(Response.Status.OK).entity(passengers).build();
    }

    @Override
    public Response getGuestsByEmployeeId(HttpServletRequest servletRequest) throws BusinessException {
        validateUser(servletRequest);
        String id = servletRequest.getHeader(HEADER_USER_ID);
        if (StringUtils.isBlank(id)) {
            throw new BusinessException("Missing employee id in header");
        }
        id = prependZerosForEmployeeId(id);
        LOG.info("Looking up guests for employee with id {}", id);
        List<GuestDTO> guests = guestSB.getGuestsByEmployeeId(id);
        LOG.debug("Result : {}", ToJson.toJson(guests));
        return Response.status(Response.Status.OK).entity(guests).build();
    }

    @Override
    public Response createPassenger(PassengerDTO passengerDTO, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Creating new passenger for employee with id {}", passengerDTO.getEmployeeId());
        PassengerDTO result = passengerSB.createPassenger(passengerDTO);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response createGuest(GuestDTO guestDTO, HttpServletRequest servletRequest) throws BusinessException {
        String id = servletRequest.getHeader(HEADER_USER_ID);
        if (StringUtils.isBlank(id)) {
            throw new BusinessException("Missing employee id in header");
        }
        LOG.info("Creating new guest for employee with id {}", id);
        GuestDTO result = guestSB.createGuest(guestDTO);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getPassengerByPassengerId(Long passengerId, HttpServletRequest servletRequest) {
        LOG.info("Looking up passenger for passenger id {}", passengerId);
        PassengerDTO result = passengerSB.getPassengerByPassengerId(passengerId);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response updatePassenger(PassengerDTO passengerDTO, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Updating passenger {}", passengerDTO);
        PassengerDTO result = passengerSB.updatePassenger(passengerDTO);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response deletePassengerByPassengerId(Long passengerId, HttpServletRequest servletRequest) throws NotFoundException {
        LOG.info("Deleting passenger with passenger id {}", passengerId);
        passengerSB.deletePassengerByPassengerId(passengerId);
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response getConfigurations(HttpServletRequest servletRequest) {
        LOG.info("Getting all configurations.");
        List<ConfigurationDTO> result = configurationSB.getConfigurations();
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response updateConfiguration(ConfigurationDTO configurationDTO, HttpServletRequest servletRequest) throws NotFoundException {
        LOG.info("Updating configuration id {} ", configurationDTO.getId());
        ConfigurationDTO result = configurationSB.updateConfiguration(configurationDTO);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getSuspensionById(Long suspensionId, HttpServletRequest servletRequest) throws NotFoundException {
        LOG.info("Getting suspension with id {} ", suspensionId);
        SuspensionDTO result = suspensionSB.getSuspensionById(suspensionId);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getSuspensionsByEmployeeId(String employeeId, HttpServletRequest servletRequest) {
        LOG.debug("Getting all suspensions for employee employeeId {} ", employeeId);
        List<SuspensionDTO> result = suspensionSB.getSuspensionByEmployeeId(employeeId);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getSuspensionSummaryByEmployeeId(String employeeId, HttpServletRequest servletRequest) throws NotFoundException {
        LOG.debug("Getting suspension summary for employee employeeId {} ", employeeId);
        SuspensionSummaryDTO result = suspensionSB.getSuspensionSummaryByEmployeeId(employeeId);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getSuspensionSummary(HttpServletRequest servletRequest) throws BusinessException {
        String employeeId = servletRequest.getHeader(HEADER_USER_ID);
        if (StringUtils.isBlank(employeeId)) {
            throw new BusinessException("Missing employee id in header");
        }
        employeeId = prependZerosForEmployeeId(employeeId);
        LOG.info("Logged in employee retrieves their own suspension summary: employeeId {} ", employeeId);
        SuspensionSummaryDTO result = suspensionSB.getSuspensionSummaryByEmployeeId(employeeId);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response createSuspension(SuspensionDTO suspensionDTO, HttpServletRequest servletRequest) throws NotFoundException {
        LOG.info("Creating suspension for employee id {} ", suspensionDTO.getEmployeeId());
        Enumeration<String> headerNames = servletRequest.getHeaderNames();
        String userId = servletRequest.getHeader(HEADER_USER_ID);
        String userName = servletRequest.getHeader(HEADER_USERNAME);
        SuspensionDTO result = suspensionSB.createSuspension(suspensionDTO, userId, userName);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response updateSuspension(SuspensionDTO suspensionDTO, HttpServletRequest servletRequest) throws NotFoundException {
        LOG.info("Updating suspension with id {} ", suspensionDTO.getId());
        Enumeration<String> headerNames = servletRequest.getHeaderNames();
        String userId = servletRequest.getHeader(HEADER_USER_ID);
        String userName = servletRequest.getHeader(HEADER_USERNAME);
        SuspensionDTO result = suspensionSB.updateSuspension(suspensionDTO, userId, userName);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getRelationshipTypes(HttpServletRequest servletRequest) {
        LOG.info("Getting relationship types.");
        List<TravelerRelationshipDTO> result = travellerRelationshipsSB.getRelationshipTypes();
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getSuspensionTypes(HttpServletRequest servletRequest) {
        LOG.info("Getting suspension types.");
        List<SuspensionTypeDTO> result = suspensionTypeSB.getSuspensionTypes();
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response updateEmployee(EmployeePartialDTO employeePartialDTO, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Updating Employee with id {}.", employeePartialDTO.getEmployeeId());
        employeePartialDTO.setEmployeeId(prependZerosForEmployeeId(employeePartialDTO.getEmployeeId()));
        EmployeePartialDTO result = employeeSB.updateEmployee(employeePartialDTO);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response createEmployee(EmployeePartialDTO employeePartialDTO, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Creating new employee.");
        EmployeePartialDTO result = employeeSB.createEmployee(employeePartialDTO);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response patchEmployee(EmployeePatchDTO employeePatchDTO, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Patching employee.");
        String userId = servletRequest.getHeader(HEADER_USER_ID);
        employeeSB.patchEmployee(employeePatchDTO, userId);
        LOG.debug("Patch complete");
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response getSaCodes(HttpServletRequest servletRequest) {
        LOG.info("Getting all sa codes.");
        List<SaCodeDTO> result = saCodeSB.getSaCodes();
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response updateSaCode(SaCodeDTO saCodeDTO, HttpServletRequest servletRequest) throws NotFoundException {
        LOG.info("Updating sa code {}", saCodeDTO);
        SaCodeDTO result = saCodeSB.updateSaCode(saCodeDTO);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getConfigurationSummary(String fourDigitYear, HttpServletRequest servletRequest) {
        LOG.info("Getting summary of configuration for specific year.");
        List<ConfigurationSummaryDTO> result = configurationSB.getConfigurationSummary(Integer.parseInt(fourDigitYear));
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response createConfiguration(ConfigurationDTO configurationDTO, HttpServletRequest servletRequest) {
        LOG.info("Updating configuration id {} ", configurationDTO.getId());
        ConfigurationDTO result = configurationSB.createConfiguration(configurationDTO);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getMyIDTravelByEmployeeId(String employeeId, HttpServletRequest servletRequest) throws NotFoundException {
        LOG.info("Getting MyIDTravel info by employee id.");
        EmployeeMyIDTravelDTO result = employeeSB.getMyIDTravel(employeeId);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getGuestPassBookingsByEmployeeId(String employeeId, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Getting guest pass bookings for employee {}", employeeId);
        employeeId = prependZerosForEmployeeId(employeeId);
        List<ReservationDto> result = employeeSB.getGuestPassBookingsByEmployeeId(employeeId);
        LOG.debug("Result : {}", result);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response loadEmployee(EmployeePartialDTO employeePartialDTO, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Loading new employee.");
        EmployeePartialDTO result = employeeSB.loadEmployee(employeePartialDTO);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getRoutes(@Context HttpServletRequest servletRequest) {
        LOG.info("Getting routes.");
        List<RouteDto> result = externalServiceSB.getRoutes();
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getHealth() {
        LOG.info("Getting health related info.");
        List<String> result = healthCheckSB.getHealthCheck();
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getSSRs(@Context HttpServletRequest servletRequest) {
        LOG.info("Getting SSR's.");
        List<AncillaryFeeDto> result = externalServiceSB.getSSRs();
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getBagFees(String departureAirportCode, String arrivalAirportCode, String bookingDate, @Context HttpServletRequest servletRequest) {
        LOG.info("Getting bagfees.");
        List<AncillaryFeeDto> result = externalServiceSB.getBagFees(departureAirportCode, arrivalAirportCode, bookingDate);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getAvailableFlightDates(String departAirportCode, String arriveAirportCode, Integer channelId, HttpServletRequest servletRequest) {
        LOG.info("Getting flight dates");
        List<String> result = externalServiceSB.getAvailableFlightDates(departAirportCode, arriveAirportCode, channelId);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getAvailableFlightDatesAndFareDetails(String departAirportCode, String arriveAirportCode, String searchStartDate, String searchEndDate, String bookingType, Integer channelId, Boolean modifyRequest, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Getting flight date and fare details");
        List<FlightCalendarInfoDto> result = externalServiceSB.getAvailableFlightDatesAndFareDetails(departAirportCode, arriveAirportCode, DateTimeUtil.stringToLocalDate(searchStartDate), DateTimeUtil.stringToLocalDate(searchEndDate), bookingType, channelId, modifyRequest);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getShopFlights(String departAirportCode, String arriveAirportCode, String searchStartDate, String searchEndDate, Integer channelId, Integer passengerCount, List<String> fareClasses, String bookingDate, String bookingType, Double originalPrice, Long overrideReasonId, Double adjustedPrice, String originatingAirline, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Get shop flights");
        List<ShopDto> result = null;
        if (bookingDate != null) {
            result = externalServiceSB.getShopFlights(departAirportCode, arriveAirportCode, DateTimeUtil.stringToLocalDate(searchStartDate), DateTimeUtil.stringToLocalDate(searchEndDate), channelId, passengerCount, fareClasses, DateTimeUtil.stringToLocalDate(bookingDate), bookingType, originalPrice, overrideReasonId, adjustedPrice, originatingAirline);
        } else {
            result = externalServiceSB.getShopFlights(departAirportCode, arriveAirportCode, DateTimeUtil.stringToLocalDate(searchStartDate), DateTimeUtil.stringToLocalDate(searchEndDate), channelId, passengerCount, fareClasses, null, bookingType, originalPrice, overrideReasonId, adjustedPrice, originatingAirline);
        }
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getShopFlight(String itn, String flightNum, String departDate, Integer channelId, Integer passengerCount, String fareClass, String bookingDate, String bookingType, String originatingAirline, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Get shop flight by number");
        ShopDto result = externalServiceSB.getShopFlight(itn, flightNum, DateTimeUtil.stringToLocalDate(departDate), channelId, passengerCount, fareClass, DateTimeUtil.stringToLocalDate(bookingDate), bookingType, originatingAirline);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getOrders(String searchStartDate, String searchEndDate, String bookingType, String includes, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Getting orders by start and end search dates {} {}", searchStartDate, searchEndDate);
        String employeeNumber = servletRequest.getHeader(HEADER_USER_ID);
        if (StringUtils.isBlank(employeeNumber)) {
            throw new BusinessException("Missing employee id in header");
        }
        employeeNumber = prependZerosForEmployeeId(employeeNumber);
        int year = DateTimeUtil.getLocalDateNowGMT().getYear();

        LocalDate start;
        SystemConfigurationDTO systemConfigurationDTO = (SystemConfigurationDTO) g4tcCache.getSystemConfiguration(SystemConfigurationEnum.FIRST_SEARCH_DATE);
        LocalDate firstSearchDate = DateTimeUtil.stringToLocalDate(systemConfigurationDTO.getValue());
        if (firstSearchDate != null) {
            start = firstSearchDate;
            if (StringUtils.isNotBlank(searchStartDate)) {
                LocalDate s = LocalDate.parse(year + "-" + searchStartDate, DATE_FORMATTER);
                if (s.isAfter(firstSearchDate)) {
                    start = s;
                }
            }
        } else {
            start = StringUtils.isNotBlank(searchStartDate) ? LocalDate.parse(year + "-" + searchStartDate, DATE_FORMATTER) : LocalDate.parse(year + "-01-01", DATE_FORMATTER);
        }

        LocalDate end;
        if (StringUtils.isNotBlank(searchEndDate)) {
            end = LocalDate.parse(year + "-" + searchEndDate, DATE_FORMATTER);
        } else {
            end = LocalDate.parse(year + "-12-31", DATE_FORMATTER);
        }

        List<ReservationDto> result = externalServiceSB.getOrders(start, end, employeeNumber, bookingType, includes);
        LOG.debug("Results : {}", result);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getOrderByConfirmationNumber(String confirmationNumber, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Getting order by confirmation number {}", confirmationNumber);
        Response response = null;
        String userId = servletRequest.getHeader(HEADER_USER_ID);
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("Missing employee id in header");
        }
        ReservationDto result = null;
        try {
            result = externalServiceSB.getOrderByConfirmationNumber(confirmationNumber);
            response = Response.status(Response.Status.OK).entity(result).build();
            if (result != null && !result.getCustomerNumber().equals("E" + userId)) {
                result = null;
                response = Response.status(Response.Status.NOT_FOUND).entity(result).build();
            }
        } catch (NotFoundException e) {
            response = Response.status(Response.Status.NOT_FOUND).entity(result).build();
        }
        return response;
    }

    @Override
    public Response createOrder(FlightBookingRequestDto flightBookingRequestDto, String vacationupgrade, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("creating order {}", ToJson.toJson(flightBookingRequestDto));
        validateUser(servletRequest);
        validateBooking(flightBookingRequestDto, Boolean.parseBoolean(vacationupgrade), servletRequest);
        String userId = servletRequest.getHeader(HEADER_USER_ID);
        String userName = servletRequest.getHeader(HEADER_USERNAME);
        ReservationDto result = externalServiceSB.createOrder(flightBookingRequestDto, userId, userName);
        LOG.debug("Result : {}", result);
        processSucceededBooking(flightBookingRequestDto, Boolean.parseBoolean(vacationupgrade), servletRequest);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response updateOrder(ModifyOrderRequestDto modifyRequestDto, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("updating order {}", modifyRequestDto);
        validateUser(servletRequest);
        String userId = servletRequest.getHeader(HEADER_USER_ID);
        String userName = servletRequest.getHeader(HEADER_USERNAME);
        ReservationDto result = externalServiceSB.updateOrder(modifyRequestDto, userId, userName);
        LOG.debug("Result : {}", result);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response cancelOrder(String confirmationNumber, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("cancelling order {}", confirmationNumber);
        Response response = null;
        String employeeId = servletRequest.getHeader(HEADER_USER_ID);
        String userName = servletRequest.getHeader(HEADER_USERNAME);
        if (StringUtils.isBlank(employeeId)) {
            throw new BusinessException("Missing employee id in header");
        }
        if (StringUtils.isBlank(userName)) {
            throw new BusinessException("Missing user name id in header");
        }
        // We need to prepend missing zeros before sending to other WS.
        employeeId = prependZerosForEmployeeId(employeeId);
        ReservationDto empReservationDto = null;
        try {
            empReservationDto = externalServiceSB.getOrderByConfirmationNumber(confirmationNumber);
            if (empReservationDto != null && !empReservationDto.getCustomerNumber().equals("E" + employeeId)) {
                empReservationDto = null;
                response = Response.status(Response.Status.NOT_FOUND).entity(empReservationDto).build();
            } else {

                empReservationDto = externalServiceSB.cancelOrder(confirmationNumber, employeeId, userName);

                // Here we retrieve all buddy passes for the employee that match the depart date for
                // each one of their segments
                List<List<ReservationDto>> buddyFlightsOuterList = new ArrayList<>();
                for (PassengerSegmentDto employeeSegment : empReservationDto.getPassengerSegments()) {
                    String departDateString = employeeSegment.getDepartureDate();
                    LocalDate departDate = DateTimeUtil.stringToLocalDate(departDateString);
                    List<ReservationDto> buddyFlights = externalServiceSB.getOrders(departDate, departDate, employeeId, "BP", "PASSENGER_SEGMENT,PASSENGER_WAITLIST");
                    buddyFlightsOuterList.add(buddyFlights);
                }

                // The purpose of this for loop is to match the flight an employee is trying to cancel with
                // any potential buddy passes that are flying on the same flight. If a match is found then
                // the buddy pass needs to have the priority boarding status lowered from SA4 to SA5.
                // Each buddyFlights contains a list of PassengerSegmentDtos retrieved for a specific date.
                // Each buddy pass PassengerSegmentDto's flight number is compared to the employee
                // PassengerSegmentDto's flight number. If there is a match then the employee and buddy were
                // flying together and since the employee is cancelling their flight, their buddy's priority
                // boarding status needs to be updated to a lower priority. Flight number and date represent
                // a unique flight hence why the comparison is being made to just flight numbers - we already
                // retrieved flights based on the departure date.
                for (List<ReservationDto> buddyFlights : buddyFlightsOuterList) {
                    s:
                    for (ReservationDto bpFlight : buddyFlights) {
                        List<PassengerSegmentDto> bpSegments = bpFlight.getPassengerSegments();
                        for (PassengerSegmentDto bpSeg : bpSegments) {
                            List<PassengerSegmentDto> empSegments = empReservationDto.getPassengerSegments();
                            for (PassengerSegmentDto empSeg : empSegments) {
                                if (bpSeg.getFlightNumber().equals(empSeg.getFlightNumber()) && bpSeg.getEmployeeInformation().getSaCode().equals(SACodeEnum.SA4.name())) {
                                    ModifyOrderRequestDto bpModifyRequestDto = new ModifyOrderRequestDto();
                                    bpModifyRequestDto.setConfirmationNumber(bpFlight.getItinerary());
                                    ReservationChangeDto<EmployeeInformationDto> reservationChangeDto = new ReservationChangeDto<>();
                                    reservationChangeDto.setConfirmationNumber(bpFlight.getItinerary());
                                    reservationChangeDto.setAction("UPDATE");
                                    reservationChangeDto.setEntityType("EMPLOYEE_INFO");
                                    reservationChangeDto.setCompany(bpFlight.getCompany());
                                    reservationChangeDto.setPaxNum(bpSeg.getPaxNum());
                                    EmployeeInformationDto empInfo = bpSeg.getEmployeeInformation();
                                    empInfo.setSaCode(SACodeEnum.SA5.name());
                                    reservationChangeDto.setEntity(empInfo);
                                    reservationChangeDto.setInd(bpSeg.getInd());
                                    reservationChangeDto.setSegment(bpSeg.getSegment());
                                    List<ReservationChangeDto> reservationChangeDtos = new ArrayList<>();
                                    reservationChangeDtos.add(reservationChangeDto);
                                    bpModifyRequestDto.setReservationChanges(reservationChangeDtos);
                                    externalServiceSB.updateOrder(bpModifyRequestDto, employeeId, empReservationDto.getBookedBy());
                                    break s;
                                }
                            }
                        }
                    }
                }

                String bookingType = empReservationDto.getBookingType();
                PassengerSegmentDto passengerSegmentDto = empReservationDto.getPassengerSegments().get(0);
                switch (bookingType) {
                    case "EM":
                        EmployeeInformationDto employeeInformation = passengerSegmentDto.getEmployeeInformation();
                        String itnSACode = employeeInformation.getSaCode();
                        String currentSACode = employeeSB.getEmployeeSACode(employeeId);
                        if (currentSACode.equals(SACodeEnum.SA3.name()) && itnSACode.equals(SACodeEnum.SA2.name())) {
                            refundSB.refundOneVacationUpgrade(employeeId, confirmationNumber, passengerSegmentDto.getPaxNum());
                        }
                        break;
                    case "BP":
                        refundSB.refundOneGuestPass(employeeId, confirmationNumber, passengerSegmentDto.getPaxNum());
                        BigDecimal paidAmount = empReservationDto.getPaidAmount();
                        BigDecimal balanceDue = empReservationDto.getBalanceDue();
                        if ((paidAmount != null && paidAmount.compareTo(new BigDecimal(0)) != 0) || (balanceDue != null && balanceDue.compareTo(new BigDecimal(0)) != 0)) {
                            refundSB.refundOrder(employeeId, confirmationNumber, empReservationDto);
                        }
                        break;
                    default:
                        break;
                }
                response = Response.status(Response.Status.OK).entity(empReservationDto).build();
                LOG.debug("Result : {}", empReservationDto);
            }
        } catch (NotFoundException e) {
            response = Response.status(Response.Status.NOT_FOUND).entity(empReservationDto).build();
        }
        return response;
    }

    @Override
    public Response getPaymentTypes(@Context HttpServletRequest servletRequest) {
        List<PaymentTypeDTO> paymenttypes = externalServiceSB.getPaymenttypes();
        return Response.status(Response.Status.OK).entity(paymenttypes).build();
    }

    /**
     * @param flightBookingRequestDto
     * @param isVacationUpgrade
     * @param servletRequest
     * @throws BusinessException
     */
    private void validateBooking(FlightBookingRequestDto flightBookingRequestDto, boolean isVacationUpgrade, HttpServletRequest servletRequest) throws BusinessException {
        String employeeId = prependZerosForEmployeeId(servletRequest.getHeader(HEADER_USER_ID));
        String bookingType = flightBookingRequestDto.getBookingType();

        List<ReservationPaymentDto> payments = flightBookingRequestDto.getPayments();
        PaymentTypeDTO paymentTypeDTO = null;

        String employeeSACode = employeeSB.getEmployeeSACode(employeeId);
        String employeeStatus = employeeSB.getEmployeeStatus(employeeId);
        boolean isRetired = "R".equals(employeeStatus);
        boolean isEmployeeTravelling = isEmployeeTravelling(employeeId, flightBookingRequestDto.getSegments().get(0).getFlightPassengers());

        switch (bookingType) {
            case "EM":
                // Validations.
                if (isVacationUpgrade && employeeSB.getRemainingVacationUpgrades(employeeId) < 1) {
                    throw new LimitReachException("User with id " + employeeId + " has no more vacation upgrades available.");
                }

                // Set SA codes for all passengers on all segments.
                for (SegmentBookingRequestDto segmentBookingRequestDto: flightBookingRequestDto.getSegments() ) {
                    List<FlightPassengerDto> flightPassDtos = segmentBookingRequestDto.getFlightPassengers();
                    for (FlightPassengerDto passDto : flightPassDtos) {
                        String saCode = calculateEmployeeFlightSaCode(isEmployeeTravelling, isVacationUpgrade, isRetired, employeeSACode);
                        passDto.getEmployeeInformation().setSaCode(saCode);
                    }
                }

                break;
            case "BP":
                // There is only one segment in a BP booking, and only one passenger in that segment.
                SegmentBookingRequestDto segmentBookingRequestDto = flightBookingRequestDto.getSegments().get(0);
                List<FlightPassengerDto> flightPassDtos = segmentBookingRequestDto.getFlightPassengers();
                FlightPassengerDto flightPassDto = flightPassDtos.get(0);

                // Validations.
                List<SegmentBookingRequestDto> segments = flightBookingRequestDto.getSegments();
                if (segments.size() != 1) {
                    throw new BusinessException("Number of segments in the booking must be one");
                }
                if (employeeSB.getRemainingGuestPasses(employeeId) < 1) {
                    throw new LimitReachException("You have exceeded the maximum number of guest passes");
                }

                // Get needed data to calculate if employee is already on the flight.
                String departDateTime = segmentBookingRequestDto.getShopDto().getSegment().getLegs().get(0).getScheduledDepartDateTime();
                departDateTime = departDateTime.substring(0, departDateTime.indexOf("T"));
                LocalDate departDate = DateTimeUtil.stringToLocalDate(departDateTime);
                List<ReservationDto> employeeFlights = externalServiceSB.getOrders(departDate, departDate, employeeId, "EM", "PASSENGER_SEGMENT");

                // Calcualte and set the appropriate SA code based on if the employee is flying on the same flight.
                boolean isEmployeeOnReservation = isEmployeeOnReservation(employeeFlights, segmentBookingRequestDto.getShopDto().getSegment().getFltNum());
                String saCode = calculateGuestPassFlightSaCode(isEmployeeOnReservation);
                flightPassDto.getEmployeeInformation().setSaCode(saCode);

                break;
            default:
                break;
        }

        if (payments.isEmpty()) {
            ReservationPaymentDto reservationPaymentDto = new ReservationPaymentDto();
            paymentTypeDTO = g4tcCache.getPaymentTypeByCode(bookingType.equalsIgnoreCase("EM") ? "NA" : "PA");
            reservationPaymentDto.setPaymentType(paymentTypeDTO.getCode());
            reservationPaymentDto.setPaymentTypeId(paymentTypeDTO.getId());
            payments.add(reservationPaymentDto);
        }
    }

    /**
     * Helps calculate the SA code for guest pass, based on if the employee is on the same flight.
     *
     * @param isEmployeeOnReservation true if the employee is on the reservation.
     * @return SACode as a string.
     */
    private String calculateGuestPassFlightSaCode(boolean isEmployeeOnReservation) {
        // Use cases:
        // Retirees don't get guest passes.
        // Interns follow same rules as employees.
        // Guest Pass flying by themself - SA5
        // Guest Pass after Employee Booked on same itinerary - SA4
        // Guest Pass before Employee Booked on itinerary - SA5
        // When an employee
        // There are talks to make this SA4 down the road.
        return isEmployeeOnReservation ? SACodeEnum.SA4.name() : SACodeEnum.SA5.name();
    }

    /**
     * Helps calculate the SA code for employee and eligible flights.
     *
     * @param isEmployeeTravelling true if employee is travelling.
     * @param isVacationUpgrade    true if vacation upgrade.
     * @param isRetired            true if retired.
     * @param employeeAPTSACode    Employee's SACode in APT.
     * @return SACode as a string.
     */
    private String calculateEmployeeFlightSaCode(boolean isEmployeeTravelling, boolean isVacationUpgrade, boolean isRetired, String employeeAPTSACode) {
        // Use cases:
        // Employee by self - APT Value
        // Employee with Eligibles - EM and Eligibles are same as APT Value.
        // Employee with Vacation Upgrade - SA2
        // Employee with Eligibles w/ Vacation Upgrade - All SA2
        // Eligibles by themselves - SA4
        // No interns or retirees are to be allotted vacation upgrades (or shouldn't be so don't have to worry about priority skipping).
        // Retirees and their eligibles will always fly SA5.
        // Interns follow same rules as employees.
        return isEmployeeTravelling ? (isVacationUpgrade ? SACodeEnum.SA2.name() : (isRetired ? SA_CODE_RETIREE : employeeAPTSACode)) : (isRetired ? SA_CODE_RETIREE : SACodeEnum.SA4.name());
    }

    private <T extends Passenger> boolean isEmployeeTravelling(String employeeId, List<T> list) throws NotFoundException {
        boolean isEmployeeTravelling = false;
        String[] dobFirstMiddleLast = employeeSB.getDOBFirstMiddleLast(employeeId);
        String empDateOfBirth = dobFirstMiddleLast[0];
        String empFirstName = dobFirstMiddleLast[1];
        String empMiddleName = dobFirstMiddleLast[2];
        String empLastName = dobFirstMiddleLast[3];
        for (Passenger x : list) {
            String dateOfBirth = x.getDateOfBirth();
            String firstName = x.getFirstName();
            String middleName = x.getMiddleName();
            String lastName = x.getLastName();
            // We use defaultString to set null db values to empty string for comparisons, otherwise the following can fail on null comparison against "".
            if (StringUtils.equals(empDateOfBirth, dateOfBirth) &&
                    StringUtils.equalsIgnoreCase(StringUtils.defaultString(empFirstName, ""), StringUtils.defaultString(firstName, "")) &&
                    StringUtils.equalsIgnoreCase(StringUtils.defaultString(empMiddleName, ""), StringUtils.defaultString(middleName, "")) &&
                    StringUtils.equalsIgnoreCase(StringUtils.defaultString(empLastName, ""), StringUtils.defaultString(lastName, ""))) {
                // The employee is one of the travelers.
                isEmployeeTravelling = true;
                break;
            }
        }
        return isEmployeeTravelling;
    }

    /**
     * Validate the travel status of the employee provided in the servletRequest.
     *
     * @param servletRequest
     */
    private void validateUser(HttpServletRequest servletRequest) throws BusinessException {
        String employeeId = prependZerosForEmployeeId(servletRequest.getHeader(HEADER_USER_ID));
        if (!employeeSB.employeeExists(employeeId)) {
            throw new NotAuthorizedTravelException("User with id " + employeeId + " does not exist.");
        }
        Response suspensionSummary = getSuspensionSummary(servletRequest);
        SuspensionSummaryDTO suspensionSummaryDTO = (SuspensionSummaryDTO) suspensionSummary.getEntity();
        if (suspensionSummaryDTO == null || !suspensionSummaryDTO.isCanTravelStatus() || !employeeSB.canAccessEPT(employeeId)) {
            throw new NotAuthorizedTravelException("User with id " + employeeId + " is not authorized to travel");
        }
    }

    private void processSucceededBooking(FlightBookingRequestDto flightBookingRequestDto, boolean vacationUpgrade, HttpServletRequest servletRequest) {
        String userId = prependZerosForEmployeeId(servletRequest.getHeader(HEADER_USER_ID));
        String bookingType = flightBookingRequestDto.getBookingType();
        switch (bookingType) {
            case "EM":
                if (vacationUpgrade) {
                    employeeSB.incrementVacationUpgradesUsed(userId);
                }
                break;
            case "BP":
                employeeSB.incrementGuestPassesBooked(userId);
                break;
            default:
                break;
        }
    }

    @Override
    public Response updateSystemConfiguration() {
        LOG.info("Update system configuration cache.");
        List<String> elements = g4tcCache.loadSystemConfigurations();
        return Response.status(Response.Status.OK).entity(elements).build();
    }

    @Override
    public Response updateAccessConfiguration() {
        LOG.info("Update system configuration cache.");
        List<String> elements = g4tcCache.loadAccessConfigurations();
        return Response.status(Response.Status.OK).entity(elements).build();
    }

    @Override
    public Response getSystemConfigurations(HttpServletRequest servletRequest) {
        LOG.info("Getting all active system configurations.");
        List<SystemConfigurationDTO> result = g4tcCache.getActiveSystemConfigurations();
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getAccessConfigurations(HttpServletRequest servletRequest) {
        LOG.info("Getting all access configurations.");
        List<AccessConfigurationDTO> result = g4tcCache.getAccessConfigurations();
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getWaitlist(WaitlistRequestDTO waitlistRequestDTO, HttpServletRequest servletRequest) throws BusinessException {
        boolean isVacationUpgrade = waitlistRequestDTO.getVacationUpgrade();
        String employeeId = servletRequest.getHeader(HEADER_USER_ID);
        employeeId = prependZerosForEmployeeId(employeeId);
        waitlistRequestDTO.setEmployeeId(employeeId);
        String employeeSACode = employeeSB.getEmployeeSACode(employeeId);
        String employeeStatus = employeeSB.getEmployeeStatus(employeeId);
        List<WaitlistPassengerDTO> passengers = waitlistRequestDTO.getPassengers();
        boolean isRetired = "R".equals(employeeStatus);
        String saCode = null;
        ReservationDto order = null;
        String itn = waitlistRequestDTO.getItn();
        boolean forecast = waitlistRequestDTO.getForecast();
        if (!forecast && StringUtils.isNotBlank(itn)) {
            order = externalServiceSB.getOrderByConfirmationNumber(itn);
            List<PassengerSegmentDto> passengerSegments = order.getPassengerSegments();
            for (PassengerSegmentDto passengerSegment : passengerSegments) {
                WaitlistPassengerDTO waitlistPassengerDTO = new WaitlistPassengerDTO();
                PassengerInformationDto passengerInformation = passengerSegment.getPassengerInformation();
                waitlistPassengerDTO.setFirstName(passengerInformation.getFirstName());
                waitlistPassengerDTO.setLastName(passengerInformation.getMiddleName());
                waitlistPassengerDTO.setMiddleName(passengerInformation.getMiddleName());
                waitlistPassengerDTO.setDobString(passengerInformation.getDateOfBirth());
                waitlistPassengerDTO.setPriorityCode(saCode);
                passengers.add(waitlistPassengerDTO);
            }
        }

        switch (waitlistRequestDTO.getBookingType()) {
            case "EM":
                // Set SA codes.
                boolean isEmployeeTravelling = isEmployeeTravelling(employeeId, passengers);
                saCode = calculateEmployeeFlightSaCode(isEmployeeTravelling, isVacationUpgrade, isRetired, employeeSACode);
                if (forecast) {
                    for (WaitlistPassengerDTO waitlistPassengerDTO : passengers) {
                        waitlistPassengerDTO.setPriorityCode(saCode);
                    }
                }
                // TODO: Upon business approval, this is where the code would go to upgrade GP's to SA4. We currently handle the case where the GP books after the EM, but this location would handle when a EM booking is made after a GP.
                break;
            case "BP":
                if (forecast) {
                    // Get needed data to calculate if employee is already on the flight.
                    String departDateTime = waitlistRequestDTO.getDepartDate();
                    LocalDate departDate = LocalDate.parse(departDateTime, DATE_FORMATTER);
                    List<ReservationDto> employeeFlights = externalServiceSB.getOrders(departDate, departDate, employeeId, null, "PASSENGER_SEGMENT");
                    // Calculate and set the appropriate SA code based on if the employee is flying on the same flight.
                    boolean isEmployeeOnReservation = isEmployeeOnReservation(employeeFlights, waitlistRequestDTO.getFlightNumber());
                    saCode = calculateGuestPassFlightSaCode(isEmployeeOnReservation);
                    for (WaitlistPassengerDTO passenger : passengers) {
                        passenger.setPriorityCode(saCode);
                    }
                }
                break;
            default:
                break;
        }

        // Convert to FlightManifestDetailWaitlistRequestDto
        FlightManifestDetailWaitlistRequestDto flightManifestDetailWaitlistRequestDto = new FlightManifestDetailWaitlistRequestDto();
        flightManifestDetailWaitlistRequestDto.setEmployeeId(waitlistRequestDTO.getEmployeeId());
        flightManifestDetailWaitlistRequestDto.setFlightNumber(waitlistRequestDTO.getFlightNumber());
        flightManifestDetailWaitlistRequestDto.setDepartDate(waitlistRequestDTO.getDepartDate());
        flightManifestDetailWaitlistRequestDto.setForecast(waitlistRequestDTO.getForecast());
        flightManifestDetailWaitlistRequestDto.setHireDate(waitlistRequestDTO.getHireDate());
        flightManifestDetailWaitlistRequestDto.setLeg(waitlistRequestDTO.getLeg());
        List<PassengerWaitlistDto> passengerWaitlistDtolist = new ArrayList<>();
        for (WaitlistPassengerDTO passDto : passengers) {
            passengerWaitlistDtolist.add(new PassengerWaitlistDto(passDto.getFirstName(), passDto.getMiddleName(), passDto.getLastName(), passDto.getPriorityCode()));
        }
        flightManifestDetailWaitlistRequestDto.setPassengers(passengerWaitlistDtolist);
        FlightManifestDetailWaitlistResponseDto flightManifestDetailWaitlistResponseDto = null;

        Response.Status status = Response.Status.OK;
        try {
            flightManifestDetailWaitlistResponseDto = externalServiceSB.getWaitlist(flightManifestDetailWaitlistRequestDto);
            if (order != null) {
                flightManifestDetailWaitlistResponseDto.setPriorityNumber(getPriorityCode(order, flightManifestDetailWaitlistResponseDto.getWaitList()));
            }
        } catch (Exception e) {
            // If an exception is caught we still want to return to the client. G4TC can still function even tho we cant get a waitlist.
            flightManifestDetailWaitlistResponseDto = null;
            status = Response.Status.NOT_FOUND;
        }
        return Response.status(status).entity(flightManifestDetailWaitlistResponseDto).build();
    }

    private Integer getPriorityCode(ReservationDto orderByConfirmationNumber, List<WaitlistDetailsDto> waitList) throws BusinessException {
        Integer result = null;
        List<PassengerSegmentDto> passengerSegments = orderByConfirmationNumber.getPassengerSegments();
        Collections.sort(waitList, (w1, w2) -> w1.getRanking().compareTo(w2.getRanking()));
        s:
        for (WaitlistDetailsDto waitlistDetailsDto : waitList) {
            for (PassengerSegmentDto passengerSegment : passengerSegments) {
                PassengerInformationDto passengerInformation = passengerSegment.getPassengerInformation();
                // Underlying systems may be more restrictive on length of first, middle, and last name.
                // Because of this, we do a substring compare instead of equality check. DOB will match.
                // The data we are receiving can come from different tables, so depending on the schema sizes, truncation could happen one way or the other, so we must do a contains compare both ways.
                // Lastly, StringUtils.contains("", "") --> true, so using default value of "" helps us get past null issues.
                String waitFirstName = StringUtils.upperCase(StringUtils.defaultString(waitlistDetailsDto.getFirstName(), ""));
                String waitMiddleName = StringUtils.upperCase(StringUtils.defaultString(waitlistDetailsDto.getMiddleName(), ""));
                String waitLastName = StringUtils.upperCase(StringUtils.defaultString(waitlistDetailsDto.getLastName(), ""));
                String passengerFirstName = StringUtils.upperCase(StringUtils.defaultString(passengerInformation.getFirstName(), ""));
                String passengerMiddleName = StringUtils.upperCase(StringUtils.defaultString(passengerInformation.getMiddleName(), ""));
                String passengerLastName = StringUtils.upperCase(StringUtils.defaultString(passengerInformation.getLastName(), ""));
                if (StringUtils.equals(waitlistDetailsDto.getDob(), passengerInformation.getDateOfBirth())
                        && (StringUtils.contains(waitFirstName, passengerFirstName) || StringUtils.contains(passengerFirstName, waitFirstName))
                        && (StringUtils.contains(waitMiddleName, passengerMiddleName) || StringUtils.contains(passengerMiddleName, waitMiddleName))
                        && (StringUtils.contains(waitLastName, passengerLastName) || StringUtils.contains(passengerLastName, waitLastName))
                        ) {
                    result = waitlistDetailsDto.getRanking();
                    break s;
                }
            }
        }
        return result;
    }

    /**
     * Helps calcualte if the employee is on the reservation or not.
     *
     * @param employeeFlights Flights the employee is part of.
     * @param flightNumber    Flight number we are trying to match.
     * @return true if the flight number matches any of the employee flights.
     */
    private boolean isEmployeeOnReservation(List<ReservationDto> employeeFlights, String flightNumber) {
        Boolean match = false;
        s:
        for (ReservationDto empRes : employeeFlights) {
            List<PassengerSegmentDto> empSegments = empRes.getPassengerSegments();
            for (PassengerSegmentDto empSeg : empSegments) {
                if (empSeg.getFlightNumber().equals(flightNumber)) {
                    match = true;
                    break s;
                }
            }
        }
        return match;
    }

    @Override
    public Response startpaymentrefund(@Context HttpServletRequest servletRequest) {
        List<RefundEN> result = refundSB.refundPayments();
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response startpassrefund(@Context HttpServletRequest servletRequest) {
        List<RefundEN> result = refundSB.refundGuestPassesAndVacationUpgrades();
        return Response.status(Response.Status.OK).entity(result).build();
    }

}
