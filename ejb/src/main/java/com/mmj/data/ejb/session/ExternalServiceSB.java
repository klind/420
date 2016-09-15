package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.PaymentTypeDTO;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.extclient.dto.AncillaryFeeDto;
import com.mmj.data.core.extclient.dto.FlightBookingRequestDto;
import com.mmj.data.core.extclient.dto.FlightCalendarInfoDto;
import com.mmj.data.core.extclient.dto.ModifyOrderRequestDto;
import com.mmj.data.core.extclient.dto.PassengerSegmentDto;
import com.mmj.data.core.extclient.dto.ReservationDto;
import com.mmj.data.core.extclient.dto.RouteDto;
import com.mmj.data.core.extclient.dto.ShopDto;
import com.mmj.data.core.extclient.dto.summary.FlightManifestDetailWaitlistRequestDto;
import com.mmj.data.core.extclient.dto.summary.FlightManifestDetailWaitlistResponseDto;
import com.mmj.data.core.flights.FlightsService;
import com.mmj.data.core.payments.PaymentService;
import com.mmj.data.ejb.cache.G4tcCache;
import com.mmj.data.ejb.dao.EmployeeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

import static com.mmj.data.core.constant.Constants.SHOW_CANCEL;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@LocalBean
public class ExternalServiceSB {
    private static final Logger LOG = LoggerFactory.getLogger(ExternalServiceSB.class);

    @Inject
    private G4tcCache g4tcCache;

    @Inject
    private FlightsService flightsService;

    @Inject
    private PaymentService paymentService;

    @Inject
    private EmployeeDao employeeDao;

    public List<AncillaryFeeDto> getSSRs() {
        return g4tcCache.getSSRs();
    }

    public List<AncillaryFeeDto> getBagFees(String departureAirportCode, String arrivalAirportCode, String bookingDate) {
        return flightsService.getBagFees(departureAirportCode, arrivalAirportCode, bookingDate);
    }

    public List<RouteDto> getRoutes() {
        return g4tcCache.getRoutes();
    }

    public List<String> getAvailableFlightDates(String departAirportCode, String arriveAirportCode, Integer channelId) {
        return flightsService.getAvailableFlightDates(departAirportCode, arriveAirportCode, channelId);
    }

    public List<FlightCalendarInfoDto> getAvailableFlightDatesAndFareDetails(String departAirportCode, String arriveAirportCode, LocalDate searchStartDate, LocalDate searchEndDate, String bookingType, Integer channelId, Boolean modifyRequest) {
        return flightsService.getAvailableFlightDatesAndFareDetails(departAirportCode, arriveAirportCode, searchStartDate, searchEndDate, bookingType, channelId, modifyRequest);
    }

    public List<ShopDto> getShopFlights(String departAirportCode, String arriveAirportCode, LocalDate searchStartDate, LocalDate searchEndDate, Integer channelId, Integer passengerCount, List<String> fareClasses, LocalDate bookingDate, String bookingType, Double originalPrice, Long overrideReasonId, Double adjustedPrice, String originatingAirline) {
        return flightsService.getShopFlights(departAirportCode, arriveAirportCode, searchStartDate, searchEndDate, channelId, passengerCount, fareClasses, bookingDate, bookingType, originalPrice, overrideReasonId, adjustedPrice, originatingAirline);
    }

    public ShopDto getShopFlight(String itn, String flightNum, LocalDate departDate, Integer channelId, Integer passengerCount, String fareClass, LocalDate bookingDate, String bookingType, String originatingAirline) throws BusinessException {
        ShopDto shopDto = flightsService.getShopFlight(flightNum, departDate, channelId, passengerCount, fareClass, bookingDate, bookingType, originatingAirline);
        ReservationDto reservationDto = getOrderByConfirmationNumber(itn);
        List<PassengerSegmentDto> passengerSegments = reservationDto.getPassengerSegments();
        for (PassengerSegmentDto passengerSegment : passengerSegments) {
            String s = (passengerSegment.isCheckedIn() ? "1" : "0") + (passengerSegment.isOnboard() ? "1" : "0") + (passengerSegment.isHasFlown() ? "1" : "0");
            Integer u = Integer.parseInt(s, 2);
            if (SHOW_CANCEL.contains(u)) {
                shopDto.setCanCancel(true);
                break;
            }
        }
        return shopDto;
    }

    public List<ReservationDto> getOrders(LocalDate searchStartDate, LocalDate searchEndDate, String employeeNumber, String bookingType, String includes) {
        return flightsService.getOrders(searchStartDate, searchEndDate, employeeNumber, bookingType, includes);
    }

    public ReservationDto getOrderByConfirmationNumber(String confirmationNumber) throws BusinessException {
        return flightsService.getOrderByConfirmationNumber(confirmationNumber);
    }

    public ReservationDto createOrder(FlightBookingRequestDto flightBookingRequestDto, String userId, String userName) {
        return flightsService.createOrder(flightBookingRequestDto, userId, userName);
    }

    public ReservationDto updateOrder(ModifyOrderRequestDto modifyRequestDto, String userId, String userName) {
        return flightsService.updateOrder(modifyRequestDto, userId, userName);
    }

    public ReservationDto cancelOrder(String confirmationNumber, String userId, String userName) throws BusinessException {
        return flightsService.cancelOrder(confirmationNumber, userId, userName);
    }

    public List<PaymentTypeDTO> getPaymenttypes() {
        return g4tcCache.getPaymentTypes();
    }

    public FlightManifestDetailWaitlistResponseDto getWaitlist(FlightManifestDetailWaitlistRequestDto flightManifestDetailWaitlistRequestDto) {
        return flightsService.getWaitlist(flightManifestDetailWaitlistRequestDto);
    }


}
