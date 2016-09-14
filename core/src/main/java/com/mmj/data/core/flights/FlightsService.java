package com.mmj.data.core.flights;

import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.SystemException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.extclient.ancillary.AncillaryFeeServiceClient;
import com.mmj.data.core.extclient.dto.AncillaryFeeDto;
import com.mmj.data.core.extclient.dto.AncillaryFeeResponse;
import com.mmj.data.core.extclient.dto.BookReservationResponseDto;
import com.mmj.data.core.extclient.dto.CancelOrderResponseDto;
import com.mmj.data.core.extclient.dto.FlightBookingRequestDto;
import com.mmj.data.core.extclient.dto.FlightCalendarInfoDto;
import com.mmj.data.core.extclient.dto.ModifyOrderRequestDto;
import com.mmj.data.core.extclient.dto.ModifyOrderResponseDto;
import com.mmj.data.core.extclient.dto.ReservationDto;
import com.mmj.data.core.extclient.dto.ReservationResponseDto;
import com.mmj.data.core.extclient.dto.ReservationsResponseDto;
import com.mmj.data.core.extclient.dto.RouteDto;
import com.mmj.data.core.extclient.dto.RouteListResponseDto;
import com.mmj.data.core.extclient.dto.ShopDto;
import com.mmj.data.core.extclient.dto.ShopFlightResponseDto;
import com.mmj.data.core.extclient.dto.ShopResponseDto;
import com.mmj.data.core.extclient.dto.summary.FlightManifestDetailWaitlistRequestDto;
import com.mmj.data.core.extclient.dto.summary.FlightManifestDetailWaitlistResponseDto;
import com.mmj.data.core.extclient.order.G4FlightOrderClient;
import com.mmj.data.core.extclient.schedules.RoutesServiceClient;
import com.mmj.data.core.extclient.shop.ShopServiceClient;
import com.mmj.data.core.extclient.summary.FlightSummaryServiceClient;
import com.mmj.data.core.util.ToJson;
import com.mmj.data.core.constant.Constants;
import com.mmj.data.core.util.SystemProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.util.GenericType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class FlightsService {
    private static final Logger LOG = LoggerFactory.getLogger(FlightsService.class);

    public FlightsService() {
    }

    /**
     * @return List<RouteDto>
     * @
     */
    public List<RouteDto> getRoutes() {
        ClientResponse<RouteListResponseDto> response = null;
        int statusCode = 0;
        try {
            String g4FlightScheduleURL = SystemProperty.G4_FLIGHT_SCHEDULE_URL.getValue();
            if (StringUtils.isBlank(g4FlightScheduleURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_SCHEDULE_URL + " not set");
            }
            RoutesServiceClient routeResource = ProxyFactory.create(RoutesServiceClient.class, g4FlightScheduleURL);
            response = routeResource.getAllRoutes();
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String msg = SystemProperty.G4_FLIGHT_SCHEDULE_URL.getValue() + " returned status code " + statusCode;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            RouteListResponseDto routesDto = response.getEntity();
            List<RouteDto> routes = routesDto.getAirports();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Retrieved routes : ");
                for (RouteDto route : routes) {
                    LOG.debug(route.getDisplayName());
                }
            }
            return routes;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get routes from flights service", e);
            throw new SystemException("Could not get routes from flights service", e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    /**
     * @param employeeId
     * @param searchStartDate
     * @param searchEndDate
     * @param bookingType
     * @return String
     * @
     */
    public List<ReservationDto> getGuestPassBookingsByEmployeeId(String employeeId, LocalDate searchStartDate, LocalDate searchEndDate, String bookingType) {
        List<ReservationDto> result = null;
        ClientResponse<ReservationsResponseDto> response = null;
        int statusCode = 0;
        try {
            String g4FlightReservationsURL = SystemProperty.G4_FLIGHT_ORDER_URL.getValue();
            if (StringUtils.isBlank(g4FlightReservationsURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_ORDER_URL + " not set");
            }
            G4FlightOrderClient g4FlightOrderClient = ProxyFactory.create(G4FlightOrderClient.class, g4FlightReservationsURL);
            response = g4FlightOrderClient.retrieveOrders(searchStartDate.format(Constants.DATE_FORMATTER), searchEndDate.format(Constants.DATE_FORMATTER), "E" + employeeId, bookingType, Constants.PASSENGER_SEGMENT);
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String msg = SystemProperty.G4_FLIGHT_ORDER_URL.getValue() + " (employeeId=E" + employeeId + " searchStartDate=" + searchStartDate.format(Constants.DATE_FORMATTER) + " searchEndDate=" + searchEndDate.format(Constants.DATE_FORMATTER) + " includes=" + Constants.PASSENGER_SEGMENT + ") returned status code " + statusCode;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            ReservationsResponseDto reservationsResponseDto = response.getEntity();
            List<ReservationDto> reservations = reservationsResponseDto.getReservations();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Retrieved reservations : ");
                for (ReservationDto reservation : reservations) {
                    LOG.debug("{}", reservation);
                }
            }
            result = reservations;
            return result;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get orders from flights orders", e);
            throw new SystemException("Could not get orders from flights orders", e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    /**
     * @return List<AncillaryFeeDto>
     * @
     */
    public List<AncillaryFeeDto> getSSRs() {
        List<AncillaryFeeDto> result = null;
        ClientResponse<AncillaryFeeResponse> response = null;
        int statusCode = 0;
        String g4FlightAncillaryURL = SystemProperty.G4_FLIGHT_ANCILLARY_URL.getValue();
        try {
            if (StringUtils.isBlank(g4FlightAncillaryURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_ANCILLARY_URL + " not set");
            }
            AncillaryFeeServiceClient ancillaryFeeServiceClient = ProxyFactory.create(AncillaryFeeServiceClient.class, g4FlightAncillaryURL);
            response = ancillaryFeeServiceClient.getSSRFees(1, true);
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String msg = SystemProperty.G4_FLIGHT_ANCILLARY_URL.getValue() + " (channelId=1&activeOnly=true) returned status code " + statusCode;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            AncillaryFeeResponse ancillaryFeeResponse = response.getEntity();
            result = ancillaryFeeResponse.getFees();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Retrieved SSR's : ");
                for (AncillaryFeeDto ancillaryFeeDto : result) {
                    LOG.debug(ancillaryFeeDto.getCode() + " - " + ancillaryFeeDto.getDescription());
                }
            }
            return result;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get SSR's from {}", g4FlightAncillaryURL, e);
            throw new SystemException("Could not get SSR's from " + g4FlightAncillaryURL, e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    /**
     * @return List<AncillaryFeeDto>
     * @
     */
    public List<AncillaryFeeDto> getBagFees(String departureAirportCode, String arrivalAirportCode, String bookingDate) {
        List<AncillaryFeeDto> result = null;
        ClientResponse<AncillaryFeeResponse> response = null;
        int statusCode = 0;
        String g4FlightAncillaryURL = SystemProperty.G4_FLIGHT_ANCILLARY_URL.getValue();
        try {
            if (StringUtils.isBlank(g4FlightAncillaryURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_ANCILLARY_URL + " not set");
            }
            AncillaryFeeServiceClient ancillaryFeeServiceClient = ProxyFactory.create(AncillaryFeeServiceClient.class, g4FlightAncillaryURL);
            response = ancillaryFeeServiceClient.getBagFees(departureAirportCode, arrivalAirportCode, bookingDate);
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String msg = SystemProperty.G4_FLIGHT_ANCILLARY_URL.getValue() + " (" + departureAirportCode + ", " + arrivalAirportCode + ", " + bookingDate + ") returned status code " + statusCode;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            AncillaryFeeResponse ancillaryFeeResponse = response.getEntity();
            result = ancillaryFeeResponse.getFees();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Retrieved Bag fees : ");
                for (AncillaryFeeDto ancillaryFeeDto : result) {
                    LOG.debug(ancillaryFeeDto.getCode() + " - " + ancillaryFeeDto.getDescription());
                }
            }
            return result;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get Bag fees from {}", g4FlightAncillaryURL, e);
            throw new SystemException("Could not get Bag fees from " + g4FlightAncillaryURL, e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    /**
     * @param departAirportCode
     * @param arriveAirportCode
     * @param channelId
     * @return List<String>
     * @
     */
    public List<String> getAvailableFlightDates(String departAirportCode, String arriveAirportCode, Integer channelId) {
        List<String> result = null;
        ClientResponse<ShopResponseDto> response = null;
        int statusCode = 0;
        String g4FlightShopURL = SystemProperty.G4_FLIGHT_SHOP_URL.getValue();
        try {
            if (StringUtils.isBlank(g4FlightShopURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_SHOP_URL + " not set");
            }
            ShopServiceClient shopServiceClient = ProxyFactory.create(ShopServiceClient.class, g4FlightShopURL);
            response = shopServiceClient.getAvailableDatesByMarketAndDateRange(departAirportCode, arriveAirportCode, channelId);
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String msg = SystemProperty.G4_FLIGHT_ORDER_URL.getValue() + " status code: " + statusCode;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            result = response.getEntity(ArrayList.class);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Retrieved available flight dates:");
                for (String res : result) {
                    LOG.debug(res);
                }
            }
            return result;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get flight dates from {}", g4FlightShopURL, e);
            throw new SystemException("Could not get available dates from " + g4FlightShopURL, e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    /**
     * @param departAirportCode
     * @param arriveAirportCode
     * @param searchStartDate
     * @param searchEndDate
     * @param bookingType
     * @param channelId
     * @param modifyRequest
     * @return List<FlightCalendarInfoDto>
     * @
     */
    public List<FlightCalendarInfoDto> getAvailableFlightDatesAndFareDetails(String departAirportCode, String arriveAirportCode, LocalDate searchStartDate, LocalDate searchEndDate, String bookingType, Integer channelId, Boolean modifyRequest) {
        List<FlightCalendarInfoDto> result = null;
        ClientResponse<ShopResponseDto> response = null;
        int statusCode = 0;
        String g4FlightShopURL = SystemProperty.G4_FLIGHT_SHOP_URL.getValue();
        try {
            if (StringUtils.isBlank(g4FlightShopURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_SHOP_URL + " not set");
            }
            ShopServiceClient shopServiceClient = ProxyFactory.create(ShopServiceClient.class, g4FlightShopURL);
            if (modifyRequest == null) {
                modifyRequest = false;
            }
            String searchStartDateAsString = null;
            if (searchStartDate != null) {
                searchStartDateAsString = searchStartDate.format(Constants.DATE_FORMATTER);
            }
            String searchEndDateAsString = null;
            if (searchEndDate != null) {
                searchEndDateAsString = searchEndDate.format(Constants.DATE_FORMATTER);
            }
            response = shopServiceClient.getAvailableDatesDetailsByMarketAndDateRange(departAirportCode,
                    arriveAirportCode, searchStartDateAsString,
                    searchEndDateAsString, bookingType, channelId,
                    modifyRequest);
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String msg = SystemProperty.G4_FLIGHT_ORDER_URL.getValue() + " status code: " + statusCode;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            result = response.getEntity(new GenericType<List<FlightCalendarInfoDto>>() {
            });
            return result;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get flight date and fare details from {}", g4FlightShopURL, e);
            throw new SystemException("Could not get available dates from " + g4FlightShopURL, e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    /**
     * @param departAirportCode
     * @param arriveAirportCode
     * @param searchStartDate
     * @param searchEndDate
     * @param channelId
     * @param passengerCount
     * @param fareClasses
     * @param bookingDate
     * @param bookingType
     * @param originalPrice
     * @param overrideReasonId
     * @param adjustedPrice
     * @param originatingAirline
     * @return List<ShopDto>
     * @
     */
    public List<ShopDto> getShopFlights(String departAirportCode, String arriveAirportCode, LocalDate searchStartDate, LocalDate searchEndDate, Integer channelId, Integer passengerCount, List<String> fareClasses, LocalDate bookingDate, String bookingType, Double originalPrice, Long overrideReasonId, Double adjustedPrice, String originatingAirline) {
        List<ShopDto> result = null;
        ClientResponse<ShopResponseDto> response = null;
        int statusCode = 0;
        String g4FlightShopURL = SystemProperty.G4_FLIGHT_SHOP_URL.getValue();
        try {
            if (StringUtils.isBlank(g4FlightShopURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_SHOP_URL + " not set");
            }
            ShopServiceClient shopServiceClient = ProxyFactory.create(ShopServiceClient.class, g4FlightShopURL);
            String startAsString = searchStartDate.format(Constants.DATE_FORMATTER);
            String endAsString = searchEndDate.format(Constants.DATE_FORMATTER);
            String bookingDateAsString = null;
            if (bookingDate != null) {
                bookingDateAsString = bookingDate.format(Constants.DATE_FORMATTER);
            }
            BigDecimal originalPriceParam = null;
            if (originalPrice != null) {
                originalPriceParam = BigDecimal.valueOf(originalPrice);
            }
            BigDecimal adjustedPriceParam = null;
            if (adjustedPrice != null) {
                adjustedPriceParam = BigDecimal.valueOf(adjustedPrice);
            }
            if (fareClasses.size() == 0) {
                fareClasses = null;
            }

            response = shopServiceClient.shopFlights(departAirportCode, arriveAirportCode, startAsString,
                    endAsString, channelId, passengerCount, fareClasses, bookingDateAsString, bookingType,
                    originalPriceParam, overrideReasonId, adjustedPriceParam, "G4");
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String msg = SystemProperty.G4_FLIGHT_ORDER_URL.getValue() + " status code: " + statusCode;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            ShopResponseDto shopResponseDto = response.getEntity(ShopResponseDto.class);
            result = shopResponseDto.getShopDtos();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Retrieved available flight dates:");
                for (ShopDto res : result) {
                    LOG.debug("{} ", res);
                }
            }
            return result;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get shop flight {}", g4FlightShopURL, e);
            throw new SystemException("Could not get shop flight from " + g4FlightShopURL, e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    /**
     * @param flightNum
     * @param departDate
     * @param channelId
     * @param passengerCount
     * @param fareClass
     * @param passengerCount
     * @param bookingDate
     * @param bookingType
     * @param originatingAirline
     * @return ShopDto
     * @
     */
    public ShopDto getShopFlight(String flightNum, LocalDate departDate, Integer channelId, Integer passengerCount, String fareClass, LocalDate bookingDate, String bookingType, String originatingAirline) {
        ShopDto result = null;
        ClientResponse<ShopResponseDto> response = null;
        int statusCode = 0;
        String g4FlightShopURL = SystemProperty.G4_FLIGHT_SHOP_URL.getValue();
        try {
            if (StringUtils.isBlank(g4FlightShopURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_SHOP_URL + " not set");
            }
            ShopServiceClient shopServiceClient = ProxyFactory.create(ShopServiceClient.class, g4FlightShopURL);
            String departDateAsString = null;
            if (departDate != null) {
                departDateAsString = departDate.format(Constants.DATE_FORMATTER);
            }
            String bookingDateAsString = null;
            if (bookingDate != null) {
                bookingDateAsString = bookingDate.format(Constants.DATE_FORMATTER);
            }
            response = shopServiceClient.shopByFlightNum(flightNum, departDateAsString, channelId, passengerCount, fareClass, bookingDateAsString, bookingType, originatingAirline);
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String msg = SystemProperty.G4_FLIGHT_ORDER_URL.getValue() + " status code: " + statusCode;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            ShopFlightResponseDto shopFlightResponseDto = response.getEntity(ShopFlightResponseDto.class);
            result = shopFlightResponseDto.getShopDto();
            LOG.debug("Retrieved available flight dates:");
            LOG.debug("{} ", result);
            return result;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get shop flight {}", g4FlightShopURL, e);
            throw new SystemException("Could not get shop flight from " + g4FlightShopURL, e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    /**
     * @param searchStartDate
     * @param searchEndDate
     * @param employeeNumber
     * @param bookingType
     * @param includes
     * @return String
     * @
     */
    public List<ReservationDto> getOrders(LocalDate searchStartDate, LocalDate searchEndDate, String employeeNumber, String bookingType, String includes) {
        List<ReservationDto> results = null;
        ClientResponse<ReservationsResponseDto> response = null;
        int statusCode = 0;
        try {
            String g4FlightReservationsURL = SystemProperty.G4_FLIGHT_ORDER_URL.getValue();
            if (StringUtils.isBlank(g4FlightReservationsURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_ORDER_URL + " not set");
            }
            G4FlightOrderClient g4FlightOrderClient = ProxyFactory.create(G4FlightOrderClient.class, g4FlightReservationsURL);
            response = g4FlightOrderClient.retrieveOrders(searchStartDate.toString(), searchEndDate.toString(), "E" + employeeNumber, bookingType, includes);
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String msg = SystemProperty.G4_FLIGHT_ORDER_URL.getValue() + " searchStartDate: " + searchStartDate + ", searchEndDate: " + searchEndDate;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            ReservationsResponseDto reservationsResponseDto = response.getEntity();
            List<ReservationDto> reservations = reservationsResponseDto.getReservations();
            if (LOG.isDebugEnabled()) {
                for (ReservationDto reservation : reservations) {
                    LOG.debug("Retrieved reservation : " + reservation);
                }
            }
            results = reservations;
            return results;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get orders from flights orders", e);
            throw new SystemException("Could not get orders from flights orders", e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    /**
     * @param confirmationNumber
     * @return String
     * @
     */
    public ReservationDto getOrderByConfirmationNumber(String confirmationNumber) throws BusinessException {
        ReservationDto result = null;
        ClientResponse<ReservationResponseDto> response = null;
        int statusCode = 0;
        try {
            String g4FlightReservationsURL = SystemProperty.G4_FLIGHT_ORDER_URL.getValue();
            if (StringUtils.isBlank(g4FlightReservationsURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_ORDER_URL + " not set");
            }
            G4FlightOrderClient g4FlightOrderClient = ProxyFactory.create(G4FlightOrderClient.class, g4FlightReservationsURL);
            response = g4FlightOrderClient.retrieveOrder(confirmationNumber);
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode == HttpStatus.SC_NOT_FOUND) {
                throw new NotFoundException("Reservation with confirmation number " + confirmationNumber + " does not exist");
            }
            if (statusCode != HttpStatus.SC_OK) {
                String msg = SystemProperty.G4_FLIGHT_ORDER_URL.getValue() + " confirmationNumber: " + confirmationNumber;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            ReservationResponseDto reservationsResponseDto = response.getEntity();
            ReservationDto reservation = reservationsResponseDto.getReservation();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Retrieved reservation : " + reservation);
            }
            result = reservation;
            return result;
        } catch (SystemException | BusinessException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get orders from flights orders", e);
            throw new SystemException("Could not get orders from flights orders", e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    /**
     * @param flightBookingRequestDto
     * @return String
     * @
     */
    public ReservationDto createOrder(FlightBookingRequestDto flightBookingRequestDto, String userId, String userName) {
        ReservationDto result = null;
        ClientResponse<BookReservationResponseDto> response = null;
        int statusCode = 0;
        try {
            String g4FlightReservationsURL = SystemProperty.G4_FLIGHT_ORDER_URL.getValue();
            if (StringUtils.isBlank(g4FlightReservationsURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_ORDER_URL + " not set");
            }
            G4FlightOrderClient g4FlightOrderClient = ProxyFactory.create(G4FlightOrderClient.class, g4FlightReservationsURL);
            LOG.debug("Sending order to g4-flight-order {}", ToJson.toJson(flightBookingRequestDto));
            response = g4FlightOrderClient.saveOrder(flightBookingRequestDto, Constants.CHANNEL_ID, Constants.CALLER_KEY, userId);
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_ACCEPTED && statusCode != HttpStatus.SC_CREATED) {
                String msg = SystemProperty.G4_FLIGHT_ORDER_URL.getValue() + " flightBookingRequestDto: " + flightBookingRequestDto + " returned " + statusCode;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            BookReservationResponseDto bookReservationDto = response.getEntity();
            ReservationDto reservationDto = bookReservationDto.getReservationDto();
            LOG.debug("Retrieved reservation : " + reservationDto);
            result = reservationDto;
            return result;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get orders from flights orders", e);
            throw new SystemException("Could not get orders from flights orders", e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    /**
     * @param modifyRequestDto
     * @return String
     * @
     */
    public ReservationDto updateOrder(ModifyOrderRequestDto modifyRequestDto, String userId, String userName) {
        ReservationDto result = null;
        ClientResponse<ModifyOrderResponseDto> response = null;
        int statusCode = 0;
        try {
            String g4FlightReservationsURL = SystemProperty.G4_FLIGHT_ORDER_URL.getValue();
            if (StringUtils.isBlank(g4FlightReservationsURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_ORDER_URL + " not set");
            }
            G4FlightOrderClient g4FlightOrderClient = ProxyFactory.create(G4FlightOrderClient.class, g4FlightReservationsURL);
            response = g4FlightOrderClient.modifyOrder(modifyRequestDto, Constants.CHANNEL_ID, Constants.CALLER_KEY, userId);
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String msg = SystemProperty.G4_FLIGHT_ORDER_URL.getValue() + " modifyRequestDto: " + modifyRequestDto;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            ModifyOrderResponseDto modifyResponseDto = response.getEntity();
            result = modifyResponseDto.getUpdatedReservation();
            LOG.debug("Retrieved reservation : " + result);
            return result;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get orders from flights orders", e);
            throw new SystemException("Could not get orders from flights orders", e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    /**
     * @param confirmationNumber
     * @param userId
     * @param userName
     * @return String
     * @
     */
    public ReservationDto cancelOrder(String confirmationNumber, String userId, String userName) throws BusinessException {
        ReservationDto result = null;
        ClientResponse<CancelOrderResponseDto> response = null;
        int statusCode = 0;
        try {
            String g4FlightReservationsURL = SystemProperty.G4_FLIGHT_ORDER_URL.getValue();
            if (StringUtils.isBlank(g4FlightReservationsURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_ORDER_URL + " not set");
            }
            G4FlightOrderClient g4FlightOrderClient = ProxyFactory.create(G4FlightOrderClient.class, g4FlightReservationsURL);
            response = g4FlightOrderClient.cancelOrder(confirmationNumber, Constants.CANCEL_REASON_P1, Constants.CHANNEL_ID, Constants.CALLER_KEY, userId);
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode == HttpStatus.SC_NOT_FOUND) {
                throw new NotFoundException("Reservation with confirmation number " + confirmationNumber + " does not exist");
            }
            if (statusCode != HttpStatus.SC_OK) {
                String msg = SystemProperty.G4_FLIGHT_ORDER_URL.getValue() + " confirmationNumber: " + confirmationNumber;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            CancelOrderResponseDto cancelOrderResponseDto = response.getEntity();
            result = cancelOrderResponseDto.getReservationDto();
            LOG.debug("Retrieved reservation :" + result);
            return result;
        } catch (SystemException | BusinessException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get orders from flights orders", e);
            throw new SystemException("Could not get orders from flights orders", e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    /**
     * @param searchStartDate
     * @param searchEndDate
     * @param bookingType
     * @param includes
     * @return List<ReservationDto>
     * @
     */
    public List<ReservationDto> getUnusedItineraries(LocalDate searchStartDate, LocalDate searchEndDate, String bookingType, String includes) {
        List<ReservationDto> results = null;
        ClientResponse<ReservationsResponseDto> response = null;
        int statusCode = 0;
        try {
            String g4FlightReservationsURL = SystemProperty.G4_FLIGHT_ORDER_URL.getValue();
            if (StringUtils.isBlank(g4FlightReservationsURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_ORDER_URL + " not set");
            }
            G4FlightOrderClient g4FlightOrderClient = ProxyFactory.create(G4FlightOrderClient.class, g4FlightReservationsURL);
            LOG.debug("Getting unused itineraries for date {} - {} and type {}", searchStartDate, searchEndDate, bookingType);
            response = g4FlightOrderClient.retrieveUnflownSegments(searchStartDate.toString(), searchEndDate.toString(), bookingType, includes);
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String msg = SystemProperty.G4_FLIGHT_ORDER_URL.getValue() + " searchStartDate: " + searchStartDate + ", searchEndDate: " + searchEndDate + ", bookingType: " + bookingType;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            ReservationsResponseDto reservationsResponseDto = response.getEntity();
            List<ReservationDto> reservations = reservationsResponseDto.getReservations();
            for (ReservationDto reservation : reservations) {
                if (reservation.getCustomerNumber() != null) {
                    reservation.setCustomerNumber(reservation.getCustomerNumber().replace("E", ""));
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Retrieved reservations : ");
                for (ReservationDto reservation : reservations) {
                    LOG.debug("{}", reservation);
                }
            }
            results = reservations;
            LOG.debug("Getting unused itineraries returned {} itineraries", reservations.size());
            return results;
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get unflown reservations from flights orders", e);
            throw new SystemException("Could not get unflown reservations from flights orders", e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    /**
     * @param flightManifestDetailWaitlistRequestDto
     * @return waitlist
     */
    public FlightManifestDetailWaitlistResponseDto getWaitlist(FlightManifestDetailWaitlistRequestDto flightManifestDetailWaitlistRequestDto) {
        FlightManifestDetailWaitlistResponseDto result;
        int statusCode;
        ClientResponse<FlightManifestDetailWaitlistResponseDto> response = null;
        try {
            String g4FlightReservationsURL = SystemProperty.G4_FLIGHT_SUMMARY_URL.getValue();
            if (StringUtils.isBlank(g4FlightReservationsURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_SUMMARY_URL + " not set");
            }
            FlightSummaryServiceClient flightSummaryServiceClient = ProxyFactory.create(FlightSummaryServiceClient.class, g4FlightReservationsURL);
            LOG.debug("Getting wait list for {} ", ToJson.toJson(flightManifestDetailWaitlistRequestDto));
            response = flightSummaryServiceClient.flightManifestDetailWaitlist(flightManifestDetailWaitlistRequestDto);
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String msg = SystemProperty.G4_FLIGHT_SUMMARY_URL.getValue() + " jsonRequest: " + flightManifestDetailWaitlistRequestDto + " returned status " + statusCode;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            result = response.getEntity();
            LOG.debug("Retrieved reservation :" + result);
            return result;
        } catch (Exception e) {
            LOG.error("Could not get wait list flight summary", e);
            throw new SystemException("Could not get wait list flight summary", e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

    public void refundOrder(String itinerary) {
        String result = null;
        ClientResponse<String> response = null;
        int statusCode = 0;
        try {
            String g4FlightReservationsURL = SystemProperty.G4_FLIGHT_ORDER_URL.getValue();
            if (StringUtils.isBlank(g4FlightReservationsURL)) {
                throw new SystemException("System property " + SystemProperty.G4_FLIGHT_ORDER_URL + " not set");
            }
            G4FlightOrderClient g4FlightOrderClient = ProxyFactory.create(G4FlightOrderClient.class, g4FlightReservationsURL);
            LOG.debug("Refunding order " + itinerary);
            response = g4FlightOrderClient.refundOrder(itinerary, Constants.CHANNEL_ID, Constants.CALLER_KEY, "G4TC");
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_ACCEPTED && statusCode != HttpStatus.SC_CREATED) {
                String msg = SystemProperty.G4_FLIGHT_ORDER_URL.getValue() + " refundOrder: " + itinerary + " returned " + statusCode;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            result = response.getEntity();
            LOG.debug("Refund itinerary result : " + result);
        } catch (Exception e) {
            // Do not log an error. If the order could not be refunded it will be recorded in that database
            throw new SystemException("Could not refund itinerary " + itinerary, e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }
}
