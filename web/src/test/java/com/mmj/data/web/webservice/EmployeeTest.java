package com.mmj.data.web.webservice;

import com.mmj.data.core.dto.entity.EmployeeFullDTO;
import com.mmj.data.core.dto.entity.EmployeePartialDTO;
import com.mmj.data.core.dto.entity.EmployeePatchDTO;
import com.mmj.data.core.dto.entity.SaCodeDTO;
import com.mmj.data.core.dto.entity.SuspensionDTO;
import com.mmj.data.core.enums.ConfigurationCategoryEnum;
import com.mmj.data.core.enums.ConfigurationIdentityEnum;
import com.mmj.data.core.enums.SACodeEnum;
import com.mmj.data.core.util.DateTimeUtil;
import com.mmj.data.core.util.ToJson;
import com.mmj.data.ejb.dao.ConfigurationDao;
import com.mmj.data.ejb.model.ConfigurationEN;
import com.mmj.data.ejb.session.EmployeeSB;
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
import java.time.Month;
import java.util.Set;

import static com.mmj.data.web.webservice.Constants.E12088;
import static com.mmj.data.web.webservice.Constants.E12089;

/**
 *
 */
@RunWith(Arquillian.class)
public class EmployeeTest extends BaseTest {

    /**
     * G4 Travel Center Webservice. Needed for unit tests.
     */
    @Inject
    private G4tcRestWebservice g4tcRestWebservice;

    @Inject
    private EmployeeSB employeeSB;

    @Inject
    private ConfigurationDao configurationDao;

    @Deployment
    public static Archive<?> getDeploymentArchive() {

        return getDeployment();
    }

