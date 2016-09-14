package com.mmj.data.web.webservice;

import com.mmj.data.core.dto.entity.GuestDTO;
import com.mmj.data.core.util.DateTimeUtil;
import com.mmj.data.ejb.session.PassengerSB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 */
@RunWith(Arquillian.class)
public class GuestTest extends BaseTest {

    /**
     * G4 Travel Center Webservice. Needed for unit tests.
     */
    @Inject
    private G4tcRestWebservice g4tcRestWebservice;

    @Inject
    private PassengerSB passengerSB;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Deployment
    public static Archive<?> getDeploymentArchive() {

        return getDeployment();
    }

    @Test
    public void guests()  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {

            HttpServletRequest mockRequest = getHttpServletRequest(Constants.E12088);
            Response guests = g4tcRestWebservice.getGuestsByEmployeeId(mockRequest);
            List<GuestDTO> result = (List<GuestDTO>) guests.getEntity();

            Assert.assertTrue(result.size() == 2);

            mockRequest = getHttpServletRequest(Constants.E12089);

            GuestDTO guestDTO = new GuestDTO();
            guestDTO.setEmployeeId(Constants.E12089);
            guestDTO.setFirstName("John");
            guestDTO.setMiddleName(null);
            guestDTO.setLastName("Smith");
            guestDTO.setDob(DateTimeUtil.dateToLocalDate(sdf.parse("1970-02-01")));
            guestDTO.setGender("M");
            guestDTO.setKnownTravelerNumber(null);

            g4tcRestWebservice.createGuest(guestDTO, mockRequest);

            guests = g4tcRestWebservice.getGuestsByEmployeeId(mockRequest);
            result = (List<GuestDTO>) guests.getEntity();

            Assert.assertTrue(result.size() == 1);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void guestsLimit()  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {

            HttpServletRequest mockRequest = getHttpServletRequest(Constants.E12089);

            GuestDTO guestDTO = new GuestDTO();

            for (int i = 0; i < 9; i++) {
                guestDTO = new GuestDTO();
                guestDTO.setEmployeeId(Constants.E12089);
                guestDTO.setFirstName("John");
                guestDTO.setMiddleName(null);
                guestDTO.setLastName("Smith");
                guestDTO.setDob(DateTimeUtil.dateToLocalDate(sdf.parse("1970-02-01")));
                guestDTO.setGender("M");
                guestDTO.setKnownTravelerNumber(null);
                g4tcRestWebservice.createGuest(guestDTO, mockRequest);
            }
            Response guests = g4tcRestWebservice.getGuestsByEmployeeId(mockRequest);
            List<GuestDTO> result = (List<GuestDTO>) guests.getEntity();

            Assert.assertEquals(result.size(), 10);

            g4tcRestWebservice.createGuest(guestDTO, mockRequest);

            guests = g4tcRestWebservice.getGuestsByEmployeeId(mockRequest);
            result = (List<GuestDTO>) guests.getEntity();

            Assert.assertEquals(result.size(), 10);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
