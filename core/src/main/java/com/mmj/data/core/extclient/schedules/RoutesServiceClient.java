package com.mmj.data.core.extclient.schedules;


import com.mmj.data.core.extclient.dto.RouteListResponseDto;
import org.jboss.resteasy.client.ClientResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/flight")
@Produces({MediaType.APPLICATION_JSON})
public interface RoutesServiceClient {

    @GET
    @Path("/routes")
    public ClientResponse<RouteListResponseDto> getAllRoutes();

    @GET
    @Path("/routes/{airport}")
    public ClientResponse<RouteListResponseDto> getAllRoutes(@PathParam("airport") String airport);
}
