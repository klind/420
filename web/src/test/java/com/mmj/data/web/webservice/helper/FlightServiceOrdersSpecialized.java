package com.mmj.data.web.webservice.helper;

import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.extclient.dto.AncillaryFeeDto;
import com.mmj.data.core.extclient.dto.EmployeeInformationDto;
import com.mmj.data.core.extclient.dto.FlightBookingRequestDto;
import com.mmj.data.core.extclient.dto.FlightCalendarInfoDto;
import com.mmj.data.core.extclient.dto.ModifyOrderRequestDto;
import com.mmj.data.core.extclient.dto.PassengerSegmentDto;
import com.mmj.data.core.extclient.dto.ReservationDto;
import com.mmj.data.core.extclient.dto.RouteDto;
import com.mmj.data.core.extclient.dto.ShopDto;
import com.mmj.data.core.flights.FlightsService;

import javax.enterprise.inject.Alternative;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Alternative
public class FlightServiceOrdersSpecialized extends FlightsService {
    @Override
    public List<RouteDto> getRoutes() {
        return super.getRoutes();
    }

    @Override
    public List<ReservationDto> getGuestPassBookingsByEmployeeId(String employeeId, LocalDate searchStartDate, LocalDate searchEndDate, String bookingType) {
        return super.getGuestPassBookingsByEmployeeId(employeeId, searchStartDate, searchEndDate, bookingType);
    }

    @Override
    public List<AncillaryFeeDto> getSSRs() {
        return super.getSSRs();
    }

    @Override
    public List<AncillaryFeeDto> getBagFees(String departureAirportCode, String arrivalAirportCode, String bookingDate) {
        return super.getBagFees(departureAirportCode, arrivalAirportCode, bookingDate);
    }

    @Override
    public List<String> getAvailableFlightDates(String departAirportCode, String arriveAirportCode, Integer channelId) {
        return super.getAvailableFlightDates(departAirportCode, arriveAirportCode, channelId);
    }

    @Override
    public List<FlightCalendarInfoDto> getAvailableFlightDatesAndFareDetails(String departAirportCode, String arriveAirportCode, LocalDate searchStartDate, LocalDate searchEndDate, String bookingType, Integer channelId, Boolean modifyRequest) {
        return super.getAvailableFlightDatesAndFareDetails(departAirportCode, arriveAirportCode, searchStartDate, searchEndDate, bookingType, channelId, modifyRequest);
    }

    @Override
    public List<ShopDto> getShopFlights(String departAirportCode, String arriveAirportCode, LocalDate searchStartDate, LocalDate searchEndDate, Integer channelId, Integer passengerCount, List<String> fareClasses, LocalDate bookingDate, String bookingType, Double originalPrice, Long overrideReasonId, Double adjustedPrice, String originatingAirline) {
        return super.getShopFlights(departAirportCode, arriveAirportCode, searchStartDate, searchEndDate, channelId, passengerCount, fareClasses, bookingDate, bookingType, originalPrice, overrideReasonId, adjustedPrice, originatingAirline);
    }

    @Override
    public ShopDto getShopFlight(String flightNum, LocalDate departDate, Integer channelId, Integer passengerCount, String fareClass, LocalDate bookingDate, String bookingType, String originatingAirline) {
        return super.getShopFlight(flightNum, departDate, channelId, passengerCount, fareClass, bookingDate, bookingType, originatingAirline);
    }

    @Override
    public List<ReservationDto> getOrders(LocalDate searchStartDate, LocalDate searchEndDate, String employeeNumber, String bookingType, String includes) {
        List<ReservationDto> buddyPassReservationDtos = new ArrayList<>();

        ReservationDto buddyPassReservationDto = new ReservationDto();
        buddyPassReservationDto.setCustomerNumber("E012088");
        buddyPassReservationDto.setCompany("20");
        buddyPassReservationDto.setMarket("ABQLAS");
        buddyPassReservationDto.setPaxCount(1);
        buddyPassReservationDto.setBookingType("BP");
        buddyPassReservationDto.setItinerary("6M6TFG");

        Date departDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDepartDate = dateFormat.format(departDate);

        PassengerSegmentDto buddyPassPassengerSegmentDto = new PassengerSegmentDto();
        buddyPassPassengerSegmentDto.setDepartureDate(strDepartDate);
        buddyPassPassengerSegmentDto.setItinerary("6M6TFG");
        buddyPassPassengerSegmentDto.setSegment("1");
        buddyPassPassengerSegmentDto.setInd("A");
        buddyPassPassengerSegmentDto.setPaxNum("1");
        buddyPassPassengerSegmentDto.setDepartureDate(strDepartDate);
        buddyPassPassengerSegmentDto.setOrigin("LAS");
        buddyPassPassengerSegmentDto.setDestination("ABQ");
        buddyPassPassengerSegmentDto.setFlightNumber("470");
        buddyPassPassengerSegmentDto.setLeg("1");

        EmployeeInformationDto employeeInformationDto = new EmployeeInformationDto();
        employeeInformationDto.setSaCode("SA4");
        employeeInformationDto.setEmployeeId("012088");

        buddyPassPassengerSegmentDto.setEmployeeInformation(employeeInformationDto);

        List<PassengerSegmentDto> employeeSegments = new ArrayList<>();
        employeeSegments.add(buddyPassPassengerSegmentDto);

        buddyPassReservationDto.setPassengerSegments(employeeSegments);

        buddyPassReservationDtos.add(buddyPassReservationDto);

        return buddyPassReservationDtos;
    }

