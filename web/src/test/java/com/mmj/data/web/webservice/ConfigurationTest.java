package com.mmj.data.web.webservice;

import com.mmj.data.core.dto.entity.ConfigurationDTO;
import com.mmj.data.ejb.session.ConfigurationSB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;

/**
 *
 */
@RunWith(Arquillian.class)
public class ConfigurationTest extends BaseTest {

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

    @Test
    public void testCreateAndUpdateConfiguration() {
        try {
            HttpServletRequest mockRequest = mock(HttpServletRequest.class);
            ConfigurationDTO originalDTO = new ConfigurationDTO();
            ConfigurationDTO returnedValue = null;

            // Create
            originalDTO.setCategory("Fluffy cat");
            originalDTO.setIdentity("Garfield");
            originalDTO.setValue("Go Hang a Salami! I'm a Lasagna Hog!");
            originalDTO.setDescription("A funny palindrome that involves lasagna.");
            originalDTO.setYear(2015);
            originalDTO.setRegex("/^[0-9]{3}$/");
            returnedValue = (ConfigurationDTO) g4tcRestWebservice.createConfiguration(originalDTO, mockRequest).getEntity();

            ConfigurationDTO configurationFromSearch = configurationSB.getConfigurationById(returnedValue.getId());
            Assert.assertEquals(true, originalDTO.getCategory().equals(configurationFromSearch.getCategory()));
            Assert.assertEquals(true, originalDTO.getIdentity().equals(configurationFromSearch.getIdentity()));
            Assert.assertEquals(true, originalDTO.getValue().equals(configurationFromSearch.getValue()));
            Assert.assertEquals(true, originalDTO.getDescription().equals(configurationFromSearch.getDescription()));
            Assert.assertEquals(originalDTO.getYear(), configurationFromSearch.getYear());
            Assert.assertEquals(true, originalDTO.getRegex().equals(configurationFromSearch.getRegex()));

            // Update
            originalDTO.setId(configurationFromSearch.getId());
            originalDTO.setCategory("Fluffy cat123");
            originalDTO.setIdentity("Garfield123");
            originalDTO.setValue("Go Hang a Salami! I'm a Lasagna Hog!123");
            originalDTO.setDescription("A funny palindrome that involves lasagna.123");
            originalDTO.setYear(2017);
            originalDTO.setRegex("/^[0-9]{4}$/");
            returnedValue = (ConfigurationDTO) g4tcRestWebservice.updateConfiguration(originalDTO, mockRequest).getEntity();

            configurationFromSearch = configurationSB.getConfigurationById(returnedValue.getId());
            Assert.assertEquals(true, originalDTO.getCategory().equals(configurationFromSearch.getCategory()));
            Assert.assertEquals(true, originalDTO.getIdentity().equals(configurationFromSearch.getIdentity()));
            Assert.assertEquals(true, originalDTO.getValue().equals(configurationFromSearch.getValue()));
            Assert.assertEquals(true, originalDTO.getDescription().equals(configurationFromSearch.getDescription()));
            Assert.assertEquals(originalDTO.getYear(), configurationFromSearch.getYear());
            Assert.assertEquals(true, originalDTO.getRegex().equals(configurationFromSearch.getRegex()));

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
