package com.mmj.data.web.webservice;

import com.mmj.data.core.dto.entity.ConfigurationDTO;
import com.mmj.data.core.dto.entity.EmployeePartialDTO;
import com.mmj.data.core.dto.entity.EmployeePatchDTO;
import com.mmj.data.core.dto.entity.GuestDTO;
import com.mmj.data.core.dto.entity.PassengerDTO;
import com.mmj.data.core.dto.entity.SaCodeDTO;
import com.mmj.data.core.dto.entity.SuspensionDTO;
import com.mmj.data.core.dto.ws.WaitlistRequestDTO;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.extclient.dto.FlightBookingRequestDto;
import com.mmj.data.core.extclient.dto.ModifyOrderRequestDto;
import com.mmj.data.web.PATCH;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.jboss.resteasy.spi.validation.ValidateRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.mmj.data.core.constant.Constants.DATE_REGEX;
import static com.mmj.data.core.constant.Constants.DATE_REGEX_ERROR_TXT;
import static com.mmj.data.core.constant.Constants.EMAIL_REGEX;
import static com.mmj.data.core.constant.Constants.SEARCH_EMPLOYEE_ID_ERROR_TXT;
import static com.mmj.data.core.constant.Constants.SEARCH_EMPLOYEE_ID_REGEX;
import static com.mmj.data.core.constant.Constants.VACATIONUPGRADE;
import static com.mmj.data.core.constant.Constants.WILDCARD_SEARCH_REGEX;

/**
 * Interface for Rest Webservices for G4 Travel Center. Used for CRUD operations that help manage employee travel benefits for them and their passengers.
 */
@Api(value = "/", description = "Travel Center")
@Produces(MediaType.APPLICATION_JSON)
public interface G4tcRestWebserviceI {

