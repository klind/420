package com.mmj.data.web.webservice;

import com.mmj.data.core.LoginDTO;
import com.mmj.data.core.dto.entity.AnswerDTO;
import com.mmj.data.core.dto.entity.ProfileDTO;
import com.mmj.data.core.dto.entity.RegisterDTO;
import com.mmj.data.core.dto.entity.SurveyDTO;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
//import org.jboss.resteasy.spi.validation.ValidateRequest;

import javax.annotation.security.RolesAllowed;
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


@Api(value = "/", description = "MMJ")
@Produces(MediaType.APPLICATION_JSON)
public interface MMJRestWebserviceI {


    @POST
    @Path("/profiles")
    @RolesAllowed("admin")
    @ApiOperation(value = "Create new profile.",
            notes = "{ <br>" +
                    "\"email\":\"john.doe@mmj.com\", <br>" +
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
                    "\"amountNicotineDay\":\"0\", <br>" +
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
    @RolesAllowed("admin")
    @Path("/profiles/{id}")
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
    @Path("/profiles/email/{email}")
    
    @ApiOperation(value = "Get profile information by email.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Profile not found")}) Response getProfileByEmail(
            @ApiParam(value = "Email of the profile", required = true)
            @NotNull
            @PathParam("email")
            String email,
            @Context HttpServletRequest servletRequest) throws NotFoundException;

    @GET
    @Path("/profiles")
    @RolesAllowed("admin")
    @ApiOperation(value = "Get profiles.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Profile not found")}) Response getProfiles(
            @Context HttpServletRequest servletRequest) throws NotFoundException;

    @POST
    @Path("/surveys")
    
    @ApiOperation(value = "Create new survey.",
            notes = "{ <br>" +
                    "\"name\":\"Awesome Survey\", <br>" +
                    "}<br>",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response createSurvey(
            @ApiParam(required = true)
            @Valid SurveyDTO surveyDTO,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    @GET
    @Path("/surveys/{id}")
    @ApiOperation(value = "Get survey by id.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Employee not found")}) Response getSurveyById(
            @ApiParam(value = "Id of the profile", required = true)
            @NotNull
            @PathParam("id")
            Long id,
            @Context HttpServletRequest servletRequest) throws NotFoundException;

    @GET
    @Path("/mysurveys")
    @ApiOperation(value = "Get surveys.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Employee not found")}) Response getMySurveys(
            @Context HttpServletRequest servletRequest) throws BusinessException;

    @GET
    @Path("/surveys")
    @ApiOperation(value = "Get surveys.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Employee not found")}) Response getSurveys(
            @Context HttpServletRequest servletRequest);

    @GET
    @Path("/surveys/answers/{surveyId}")
    
    @ApiOperation(value = "Get answers by survey id.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getAnswersBySurveyId(
            @ApiParam(value = "surveyId", required = true)
            @NotNull
            @PathParam("surveyId")
            Long surveyId,
            @Context HttpServletRequest servletRequest) throws NotFoundException;

    @GET
    @Path("/surveys/questions/{surveyId}")
    
    @ApiOperation(value = "Get questions by survey id.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getQuestionsBySurveyId(
            @ApiParam(value = "surveyId", required = true)
            @NotNull
            @PathParam("surveyId")
            Long surveyId,
            @Context HttpServletRequest servletRequest) throws NotFoundException;


    @GET
    @Path("/questions")
    
    @ApiOperation(value = "Get questions.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getQuestions(
            @Context HttpServletRequest servletRequest);


    @GET
    @Path("/questionranges")
    
    @ApiOperation(value = "Get all question ranges ranges.",
            notes = "",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response getQuestionRanges(
            @Context HttpServletRequest servletRequest);

    @POST
    @Path("/answers")
    @ApiOperation(value = "Create new answer.",
            notes = "{<br>" +
                    "\"answer\": \"2:30pm\",<br>" +
                    "\"profileId\": 1,<br>" +
                    "\"surveyId\": 1,<br>" +
                    "\"questionId\": 3<br>" +
                    "}<br>",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response createAnswer(
            @ApiParam(required = true)
            @Valid AnswerDTO answerDTO,
            @Context HttpServletRequest servletRequest) throws BusinessException;

    /*@POST
    @Path("/answers")
    @ApiOperation(value = "Create a list of answers.",
            notes = "{<br>" +
                    "\"answers\":[<br>" +
                    "{<br>" +
                    "\"answer\": \"2:30pm\",<br>" +
                    "\"profileId\": 1,<br>" +
                    "\"surveyId\": 1,<br>" +
                    "\"questionId\": 3<br>" +
                    "},<br>" +
                    "{<br>" +
                    "\"answer\": \"45min\",<br>" +
                    "\"profileId\": 1,<br>" +
                    "\"surveyId\": 1,<br>" +
                    "\"questionId\": 4<br>" +
                    "}<br>" +
                    "]<br>" +
                    "}<br>",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response createAnswers(
            @ApiParam(required = true)
            @Valid AnswerListWrapper answerListWrapper,
            @Context HttpServletRequest servletRequest) throws BusinessException;*/
}
