package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.AccessConfigurationDTO;
import com.mmj.data.core.dto.entity.ConfigurationDTO;
import com.mmj.data.core.dto.entity.ConfigurationSummaryDTO;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.ejb.dao.ConfigurationDao;
import com.mmj.data.ejb.model.AccessConfigurationEN;
import com.mmj.data.ejb.model.ConfigurationEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@LocalBean
public class ConfigurationSB {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationSB.class);

    @Inject
    private ConfigurationDao configurationDao;

    /**
     * Get Configuration by Id.
     *
     * @param id Id of the configuration we want to get.
     * @return ConfigurationDTO  Configuration info in DTO form.
     * @ Any excpetion that might occur.
     */
    public ConfigurationDTO getConfigurationById(Long id) throws NotFoundException {
        ConfigurationEN result = configurationDao.getConfigurationById(id);
        return result.getConfigurationDTO();
    }

    /**
     * @return List<ConfigurationDTO>
     *
     */
    public List<ConfigurationDTO> getConfigurations() {
        List<ConfigurationEN> configurations = configurationDao.getConfigurations();
        List<ConfigurationDTO> configurationDTOs = new ArrayList<ConfigurationDTO>();
        for (ConfigurationEN configurationEN : configurations) {
            configurationDTOs.add(configurationEN.getConfigurationDTO());
        }
        return configurationDTOs;
    }

    /**
     * @return List<AccessConfigurationDTO>
     *
     */
    public List<AccessConfigurationDTO> getAccessConfigurations() {
        List<AccessConfigurationEN> configurations = configurationDao.getAccessConfigurations();
        List<AccessConfigurationDTO> accessConfigurationDTOs  = new ArrayList<AccessConfigurationDTO>();
        for (AccessConfigurationEN accessConfigurationEN : configurations) {
            accessConfigurationDTOs.add(accessConfigurationEN.getAccessConfigurationDTO());
        }
        return accessConfigurationDTOs;
    }

    /**
     * Updates a configuration if it exists, otherwise it creates a new one.
     *
     * @param configurationDTO The values we are attemping to update.
     * @return ConfigurationEN  ConfigurationEN if an update, null if it is a create.
     * @ Any possible excpetion during the process.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ConfigurationDTO updateConfiguration(ConfigurationDTO configurationDTO) throws NotFoundException {
        ConfigurationEN configurationEN = configurationDao.getConfigurationById(configurationDTO.getId());
        configurationEN.setValue(configurationDTO.getValue());
        configurationEN.setIdentity(configurationDTO.getIdentity());
        configurationEN.setDescription(configurationDTO.getDescription());
        configurationEN.setCategory(configurationDTO.getCategory());
        configurationEN.setYear(configurationDTO.getYear());
        configurationEN.setRegex(configurationDTO.getRegex());
        configurationEN = configurationDao.updateConfiguration(configurationEN);
        return configurationEN.getConfigurationDTO();
    }

    /**
     * Get a summary of unique configurations for specified year and the next year's values.
     *
     * @param year Normally the current year.
     * @return List of configuration summary details.
     * @ Any exception that could be thrown during retrival of the info.
     */
    public List<ConfigurationSummaryDTO> getConfigurationSummary(int year) {
        List<ConfigurationSummaryDTO> result = new ArrayList<ConfigurationSummaryDTO>();
        List<ConfigurationEN> resultCurrentYear = configurationDao.getConfigurationsForYear(year);
        List<ConfigurationEN> resultNextYear = configurationDao.getConfigurationsForYear(year + 1);
        // There is the possibility that a configuration is not used this year, but is next year, so we iterate through next year's result to ensure we pick those up.
        // The id we use is also of next years, as that is the only record that will be updated, if there is an update.
        for (ConfigurationEN config : resultNextYear) {
            ConfigurationSummaryDTO temp = new ConfigurationSummaryDTO();
            if (config.getYear() == (year + 1)) {
                temp.setIdNextYear(config.getId());
            } else {
                temp.setIdNextYear(0L);  // Value was inherited, so value of 0 will help trigger the creation of a new record, when passed back.
            }
            temp.setCategory(config.getCategory());
            temp.setDescription(config.getDescription());
            temp.setIdentity(config.getIdentity());
            temp.setValueNextYear(config.getValue());
            temp.setRegex(config.getRegex());
            for (ConfigurationEN currYear : resultCurrentYear) {
                if (currYear.getIdentity().equals(config.getIdentity())) {
                    temp.setValueCurrentYear(currYear.getValue());
                    break;
                }
            }
            result.add(temp);
        }
        return result;
    }

    /**
     * Create a configuration.
     *
     * @param configurationDTO Value object.
     * @return Created object.
     * @ Any exception that might occur.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ConfigurationDTO createConfiguration(ConfigurationDTO configurationDTO) {
        ConfigurationEN configurationEN = new ConfigurationEN();
        configurationEN.setCategory(configurationDTO.getCategory());
        configurationEN.setIdentity(configurationDTO.getIdentity());
        configurationEN.setDescription(configurationDTO.getDescription());
        configurationEN.setValue(configurationDTO.getValue());
        configurationEN.setYear(configurationDTO.getYear());
        configurationEN.setRegex(configurationDTO.getRegex());
        configurationDao.saveNewConfiguration(configurationEN);
        return configurationEN.getConfigurationDTO();
    }
}
