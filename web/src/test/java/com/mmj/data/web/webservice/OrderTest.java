package com.mmj.data.web.webservice;

import com.mmj.data.core.extclient.dto.EmployeeInformationDto;
import com.mmj.data.core.extclient.dto.FlightBookingRequestDto;
import com.mmj.data.core.extclient.dto.FlightPassengerDto;
import com.mmj.data.core.extclient.dto.LegDto;
import com.mmj.data.core.extclient.dto.ReservationDto;
import com.mmj.data.core.extclient.dto.SegmentBookingRequestDto;
import com.mmj.data.core.extclient.dto.SegmentDto;
import com.mmj.data.core.extclient.dto.ShopDto;
import com.mmj.data.ejb.session.EmployeeSB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@RunWith(Arquillian.class)
public class OrderTest extends BaseTest {

    /**
     * G4 Travel Center Webservice. Needed for unit tests.
     */
    @Inject
    private G4tcRestWebservice g4tcRestWebservice;

    @Inject
    private EmployeeSB employeeSB;

    @Deployment
    public static Archive<?> getDeploymentArchive() {

        WebArchive war = getDeployment();
        war.addAsWebInfResource("OrderTest/beans.xml");

        return war;
    }

    @Test
    public void cancelOrderTest() {
        try {

            HttpServletRequest mockRequest = getHttpServletRequest(Constants.E12088);
            Response employeeReservationDto = g4tcRestWebservice.cancelOrder("6M6TFS", mockRequest);
            ReservationDto result = (ReservationDto) employeeReservationDto.getEntity();

            Assert.assertEquals(result.getItinerary(), "6M6TFS");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void createOrderTest() {
        try {
            FlightBookingRequestDto flightBookingRequestDto = new FlightBookingRequestDto();
            flightBookingRequestDto.setMarket("ABQLAS");
            flightBookingRequestDto.setBookedBy("John Doe");
            flightBookingRequestDto.setCustomerNumber("E012088");
            flightBookingRequestDto.setBookingType("BP");
            flightBookingRequestDto.setPrimaryEmail("john.doe@allegiantair.com");

            SegmentBookingRequestDto segmentBookingRequestDto = new SegmentBookingRequestDto();

            ShopDto shopDto = new ShopDto();

            SegmentDto segmentDto = new SegmentDto();
            segmentDto.setAirlineCode("G4");
            segmentDto.setFltNum("470");

            LegDto legDto = new LegDto();
            legDto.setDepartAirport("LAS");
            legDto.setArriveAirport("ABQ");
            legDto.setScheduledArriveDateTime("2016-09-30T18:15:00.000");
            legDto.setScheduledDepartDateTime("2016-09-30T15:53:00.000");
            legDto.setLeg("1");
            legDto.setActualDepartAirport("LAS");
            legDto.setActualArriveAirport("ABQ");
            legDto.setGmtScheduledArriveDateTime("2016-09-30T22:15:00.000");
            legDto.setGmtScheduledDepartDateTime("2016-09-30T19:53:00.000");

            List<LegDto> legDtoList = new ArrayList<>();
            legDtoList.add(legDto);

            segmentDto.setLegs(legDtoList);

            shopDto.setSegment(segmentDto);

            segmentBookingRequestDto.setShopDto(shopDto);

            FlightPassengerDto flightPassengerDto = new FlightPassengerDto();

            EmployeeInformationDto employeeInformationDto = new EmployeeInformationDto();
            employeeInformationDto.setEmployeeId("012088");
            employeeInformationDto.setSaCode("SA4");
            employeeInformationDto.setHireDate("2013-08-26");

            flightPassengerDto.setEmployeeInformation(employeeInformationDto);

            List<FlightPassengerDto> flightPassengers = new ArrayList<>();
            flightPassengers.add(flightPassengerDto);
            segmentBookingRequestDto.setFlightPassengers(flightPassengers);

            flightBookingRequestDto.getSegments().add(segmentBookingRequestDto);

            HttpServletRequest mockRequest = getHttpServletRequest(Constants.E12088);

            Response employeeReservationDto = g4tcRestWebservice.createOrder(flightBookingRequestDto, "false", mockRequest);
            ReservationDto result = (ReservationDto) employeeReservationDto.getEntity();

            Assert.assertEquals(result.getItinerary(), "6M6TFG");
            Assert.assertEquals(result.getPassengerSegments().size(), 1);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