    @Override
    public ReservationDto getOrderByConfirmationNumber(String confirmationNumber) throws BusinessException {
        ReservationDto employeeReservationDto = new ReservationDto();
        employeeReservationDto.setCustomerNumber("E012088");
        employeeReservationDto.setCompany("20");
        employeeReservationDto.setMarket("ABQLAS");
        employeeReservationDto.setPaxCount(1);
        employeeReservationDto.setBookingType("EM");
        employeeReservationDto.setItinerary("6M6TFS");

        Date departDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDepartDate = dateFormat.format(departDate);

        PassengerSegmentDto employeePassengerSegmentDto = new PassengerSegmentDto();
        employeePassengerSegmentDto.setDepartureDate(strDepartDate);
        employeePassengerSegmentDto.setItinerary("6M6TFS");
        employeePassengerSegmentDto.setSegment("1");
        employeePassengerSegmentDto.setInd("A");
        employeePassengerSegmentDto.setPaxNum("1");
        employeePassengerSegmentDto.setDepartureDate(strDepartDate);
        employeePassengerSegmentDto.setOrigin("LAS");
        employeePassengerSegmentDto.setDestination("ABQ");
        employeePassengerSegmentDto.setFlightNumber("470");
        employeePassengerSegmentDto.setLeg("1");

        EmployeeInformationDto employeeInformationDto = new EmployeeInformationDto();
        employeeInformationDto.setSaCode("SA4");
        employeeInformationDto.setEmployeeId("012088");

        employeePassengerSegmentDto.setEmployeeInformation(employeeInformationDto);

        List<PassengerSegmentDto> employeeSegments = new ArrayList<>();
        employeeSegments.add(employeePassengerSegmentDto);

        employeeReservationDto.setPassengerSegments(employeeSegments);

        return employeeReservationDto;
    }

    @Override
    public ReservationDto createOrder(FlightBookingRequestDto flightBookingRequestDto, String userId, String userName) {
        ReservationDto buddyPassReservationDto = new ReservationDto();
        buddyPassReservationDto.setCustomerNumber("E012088");
        buddyPassReservationDto.setCompany("20");
        buddyPassReservationDto.setMarket("ABQLAS");
        buddyPassReservationDto.setPaxCount(1);
        buddyPassReservationDto.setBookingType("BP");
        buddyPassReservationDto.setItinerary("6M6TFG");

        Date departDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDepartDate = dateFormat.format(departDate);

        PassengerSegmentDto buddyPassPassengerSegmentDto = new PassengerSegmentDto();
        buddyPassPassengerSegmentDto.setDepartureDate(strDepartDate);
        buddyPassPassengerSegmentDto.setItinerary("6M6TFG");
        buddyPassPassengerSegmentDto.setSegment("1");
        buddyPassPassengerSegmentDto.setInd("A");
        buddyPassPassengerSegmentDto.setPaxNum("1");
        buddyPassPassengerSegmentDto.setDepartureDate(strDepartDate);
        buddyPassPassengerSegmentDto.setOrigin("LAS");
        buddyPassPassengerSegmentDto.setDestination("ABQ");
        buddyPassPassengerSegmentDto.setFlightNumber("470");
        buddyPassPassengerSegmentDto.setLeg("1");

        EmployeeInformationDto employeeInformationDto = new EmployeeInformationDto();
        employeeInformationDto.setSaCode("SA4");
        employeeInformationDto.setEmployeeId("012088");

        buddyPassPassengerSegmentDto.setEmployeeInformation(employeeInformationDto);

        List<PassengerSegmentDto> employeeSegments = new ArrayList<>();
        employeeSegments.add(buddyPassPassengerSegmentDto);

        buddyPassReservationDto.setPassengerSegments(employeeSegments);

        return buddyPassReservationDto;
    }

