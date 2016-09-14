package com.mmj.data.core.extclient.lookup;


import com.mmj.data.core.extclient.dto.lookup.PaymentTypeDTO;
import org.jboss.resteasy.client.ClientResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/paymentTypes")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface PaymentTypeClient {

    @GET
    @Path("/") ClientResponse<List<PaymentTypeDTO>> getAllPaymentTypes();

    @GET
    @Path("/{idx}") ClientResponse<PaymentTypeDTO> getPaymentType(@PathParam("idx") String idx);

    @POST
    @Path("/") ClientResponse<PaymentTypeDTO> addPaymentType(PaymentTypeDTO paymentType,
                                                             String callerKey,
                                                             String userId);

    @DELETE
    @Path("/{idx}") ClientResponse  deletePaymentType(@PathParam("idx") String idx);

    @PUT
    @Path("/{idx}") ClientResponse  updatePaymentType(@PathParam("idx") String idx, PaymentTypeDTO paymentType,
                                                      String callerKey,
                                                      String userId);
}
