package com.mmj.data.web.webservice;

import com.mmj.data.core.util.TokenStorage;
import com.mmj.data.ejb.session.ProfileSB;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.time.LocalTime;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityRequestFilter implements ContainerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Inject
    private ProfileSB profileSB;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        switch (path) {
            case "/login":

                break;

            case "/register":

                break;

            default:
                validateToken(requestContext);
                break;
        }
    }

    private void validateToken(ContainerRequestContext requestContext) {
        String authorization = requestContext.getHeaderString(AUTHORIZATION_HEADER);
        if (StringUtils.isBlank(authorization) || !authorization.contains("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        } else {
            String token = authorization.replaceFirst("Bearer ", "");
            if (!TokenStorage.isTokenValid(token)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }
    }

    private void login(ContainerRequestContext requestContext) {

        /*Response response = null;
        boolean ok = false;
        String authorization = requestContext.getHeaderString(AUTHORIZATION_HEADER);
        String scheme = requestContext.getUriInfo().getRequestUri().getScheme();
        if (StringUtils.isBlank(authorization)) {
            response = Response.status(Response.Status.UNAUTHORIZED).entity("").build();
        } else {
            authorization = authorization.replaceFirst("Basic ", "");
            String decoded = Base64.getDecoder().decode(authorization).toString();
            StringTokenizer tokenizer = new StringTokenizer(decoded, ":");
            String email = tokenizer.nextToken();
            String password = tokenizer.nextToken();
            try {
                ProfilePasswordDTO profilePasswordDTO = profileSB.getProfilPasswordByEmail(email);
                if (SCryptUtil.check(password, profilePasswordDTO.getHashedPassword())) {
                    ok = true;
                    response = Response.status(Response.Status.OK).entity("OK").build();
                }
                RestSecurityContext restSecurityContext = new RestSecurityContext(profilePasswordDTO.getEmail(), profilePasswordDTO.getRoles(), scheme);
                requestContext.setSecurityContext(restSecurityContext);
            } catch (NotFoundException e) {
                response = Response.status(Response.Status.UNAUTHORIZED).entity("").build();
            }
        }
        if (!ok) {
            requestContext.abortWith(response);
        }*/
    }
}