    @Override
    public ReservationDto updateOrder(ModifyOrderRequestDto modifyRequestDto, String userId, String userName) {
        ReservationDto buddyPassReservationDto = new ReservationDto();
        buddyPassReservationDto.setCustomerNumber("E012088");
        buddyPassReservationDto.setCompany("20");
        buddyPassReservationDto.setMarket("ABQLAS");
        buddyPassReservationDto.setPaxCount(1);
        buddyPassReservationDto.setBookingType("BP");
        buddyPassReservationDto.setItinerary("6M6TFG");

        Date departDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDepartDate = dateFormat.format(departDate);

        PassengerSegmentDto employeePassengerSegmentDto = new PassengerSegmentDto();
        employeePassengerSegmentDto.setDepartureDate(strDepartDate);
        employeePassengerSegmentDto.setItinerary("6M6TFG");
        employeePassengerSegmentDto.setSegment("1");
        employeePassengerSegmentDto.setInd("A");
        employeePassengerSegmentDto.setPaxNum("1");
        employeePassengerSegmentDto.setDepartureDate(strDepartDate);
        employeePassengerSegmentDto.setOrigin("LAS");
        employeePassengerSegmentDto.setDestination("ABQ");
        employeePassengerSegmentDto.setFlightNumber("470");
        employeePassengerSegmentDto.setLeg("1");

        EmployeeInformationDto employeeInformationDto = new EmployeeInformationDto();
        employeeInformationDto.setSaCode("SA5");
        employeeInformationDto.setEmployeeId("012088");

        employeePassengerSegmentDto.setEmployeeInformation(employeeInformationDto);

        List<PassengerSegmentDto> employeeSegments = new ArrayList<>();
        employeeSegments.add(employeePassengerSegmentDto);

        buddyPassReservationDto.setPassengerSegments(employeeSegments);

        return buddyPassReservationDto;
    }

    @Override
    public ReservationDto cancelOrder(String confirmationNumber, String userId, String userName) throws BusinessException {
        ReservationDto employeeReservationDto = new ReservationDto();
        employeeReservationDto.setCustomerNumber("E012088");
        employeeReservationDto.setCompany("20");
        employeeReservationDto.setMarket("ABQLAS");
        employeeReservationDto.setPaxCount(1);
        employeeReservationDto.setBookingType("EM");
        employeeReservationDto.setItinerary("6M6TFS");
        employeeReservationDto.setCancelCode("P1");

        Date departDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDepartDate = dateFormat.format(departDate);

        PassengerSegmentDto employeePassengerSegmentDto = new PassengerSegmentDto();
        employeePassengerSegmentDto.setDepartureDate(strDepartDate);
        employeePassengerSegmentDto.setItinerary("6M6TFS");
        employeePassengerSegmentDto.setSegment("1");
        employeePassengerSegmentDto.setInd("A");
        employeePassengerSegmentDto.setPaxNum("1");
        employeePassengerSegmentDto.setDepartureDate(strDepartDate);
        employeePassengerSegmentDto.setOrigin("LAS");
        employeePassengerSegmentDto.setDestination("ABQ");
        employeePassengerSegmentDto.setFlightNumber("470");
        employeePassengerSegmentDto.setLeg("1");

        EmployeeInformationDto employeeInformationDto = new EmployeeInformationDto();
        employeeInformationDto.setSaCode("SA4");
        employeeInformationDto.setEmployeeId("012088");

        employeePassengerSegmentDto.setEmployeeInformation(employeeInformationDto);

        List<PassengerSegmentDto> employeeSegments = new ArrayList<>();
        employeeSegments.add(employeePassengerSegmentDto);

        employeeReservationDto.setPassengerSegments(employeeSegments);

        return employeeReservationDto;
    }

    @Override
    public List<ReservationDto> getUnusedItineraries(LocalDate searchStartDate, LocalDate searchEndDate, String bookingType, String includes) {
        return super.getUnusedItineraries(searchStartDate, searchEndDate, bookingType, includes);
    }

    @Override
    public void refundOrder(String itinerary) {
        super.refundOrder(itinerary);
    }
}
