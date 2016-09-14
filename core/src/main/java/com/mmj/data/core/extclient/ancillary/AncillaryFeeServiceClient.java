package com.mmj.data.core.extclient.ancillary;


import com.mmj.data.core.extclient.dto.AncillaryFeeResponse;
import org.jboss.resteasy.client.ClientResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces({MediaType.APPLICATION_JSON})
public interface AncillaryFeeServiceClient {

    @GET
    @Path("/fees")
    public ClientResponse<AncillaryFeeResponse> getAllAncillaryFees(
            @QueryParam("departAirportCode") String departAirportCode,
            @QueryParam("arriveAirportCode") String arriveAirportCode,
            @QueryParam("departDate") String departDate,
            @QueryParam("channelId") int channelId,
            @QueryParam("bookingDate") String bookingDate);

    @GET
    @Path("/fees/priority-boarding")
    public ClientResponse<AncillaryFeeResponse> getPriorityBoardingFees(
            @QueryParam("departAirportCode") String departAirportCode,
            @QueryParam("arriveAirportCode") String arriveAirportCode,
            @QueryParam("departDate") String departDateString);

    @GET
    @Path("/fees/bag")
    public ClientResponse<AncillaryFeeResponse> getBagFees(
            @QueryParam("departAirportCode") String departAirportCode,
            @QueryParam("arriveAirportCode") String arriveAirportCode,
            @QueryParam("bookingDate") String bookingDateString);

    @GET
    @Path("/fees/ssr")
    public ClientResponse<AncillaryFeeResponse> getSSRFees(
            @QueryParam("channelId") Integer channelId,
            @QueryParam("activeOnly") Boolean activeOnly);
}
