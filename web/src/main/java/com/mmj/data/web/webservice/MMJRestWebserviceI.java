package com.mmj.data.web.webservice;

import com.mmj.data.core.dto.entity.ProfileDTO;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.jboss.resteasy.spi.validation.ValidateRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Interface for Rest Webservices for G4 Travel Center. Used for CRUD operations that help manage employee travel benefits for them and their passengers.
 */
@Api(value = "/", description = "MMJ")
@Produces(MediaType.APPLICATION_JSON)
public interface MMJRestWebserviceI {

    /**
     * Create new employee.
     * This service is only used from pipeline to create a new employee.
     *
     * @return
     * @ Any possible exception thrown in making the request.
     */
    @POST
    @Path("/profiles")
    @ValidateRequest
    @ApiOperation(value = "Create new profile.",
            notes = "{ <br>" +
                    "\"id\":\"100\", <br>" +
                    "\"email\":\"john.doe@mmj.com.\", <br>" +
                    "\"firstName\":\"John\", <br>" +
                    "\"middleName\":null, <br>" +
                    "\"lastName\":\"Doe\", <br>" +
                    "\"dob\":\"1975-01-01\", <br>" +
                    "\"phone\":\"7025555555\", <br>" +
                    "\"gender\":\"M\", <br>" +
                    "\"weight\":\"153\", <br>" +
                    "\"onPrescriptionMeds\":\"1\", <br>" +
                    "\"vegetarian\":\"1\", <br>" +
                    "\"avgOunceMeatDay\":\"25\", <br>" +
                    "\"avgSexWeek\":\"4\", <br>" +
                    "\"avgHoursSweatWeek\":\"6\", <br>" +
                    "\"avgOuncePotThcWeek\":\"15\", <br>" +
                    "\"preferSalivas\":\"1\", <br>" +
                    "\"children\":\"1\", <br>" +
                    "\"geneticItems\":\"0\", <br>" +
                    "\"hotColdNormal\":\"3\", <br>" +
                    "\"useNicotine\":\"1\", <br>" +
                    "\"amountNicotineDay\":\"10\", <br>" +
                    "\"hadMenopause\":\"0\", <br>" +
                    "\"ageRange\":{ <br>" +
                        "\"id\":73 <br>" +
                        "}, <br>" +
                    "\"sleep\":{ <br>" +
                        "\"id\":78 <br>" +
                        "}, <br>" +
                    "\"caffeineDrinks\":{ <br>" +
                        "\"id\":83 <br>" +
                        "}, <br>" +
                    "\"bowelMovement\":{ <br>" +
                        "\"id\":88 <br>" +
                        "}, <br>" +
                    "\"activityLevel\":{ <br>" +
                        "\"id\":93 <br>" +
                        "}, <br>" +
                    "\"bodyfat\":{ <br>" +
                        "\"id\":73 <br>" +
                        "} <br>" +
                    "}<br>",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response createProfile(
            @ApiParam(required = true)
            @Valid ProfileDTO profileDTO,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    @GET
    @Path("/profiles/{id}")
    @ValidateRequest
    @ApiOperation(value = "Get profile information by id.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Employee not found")}) Response getProfileById(
            @ApiParam(value = "Id of the profile", required = true)
            @NotNull
            @PathParam("id")
            Long id,
            @Context HttpServletRequest servletRequest) throws NotFoundException;

    @GET
    @Path("/questionranges")
    @ValidateRequest
    @ApiOperation(value = "Get all score ranges.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getQuestionRanges(
            @Context HttpServletRequest servletRequest);
}
