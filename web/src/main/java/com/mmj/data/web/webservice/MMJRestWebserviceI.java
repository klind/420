package com.mmj.data.web.webservice;

import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.ejb.model.ProfileEN;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.jboss.resteasy.spi.validation.ValidateRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Interface for Rest Webservices for G4 Travel Center. Used for CRUD operations that help manage employee travel benefits for them and their passengers.
 */
@Api(value = "/", description = "Travel Center")
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
    @Path("/employees")
    @ValidateRequest
    @ApiOperation(value = "Create new profile.",
            notes = "{\n" +
                    "\"id\":\"100\",\n" +
                    "\"email\":\"john.doe@allegiantair.com.\",\n" +
                    "\"firstName\":\"John\",\n" +
                    "\"middleName\":null,\n" +
                    "\"lastName\":\"Doe\",\n" +
                    "\"dob\":\"1975-01-01\",\n" +
                    "\"phone\":\"7025555555\",\n" +
                    "\"gender\":\"M\",\n" +
                    "\"weight\":\"153\",\n" +
                    "\"onPrescriptionMeds\":\"1\",\n" +
                    "\"vegetarian\":\"1\",\n" +
                    "\"avgOunceMeatDay\":\"25\",\n" +
                    "\"avgSexWeek\":\"4\",\n" +
                    "\"avgHoursSweatWeek\":\"6\",\n" +
                    "\"avgOuncePotThcWeek\":\"15\",\n" +
                    "\"preferSalivas\":\"1\",\n" +
                    "\"children\":\"1\",\n" +
                    "\"geneticItems\":\"0\",\n" +
                    "\"hotColdNormal\":\"3\",\n" +
                    "\"useNicotine\":\"1\",\n" +
                    "\"amountNicotineDay\":\"10\",\n" +
                    "\"hadMenopause\":\"0\"\n" +
                    "}<br>",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response createProfile(
            @ApiParam(required = true)
            @Valid ProfileEN profileEN,
            @Context HttpServletRequest servletRequest) throws BusinessException;
}
