package com.mmj.data.web.webservice;

import com.mmj.data.core.dto.entity.SaCodeDTO;
import com.mmj.data.core.enums.SACodeEnum;
import com.mmj.data.core.util.ToJson;
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
import java.util.List;

/**
 *
 */
@RunWith(Arquillian.class)
public class SaCodeTest extends BaseTest {

    private static String TEST_USER = "999999";

    /**
     * G4 Travel Center Webservice. Needed for unit tests.
     */
    @Inject
    private G4tcRestWebservice g4tcRestWebservice;

    @Inject
    private EmployeeSB saCodeSB;

    @Deployment
    public static Archive<?> getDeploymentArchive() {

        return getDeployment();
    }

    @Test
    public void updateSaCodeTest() {
        try {
            HttpServletRequest mockRequest = getHttpServletRequest(Constants.E12088);
            final String sa1 = SACodeEnum.SA1.name();
            final String sa2 = SACodeEnum.SA2.name();
            final String description1 = "New SA 1 description.";
            final String description2 = "This is a really long description for SA2. 01234567890123456879012345678901234567890123456789";
            SaCodeDTO saCodeDTO1 = new SaCodeDTO(1L, sa1, description1, true);
            SaCodeDTO saCodeDTO2 = new SaCodeDTO(2L, sa2, description2, false);

            Response response = g4tcRestWebservice.updateSaCode(saCodeDTO1, mockRequest);
            SaCodeDTO updatedSaCodeDTO = (SaCodeDTO) response.getEntity();
            Assert.assertEquals(1, updatedSaCodeDTO.getId().intValue());
            Assert.assertEquals(true, sa1.equals(updatedSaCodeDTO.getSaCode()));
            Assert.assertEquals(true, description1.equals(updatedSaCodeDTO.getDescription()));
            Assert.assertEquals(true, updatedSaCodeDTO.isActive());

            response = g4tcRestWebservice.updateSaCode(saCodeDTO2, mockRequest);
            updatedSaCodeDTO = (SaCodeDTO) response.getEntity();
            Assert.assertEquals(2, updatedSaCodeDTO.getId().intValue());
            Assert.assertEquals(true, sa2.equals(updatedSaCodeDTO.getSaCode()));
            Assert.assertEquals(true, description2.equals(updatedSaCodeDTO.getDescription()));
            Assert.assertEquals(false, updatedSaCodeDTO.isActive());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Run after other tests, so data in db.
    @Test
    public void jsonSchemaTest() {
        try {
            HttpServletRequest mockRequest = getHttpServletRequest(Constants.E12088);
            Response response = g4tcRestWebservice.getSaCodes(mockRequest);
            List<SaCodeDTO> saCodes = (List<SaCodeDTO>) response.getEntity();
            String responseAsJson = ToJson.toJson(saCodes);
            File file = new File("src/test/resources/SaCodes.schema.json");
            Assert.assertEquals(true, G4JsonValidator.isValid(responseAsJson, file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