    /**
     * Gets all configurations of the system.
     *
     * @return List of all configurations in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/configurations")
    @ValidateRequest
    @ApiOperation(value = "Get all configurations.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getConfigurations(
            @Context HttpServletRequest servletRequest);

    /**
     * Get summary of specified year's values and the follow year's values.
     *
     * @return List of all configurations in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/configurationsummary/{fourDigitYear}")
    @ValidateRequest
    @ApiOperation(value = "Get summary of configurations for specified year and its following year.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getConfigurationSummary(
            @ApiParam(value = "Four digit year", required = true)
            @PathParam("fourDigitYear")
            @Pattern(regexp = "[0-9]{4}", message = "Four digit year. e.g. 2016")
            String fourDigitYear,
            @Context HttpServletRequest servletRequest);

    /**
     * Gets list of relationship types in the system.
     *
     * @return List of relationship types in the system in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/relationshiptypes")
    @ValidateRequest
    @ApiOperation(value = "Get list of relationship types.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getRelationshipTypes(
            @Context HttpServletRequest servletRequest);

    /**
     * Gets list of suspension types in the system.
     *
     * @return List of suspension types in the system in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/suspensiontypes")
    @ValidateRequest
    @ApiOperation(value = "Get list of suspension types.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getSuspensionTypes(
            @Context HttpServletRequest servletRequest);

    /**
     * Update configuration by configuration id.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @PUT
    @Path("/configurations")
    @ValidateRequest
    @ApiOperation(value = "Update configuration.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response updateConfiguration(
            @ApiParam(required = true)
            ConfigurationDTO configurationDTO,
            @Context HttpServletRequest servletRequest) throws NotFoundException;

    /**
     * Create configuration.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @POST
    @Path("/configurations")
    @ValidateRequest
    @ApiOperation(value = "Create configuration.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response createConfiguration(
            @ApiParam(required = true)
            ConfigurationDTO configurationDTO,
            @Context HttpServletRequest servletRequest);

    /**
     * Update fields that can be overwrote by travel center admins.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @PATCH
    @Path("/employees")
    @ValidateRequest
    @ApiOperation(value = "Update specific fields.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response patchEmployee(
            @ApiParam(required = true)
            EmployeePatchDTO employeePatchDTO,
            @Context HttpServletRequest servletRequest) throws NotFoundException, BusinessException;

    /**
     * Update an existing employee.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @PUT
    @Path("/employees")
    @ValidateRequest
    @ApiOperation(value = "Update employee's info.",
            notes = "See example at create employee",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response updateEmployee(
            @ApiParam(required = true)
            EmployeePartialDTO employeePartialDTO,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Create new employee.
     * This service is only used from pipeline to create a new employee.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @POST
    @Path("/employees")
    @ValidateRequest
    @ApiOperation(value = "Create new employee.",
            notes = "EmployeePartialDTO : <br> " +
                    "{<br>" +
                    "  \"employeeId\":\"012001\",<br>" +
                    "  \"salutation\":\"Mr.\",<br>" +
                    "  \"suffix\":\"Jr.\",<br>" +
                    "  \"firstName\":\"John\",<br>" +
                    "  \"middleName\":null,<br>" +
                    "  \"lastName\":\"Doe\",<br>" +
                    "  \"gender\":\"M\",<br>" +
                    "  \"email\":\"john.doe@allegiantair.com\",<br>" +
                    "  \"title\":\"Superman\",<br>" +
                    "  \"status\":\"A\",<br>" +
                    "  \"dob\":\"1975-01-01\",<br>" +
                    "  \"lastHireDate\":\"2010-01-01\",<br>" +
                    "  \"termDate\":\"2011-01-01\",<br>" +
                    "  \"phone\":\"7025555555\",<br>" +
                    "  \"jobCode\":\"0900\",<br>" +
                    "  \"location\":\"HQ\",<br>" +
                    "  \"departmentId\":\"686\",<br>" +
                    "  \"departmentName\":\"IT - INFORMATION TECHNOLOGY\",<br>" +
                    "  \"managerId\":\"012088\",<br>" +
                    "  \"employeeType\":\"R\"<br>" +
                    "  }<br>",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response createEmployee(
            @ApiParam(required = true)
            @Valid EmployeePartialDTO employeePartialDTO,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Get employee information by employee id.
     *
     * @param empNumber The employee ID.
     * @return Employee info in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/employees/{employeeId}")
    @ValidateRequest
    @ApiOperation(value = "Get employee information by employee identifier.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Employee not found")}) Response getEmployeeById(
            @ApiParam(value = "Id of the employee", required = true)
            @NotNull
            @PathParam("employeeId")
            @Pattern(regexp = SEARCH_EMPLOYEE_ID_REGEX, message = SEARCH_EMPLOYEE_ID_ERROR_TXT)
            String empNumber,
            @Context HttpServletRequest servletRequest) throws NotFoundException;

    /**
     * Get employee information by logged in user is.
     *
     * @return Employee info in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/employees/profile")
    @ValidateRequest
    @ApiOperation(value = "Get employee information by logged in user id.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Employee not found")}) Response getEmployeeById(
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Get suspension information by suspension id.
     *
     * @param suspensionId The suspension ID.
     * @return Suspension info in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/suspensions/{suspensionId}")
    @ValidateRequest
    @ApiOperation(value = "Get suspension information by suspension identifier.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Employee not found")}) Response getSuspensionById(
            @ApiParam(value = "Id of the suspension", required = true)
            @NotNull
            @PathParam("suspensionId")
            Long suspensionId,
            @Context HttpServletRequest servletRequest) throws NotFoundException;

    /**
     * Search feature that allows you to search for employees by filtering on id, email, firstName and lastName.
     *
     * @param id        Employee ID filter criteria.
     * @param email     Email filter criteria.
     * @param firstName First name filter criteria.
     * @param lastName  Last name filter criteria.
     * @return List of employees that match the search criteria in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/employees")
    @ValidateRequest
    @ApiOperation(value = "Get employee by id, email, firstname, lastname, id.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response searchEmployees(
            @QueryParam("id")
            @Pattern(regexp = SEARCH_EMPLOYEE_ID_REGEX, message = SEARCH_EMPLOYEE_ID_ERROR_TXT)
            @ApiParam(value = "id", required = false)
            String id,
            @QueryParam("email")
            @Pattern(regexp = EMAIL_REGEX, message = "The email must be a valid email")
            @ApiParam(value = "email", required = false)
            String email,
            @QueryParam("firstName")
            @Pattern(regexp = WILDCARD_SEARCH_REGEX, message = "The first name must be a alpha numeric value. Wildcard searches can be performed with a leading or trailing *")
            @ApiParam(value = "firstName", required = false)
            String firstName,
            @QueryParam("lastName")
            @Pattern(regexp = WILDCARD_SEARCH_REGEX, message = "The last name must be a alpha numeric value. Wildcard searches can be performed with a leading or trailing *")
            @ApiParam(value = "lastName", required = false)
            String lastName,
            @ApiParam(value = "Offset must be a positive number", required = true)
            @QueryParam("offset")
            @Min(0)
            @NotNull
            Integer offset,
            @Context HttpServletRequest servletRequest,
            @Context HttpServletResponse servletResponse);

    /**
     * Search feature for passengers that lets you filter by first and last name.
     *
     * @param firstName First name filter criteria.
     * @param lastName  Last name filter criteria
     * @return List of passengers that match search criteria in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/employees/passengers/name")
    @ValidateRequest
    @ApiOperation(value = "Get passenger by name.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getPassengerByName(
            @QueryParam("firstName")
            @Pattern(regexp = "[a-zA-Z0-9\\-'\\. ]*", message = "The first name must be a alpha numeric value")
            @ApiParam(value = "firstName", required = false)
            String firstName,
            @QueryParam("lastName")
            @Pattern(regexp = "[a-zA-Z0-9\\-'\\. ]*", message = "The last name must be a alpha numeric value")
            @ApiParam(value = "lastName", required = false)
            String lastName,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Gets all passengers assigned to current user.
     *
     * @return List of passengers in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/employees/passengers")
    @ValidateRequest
    @ApiOperation(value = "Get passengers by current user.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getPassengersByEmployeeId(
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Gets all passengers assigned to an employee.
     *
     * @param id Id of the employee.
     * @return List of passengers in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/employees/{employeeId}/passengers")
    @ValidateRequest
    @ApiOperation(value = "Get passengers by employee id.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getPassengersByEmployeeId(
            @PathParam("employeeId")
            @NotNull
            @Pattern(regexp = SEARCH_EMPLOYEE_ID_REGEX, message = SEARCH_EMPLOYEE_ID_ERROR_TXT)
            @ApiParam(value = "Employee id", required = true)
            String id,
            @Context HttpServletRequest servletRequest);

    /**
     * Gets all guests assigned to current user.
     *
     * @return List of guests in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/employees/guests")
    @ValidateRequest
    @ApiOperation(value = "Get guests by current user.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getGuestsByEmployeeId(
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Gets all suspensions of an employee.
     *
     * @param id Id of the employee.
     * @return List of suspensions in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/employees/{employeeId}/suspensions")
    @ValidateRequest
    @ApiOperation(value = "Get suspensions by employee id.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getSuspensionsByEmployeeId(
            @PathParam("employeeId")
            @NotNull
            @Pattern(regexp = SEARCH_EMPLOYEE_ID_REGEX, message = SEARCH_EMPLOYEE_ID_ERROR_TXT)
            @ApiParam(value = "Employee id", required = true)
            String id,
            @Context HttpServletRequest servletRequest);

    /**
     * Gets suspension summary of an employee.
     *
     * @param id Id of the employee.
     * @return Suspension summary in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/employees/{employeeId}/suspensionsummary")
    @ValidateRequest
    @ApiOperation(value = "Get suspension summary of the employee.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getSuspensionSummaryByEmployeeId(
            @PathParam("employeeId")
            @NotNull
            @Pattern(regexp = SEARCH_EMPLOYEE_ID_REGEX, message = SEARCH_EMPLOYEE_ID_ERROR_TXT)
            @ApiParam(value = "Employee id", required = true)
            String id,
            @Context HttpServletRequest servletRequest) throws NotFoundException;

    /**
     * Employee pulls up their own suspension summary
     *
     * @return Suspension summary in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/suspensionsummary")
    @ValidateRequest
    @ApiOperation(value = "Logged in employee retrieves their own suspension summary",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getSuspensionSummary(
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Create new passenger for specified employee.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @POST
    @Path("/passengers")
    @ValidateRequest
    @ApiOperation(value = "Create new passenger.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response createPassenger(
            @ApiParam(required = true)
            PassengerDTO passengerDTO,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Create new guest for the logged in user.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @POST
    @Path("/guests")
    @ValidateRequest
    @ApiOperation(value = "Create new guest.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response createGuest(
            @ApiParam(required = true)
            @Valid
            GuestDTO guestDTO,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Create new suspension for specified employee.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @POST
    @Path("/suspensions")
    @ValidateRequest
    @ApiOperation(value = "Create new suspension associated with employee id.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response createSuspension(
            @ApiParam(required = true)
            @Valid SuspensionDTO suspensionDTO,
            @Context HttpServletRequest servletRequest) throws NotFoundException;

    /**
     * Gets passenger by passengerId.
     *
     * @param passengerId Id of the passenger.
     * @return PassengerWaitlistDto details in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/passengers/{passengerId}")
    @ValidateRequest
    @ApiOperation(value = "Get passenger by passenger id.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getPassengerByPassengerId(
            @PathParam("passengerId")
            @NotNull
            @ApiParam(value = "PassengerWaitlistDto id", required = true)
            Long passengerId,
            @Context HttpServletRequest servletRequest);

    /**
     * Update passenger.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @PUT
    @Path("/passengers")
    @ValidateRequest
    @ApiOperation(value = "Update passenger.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response updatePassenger(
            @ApiParam(required = true)
            PassengerDTO passengerDTO,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Update suspension.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @PUT
    @Path("/suspensions")
    @ValidateRequest
    @ApiOperation(value = "Update suspension.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response updateSuspension(
            @ApiParam(required = true)
            SuspensionDTO suspensionDTO,
            @Context HttpServletRequest servletRequest) throws NotFoundException;

    /**
     * Delete passenger by passengerId.
     *
     * @param passengerId Id of the passenger.
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @DELETE
    @Path("/passengers/{passengerId}")
    @ValidateRequest
    @ApiOperation(value = "Delete passenger by passenger id.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response deletePassengerByPassengerId(
            @PathParam("passengerId")
            @NotNull
            @ApiParam(value = "PassengerWaitlistDto id", required = true)
            Long passengerId,
            @Context HttpServletRequest servletRequest) throws NotFoundException;

    /**
     * Gets all sa codes.
     *
     * @return List of all sa codes in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/sacodes")
    @ValidateRequest
    @ApiOperation(value = "Get all sa codes.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getSaCodes(
            @Context HttpServletRequest servletRequest);

    /**
     * Update sa codes by sa code id.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @PUT
    @Path("/sacodes")
    @ValidateRequest
    @ApiOperation(value = "Update SA codes.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response updateSaCode(
            @ApiParam(required = true)
            SaCodeDTO saCodeDTO,
            @Context HttpServletRequest servletRequest) throws NotFoundException;

    /**
     * Gets MyIDTravel info by employeeId.
     *
     * @param employeeId Id of the employee.
     * @return MyIDTravel details in json format.
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/employees/{employeeId}/myidtravel")
    @ValidateRequest
    @ApiOperation(value = "Get my id travel info by employee id.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getMyIDTravelByEmployeeId(
            @ApiParam(value = "Id of the employee", required = true)
            @NotNull
            @PathParam("employeeId")
            @Pattern(regexp = SEARCH_EMPLOYEE_ID_REGEX, message = SEARCH_EMPLOYEE_ID_ERROR_TXT)
            String employeeId,
            @Context HttpServletRequest servletRequest) throws NotFoundException;

    /**
     * Returns a json string representing the guest pass bookings made for the specified employee
     *
     * @param id
     * @param servletRequest
     * @return String
     * @
     */
    @GET
    @Path("/employees/{employeeId}/guestpassbookings")
    @ValidateRequest
    @ApiOperation(value = "Get guest pass bookings by employee id.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getGuestPassBookingsByEmployeeId(
            @PathParam("employeeId")
            @NotNull
            @Pattern(regexp = SEARCH_EMPLOYEE_ID_REGEX, message = SEARCH_EMPLOYEE_ID_ERROR_TXT)
            @ApiParam(value = "Employee id", required = true)
            String id,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Create new employee.
     * This service is only used from pipeline to load initial all employees.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @POST
    @Path("/loademployees")
    @ValidateRequest
    @ApiOperation(value = "Load new employee. Use only with initial load from pipeline",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response loadEmployee(
            @ApiParam(required = true)
            @Valid EmployeePartialDTO employeePartialDTO,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Return all the routes.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/routes")
    @ApiOperation(value = "Get routes.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getRoutes(
            @Context HttpServletRequest servletRequest);

    /**
     * Return all the routes.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/health")
    @ValidateRequest
    @ApiOperation(value = "Get health check related information.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getHealth();

    /**
     * Returns the SSR codes
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/ssrs")
    @ValidateRequest
    @ApiOperation(value = "Get SSR's",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getSSRs(
            @Context HttpServletRequest servletRequest);

    /**
     * Returns the bag fees
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/bagfees")
    @ValidateRequest
    @ApiOperation(value = "Get bag fees",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getBagFees(
            @QueryParam("departureAirportCode")
            @ApiParam(value = "departureAirportCode", required = true)
            String departAirportCode,
            @QueryParam("arriveAirportCode")
            @ApiParam(value = "arriveAirportCode", required = true)
            String arriveAirportCode,
            @QueryParam("bookingDate")
            @ApiParam(value = "bookingDate", required = true)
            String bookingDate,
            @Context HttpServletRequest servletRequest);

    /**
     * Get available flight dates
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/flights/dates")
    @ValidateRequest
    @ApiOperation(value = "Get available flight dates",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getAvailableFlightDates(
            @QueryParam("departAirportCode")
            @ApiParam(value = "departAirportCode", required = true)
            String departAirportCode,
            @QueryParam("arriveAirportCode")
            @ApiParam(value = "arriveAirportCode", required = true)
            String arriveAirportCode,
            @QueryParam("channelId")
            @ApiParam(value = "channelId", required = true)
            Integer channelId,
            @Context HttpServletRequest servletRequest);

    /**
     * Get available flight dates and fare details
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/flights/dates/fares/details")
    @ValidateRequest
    @ApiOperation(value = "Get available flight dates and fare details",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getAvailableFlightDatesAndFareDetails(
            @QueryParam("departAirportCode")
            @ApiParam(value = "departAirportCode", required = true)
            String departAirportCode,
            @QueryParam("arriveAirportCode")
            @ApiParam(value = "arriveAirportCode", required = true)
            String arriveAirportCode,
            @QueryParam("searchStartDate")
            @Pattern(regexp = DATE_REGEX, message = DATE_REGEX_ERROR_TXT)
            @ApiParam(value = "searchStartDate", required = true)
            String searchStartDate,
            @QueryParam("searchEndDate")
            @Pattern(regexp = DATE_REGEX, message = DATE_REGEX_ERROR_TXT)
            @ApiParam(value = "searchEndDate", required = true)
            String searchEndDate,
            @QueryParam("bookingType")
            @ApiParam(value = "bookingType", required = true)
            String bookingType,
            @QueryParam("channelId")
            @ApiParam(value = "channelId", required = true)
            Integer channelId,
            @QueryParam("modifyRequest")
            @ApiParam(value = "modifyRequest", required = false)
            Boolean modifyRequest,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Get shop flights
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/flights")
    @ValidateRequest
    @ApiOperation(value = "Get shop flights",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getShopFlights(
            @QueryParam("departAirportCode")
            @ApiParam(value = "departAirportCode", required = true)
            String departAirportCode,
            @QueryParam("arriveAirportCode")
            @ApiParam(value = "arriveAirportCode", required = true)
            String arriveAirportCode,
            @QueryParam("searchStartDate")
            @Pattern(regexp = DATE_REGEX, message = DATE_REGEX_ERROR_TXT)
            @ApiParam(value = "searchStartDate", required = true)
            String searchStartDate,
            @QueryParam("searchEndDate")
            @Pattern(regexp = DATE_REGEX, message = DATE_REGEX_ERROR_TXT)
            @ApiParam(value = "searchEndDate", required = true)
            String searchEndDate,
            @QueryParam("channelId")
            @ApiParam(value = "channelId", required = true)
            Integer channelId,
            @QueryParam("passengerCount")
            @ApiParam(value = "passengerCount", required = true)
            Integer passengerCount,
            @QueryParam("fareClasses")
            @ApiParam(value = "fareClasses", required = false)
            List<String> fareClasses,
            @QueryParam("bookingDate")
            @Pattern(regexp = DATE_REGEX, message = DATE_REGEX_ERROR_TXT)
            @ApiParam(value = "bookingDate", required = false)
            String bookingDate,
            @QueryParam("bookingType")
            @ApiParam(value = "bookingType", required = true)
            String bookingType,
            @QueryParam("originalPrice")
            @ApiParam(value = "originalPrice", required = false)
            Double originalPrice,
            @QueryParam("overrideReasonId")
            @ApiParam(value = "overrideReasonId", required = false)
            Long overrideReasonId,
            @QueryParam("adjustedPrice")
            @ApiParam(value = "adjustedPrice", required = false)
            Double adjustedPrice,
            @QueryParam("originatingAirline")
            @ApiParam(value = "originatingAirline", required = false)
            String originatingAirline,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Get shop flight by flight number
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/flights/flight")
    @ValidateRequest
    @ApiOperation(value = "Get shop flight by number",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getShopFlight(
            @QueryParam("itn")
            @ApiParam(value = "itn", required = true)
            String itn,
            @QueryParam("flightNum")
            @ApiParam(value = "flightNum", required = true)
            String flightNum,
            @QueryParam("departDate")
            @Pattern(regexp = DATE_REGEX, message = DATE_REGEX_ERROR_TXT)
            @ApiParam(value = "departDate", required = true)
            String departDate,
            @QueryParam("channelId")
            @ApiParam(value = "channelId", required = true)
            Integer channelId,
            @QueryParam("passengerCount")
            @ApiParam(value = "passengerCount", required = true)
            Integer passengerCount,
            @QueryParam("fareClass")
            @ApiParam(value = "fareClass", required = false)
            String fareClass,
            @QueryParam("bookingDate")
            @Pattern(regexp = DATE_REGEX, message = DATE_REGEX_ERROR_TXT)
            @ApiParam(value = "bookingDate", required = false)
            String bookingDate,
            @QueryParam("bookingType")
            @ApiParam(value = "bookingType", required = true)
            String bookingType,
            @QueryParam("originatingAirline")
            @ApiParam(value = "originatingAirline", required = false)
            String originatingAirline,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Return all Orders based on supplied query params.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/orders")
    @ApiOperation(value = "Get orders based on query params.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getOrders(
            @QueryParam("searchStartDate")
            @ApiParam(value = "Search start date", required = true)
            String searchStartDate,
            @QueryParam("searchEndDate")
            @ApiParam(value = "Search end date", required = true)
            String searchEndDate,
            @QueryParam("bookingType")
            @ApiParam(value = "Booking type", required = false)
            String bookingType,
            @QueryParam("includes")
            @ApiParam(value = "includes", required = false)
            String includes,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Return an Order by the confirmation number.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/orders/{confirmationNumber}")
    @ApiOperation(value = "Get order by confirmation number.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getOrderByConfirmationNumber(
            @PathParam("confirmationNumber")
            @NotNull
            @ApiParam(value = "Confirmation Number", required = true)
            String confirmationNumber,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Create an order.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @POST
    @Path("/orders")
    @ValidateRequest
    @ApiOperation(value = "Create order.",
            notes = "<pre>FlightManifestDetailWaitlistRequestDto employee booking :\n{\n" +
                    "  \"market\" : \"BILLAS\",\n" +
                    "  \"bookedBy\" : \"010339\",\n" +
                    "  \"customerNumber\" : \"010339\",\n" +
                    "  \"bookingType\" : \"EM\",\n" +
                    "  \"convenienceFeeTotal\" : 0,\n" +
                    "  \"primaryEmail\" : \"nesbert.hidalgo@allegiantair.com\",\n" +
                    "  \"fax\" : \"\",\n" +
                    "  \"comments\" : \"\",\n" +
                    "  \"agent\" : \"\",\n" +
                    "  \"groupName\" : \"\",\n" +
                    "  \"travelProtection\" : \"\",\n" +
                    "  \"travelProtectionTotal\" : 0,\n" +
                    "  \"childCount\" : 0,\n" +
                    "  \"itineraryTotal\" : 0,\n" +
                    "  \"phone1Type\" : \"\",\n" +
                    "  \"phone1\" : \"\",\n" +
                    "  \"phone2Type\" : \"\",\n" +
                    "  \"phone2\" : \"\",\n" +
                    "  \"segments\" : [ {\n" +
                    "    \"shopDto\" : {\n" +
                    "      \"segment\" : {\n" +
                    "        \"airlineCode\" : \"G4\",\n" +
                    "        \"fltNum\" : \"548\",\n" +
                    "        \"equipment\" : {\n" +
                    "          \"make\" : \"McDonnell Douglas\",\n" +
                    "          \"model\" : \"M86\",\n" +
                    "          \"tailNbr\" : null\n" +
                    "        },\n" +
                    "        \"flightStatus\" : \"\",\n" +
                    "        \"closeFlag\" : \"\",\n" +
                    "        \"legs\" : [ {\n" +
                    "          \"departAirport\" : \"LAS\",\n" +
                    "          \"arriveAirport\" : \"BIL\",\n" +
                    "          \"scheduledDepartDateTime\" : 1466810040000,\n" +
                    "          \"estimatedDepartDateTime\" : null,\n" +
                    "          \"actualDepartDateTime\" : null,\n" +
                    "          \"scheduledArriveDateTime\" : 1466820720000,\n" +
                    "          \"estimatedArriveDateTime\" : null,\n" +
                    "          \"actualArriveDateTime\" : null,\n" +
                    "          \"gmtScheduledDepartDateTime\" : 1466835240000,\n" +
                    "          \"gmtEstimatedDepartDateTime\" : null,\n" +
                    "          \"gmtActualDepartDateTime\" : null,\n" +
                    "          \"gmtScheduledArriveDateTime\" : 1466842320000,\n" +
                    "          \"gmtEstimatedArriveDateTime\" : null,\n" +
                    "          \"gmtActualArriveDateTime\" : null,\n" +
                    "          \"routeMiles\" : 754,\n" +
                    "          \"divertStatus\" : null,\n" +
                    "          \"actualDepartAirport\" : \"LAS\",\n" +
                    "          \"actualArriveAirport\" : \"BIL\",\n" +
                    "          \"gmtActualOffUTC\" : null,\n" +
                    "          \"gmtActualOnUTC\" : null,\n" +
                    "          \"leg\" : \"1\"\n" +
                    "        } ],\n" +
                    "        \"firstLeg\" : {\n" +
                    "          \"departAirport\" : \"LAS\",\n" +
                    "          \"arriveAirport\" : \"BIL\",\n" +
                    "          \"scheduledDepartDateTime\" : 1466810040000,\n" +
                    "          \"estimatedDepartDateTime\" : null,\n" +
                    "          \"actualDepartDateTime\" : null,\n" +
                    "          \"scheduledArriveDateTime\" : 1466820720000,\n" +
                    "          \"estimatedArriveDateTime\" : null,\n" +
                    "          \"actualArriveDateTime\" : null,\n" +
                    "          \"gmtScheduledDepartDateTime\" : 1466835240000,\n" +
                    "          \"gmtEstimatedDepartDateTime\" : null,\n" +
                    "          \"gmtActualDepartDateTime\" : null,\n" +
                    "          \"gmtScheduledArriveDateTime\" : 1466842320000,\n" +
                    "          \"gmtEstimatedArriveDateTime\" : null,\n" +
                    "          \"gmtActualArriveDateTime\" : null,\n" +
                    "          \"routeMiles\" : 754,\n" +
                    "          \"divertStatus\" : null,\n" +
                    "          \"actualDepartAirport\" : \"LAS\",\n" +
                    "          \"actualArriveAirport\" : \"BIL\",\n" +
                    "          \"gmtActualOffUTC\" : null,\n" +
                    "          \"gmtActualOnUTC\" : null,\n" +
                    "          \"leg\" : \"1\"\n" +
                    "        }\n" +
                    "      },\n" +
                    "      \"fareAndAvail\" : {\n" +
                    "        \"fare\" : {\n" +
                    "          \"fareClassCode\" : \"Y\",\n" +
                    "          \"baseFare\" : 0.0,\n" +
                    "          \"fee\" : [ ]\n" +
                    "        },\n" +
                    "        \"fareClassInventory\" : {\n" +
                    "          \"fareclassCode\" : \"Y\",\n" +
                    "          \"numberAvail\" : 159,\n" +
                    "          \"totalFlightSeats\" : 166,\n" +
                    "          \"waitListedCount\" : 0\n" +
                    "        }\n" +
                    "      }\n" +
                    "    },\n" +
                    "    \"flightPassengers\" : [ {\n" +
                    "      \"firstName\" : \"Nesbert\",\n" +
                    "      \"middleName\" : \"Maralit\",\n" +
                    "      \"lastName\" : \"Hidalgo\",\n" +
                    "      \"dateOfBirth\" : \"1979-03-06\",\n" +
                    "      \"gender\" : \"M\",\n" +
                    "      \"seat\" : \"\",\n" +
                    "      \"seatFee\" : 0,\n" +
                    "      \"bags\" : [ ],\n" +
                    "      \"ssrs\" : [ ],\n" +
                    "      \"priorityBoarding\" : null,\n" +
                    "      \"employeeInformation\": { \n" +
                    "           \"saCode\" : \"SA3\", \n" +
                    "           \"hireDate\" : \"2015-07-01\", \n" +
                    "           \"employeeId\" : \"012088\" \n" +
                    "      },\n" +
                    "      \"passengerIdentification\" : null,\n" +
                    "      \"infantTravelerDtos\" : [ ]\n" +
                    "    } ]\n" +
                    "  }, {\n" +
                    "    \"shopDto\" : {\n" +
                    "      \"segment\" : {\n" +
                    "        \"airlineCode\" : \"G4\",\n" +
                    "        \"fltNum\" : \"549\",\n" +
                    "        \"equipment\" : {\n" +
                    "          \"make\" : \"McDonnell Douglas\",\n" +
                    "          \"model\" : \"M86\",\n" +
                    "          \"tailNbr\" : null\n" +
                    "        },\n" +
                    "        \"flightStatus\" : \"\",\n" +
                    "        \"closeFlag\" : \"\",\n" +
                    "        \"legs\" : [ {\n" +
                    "          \"departAirport\" : \"BIL\",\n" +
                    "          \"arriveAirport\" : \"LAS\",\n" +
                    "          \"scheduledDepartDateTime\" : 1467059820000,\n" +
                    "          \"estimatedDepartDateTime\" : null,\n" +
                    "          \"actualDepartDateTime\" : null,\n" +
                    "          \"scheduledArriveDateTime\" : 1467063660000,\n" +
                    "          \"estimatedArriveDateTime\" : null,\n" +
                    "          \"actualArriveDateTime\" : null,\n" +
                    "          \"gmtScheduledDepartDateTime\" : 1467081420000,\n" +
                    "          \"gmtEstimatedDepartDateTime\" : null,\n" +
                    "          \"gmtActualDepartDateTime\" : null,\n" +
                    "          \"gmtScheduledArriveDateTime\" : 1467088860000,\n" +
                    "          \"gmtEstimatedArriveDateTime\" : null,\n" +
                    "          \"gmtActualArriveDateTime\" : null,\n" +
                    "          \"routeMiles\" : 754,\n" +
                    "          \"divertStatus\" : null,\n" +
                    "          \"actualDepartAirport\" : \"BIL\",\n" +
                    "          \"actualArriveAirport\" : \"LAS\",\n" +
                    "          \"gmtActualOffUTC\" : null,\n" +
                    "          \"gmtActualOnUTC\" : null,\n" +
                    "          \"leg\" : \"1\"\n" +
                    "        } ],\n" +
                    "        \"firstLeg\" : {\n" +
                    "          \"departAirport\" : \"BIL\",\n" +
                    "          \"arriveAirport\" : \"LAS\",\n" +
                    "          \"scheduledDepartDateTime\" : 1467059820000,\n" +
                    "          \"estimatedDepartDateTime\" : null,\n" +
                    "          \"actualDepartDateTime\" : null,\n" +
                    "          \"scheduledArriveDateTime\" : 1467063660000,\n" +
                    "          \"estimatedArriveDateTime\" : null,\n" +
                    "          \"actualArriveDateTime\" : null,\n" +
                    "          \"gmtScheduledDepartDateTime\" : 1467081420000,\n" +
                    "          \"gmtEstimatedDepartDateTime\" : null,\n" +
                    "          \"gmtActualDepartDateTime\" : null,\n" +
                    "          \"gmtScheduledArriveDateTime\" : 1467088860000,\n" +
                    "          \"gmtEstimatedArriveDateTime\" : null,\n" +
                    "          \"gmtActualArriveDateTime\" : null,\n" +
                    "          \"routeMiles\" : 754,\n" +
                    "          \"divertStatus\" : null,\n" +
                    "          \"actualDepartAirport\" : \"BIL\",\n" +
                    "          \"actualArriveAirport\" : \"LAS\",\n" +
                    "          \"gmtActualOffUTC\" : null,\n" +
                    "          \"gmtActualOnUTC\" : null,\n" +
                    "          \"leg\" : \"1\"\n" +
                    "        }\n" +
                    "      },\n" +
                    "      \"fareAndAvail\" : {\n" +
                    "        \"fare\" : {\n" +
                    "          \"fareClassCode\" : \"Y\",\n" +
                    "          \"baseFare\" : 0.0,\n" +
                    "          \"fee\" : [ ]\n" +
                    "        },\n" +
                    "        \"fareClassInventory\" : {\n" +
                    "          \"fareclassCode\" : \"Y\",\n" +
                    "          \"numberAvail\" : 159,\n" +
                    "          \"totalFlightSeats\" : 166,\n" +
                    "          \"waitListedCount\" : 0\n" +
                    "        }\n" +
                    "      }\n" +
                    "    },\n" +
                    "    \"flightPassengers\" : [ {\n" +
                    "      \"firstName\" : \"Nesbert\",\n" +
                    "      \"middleName\" : \"Maralit\",\n" +
                    "      \"lastName\" : \"Hidalgo\",\n" +
                    "      \"dateOfBirth\" : \"1979-03-06\",\n" +
                    "      \"gender\" : \"M\",\n" +
                    "      \"seat\" : \"\",\n" +
                    "      \"seatFee\" : 0,\n" +
                    "      \"bags\" : [ ],\n" +
                    "      \"ssrs\" : [ ],\n" +
                    "      \"priorityBoarding\" : null,\n" +
                    "      \"employeeInformation\": { \n" +
                    "           \"saCode\" : \"SA3\", \n" +
                    "           \"hireDate\" : \"2015-07-01\", \n" +
                    "           \"employeeId\" : \"012088\" \n" +
                    "      },\n" +
                    "      \"passengerIdentification\" : null,\n" +
                    "      \"infantTravelerDtos\" : [ ]\n" +
                    "    } ]\n" +
                    "  } ],\n" +
                    "  \"payments\" : [ ]\n" +
                    "}</pre>",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized")}) Response createOrder(
            @ApiParam(required = true)
            FlightBookingRequestDto flightBookingRequestDto,
            @QueryParam("vacationupgrade")
            @Pattern(regexp = VACATIONUPGRADE, message = VACATIONUPGRADE)
            @ApiParam(value = "vacationupgrade", required = false, defaultValue = "false")
            String vacationupgrade,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Update an order.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @PUT
    @Path("/orders")
    @ValidateRequest
    @ApiOperation(value = "Update order.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized")}) Response updateOrder(
            @ApiParam(required = true)
            ModifyOrderRequestDto modifyRequestDto,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Cancel an order.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @PUT
    @Path("/orders/{confirmationNumber}/cancel")
    @ValidateRequest
    @ApiOperation(value = "Cancel order.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response cancelOrder(
            @PathParam("confirmationNumber")
            @ApiParam(value = "Confirmation Number", required = true)
            String confirmationNumber,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     * Returns a list of payment types.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @GET
    @Path("/paymenttypes")
    @ValidateRequest
    @ApiOperation(value = "Get payment types.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 401, message = "Unauthorized")}) Response getPaymentTypes(
            @Context HttpServletRequest servletRequest);

    /**
     * Updates the system configuration.
     */
    @PUT
    @Path("/systemconfiguration")
    @ValidateRequest
    @ApiOperation(value = "Updates the cache with system configuration from database",
            response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response updateSystemConfiguration();

    /**
     * Updates the access configuration.
     */
    @PUT
    @Path("/accessconfiguration")
    @ValidateRequest
    @ApiOperation(value = "Updates the cache with access configuration from database",
            response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response updateAccessConfiguration();

    /**
     * Retrieves the active system configurations.
     */
    @GET
    @Path("/systemconfigurations")
    @ValidateRequest
    @ApiOperation(value = "Retrieves the active system configurations",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getSystemConfigurations(
            @Context HttpServletRequest servletRequest);

    /**
     * Retrieves the access configurations.
     */
    @GET
    @Path("/accessconfigurations")
    @ValidateRequest
    @ApiOperation(value = "Retrieves the access configurations",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getAccessConfigurations(
            @Context HttpServletRequest servletRequest);

    /**
     *
     */
    @POST
    @Path("/waitlist")
    @ValidateRequest
    @ApiOperation(value = "returns the waitlist",
            response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getWaitlist(
            @ApiParam(value = "Request", required = true)
            WaitlistRequestDTO waitlistRequestDTO,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /**
     *
     */
    @GET
    @Path("/startpaymentrefund")
    @ApiOperation(value = "starts the payment refund job for testing",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response startpaymentrefund(
            @Context HttpServletRequest servletRequest);

    /**
     *
     */
    @GET
    @Path("/startpassrefund")
    @ApiOperation(value = "starts the pass refund job for testing",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response startpassrefund(
            @Context HttpServletRequest servletRequest);


}
