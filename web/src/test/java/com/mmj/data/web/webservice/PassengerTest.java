package com.mmj.data.web.webservice;

import com.mmj.data.core.dto.entity.PassengerDTO;
import com.mmj.data.core.dto.entity.TravelerRelationshipDTO;
import com.mmj.data.core.exception.BusinessException;
import com.mmj.data.core.exception.LimitReachException;
import com.mmj.data.core.util.ToJson;
import com.mmj.data.ejb.session.PassengerSB;
import com.mmj.data.web.integration.checks.G4JsonValidator;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.mmj.data.core.constant.Constants.DATE_FORMATTER;
import static org.mockito.Mockito.mock;

/**
 *
 */
@RunWith(Arquillian.class)
public class PassengerTest extends BaseTest {

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
    public void testCreateAndUpdateNewEmployee() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            HttpServletRequest mockRequest = mock(HttpServletRequest.class);

            final String input1 = "1920-02-15";
            final String input2 = "1965-10-28";
            final LocalDate localDate1 = LocalDate.parse(input1, DATE_FORMAT);
            final LocalDate localDate2 = LocalDate.parse(input2, DATE_FORMAT);

            TravelerRelationshipDTO relationship1 = new TravelerRelationshipDTO(1L, "PA", "Parent");
            TravelerRelationshipDTO relationship2 = new TravelerRelationshipDTO(2L, "CH", "Child");
            TravelerRelationshipDTO relationship3 = new TravelerRelationshipDTO(3L, "SP", "Spouse");
            TravelerRelationshipDTO relationship4 = new TravelerRelationshipDTO(4L, "DP", "Domestic Partner");
            TravelerRelationshipDTO relationship5 = new TravelerRelationshipDTO(5L, "TC", "Travel Companion");

            PassengerDTO testPass = new PassengerDTO();
            testPass.setEmployeeId("012091");  // Johnnathan Peterson
            testPass.setFirstName("Bob");
            testPass.setMiddleName("Happiness");
            testPass.setLastName("Marley");
            testPass.setGender("M");
            testPass.setRelationship(relationship1);
            testPass.setDob(localDate1);
            testPass.setPhone(2345678901L);
            testPass.setReAddress("Some address");
            testPass.setKnownTravelerNumber(null);
            testPass.setVerified(false);
            g4tcRestWebservice.createPassenger(testPass, mockRequest);

            List<PassengerDTO> results = passengerSB.getPassengerByEmployeeId("012091");
            PassengerDTO resultDTO = null;
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getMiddleName().equals("Happiness")) {
                    resultDTO = results.get(i);
                    break;
                }
            }

            Assert.assertEquals("012091", resultDTO.getEmployeeId());
            Assert.assertEquals("Bob", resultDTO.getFirstName());
            Assert.assertEquals("Happiness", resultDTO.getMiddleName());
            Assert.assertEquals("Marley", resultDTO.getLastName());
            Assert.assertEquals("M", resultDTO.getGender());
            Assert.assertEquals(relationship1.getId(), resultDTO.getRelationship().getId());
            Assert.assertEquals(true, resultDTO.getDob().equals(localDate1));
            Assert.assertEquals(2345678901L, (long) resultDTO.getPhone());
            Assert.assertEquals("Some address", resultDTO.getReAddress());
            Assert.assertEquals(null, resultDTO.getKnownTravelerNumber());
            Assert.assertEquals(false, resultDTO.isVerified());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    public void passengerSpouseLimitTest() throws BusinessException {

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        Response relationshipTypes = g4tcRestWebservice.getRelationshipTypes(mockRequest);
        List<TravelerRelationshipDTO> result = (List<TravelerRelationshipDTO>) relationshipTypes.getEntity();
        TravelerRelationshipDTO spouse = result.stream().filter(r -> r.getValue().equals("SP")).findFirst().get();
        PassengerDTO passengerDTO = getPassengerDTO(spouse);
        try {
            g4tcRestWebservice.createPassenger(passengerDTO, mockRequest);
        } catch (LimitReachException e) {
            // OK
        }

        TravelerRelationshipDTO domesticPartner = result.stream().filter(r -> r.getValue().equals("DP")).findFirst().get();
        passengerDTO.setRelationship(domesticPartner);
        try {
            g4tcRestWebservice.createPassenger(passengerDTO, mockRequest);
        } catch (LimitReachException e) {
            // OK
        }

        TravelerRelationshipDTO travelCompanion = result.stream().filter(r -> r.getValue().equals("TC")).findFirst().get();
        passengerDTO.setRelationship(travelCompanion);
        try {
            g4tcRestWebservice.createPassenger(passengerDTO, mockRequest);
        } catch (LimitReachException e) {
            // OK
        }
    }

    @Test(expected = LimitReachException.class)
    public void passengerParentLimitTest() throws BusinessException {

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        Response relationshipTypes = g4tcRestWebservice.getRelationshipTypes(mockRequest);
        List<TravelerRelationshipDTO> result = (List<TravelerRelationshipDTO>) relationshipTypes.getEntity();

        TravelerRelationshipDTO parent = result.stream().filter(r -> r.getValue().equals("PA")).findFirst().get();
        PassengerDTO passengerDTO = getPassengerDTO(parent);
        passengerDTO.setRelationship(parent);
        try {
            g4tcRestWebservice.createPassenger(passengerDTO, mockRequest);
            g4tcRestWebservice.createPassenger(passengerDTO, mockRequest);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        g4tcRestWebservice.createPassenger(passengerDTO, mockRequest);
    }

    @Test(expected = LimitReachException.class)
    public void passengerChildLimitTest() throws BusinessException {

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        Response relationshipTypes = g4tcRestWebservice.getRelationshipTypes(mockRequest);
        List<TravelerRelationshipDTO> result = (List<TravelerRelationshipDTO>) relationshipTypes.getEntity();

        TravelerRelationshipDTO child = result.stream().filter(r -> r.getValue().equals("CH")).findFirst().get();
        PassengerDTO passengerDTO = getPassengerDTO(child);
        passengerDTO.setRelationship(child);

        for (int i = 0; i < 7; i++) {
            try {
                g4tcRestWebservice.createPassenger(passengerDTO, mockRequest);
            } catch (Exception e) {
                Assert.fail(e.getMessage());
            }
        }
        g4tcRestWebservice.createPassenger(passengerDTO, mockRequest);
    }

    private PassengerDTO getPassengerDTO(TravelerRelationshipDTO travelerRelationshipDTO) {
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setDob(LocalDate.parse("1970-05-05", DATE_FORMATTER));
        passengerDTO.setEmployeeId("012088");
        passengerDTO.setFirstName("Lauren");
        passengerDTO.setGender("F");
        passengerDTO.setKnownTravelerNumber("");
        passengerDTO.setLastName("Smith");
        passengerDTO.setMiddleName(null);
        passengerDTO.setPhone(7025555555L);
        passengerDTO.setReAddress(null);
        passengerDTO.setRelationship(travelerRelationshipDTO);
        passengerDTO.setVerified(true);
        return passengerDTO;
    }

    // Run after other tests, so data in db.
    @Test
    public void jsonSchemaTest() {
        try {
            boolean result = false;
            List<PassengerDTO> response = passengerSB.getPassengerByEmployeeId("012091");
            String responseAsJson = ToJson.toJson(response);
            File file = new File("src/test/resources/Passenger.schema.json");
            Assert.assertEquals(true, G4JsonValidator.isValid(responseAsJson, file));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
