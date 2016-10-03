package com.mmj.data.web.webservice;

import com.mmj.data.core.LoginDTO;
import com.mmj.data.core.dto.entity.RegisterDTO;
import com.mmj.data.core.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Api(value = "/", description = "MMJ")
@Produces(MediaType.APPLICATION_JSON)
public interface SecRestWebserviceI {

    @POST
    @Path("/register")
    
    @ApiOperation(value = "Register new answer.",
            notes = "{<br>" +
                    "\"email\": \"johndoe@email.com\",<br>" +
                    "\"password1\": \"password\",<br>" +
                    "\"password2\": \"password\"<br>" +
                    "}<br>",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response register(
            @ApiParam(required = true)
            @Valid RegisterDTO registerDTO,
            @Context HttpServletRequest servletRequest,
            @Context HttpServletResponse servletResponse) throws BusinessException;

    @POST
    @Path("/login")
    @ApiOperation(value = "Login.",
            notes = "{<br>" +
                    "\"email\": \"johndoe@email.com\",<br>" +
                    "\"password\": \"password\"<br>" +
                    "}<br>",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response login(
            @ApiParam(required = true)
            @Valid LoginDTO loginDTO,
            @Context HttpServletRequest servletRequest,
            @Context HttpServletResponse servletResponse) throws BusinessException;

    @GET
    @Path("/logout")
    @ApiOperation(value = "Logout.",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad request")}) Response logout(
            @Context HttpServletRequest servletRequest,
            @Context HttpServletResponse servletResponse) throws BusinessException;
}
