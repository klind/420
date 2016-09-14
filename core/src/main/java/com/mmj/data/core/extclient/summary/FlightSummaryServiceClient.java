package com.mmj.data.core.extclient.summary;

import com.mmj.data.core.extclient.dto.summary.FlightManifestDetailWaitlistRequestDto;
import com.mmj.data.core.extclient.dto.summary.FlightManifestDetailWaitlistResponseDto;
import org.jboss.resteasy.client.ClientResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/flights")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public interface FlightSummaryServiceClient {

    @POST
    @Path("/manifest/waitlist") ClientResponse<FlightManifestDetailWaitlistResponseDto> flightManifestDetailWaitlist(FlightManifestDetailWaitlistRequestDto flightManifestDetailWaitlistRequestDto) throws Exception;
}
