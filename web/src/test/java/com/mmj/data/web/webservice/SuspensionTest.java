package com.mmj.data.web.webservice;

import com.mmj.data.core.dto.entity.EmployeeFullDTO;
import com.mmj.data.core.dto.entity.SuspensionCommentDTO;
import com.mmj.data.core.dto.entity.SuspensionDTO;
import com.mmj.data.core.dto.entity.SuspensionTypeDTO;
import com.mmj.data.core.exception.NotAuthorizedTravelException;
import com.mmj.data.core.extclient.dto.FlightBookingRequestDto;
import com.mmj.data.core.util.DateTimeUtil;
import com.mmj.data.core.util.ToJson;
import com.mmj.data.ejb.session.EmployeeSB;
import com.mmj.data.ejb.session.SuspensionTypeSB;
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
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 *
 */
@RunWith(Arquillian.class)
public class SuspensionTest extends BaseTest {

    /**
     * G4 Travel Center Webservice. Needed for unit tests.
     */
    @Inject
    private G4tcRestWebservice g4tcRestWebservice;

    @Inject
    private EmployeeSB employeeSB;

    @Inject
    private SuspensionTypeSB suspensionTypeSB;

    @Deployment
    public static Archive<?> getDeploymentArchive() {

        return getDeployment();
    }

    @Test
    public void createSuspensionTest() {
        try {

            HttpServletRequest mockRequest = getHttpServletRequest(Constants.E12089);

            SuspensionDTO suspensionDTO = new SuspensionDTO();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            LocalDate begin = DateTimeUtil.getLocalDateNowGMT();
            begin.minusDays(10);

            LocalDate end = DateTimeUtil.getLocalDateNowGMT();
            begin.plusDays(10);

            SuspensionTypeDTO suspensionTypeDTO = suspensionTypeSB.getSuspensionTypeByValue("NWH");
            suspensionDTO.setEmployeeId(Constants.E12088);
            suspensionDTO.setBegin(begin);
            suspensionDTO.setEnd(end);
            suspensionDTO.setSuspensionType(suspensionTypeDTO);
            SuspensionCommentDTO suspensionCommentDTO = new SuspensionCommentDTO();
            suspensionCommentDTO.setComment("Welcome to Allegiant");
            suspensionDTO.addSuspensionComment(suspensionCommentDTO);
            Response response = g4tcRestWebservice.createSuspension(suspensionDTO, mockRequest);
            SuspensionDTO createdSuspensionDTO = (SuspensionDTO) response.getEntity();

            response  = g4tcRestWebservice.getSuspensionById(createdSuspensionDTO.getId(), mockRequest);
            createdSuspensionDTO = (SuspensionDTO) response.getEntity();

            Assert.assertEquals(Constants.E12088, createdSuspensionDTO.getEmployeeId());
            Assert.assertEquals(Constants.E12089, createdSuspensionDTO.getUserid());
            Assert.assertEquals(Constants.E12089, createdSuspensionDTO.getUsername());
            Assert.assertNotNull(createdSuspensionDTO.getCreated());

            Calendar created = Calendar.getInstance();
            created.setTimeZone(TimeZone.getTimeZone("UTC"));
            created.setTimeInMillis(createdSuspensionDTO.getCreated().getTime());
            created.get(Calendar.HOUR_OF_DAY);
            Calendar nowGMT = Calendar.getInstance();
            nowGMT.setTimeZone(TimeZone.getTimeZone("UTC"));
            nowGMT.setTimeInMillis(DateTimeUtil.getNowGMT().getTime());
            Assert.assertEquals(created.get(Calendar.HOUR_OF_DAY), nowGMT.get(Calendar.HOUR_OF_DAY));

            Assert.assertTrue(begin.equals(createdSuspensionDTO.getBegin()));
            Assert.assertTrue(end.equals(createdSuspensionDTO.getEnd()));
            Assert.assertEquals("NWH", createdSuspensionDTO.getSuspensionType().getValue());

            List<SuspensionCommentDTO> comments = createdSuspensionDTO.getComments();
            Assert.assertEquals(1, comments.size());

            SuspensionCommentDTO createdSuspensionCommentDTO = comments.get(0);
            Assert.assertEquals("Welcome to Allegiant", createdSuspensionCommentDTO.getComment());
            Assert.assertEquals(Constants.E12089, createdSuspensionCommentDTO.getUsername());
            Assert.assertEquals(Constants.E12089, createdSuspensionCommentDTO.getUserid());
            Assert.assertNotNull(createdSuspensionCommentDTO.getCreated());
            Assert.assertEquals(createdSuspensionDTO.getId(), createdSuspensionCommentDTO.getSuspensionId());

            mockRequest = getHttpServletRequest(Constants.E12088);

            try {
                response = g4tcRestWebservice.createOrder(new FlightBookingRequestDto(), "false", mockRequest);
            } catch (NotAuthorizedTravelException e) {
                // OK
            }

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void activeSuspensionTest() {
        try {

            HttpServletRequest mockRequest = getHttpServletRequest(Constants.TEST_USER);

            Response response = g4tcRestWebservice.getEmployeeById("012090", mockRequest);
            EmployeeFullDTO employeeFullDTO = (EmployeeFullDTO) response.getEntity();

            Assert.assertEquals(false, employeeFullDTO.getTravelStatus());

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    // Run after other tests, so data in db.
    @Test
    public void jsonSchemaTest() {
        try {
            HttpServletRequest mockRequest = getHttpServletRequest(Constants.TEST_USER);
            Response response  = g4tcRestWebservice.getSuspensionsByEmployeeId("012090", mockRequest);
            List<SuspensionDTO> suspensions = (List<SuspensionDTO>) response.getEntity();
            String responseAsJson = ToJson.toJson(suspensions);
            File file = new File("src/test/resources/SuspensionsByEmployeeId.schema.json");
            Assert.assertEquals(true, G4JsonValidator.isValid(responseAsJson, file));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

}