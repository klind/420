package com.mmj.data.web.webservice;

import com.mmj.data.core.dto.entity.ProfileDTO;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.util.ToJson;
import com.mmj.data.ejb.model2.QuestionRangesEN;
import com.mmj.data.ejb.session.ProfileSB;
import com.mmj.data.ejb.session.QuestionRangesSB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MMJRestWebservice implements MMJRestWebserviceI {
    private static final Logger LOG = LoggerFactory.getLogger(MMJRestWebservice.class);

    @Inject
    private ProfileSB profileSB;

    @Inject
    private QuestionRangesSB scoresSB;

    @Override
    public Response createProfile(ProfileDTO profileDTO, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Saving profile {}.", profileDTO);
        ProfileDTO result = profileSB.saveNewProfile(profileDTO);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getProfileById(Long id, HttpServletRequest servletRequest) throws NotFoundException {
        LOG.info("Looking up prfile with id {}", id);
        ProfileDTO result = profileSB.getProfileById(id);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Override
    public Response getQuestionRanges(@Context HttpServletRequest servletRequest) {
        LOG.info("Getting all scores ranges.");
        List<QuestionRangesEN> result = scoresSB.getQuestionRanges();
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
