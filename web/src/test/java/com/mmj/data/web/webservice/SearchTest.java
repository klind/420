package com.mmj.data.web.webservice;

import com.mmj.data.core.dto.entity.EmployeeBaseDTO;
import com.mmj.data.core.dto.entity.PassengerDTO;
import com.mmj.data.core.dto.entity.SearchResultDTO;
import com.mmj.data.core.util.DateTimeUtil;
import com.mmj.data.ejb.session.EmployeeSB;
import org.apache.commons.lang3.StringUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 *
 */
@RunWith(Arquillian.class)
public class SearchTest extends BaseTest {

    /**
     * G4 Travel Center Webservice. Needed for unit tests.
     */
    @Inject
    private G4tcRestWebservice g4tcRestWebservice;

    @Inject
    private EmployeeSB employeeSB;

    @Deployment
    public static Archive<?> getDeploymentArchive() {

        return getDeployment();
    }

    /**
     * Unit test to verify we can get an employee's details by their id.
     */
    @Test
    public void testGetPassengerByEmployeeId() {
        try {
            HttpServletRequest mockRequest = mock(HttpServletRequest.class);
            Response passengerByEmployeeId = g4tcRestWebservice.getPassengersByEmployeeId("012088", mockRequest);
            List<PassengerDTO> passengerDTOs = (List<PassengerDTO>) passengerByEmployeeId.getEntity();
            Collections.sort(passengerDTOs, PassengerDTO.ID_COMPARATOR);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            PassengerDTO passengerDTO = passengerDTOs.get(0);
            Assert.assertEquals("1970-01-01", simpleDateFormat.format(DateTimeUtil.localDateToDate(passengerDTO.getDob())));
            Assert.assertEquals("John", passengerDTO.getFirstName());
            Assert.assertNull(passengerDTO.getMiddleName());
            Assert.assertEquals("Doe", passengerDTO.getLastName());
            Assert.assertEquals("M", passengerDTO.getGender());
            Assert.assertEquals("101", passengerDTO.getKnownTravelerNumber());
            Assert.assertTrue(StringUtils.isBlank(passengerDTO.getReAddress()));
            Assert.assertNull(passengerDTO.getPhone());
            Assert.assertEquals("CH", passengerDTO.getRelationship().getValue());

            passengerDTO = passengerDTOs.get(1);
            Assert.assertEquals("1972-01-01", simpleDateFormat.format(DateTimeUtil.localDateToDate(passengerDTO.getDob())));
            Assert.assertEquals("Christina", passengerDTO.getFirstName());
            Assert.assertNull(passengerDTO.getMiddleName());
            Assert.assertEquals("Young", passengerDTO.getLastName());
            Assert.assertEquals("F", passengerDTO.getGender());
            Assert.assertEquals("102", passengerDTO.getKnownTravelerNumber());
            Assert.assertTrue(StringUtils.isBlank(passengerDTO.getReAddress()));
            Assert.assertEquals(7025555555L, passengerDTO.getPhone().longValue());
            Assert.assertEquals("SP", passengerDTO.getRelationship().getValue());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void searchTest() {
        try {
            HttpServletRequest mockRequest = mock(HttpServletRequest.class);
            HttpServletResponse mockResponse = mock(HttpServletResponse.class);

            Response response = g4tcRestWebservice.searchEmployees("012088", null, null, null, 0, mockRequest, mockResponse);
            SearchResultDTO searchResultDTO = (SearchResultDTO) response.getEntity();

            List<EmployeeBaseDTO> result = searchResultDTO.getEmployeeBaseDTOs();

            Assert.assertEquals(1, result.size());

            response = g4tcRestWebservice.searchEmployees(null, "kristian.lind@allegiantair.com", null, null, 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(1, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, "Kristian", null, 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(1, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, null, "Lind", 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(1, result.size());

            response = g4tcRestWebservice.searchEmployees("012088", "kristian.lind@allegiantair.com", "Kristian", "Lind", 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(1, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, "Kris*", null, 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(1, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, null, "Lin*", 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(1, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, "*tian", null, 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(1, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, null, "*ind", 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(1, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, "*ohn*", null, 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(2, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, null, "*eters*", 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(2, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, "Johnn*", null, 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(2, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, null, "Peters*", 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(2, result.size());

            // negative
            response = g4tcRestWebservice.searchEmployees("01111", null, null, null, 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(0, result.size());

            response = g4tcRestWebservice.searchEmployees(null, "no.way@allegiantair.com", null, null, 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(0, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, "Sara", null, 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(0, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, null, "Smith", 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(0, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, "Tomm*", null, 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(0, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, null, "Tric*", 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(0, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, "*ander", null, 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(0, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, null, "*rica", 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(0, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, "*bob*", null, 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(0, result.size());

            response = g4tcRestWebservice.searchEmployees(null, null, null, "*borg*", 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(0, result.size());

            response = g4tcRestWebservice.searchEmployees("012088", "kristian.lind@allegiantair.com", "Kristian", "Hansen", 0, mockRequest, mockResponse);
            result = ((SearchResultDTO) response.getEntity()).getEmployeeBaseDTOs();
            Assert.assertEquals(0, result.size());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
