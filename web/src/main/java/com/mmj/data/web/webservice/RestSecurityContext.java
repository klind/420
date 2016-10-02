package com.mmj.data.web.webservice;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

public class RestSecurityContext implements SecurityContext {
    private String userName;
    private Set<String> roles = new HashSet<>();
    private String authenticationScheme;

    public RestSecurityContext(String userName, Set<String> roles, String authenticationScheme) {
        this.userName = userName;
        this.roles = roles;
        this.authenticationScheme = authenticationScheme;
    }

    @Override
    public Principal getUserPrincipal() {
        return new Principal() {

            @Override
            public String getName() {
                return userName;
            }
        };
    }

    @Override
    public boolean isUserInRole(String role) {
        return roles.contains(role);
    }

    @Override
    public boolean isSecure() {
        return "https".equals(this.authenticationScheme);
    }

    @Override
    public String getAuthenticationScheme() {
        return authenticationScheme;
    }
}