    @Test
    public void testPatchEmployee() {
        try {
            HttpServletRequest mockRequest = getHttpServletRequest(Constants.TEST_USER);

            SaCodeDTO saCodeDTO = new SaCodeDTO(4L, SACodeEnum.SA4.name(), "Don't care", true);
            EmployeePatchDTO employeePatchDTO = new EmployeePatchDTO(Constants.E12345, 88, 87, saCodeDTO);
            g4tcRestWebservice.patchEmployee(employeePatchDTO, mockRequest);
            EmployeeFullDTO employee = employeeSB.getEmployeeById(Constants.E12345);
            Assert.assertEquals(Constants.E12345, employee.getEmployeeId());
            Assert.assertEquals(88, employee.getGuestPassesAlloted().intValue());
            Assert.assertEquals(87, employee.getVacationPassesAlloted().intValue());
            Assert.assertEquals(4, employee.getSaCode().getId().intValue());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testCreateAndUpdateNewEmployee() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            HttpServletRequest mockRequest = getHttpServletRequest(Constants.TEST_USER);
            LocalDate now = DateTimeUtil.getLocalDateNowGMT();

            EmployeePartialDTO employeePartialDTO = new EmployeePartialDTO(
                    "009999",
                    null,
                    "Jr.",
                    "John",
                    null,
                    "Doe",
                    "john.doe@allegiantair.com",
                    "IronMan",
                    "A",
                    DateTimeUtil.dateToLocalDate(sdf.parse("1970-02-01")), // dob
                    now, // lastHireDate
                    null, // termDate
                    "7021115454",
                    "0690",
                    "HQ",
                    "681",
                    "TRAINING",
                    E12088,
                    null,
                    "R",
                    "M"
            );

            g4tcRestWebservice.createEmployee(employeePartialDTO, mockRequest);
            EmployeeFullDTO employee = employeeSB.getEmployeeById("009999");

            Assert.assertEquals("009999", employee.getEmployeeId());
            Assert.assertEquals("Mr.", employee.getSalutation());
            Assert.assertEquals("Jr.", employee.getSuffix());
            Assert.assertEquals("John", employee.getFirstName());
            Assert.assertEquals(null, employee.getMiddleName());
            Assert.assertEquals("Doe", employee.getLastName());
            Assert.assertEquals("A", employee.getStatus());
            Assert.assertEquals("john.doe@allegiantair.com", employee.getEmail());
            Assert.assertEquals("IronMan", employee.getTitle());
            Assert.assertEquals(employeeSB.getGuestPasses(employeePartialDTO), employee.getGuestPassesAlloted().intValue());
            Assert.assertTrue(LocalDate.of(1970, Month.FEBRUARY, 1).equals(employee.getDob()));
            Assert.assertTrue(now.isEqual(employee.getLastHireDate()));
            Assert.assertNull(employee.getTermDate());
            Assert.assertEquals("7021115454", employee.getPhone());
            Assert.assertEquals("0690", employee.getJobCode());
            Assert.assertEquals("HQ", employee.getLocation());
            Assert.assertEquals("681", employee.getDepartmentId());
            Assert.assertEquals("TRAINING", employee.getDepartmentName());
            Assert.assertEquals(E12088, employee.getManagerId());
            Assert.assertEquals("Kristian Lind", employee.getManagerName());
            Assert.assertEquals("R", employee.getEmployeeType());
            Assert.assertEquals("M", employee.getGender());

            String responseAsJson = ToJson.toJson(employee);
            System.out.println(responseAsJson);
            File file = new File("src/test/resources/EmployeeFull.schema.json");
            Assert.assertEquals(true, G4JsonValidator.isValid(responseAsJson, file));

            Set<SuspensionDTO> suspensions = employee.getSuspensions();
            Assert.assertEquals(1, suspensions.size());

            for (SuspensionDTO suspension : suspensions) {
                Assert.assertEquals("System", suspension.getUserid());
                Assert.assertEquals("System", suspension.getUsername());
                Assert.assertEquals("009999", suspension.getEmployeeId());
                Assert.assertEquals("NWH", suspension.getSuspensionType().getValue());
                //Assert.assertTrue(LocalDate.of(2010, Month.FEBRUARY, 1).equals(suspension.getBegin()));
                Assert.assertTrue(now.isEqual(suspension.getBegin()));
                ConfigurationEN configurationEN = configurationDao.getConfigurationByCatIden(ConfigurationCategoryEnum.SUSPENSION, ConfigurationIdentityEnum.NEW_HIRE_SUSPENSION_LENGTH);
                LocalDate end = suspension.getBegin().plusDays(Long.parseLong(configurationEN.getValue()));
                Assert.assertTrue(end.isEqual(suspension.getEnd()));
            }

            employeePartialDTO = new EmployeePartialDTO(
                    "009999",
                    null,
                    null,
                    "Susan",
                    "L",
                    "Petrio",
                    "susan.petrio@allegiantair.com",
                    "BatGirl",
                    "T",
                    DateTimeUtil.dateToLocalDate(sdf.parse("1971-02-01")), // dob
                    now.plusDays(10), // lastHireDate
                    null, // termDate
                    "8555556666",
                    "0670",
                    "LAX",
                    "682",
                    "ADMINISTRATION",
                    E12089,
                    null,
                    "R",
                    "F"
            );

            g4tcRestWebservice.updateEmployee(employeePartialDTO, mockRequest);

            employee = employeeSB.getEmployeeById("009999");
            Assert.assertEquals("Ms.", employee.getSalutation());
            Assert.assertNull(employee.getSuffix());
            Assert.assertEquals("Susan", employee.getFirstName());
            Assert.assertEquals("L", employee.getMiddleName());
            Assert.assertEquals("Petrio", employee.getLastName());
            Assert.assertEquals("T", employee.getStatus());
            Assert.assertEquals("susan.petrio@allegiantair.com", employee.getEmail());
            Assert.assertEquals("BatGirl", employee.getTitle());
            Assert.assertEquals(employeeSB.getGuestPasses(employeePartialDTO), employee.getGuestPassesAlloted().intValue());
            Assert.assertTrue(LocalDate.of(1971, Month.FEBRUARY, 1).equals(employee.getDob()));
            Assert.assertTrue(now.plusDays(10).isEqual(employee.getLastHireDate()));
            Assert.assertNull(employee.getTermDate());
            Assert.assertEquals("8555556666", employee.getPhone());
            Assert.assertEquals("0670", employee.getJobCode());
            Assert.assertEquals("LAX", employee.getLocation());
            Assert.assertEquals("682", employee.getDepartmentId());
            Assert.assertEquals("ADMINISTRATION", employee.getDepartmentName());
            Assert.assertEquals(E12089, employee.getManagerId());
            Assert.assertEquals("Peter Hahn", employee.getManagerName());
            Assert.assertEquals("R", employee.getEmployeeType());
            Assert.assertEquals("F", employee.getGender());

            suspensions = employee.getSuspensions();
            Assert.assertEquals(1, suspensions.size());

            for (SuspensionDTO suspension : suspensions) {
                Assert.assertEquals("System", suspension.getUserid());
                Assert.assertEquals("System", suspension.getUsername());
                Assert.assertEquals("009999", suspension.getEmployeeId());
                Assert.assertEquals("NWH", suspension.getSuspensionType().getValue());
                Assert.assertTrue(now.plusDays(10).isEqual(suspension.getBegin()));
                ConfigurationEN configurationEN = configurationDao.getConfigurationByCatIden(ConfigurationCategoryEnum.SUSPENSION, ConfigurationIdentityEnum.NEW_HIRE_SUSPENSION_LENGTH);
                LocalDate end = suspension.getBegin().plusDays(Long.parseLong(configurationEN.getValue()));
                Assert.assertTrue(end.isEqual(suspension.getEnd()));
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testGetEmployeeProfile() {
        try {
            HttpServletRequest mockRequest = getHttpServletRequest(Constants.E12088);
            Response response = g4tcRestWebservice.getEmployeeById(mockRequest);
            EmployeeFullDTO employeeFullDTO = (EmployeeFullDTO) response.getEntity();
            Assert.assertEquals(Constants.E12088, employeeFullDTO.getEmployeeId());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
