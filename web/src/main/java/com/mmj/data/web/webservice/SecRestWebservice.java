package com.mmj.data.web.webservice;

import com.mmj.data.core.LoginDTO;
import com.mmj.data.core.TokenDTO;
import com.mmj.data.core.dto.entity.RegisterDTO;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.NotAuthorizedException;
import com.mmj.data.core.util.TokenStorage;
import com.mmj.data.ejb.session.AnswerSB;
import com.mmj.data.ejb.session.QuestionRangesSB;
import com.mmj.data.ejb.session.QuestionSB;
import com.mmj.data.ejb.session.SecuritySB;
import com.mmj.data.ejb.session.SurveySB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class SecRestWebservice implements SecRestWebserviceI {
    private static final Logger LOG = LoggerFactory.getLogger(SecRestWebservice.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Context
    private SecurityContext securityContext;
    ;

    @Inject
    private SecuritySB securitySB;

    @Inject
    private SurveySB surveySB;

    @Inject
    private QuestionRangesSB questionRangesSB;

    @Inject
    private QuestionSB questionSB;

    @Inject
    private AnswerSB answerSB;

    @Override
    public Response register(RegisterDTO registerDTO, HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws BusinessException {
        LOG.info("Register user {}.", registerDTO.getEmail());
        TokenDTO token = securitySB.register(registerDTO);
        servletResponse.addHeader("Authorization:", "Bearer " + token);
        return Response.status(Response.Status.OK).entity(token).build();
    }

    @Override
    public Response login(LoginDTO loginDTO, HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws BusinessException {
        LOG.info("Login user {}.", loginDTO.getEmail());
        TokenDTO tokenDTO = null;
        try {
            tokenDTO = securitySB.login(loginDTO);
        } catch (NotAuthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("").build();
        }
        //servletResponse.addHeader("Authorization:", "Bearer "  + token);
        return Response.status(Response.Status.OK).entity(tokenDTO).build();
    }

    @Override
    public Response logout(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws BusinessException {
        String authorization = servletRequest.getHeader(AUTHORIZATION_HEADER);
        String token = authorization.replaceFirst("Bearer ", "");
        String userId = servletRequest.getHeader("UserId");
        TokenStorage.removeToken(token);
        return Response.status(Response.Status.OK).build();
    }
}
