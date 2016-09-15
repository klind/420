package com.mmj.data.core.extclient.order;

import com.mmj.data.core.constant.Constants;
import com.mmj.data.core.extclient.dto.BookReservationResponseDto;
import com.mmj.data.core.extclient.dto.CancelOrderResponseDto;
import com.mmj.data.core.extclient.dto.FlightBookingRequestDto;
import com.mmj.data.core.extclient.dto.ModifyOrderRequestDto;
import com.mmj.data.core.extclient.dto.ModifyOrderResponseDto;
import com.mmj.data.core.extclient.dto.ReservationResponseDto;
import com.mmj.data.core.extclient.dto.ReservationsResponseDto;
import org.jboss.resteasy.client.ClientResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Path("/orders")
public interface G4FlightOrderClient {

    @GET
    @Path("/")
    ClientResponse<ReservationsResponseDto> retrieveOrders(
            @QueryParam("searchStartDate") String searchStartDate,
            @QueryParam("searchEndDate") String searchEndDate,
            @QueryParam("employeeNumber") String employeeNumber,
            @QueryParam("bookingType") String bookingType,
            @QueryParam("includes") String includes) ;


    @GET
    @Path("/")
    ClientResponse<String> retrieveOrdersString(
            @QueryParam("searchStartDate") String searchStartDate,
            @QueryParam("searchEndDate") String searchEndDate,
            @QueryParam("employeeNumber") String employeeNumber,
            @QueryParam("bookingType") String bookingType,
            @QueryParam("includes") String includes) ;

    @GET
    @Path("/{confirmationNumber}")
    ClientResponse<ReservationResponseDto> retrieveOrder(
            @PathParam("confirmationNumber") String confirmationNumber) ;

    @GET
    @Path("/segments/unflown")
    ClientResponse<ReservationsResponseDto> retrieveUnflownSegments(
            @QueryParam("searchStartDate")String searchStartDate,
            @QueryParam("searchEndDate") String searchEndDate,
            @QueryParam("bookingType") String bookingType,
            @QueryParam("includes") String includes);

    @POST
    @Path("/")
    ClientResponse<BookReservationResponseDto> saveOrder(FlightBookingRequestDto flightBookingRequestDto,
                                                         @HeaderParam(Constants.HEADER_CHANNEL_ID) Integer channel,
                                                         @HeaderParam(Constants.HEADER_CALLER_KEY) String callerKey,
                                                         @HeaderParam(Constants.HEADER_USER_ID)  String userId) ;

    @PUT
    @Path("/")
    ClientResponse<ModifyOrderResponseDto> modifyOrder(ModifyOrderRequestDto modifyOrderRequestDto,
                                                       @HeaderParam(Constants.HEADER_CHANNEL_ID) Integer channel,
                                                       @HeaderParam(Constants.HEADER_CALLER_KEY) String callerKey,
                                                       @HeaderParam(Constants.HEADER_USER_ID)  String userId) ;

    @PUT
    @Path("/{confirmationNumber}/cancel")
    ClientResponse<CancelOrderResponseDto> cancelOrder(@PathParam("confirmationNumber") String confirmationNumber,
                                                       @QueryParam("cancelReason") String cancelReason,
                                                       @HeaderParam(Constants.HEADER_CHANNEL_ID) Integer channel,
                                                       @HeaderParam(Constants.HEADER_CALLER_KEY) String callerKey,
                                                       @HeaderParam(Constants.HEADER_USER_ID)  String userId) ;

    @PUT
    @Path("/{confirmationNumber}/refund")
    ClientResponse<String> refundOrder(@PathParam("confirmationNumber") String confirmationNumber,
                                                       @HeaderParam(Constants.HEADER_CHANNEL_ID) Integer channel,
                                                       @HeaderParam(Constants.HEADER_CALLER_KEY) String callerKey,
                                                       @HeaderParam(Constants.HEADER_USER_ID)  String userId) throws Exception;
}
