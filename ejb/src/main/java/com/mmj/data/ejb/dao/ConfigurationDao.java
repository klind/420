package com.mmj.data.ejb.dao;

import com.mmj.data.core.enums.ConfigurationCategoryEnum;
import com.mmj.data.core.enums.ConfigurationIdentityEnum;
import com.mmj.data.core.enums.SystemConfigurationEnum;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.exception.SystemException;
import com.mmj.data.ejb.model.AccessConfigurationEN;
import com.mmj.data.ejb.model.ConfigurationEN;
import com.mmj.data.ejb.model.SystemConfigurationEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ConfigurationDao {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationDao.class);

    @PersistenceContext
    private EntityManager em;

    /**
     * @param id
     * @return
     * @throws NotFoundException
     * @
     */
    public ConfigurationEN getConfigurationById(Long id) throws NotFoundException {
        ConfigurationEN result = null;
        try {
            result = em.find(ConfigurationEN.class, id);
            if (result == null) {
                throw new NotFoundException("Configuration with id " + id + " could not be found");
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Could not get Configuration with id {}", id, e);
            throw new SystemException("Could not get Configuration with id " + id);
        }
        return result;
    }

    /**
     * @return
     */
    public List<ConfigurationEN> getConfigurations() {
        List<ConfigurationEN> result = null;
        try {
            result = em.createQuery("SELECT c FROM ConfigurationEN c").getResultList();
        } catch (Exception e) {
            LOG.error("Could not get Configurations", e);
            throw new SystemException("Could not get Configurations");
        }
        return result;
    }

    /**
     * @param configurationEN
     * @
     */
    public ConfigurationEN updateConfiguration(ConfigurationEN configurationEN) {
        try {
            em.merge(configurationEN);
            return configurationEN;
        } catch (Exception e) {
            LOG.error("Could not update Configuration: {}", configurationEN, e);
            throw new SystemException("Could not update Configuration: " + configurationEN, e);
        }
    }

    /**
     * Returns the ConfigurationEN with the specified category and identity valid for current year
     *
     * @param category
     * @param identity
     * @return ConfigurationEN
     */
    public ConfigurationEN getConfigurationByCatIden(ConfigurationCategoryEnum category, ConfigurationIdentityEnum identity) throws NotFoundException {
        ConfigurationEN result = null;
        try {
            Query query = em.createNativeQuery("SELECT id FROM configuration " +
                    "WHERE category = :category " +
                    "AND identity = :identity " +
                    "AND year =  " +
                    "   (" +
                    "   SELECT MAX(year) FROM " +
                    "       (" +
                    "           SELECT year FROM configuration WHERE year <= :year AND category = :category AND identity = :identity" +
                    "       ) " +
                    "       AS x" +
                    ")");
            query.setParameter("category", category.toString())
                    .setParameter("identity", identity.toString())
                    .setParameter("year", Year.now().getValue());
            Long id = ((BigInteger) query.getSingleResult()).longValue();
            result = getConfigurationById(id);
        } catch (NoResultException e) {
            String msg = "Could not find a configurations for category " + category.toString() + ", identity " + identity.toString() + " and year " + Year.now().getValue() + ". Verify the configuration table for missing data.";
            LOG.error(msg);
            throw new NotFoundException(msg);
        } catch (NonUniqueResultException e) {
            String msg = "Did find more than one configuration for category " + category.toString() + ", identity " + identity.toString() + " and year " + Year.now().getValue() + ". Verify the configuration table for duplicate data.";
            LOG.error(msg);
            throw new SystemException(msg);
        }
        return result;
    }

    /**
     * Gets and builds the configurations summary based on what is in the database.
     *
     * @param year Normally the current year.
     * @return Configurations summary that has specified year's value and the follow year's data.
     * @ Any possible exception that can be thrown during the process.
     */
    public List<ConfigurationEN> getConfigurationsForYear(int year) {
        List<String> identities = new ArrayList<String>();
        List<ConfigurationEN> result = new ArrayList<ConfigurationEN>();

        try {
            // Dynamically build a list of all unique indentities in the system.
            List uniqueIdentities = em.createNativeQuery("SELECT distinct(identity) FROM configuration").getResultList();
            identities.addAll(uniqueIdentities);

            // Current year.
            for (String identity : identities) {
                result.addAll(em.createQuery("SELECT c FROM ConfigurationEN c WHERE year <= :year AND identity = :identity ORDER BY year DESC")
                        .setParameter("identity", identity)
                        .setParameter("year", year)
                        .setMaxResults(1)
                        .getResultList());
            }
        } catch (Exception e) {
            String errorStr = "Error building configuration summary.";
            LOG.error(errorStr, e);
            throw new SystemException(errorStr);
        }
        return result;
    }

    /**
     * Add a new configuration.
     *
     * @param configurationEN
     */
    public void saveNewConfiguration(ConfigurationEN configurationEN) {
        try {
            em.persist(configurationEN);
        } catch (Exception e) {
            LOG.error("Could not save configuration: {}", configurationEN, e);
            throw new SystemException("Could not save configuration: " + configurationEN, e);
        }
    }

    /**
     * Retrieve system configuration by identity
     *
     * @param systemConfigurationEnum
     * @return String
     */
    public SystemConfigurationEN getSystemConfigurationByIden(SystemConfigurationEnum systemConfigurationEnum) {
        SystemConfigurationEN result = null;
        try {
            Query query = em.createQuery("SELECT s FROM SystemConfigurationEN s WHERE identity = :identity AND ((startDate <= CURRENT_DATE AND endDate is null) OR (CURRENT_DATE BETWEEN startDate AND endDate))")
                    .setParameter("identity", systemConfigurationEnum.name());
            result = ((SystemConfigurationEN) query.getSingleResult());
        } catch (NoResultException e) {
            if (systemConfigurationEnum.isRequired()) {
                String msg = "Could not find a system configurations for identity " + systemConfigurationEnum.name() + ". Verify the configuration table for missing data.";
                LOG.error(msg);
                System.exit(-1);    // This was caught by Veracode, however the intent is to shut down the VM if we cannot load system confirmations.
            }
        } catch (NonUniqueResultException e) {
            String msg = "Did find more than one valid system configuration for identity " + systemConfigurationEnum.name() + " Verify the system configuration table for duplicate data.";
            LOG.error(msg);
            throw new SystemException(msg);
        }
        return result;
    }

    /**
     * @return
     */
    public List<AccessConfigurationEN> getAccessConfigurations() {
        List<AccessConfigurationEN> result = null;
        try {
            result = em.createQuery("SELECT c FROM AccessConfigurationEN c").getResultList();
        } catch (Exception e) {
            LOG.error("Could not get access configurations", e);
            throw new SystemException("Could not get access configurations");
        }
        return result;
    }
}
