package com.mmj.data.web.webservice;

import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.util.ToJson;
import com.mmj.data.ejb.model.ProfileEN;
import com.mmj.data.ejb.session.ProfileSB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MMJRestWebservice implements MMJRestWebserviceI {
    private static final Logger LOG = LoggerFactory.getLogger(MMJRestWebservice.class);

    @Inject
    private ProfileSB profileSB;

    @Override
    public Response createProfile(ProfileEN profileEN, HttpServletRequest servletRequest) throws BusinessException {
        LOG.info("Saving profile {}.", profileEN);
        ProfileEN result = profileSB.saveNewProfile(profileEN);
        LOG.debug("Result : {}", ToJson.toJson(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
