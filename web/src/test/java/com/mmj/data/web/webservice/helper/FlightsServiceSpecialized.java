package com.mmj.data.web.webservice.helper;

import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.enums.SACodeEnum;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Alternative
public class FlightsServiceSpecialized extends FlightsService {
    @Override
    public List<RouteDto> getRoutes() {

        List<RouteDto> routes = new ArrayList<>();

        return routes;
    }

    @Override
    public List<ReservationDto> getGuestPassBookingsByEmployeeId(String employeeId, LocalDate searchStartDate, LocalDate searchEndDate, String bookingType) {
        return super.getGuestPassBookingsByEmployeeId(employeeId, searchStartDate, searchEndDate, bookingType);
    }

    @Override
    public List<AncillaryFeeDto> getSSRs() {
        List<AncillaryFeeDto> result = new ArrayList<>();
        return result;
    }

    @Override
    public List<AncillaryFeeDto> getBagFees(String departureAirportCode, String arrivalAirportCode, String bookingDate) {
        List<AncillaryFeeDto> result = new ArrayList<>();
        return result;
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
        return super.getOrders(searchStartDate, searchEndDate, employeeNumber, bookingType, includes);
    }

    @Override
    public ReservationDto getOrderByConfirmationNumber(String confirmationNumber) throws BusinessException {
        return super.getOrderByConfirmationNumber(confirmationNumber);
    }

    @Override
    public ReservationDto createOrder(FlightBookingRequestDto flightBookingRequestDto, String userId, String userName) {
        return super.createOrder(flightBookingRequestDto, userId, userName);
    }

    @Override
    public ReservationDto updateOrder(ModifyOrderRequestDto modifyRequestDto, String userId, String userName) {
        return super.updateOrder(modifyRequestDto, userId, userName);
    }

    @Override
    public ReservationDto cancelOrder(String confirmationNumber, String userId, String userName) throws BusinessException {
        return super.cancelOrder(confirmationNumber, userId, userName);
    }

    @Override
    public List<ReservationDto> getUnusedItineraries(LocalDate searchStartDate, LocalDate searchEndDate, String bookingType, String includes) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setCustomerNumber("012346");
        reservationDto.setPaidAmount(new BigDecimal(30));
        PassengerSegmentDto passengerSegmentDto = new PassengerSegmentDto();
        EmployeeInformationDto employeeInformationDto = new EmployeeInformationDto();
        employeeInformationDto.setSaCode(SACodeEnum.SA2.name());
        employeeInformationDto.setEmployeeId("012346");
        passengerSegmentDto.setEmployeeInformation(employeeInformationDto);
        List<PassengerSegmentDto> passengerSegmentDtoList = new ArrayList<>();
        passengerSegmentDtoList.add(passengerSegmentDto);
        reservationDto.setPassengerSegments(passengerSegmentDtoList);
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        reservationDtoList.add(reservationDto);
        return reservationDtoList;
    }

    public void refundOrder(String itinerary) {
        //throw new SystemException();
    }
}
