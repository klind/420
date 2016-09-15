package com.mmj.data.web.webservice;

import com.mmj.data.core.enums.SACodeEnum;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.LimitReachException;
import com.mmj.data.core.extclient.dto.AncillaryFeeDto;
import com.mmj.data.core.extclient.dto.EmployeeInformationDto;
import com.mmj.data.core.extclient.dto.FlightBookingRequestDto;
import com.mmj.data.core.extclient.dto.FlightPassengerDto;
import com.mmj.data.core.extclient.dto.SegmentBookingRequestDto;
import com.mmj.data.ejb.session.ConfigurationSB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 */
@RunWith(Arquillian.class)
public class BookingTest extends BaseTest {

    /**
     * G4 Travel Center Webservice. Needed for unit tests.
     */
    @Inject
    private G4tcRestWebservice g4tcRestWebservice;

    @Inject
    private ConfigurationSB configurationSB;

    @Deployment
    public static Archive<?> getDeploymentArchive() {

        return getDeployment();
    }

    @Test(expected = LimitReachException.class)
    public void testVacationExceeded() throws BusinessException {
        HttpServletRequest mockRequest = getHttpServletRequest(Constants.E12088);
        FlightBookingRequestDto flightBookingRequestDto = getFlightBookingRequestDto();
        g4tcRestWebservice.createOrder(flightBookingRequestDto, "true", mockRequest);
    }

    @Test(expected = BusinessException.class)
    public void testToManySegments() throws BusinessException {
        HttpServletRequest mockRequest = getHttpServletRequest(Constants.E12088);
        FlightBookingRequestDto flightBookingRequestDto = getFlightBookingRequestDto();
        flightBookingRequestDto.getSegments().add(new SegmentBookingRequestDto());
        flightBookingRequestDto.setBookingType("BP");
        g4tcRestWebservice.createOrder(flightBookingRequestDto, "false", mockRequest);
    }

    @Test(expected = BusinessException.class)
    public void testSACODE5OnGuestPasses() throws BusinessException {
        HttpServletRequest mockRequest = getHttpServletRequest(Constants.E12088);
        FlightBookingRequestDto flightBookingRequestDto = getFlightBookingRequestDto();
        flightBookingRequestDto.getSegments().add(new SegmentBookingRequestDto());
        flightBookingRequestDto.setBookingType("BP");
        SegmentBookingRequestDto segmentBookingRequestDto = flightBookingRequestDto.getSegments().get(0);
        List<FlightPassengerDto> flightPassDtos = segmentBookingRequestDto.getFlightPassengers();
        FlightPassengerDto flightPassDto = flightPassDtos.get(0);
        flightPassDto.getEmployeeInformation().setSaCode(SACodeEnum.SA4.name());
        g4tcRestWebservice.createOrder(flightBookingRequestDto, "false", mockRequest);
    }


    private FlightBookingRequestDto getFlightBookingRequestDto() {
        AncillaryFeeDto bagAncillaryFeeDto = new AncillaryFeeDto();
        bagAncillaryFeeDto.setCode("BB");
        AncillaryFeeDto ssrAncillaryFeeDto = new AncillaryFeeDto();
        ssrAncillaryFeeDto.setCode("LA");
        FlightPassengerDto flightPassengerDto = new FlightPassengerDto();

        SegmentBookingRequestDto segmentBookingRequestDto = new SegmentBookingRequestDto();
        FlightBookingRequestDto flightBookingRequestDto = new FlightBookingRequestDto();
        flightBookingRequestDto.setBookingType("EM");
        flightBookingRequestDto.getSegments().add(segmentBookingRequestDto);
        segmentBookingRequestDto.getFlightPassengers().add(flightPassengerDto);
        flightPassengerDto.getBags().add(bagAncillaryFeeDto);
        flightPassengerDto.getSsrs().add(ssrAncillaryFeeDto);
        flightPassengerDto.setEmployeeInformation(new EmployeeInformationDto());

        return flightBookingRequestDto;
    }
}
