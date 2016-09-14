package com.mmj.data.ejb.session;

import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.SystemException;
import com.mmj.data.core.enums.SACodeEnum;
import com.mmj.data.core.extclient.dto.PassengerSegmentDto;
import com.mmj.data.core.extclient.dto.ReservationDto;
import com.mmj.data.core.flights.FlightsService;
import com.mmj.data.core.util.DateTimeUtil;
import com.mmj.data.ejb.dao.EmployeeDao;
import com.mmj.data.ejb.dao.RefundDao;
import com.mmj.data.ejb.dao.TimerDao;
import com.mmj.data.ejb.model.FailedPaymentRefundsEN;
import com.mmj.data.ejb.model.RefundEN;
import com.mmj.data.ejb.session.schedule.GuestPassAndVacationRefundScheduleSB;
import com.mmj.data.ejb.session.schedule.RefundPaymentScheduleSB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mmj.data.core.constant.Constants.PASSENGER_SEGMENT;
import static com.mmj.data.core.constant.Constants.PASSENGER_WAITLIST;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@LocalBean
public class RefundSB {
    private static final Logger LOG = LoggerFactory.getLogger(RefundSB.class);

    @Inject
    private EmployeeDao employeeDao;

    @Inject
    private FlightsService flightsService;

    @Inject
    private RefundDao refundDao;

