package com.mmj.data.web.integration.checks;

import com.mmj.data.core.exception.SystemException;
import com.mmj.data.core.extclient.dto.AncillaryFeeDto;
import com.mmj.data.core.extclient.dto.ReservationDto;
import com.mmj.data.core.extclient.dto.RouteDto;
import com.mmj.data.core.flights.FlightsService;
import com.mmj.data.core.util.ToJson;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

//import org.junit.Assert;

/**
 * Json Schema Checks that help ensure the contract on our depending webservices don't change.
 */
public class FlightServiceChecks {

    private static final Logger LOG = LoggerFactory.getLogger(FlightServiceChecks.class);

    @Inject
    private FlightsService flightsService;

    // Reservation Flight.
    public String getReservationFlightJson(){
        List<ReservationDto> reservations = null;
        try {
            reservations = flightsService.getGuestPassBookingsByEmployeeId("012649", LocalDate.of(2016, Month.JANUARY, 1), LocalDate.of(2016, Month.JANUARY, 30), null);    // TODO: Need legit search criteria.
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return ToJson.toJson(reservations);
    }
    public boolean reservationFlightCheck() {
        String responseAsJson = getReservationFlightJson();
        String jsonSchemaAsString = getJsonSchemaFromFile("Reservation.schema.json");
        return G4JsonValidator.isValid(responseAsJson, jsonSchemaAsString);
    }

    /**
     * Reads the json schema from a file into a string.
     *
     * @param filePath e.g. resource file path in war file.
     * @return The string that contains the json schema.
     */
    private String getJsonSchemaFromFile(String filePath) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String jsonSchemaAsString = "";
        try {
            jsonSchemaAsString = IOUtils.toString(reader);
        } catch (IOException e) {
            LOG.warn("Could not get file at path {}", filePath, e);
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonSchemaAsString;
    }

    // Routes
    public String getRoutesJson() {
        List<RouteDto> routes = null;
        try {
            routes = flightsService.getRoutes();
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return ToJson.toJson(routes);
    }
    public boolean routesCheck() {
        String responseAsJson = getRoutesJson();
        String jsonSchemaAsString = getJsonSchemaFromFile("Routes.schema.json");
        return G4JsonValidator.isValid(responseAsJson, jsonSchemaAsString);
    }

    // SSRs
    public String getSSRsJson() {
        List<AncillaryFeeDto> ssrs = null;
        try {
            ssrs = flightsService.getSSRs();
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return ToJson.toJson(ssrs);

    }
    public boolean ssrsCheck() {
        String responseAsJson = getSSRsJson();
        String jsonSchemaAsString = getJsonSchemaFromFile("AncillaryFee.schema.json");
        return G4JsonValidator.isValid(responseAsJson, jsonSchemaAsString);
    }

    // Order By Confirmation Number
    public String getOrderByConfirmationNumberJson() {
        ReservationDto reservation = null;
        try {
            reservation = flightsService.getOrderByConfirmationNumber("000001");   // TODO: Need legit confirmation number. Will need to figure out details of these checks after WSes get locked down.
        } catch (Exception e) {
            //Assert.fail(e.getMessage());
            LOG.error("error getting order by confirmation number and transforming to json", e);
        }
        return ToJson.toJson(reservation);

    }
    public boolean orderByConfirmationNumberCheck() {
        String responseAsJson = getOrderByConfirmationNumberJson();
        String jsonSchemaAsString = getJsonSchemaFromFile("Reservation.schema.json");
        return G4JsonValidator.isValid(responseAsJson, jsonSchemaAsString);
    }

}
