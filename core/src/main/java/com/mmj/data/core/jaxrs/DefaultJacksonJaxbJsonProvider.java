package com.mmj.data.core.jaxrs;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DefaultJacksonJaxbJsonProvider extends JacksonJaxbJsonProvider {
    private static final ObjectMapper DEFAULT_MAPPER = JsonUtils.defaultJacksonObjectMapper();

    public DefaultJacksonJaxbJsonProvider() {
        super.setMapper(DEFAULT_MAPPER);
    }

    public static ObjectMapper getMapper(){
        return DEFAULT_MAPPER;
    }
}
