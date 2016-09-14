package com.mmj.data.core.extclient.shop;

import com.mmj.data.core.extclient.dto.ShopResponseDto;
import org.jboss.resteasy.client.ClientResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;

@Path("/flights")
@Produces({MediaType.APPLICATION_JSON})
public interface ShopServiceClient {

    @GET
    @Path("/shop")
    ClientResponse<ShopResponseDto> shopFlights(
            @QueryParam("departAirportCode") String departAirportCode,
            @QueryParam("arriveAirportCode") String arriveAirportCode,
            @QueryParam("searchStartDate") String searchStartDateStr,
            @QueryParam("searchEndDate") String searchEndDateStr,
            @QueryParam("channelId") int channelId,
            @QueryParam("passengerCount") int passengerCount,
            @QueryParam("fareClasses") List<String> fareClasses,
            @QueryParam("bookingDate") String bookingDateStr,
            @QueryParam("bookingType") String bookingType,
            @QueryParam("originalPrice") BigDecimal originalPrice,
            @QueryParam("overrideReasonId") Long overrideReasonId,
            @QueryParam("adjustedPrice") BigDecimal adjustedPrice,
            @QueryParam("originatingAirline") String originatingAirline);

    @GET
    @Path("/shop-flight")
    ClientResponse<ShopResponseDto> shopByFlightNum(
            @QueryParam("flightNum") String flightNum,
            @QueryParam("departDate") String departDateStr,
            @QueryParam("channelId") int channelId,
            @QueryParam("passengerCount") int passengerCount,
            @QueryParam("fareClass") String fareClass,
            @QueryParam("bookingDate") String bookingDateStr,
            @QueryParam("bookingType") String bookingType,
            @QueryParam("originatingAirline") String originatingAirline);

    @GET
    @Path("/dates/avail")
    ClientResponse<ShopResponseDto> getAvailableDatesByMarketAndDateRange(
            @QueryParam("departAirportCode") String departAirportCode,
            @QueryParam("arriveAirportCode") String arriveAirportCode,
            @QueryParam("channelId") int channelId);

    @GET
    @Path("/dates/avail/detail")
    ClientResponse<ShopResponseDto> getAvailableDatesDetailsByMarketAndDateRange(
            @QueryParam("departAirportCode") String departAirportCode,
            @QueryParam("arriveAirportCode") String arriveAirportCode,
            @QueryParam("searchStartDate") String searchStartDateString,
            @QueryParam("searchEndDate") String searchEndDateString,
            @QueryParam("bookingType") String bookingType,
            @QueryParam("channelId") int channelId,
            @QueryParam("modifyRequest") boolean isModify);
}