    @Inject
    private TimerDao timerDao;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RefundEN> refundGuestPassesAndVacationUpgrades() {
        List<RefundEN> refunded = new ArrayList<>();
        LocalDate yesterday = DateTimeUtil.getLocalDateNowGMT();//.minusDays(1);
        LocalDate searchStartDate = getStartSearchDate(GuestPassAndVacationRefundScheduleSB.GUEST_AND_VACATION_REFUND);
        refundUnusedGuestPasses(searchStartDate, yesterday, refunded);
        refundUnusedVacationUpgrades(searchStartDate, yesterday, refunded);
        timerDao.updateLastRuntime(GuestPassAndVacationRefundScheduleSB.GUEST_AND_VACATION_REFUND, DateTimeUtil.localDateToDate(yesterday));
        return refunded;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RefundEN> refundPayments() {
        LocalDate yesterday = DateTimeUtil.getLocalDateNowGMT().minusDays(1);
        LocalDate searchStartDate = getStartSearchDate(RefundPaymentScheduleSB.PAYMENT_REFUND);
        List<RefundEN> refunded = refundPaymentsOnUnusedGuestPasses(searchStartDate, yesterday);
        timerDao.updateLastRuntime(RefundPaymentScheduleSB.PAYMENT_REFUND, DateTimeUtil.localDateToDate(yesterday));
        return refunded;
    }

    private LocalDate getStartSearchDate(String jobname) {
        LocalDate yesterday = DateTimeUtil.getLocalDateNowGMT().minusDays(1);
        LocalDate lastRunTime = DateTimeUtil.dateToLocalDate(timerDao.getLastRuntime(jobname));
        LocalDate searchStartDate = lastRunTime != null ?
                // never search for date that is after yesterday
                (lastRunTime.plusDays(1).isAfter(yesterday) ? yesterday : lastRunTime.plusDays(1))
                : /* If there was no date in db start search date is yesterday */ DateTimeUtil.getLocalDateNowGMT().minusDays(1);
        return searchStartDate;
    }

    /**
     * This method retrieves the unused BP itineraries and refunds them back to the employee
     *
     * @param searchStartDate
     * @param searchEndDate
     * @param result
     */
    public void refundUnusedGuestPasses(LocalDate searchStartDate, LocalDate searchEndDate, List<RefundEN> result) {
        List<ReservationDto> reservations = flightsService.getUnusedItineraries(searchStartDate, searchEndDate, "BP", PASSENGER_SEGMENT);
        for (ReservationDto reservation : reservations) {
            String employeeNumber = reservation.getCustomerNumber();
            String itinerary = reservation.getItinerary();
            List<PassengerSegmentDto> passengerSegments = reservation.getPassengerSegments();
            for (PassengerSegmentDto passengerSegment : passengerSegments) {
                String paxNum = passengerSegment.getPaxNum();
                RefundEN refundEN = refundOneGuestPass(employeeNumber, itinerary, paxNum);
                if (refundEN != null) {
                    result.add(refundEN);
                }
            }
        }
    }

    /**
     * This method retrieves the unused BP itineraries and refunds payments
     *
     * @param searchStartDate
     * @param searchEndDate
     */
    public List<RefundEN> refundPaymentsOnUnusedGuestPasses(LocalDate searchStartDate, LocalDate searchEndDate) {
        List<ReservationDto> reservations = null;
        List<RefundEN> refunded = new ArrayList<>();
        reservations = flightsService.getUnusedItineraries(searchStartDate, searchEndDate, "BP", PASSENGER_SEGMENT);
        String employeeNumber;
        String itinerary;
        for (ReservationDto reservation : reservations) {
            employeeNumber = reservation.getCustomerNumber();
            itinerary = reservation.getItinerary();
            BigDecimal paidAmount = reservation.getPaidAmount();
            BigDecimal balanceDue = reservation.getBalanceDue();
            if ((paidAmount != null && paidAmount.compareTo(new BigDecimal(0)) != 0) || (balanceDue != null && balanceDue.compareTo(new BigDecimal(0)) != 0)) {
                RefundEN refundEN = refundOrder(employeeNumber, itinerary, reservation);
                if (refundEN != null) {
                    refunded.add(refundEN);
                }
            }
        }
        return refunded;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveFailedPaymentRefunds(ReservationDto reservation) {
        FailedPaymentRefundsEN failedPaymentRefundsEN = new FailedPaymentRefundsEN();
        failedPaymentRefundsEN.setBalancedue(reservation.getBalanceDue());
        failedPaymentRefundsEN.setBookedDate(reservation.getBookedDate());
        failedPaymentRefundsEN.setDateFailed(DateTimeUtil.getNowGMT());
        failedPaymentRefundsEN.setEnployeeId(reservation.getCustomerNumber());
        failedPaymentRefundsEN.setItinerary(reservation.getItinerary());
        failedPaymentRefundsEN.setPadAmount(reservation.getPaidAmount());
        employeeDao.saveFailedPaymentRefunds(failedPaymentRefundsEN);
    }

    /**
     * This method retrieves unused employee itineraries, checks for any that contain vacation upgrades, and refunds
     * them
     *
     * @param searchStartDate
     * @param searchEndDate
     * @param result
     */
    public void refundUnusedVacationUpgrades(LocalDate searchStartDate, LocalDate searchEndDate, List<RefundEN> result) {
        try {
            List<ReservationDto> reservations = flightsService.getUnusedItineraries(searchStartDate, searchEndDate, "EM", PASSENGER_SEGMENT+","+PASSENGER_WAITLIST);
            s:
            for (ReservationDto reservation : reservations) {
                String employeeId = reservation.getCustomerNumber();
                if (reservation.getPassengerSegments() != null) {
                    for (PassengerSegmentDto segment : reservation.getPassengerSegments()) {
                        String employeeSaCode = null;
                        try {
                            employeeSaCode = employeeDao.getEmployeeSACode(employeeId);
                        } catch (BusinessException e) {
                            LOG.warn("Could not refund vacation upgrade for employee {} as the employee id could not be found", employeeId);
                            continue s;
                        }
                        if (employeeSaCode.equals(SACodeEnum.SA3.name()) && segment.getEmployeeInformation() != null && segment.getEmployeeInformation().getSaCode().equals(SACodeEnum.SA2.name())) {
                            String employeeNumber = reservation.getCustomerNumber();
                            String itinerary = reservation.getItinerary();
                            String paxNum = segment.getPaxNum();
                            RefundEN refundEN = refundOneVacationUpgrade(employeeId, itinerary, paxNum);
                            if (refundEN != null) {
                                result.add(refundEN);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Failed to refund vacation upgrades to employee", e);
            throw new SystemException("Failed to refund vacation upgrades to employee", e);
        }
    }

    /**
     * @param employeeId
     * @param confirmationNumber
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RefundEN refundOneGuestPass(String employeeId, String confirmationNumber, String pax) {
        RefundEN refundEN = null;
        if (!refundDao.refundExists(employeeId, confirmationNumber, pax)) {
            LOG.debug("Refunding a guest pass to employee {} on itinerary {}", employeeId, confirmationNumber);
            employeeDao.refundOneGuestPass(employeeId);
            refundEN = new RefundEN();
            refundEN.setEmployeeId(employeeId);
            refundEN.setItn(confirmationNumber);
            refundEN.setPax(pax);
            refundEN.setRefundDate(DateTimeUtil.getNowGMT());
            refundEN.setType("BP");
            refundDao.saveRefund(refundEN);
        }
        return refundEN;
    }

    /**
     * @param employeeId
     * @param confirmationNumber
     * @param pax
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RefundEN refundOneVacationUpgrade(String employeeId, String confirmationNumber, String pax) throws BusinessException {
        RefundEN refundEN = null;
        if (!refundDao.refundExists(employeeId, confirmationNumber, pax)) {
            LOG.debug("Refunding a vacation upgrade to employee {} on itinerary {}", employeeId, confirmationNumber);
            employeeDao.refundOneVacationUpgrade(employeeId);
            refundEN = new RefundEN();
            refundEN.setEmployeeId(employeeId);
            refundEN.setItn(confirmationNumber);
            refundEN.setPax(pax);
            refundEN.setRefundDate(DateTimeUtil.getNowGMT());
            refundEN.setType("VA");
            refundDao.saveRefund(refundEN);
        }
        return refundEN;
    }

    /**
     * @param employeeId
     * @param confirmationNumber
     * @param empReservationDto
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public RefundEN refundOrder(String employeeId, String confirmationNumber, ReservationDto empReservationDto) {
        RefundEN refundEN = null;
        try {
            if (!refundDao.paymentRefundExists(employeeId, confirmationNumber)) {
                LOG.debug("Refunding a payment to employee {} on itinerary {}", employeeId, employeeId);
                flightsService.refundOrder(confirmationNumber);
                refundEN = new RefundEN();
                refundEN.setEmployeeId(employeeId);
                refundEN.setItn(confirmationNumber);
                refundEN.setRefundDate(DateTimeUtil.getNowGMT());
                refundEN.setType("PAYMENT");
                refundDao.saveRefund(refundEN);
            }
        } catch (Exception e) {
            LOG.error("Failed to refund payment for cancelled itenirary for employee {} and flight {}", employeeId, confirmationNumber);
            saveFailedPaymentRefunds(empReservationDto);
        }
        return refundEN;
    }
}
