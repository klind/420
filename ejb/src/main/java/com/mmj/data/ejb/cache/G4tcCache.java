package com.mmj.data.ejb.cache;

import com.mmj.data.core.dto.entity.AccessConfigurationDTO;
import com.mmj.data.core.dto.entity.PaymentTypeDTO;
import com.mmj.data.core.dto.entity.SystemConfigurationDTO;
import com.mmj.data.core.enums.SystemConfigurationEnum;
import com.mmj.data.core.exception.SystemException;
import com.mmj.data.core.extclient.dto.AncillaryFeeDto;
import com.mmj.data.core.extclient.dto.RouteDto;
import com.mmj.data.core.flights.FlightsService;
import com.mmj.data.core.payments.PaymentService;
import com.mmj.data.core.util.DateTimeUtil;
import com.mmj.data.ejb.dao.ConfigurationDao;
import com.mmj.data.ejb.model.AccessConfigurationEN;
import com.mmj.data.ejb.model.SystemConfigurationEN;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Singleton(name = "G4tcCache")
public class G4tcCache {
    private static final Logger LOG = LoggerFactory.getLogger(G4tcCache.class);
    // The following creates a CacheManager based on the configuration defined in the ehcache.xml file in the classpath.

    @Inject
    private FlightsService flightsService;

    @Inject
    private PaymentService paymentService;

    @Inject
    private ConfigurationDao configurationDao;

    private CacheManager cacheManager;
    private Cache airportCache;
    private Cache ssrCache;
    private Cache paymentTypesCache;
    private Cache systemConfigurationCache;
    private Cache accessConfigurationCache;

    @PostConstruct
    public void init() {
        cacheManager = CacheManager.newInstance();
        airportCache = cacheManager.getCache("airportCache");
        ssrCache = cacheManager.getCache("ssrCache");
        paymentTypesCache = cacheManager.getCache("paymentTypesCache");
        systemConfigurationCache = cacheManager.getCache("systemConfigurationCache");
        accessConfigurationCache = cacheManager.getCache("accessConfigurationCache");
    }

    public G4tcCache() {

    }

    /**
     * Load the cache with values.
     */
    public void load() {
        LOG.debug("Loading airports.");
        try {
            loadAirportCache(flightsService.getRoutes());
        } catch (SystemException e) {
            // The exception is logged where is was thrown
            LOG.error("The airport cache could not be initialized.");
        }
        LOG.debug("Loading SSRs.");
        try {
            loadSSRCache(flightsService.getSSRs());
        } catch (SystemException e) {
            // The exception is logged where is was thrown
            LOG.error("The SSR cache could not be initialized.");
        }
        LOG.debug("Loading payment types.");
        try {
            loadPaymentTypesCache(paymentService.getPaymenttypes());
        } catch (SystemException e) {
            // The exception is logged where is was thrown
            LOG.error("The PaymentTypes cache could not be initialized.");
        }
        LOG.debug("Loading system configurations.");
        try {
            loadSystemConfigurations();
        } catch (SystemException e) {
            // The exception is logged where is was thrown
            LOG.error("The system configuration cache could not be initialized.");
        }
        LOG.debug("Loading access configurations.");
        try {
            loadAccessConfigurations();
        } catch (SystemException e) {
            // The exception is logged where is was thrown
            LOG.error("The access configuration cache could not be initialized.");
        }
    }

    /**
     * @return
     */
    public List<String> loadAccessConfigurations() {
        List<String> result = new ArrayList<String>();
        List<Element> elements = new ArrayList<Element>();
        try {
            synchronized (accessConfigurationCache) {
                List<AccessConfigurationEN> accessConfigurations = configurationDao.getAccessConfigurations();
                for (AccessConfigurationEN accessConfiguration : accessConfigurations) {
                    String key = new String(accessConfiguration.getEmployeeType() + "-" + accessConfiguration.getEmployeeStatus());
                    Element element = new Element(key, accessConfiguration.getAccessConfigurationDTO());
                    elements.add(element);
                }
                LOG.debug("Removing {} access configurations from the cache", accessConfigurationCache.getSize());
                accessConfigurationCache.removeAll();
                for (Element element : elements) {
                    result.add(element.getObjectKey() + " : " + element.getObjectValue());
                    accessConfigurationCache.put(element);
                }
                LOG.debug("Added {} access configurations to the cache", accessConfigurationCache.getSize());
            }
            return result;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    /**
     * Refreshes the cache
     * The system configuration is not refreshed by this method.
     */
    public void refresh() {
        LOG.debug("Refresh airports.");
        try {
            List<RouteDto> routes = flightsService.getRoutes();
            // Only load the new cache if we could actually retrieve them. If some error occurs we still want to keep the current data.
            loadAirportCache(routes);
        } catch (SystemException e) {
            // The exception is logged where is was thrown
            LOG.error("The airport cache could not be refreshed.");
        }
        LOG.debug("Refresh SSRs.");
        try {
            List<AncillaryFeeDto> ssRs = flightsService.getSSRs();
            // Only load the new cache if we could actually retrieve them. If some error occurs we still want to keep the current data.
            loadSSRCache(ssRs);
        } catch (SystemException e) {
            // The exception is logged where is was thrown
            LOG.error("The ssr cache could not be refreshed.");
        }
        LOG.debug("Refresh PaymentTypes.");
        try {
            List<PaymentTypeDTO> paymenttypes = paymentService.getPaymenttypes();
            // Only load the new cache if we could actually retrieve them. If some error occurs we still want to keep the current data.
            loadPaymentTypesCache(paymenttypes);
        } catch (SystemException e) {
            // The exception is logged where is was thrown
            LOG.error("The paymenttypes cache could not be refreshed.");
        }
    }

    private void loadAirportCache(List<RouteDto> routes) {
        synchronized (airportCache) {
            LOG.debug("Removing {} airports from the cache", airportCache.getSize());
            airportCache.removeAll();
            for (RouteDto route : routes) {
                Element element = new Element(route.getCode(), route);
                airportCache.put(element);
            }
            LOG.debug("Added {} airports to the cache", airportCache.getSize());
        }
    }

    private void loadSSRCache(List<AncillaryFeeDto> ssrs) {
        synchronized (ssrCache) {
            LOG.debug("Removing {} SSR's from the cache", ssrCache.getSize());
            ssrCache.removeAll();
            for (AncillaryFeeDto ancillaryFeeDto : ssrs) {
                Element element = new Element(ancillaryFeeDto.getCode(), ancillaryFeeDto);
                ssrCache.put(element);
            }
            LOG.debug("Added {} SSR's to the cache", ssrCache.getSize());
        }
    }

    private void loadPaymentTypesCache(List<PaymentTypeDTO> paymenttypes) {
        synchronized (paymentTypesCache) {
            LOG.debug("Removing {} PaymentTypes from the cache", paymentTypesCache.getSize());
            paymentTypesCache.removeAll();
            for (PaymentTypeDTO paymenttype : paymenttypes) {
                Element element = new Element(paymenttype.getCode(), paymenttype);
                paymentTypesCache.put(element);
            }
            LOG.debug("Added {} PaymentTypes to the cache", paymentTypesCache.getSize());
        }
    }

    /**
     * Loads the system configurations from the database into the cache.
     *
     * @return a list of system configurations loaded into the cache
     */
    public List<String> loadSystemConfigurations() {
        List<String> result = new ArrayList<String>();
        List<Element> elements = new ArrayList<Element>();
        try {
            synchronized (systemConfigurationCache) {
                EnumSet<SystemConfigurationEnum> systemConfigurationEnums = EnumSet.allOf(SystemConfigurationEnum.class);

                for (SystemConfigurationEnum systemConfigurationEnum : systemConfigurationEnums) {
                    SystemConfigurationEN systemConfigurationByIden = configurationDao.getSystemConfigurationByIden(systemConfigurationEnum);

                    switch (systemConfigurationEnum) {
                        case FIRST_SEARCH_DATE:
                            Element element = new Element(systemConfigurationEnum.name(), systemConfigurationByIden.getSystemConfigurationDTO());
                            elements.add(element);
                            break;
                        case IS_PAY_NOW_BUTTON_VISIBLE:
                            element = new Element(systemConfigurationEnum.name(), systemConfigurationByIden.getSystemConfigurationDTO());
                            elements.add(element);
                            break;
                        default:
                            break;
                    }
                }
                LOG.debug("Removing {} system configurations from the cache", systemConfigurationCache.getSize());
                systemConfigurationCache.removeAll();
                for (Element element : elements) {
                    result.add(element.getObjectKey() + " : " + element.getObjectValue());
                    systemConfigurationCache.put(element);
                }
                LOG.debug("Added {} system configurations to the cache", systemConfigurationCache.getSize());
            }
            return result;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    /**
     * Returns a list of AncillaryFeeDto from the cache
     *
     * @return a list of AncillaryFeeDto from the cache
     */
    public List<AncillaryFeeDto> getSSRs() {
        List<AncillaryFeeDto> ssrs = new ArrayList<>();
        synchronized (ssrCache) {
            List keys = ssrCache.getKeys();
            for (Object key : keys) {
                Element element = ssrCache.get(key);
                AncillaryFeeDto value = (AncillaryFeeDto) element.getObjectValue();
                ssrs.add(value);
            }
        }
        return ssrs;
    }

    /**
     * Returns a list of RouteDto from the cache
     *
     * @return a list of RouteDto from the cache
     */
    public List<RouteDto> getRoutes() {
        List<RouteDto> routeDtos = new ArrayList<RouteDto>();
        synchronized (airportCache) {
            List keys = airportCache.getKeys();
            for (Object key : keys) {
                Element element = airportCache.get(key);
                RouteDto value = (RouteDto) element.getObjectValue();
                routeDtos.add(value);
            }
        }
        return routeDtos;
    }

    /**
     * Returns a list of PaymentTypeDTO from the cache
     *
     * @return a list of PaymentTypeDTO from the cache
     */
    public List<PaymentTypeDTO> getPaymentTypes() {
        List<PaymentTypeDTO> paymentTypeDTOs = new ArrayList<PaymentTypeDTO>();
        synchronized (paymentTypesCache) {
            List keys = paymentTypesCache.getKeys();
            for (Object key : keys) {
                Element element = paymentTypesCache.get(key);
                PaymentTypeDTO value = (PaymentTypeDTO) element.getObjectValue();
                paymentTypeDTOs.add(value);
            }
        }
        return paymentTypeDTOs;
    }

    /**
     * Returns a PaymentTypeDTO with the specified code
     *
     * @param code
     * @return
     */
    public PaymentTypeDTO getPaymentTypeByCode(String code) {
        Element element = paymentTypesCache.get(code);
        return (PaymentTypeDTO) element.getObjectValue();
    }

    /**
     * Returns a list of system configurations from the cache
     *
     * @return a list of system configurations from the cache
     */
    public List<String> getSystemConfigurations() {
        List<String> elements = new ArrayList<String>();
        synchronized (systemConfigurationCache) {
            List keys = systemConfigurationCache.getKeys();
            for (Object key : keys) {
                Element element = systemConfigurationCache.get(key);
                elements.add(element.getObjectKey() + " : " + element.getObjectValue());
            }
        }
        return elements;
    }

    /**
     * Returns a system configuration with the specified systemConfigurationEnum
     *
     * @param systemConfigurationEnum
     * @return a system configuration with the specified systemConfigurationEnum
     */
    public SystemConfigurationDTO getSystemConfiguration(SystemConfigurationEnum systemConfigurationEnum) {
        Element element = systemConfigurationCache.get(systemConfigurationEnum.name());
        return element == null ? null : (SystemConfigurationDTO)element.getObjectValue();
    }

    /**
     * @return List<SystemConfigurationDTO>
     */
    public List<SystemConfigurationDTO> getActiveSystemConfigurations() {
        List<SystemConfigurationDTO> systemConfigurationDTOs = new ArrayList<>();
        synchronized (systemConfigurationCache) {
            List keys = systemConfigurationCache.getKeys();
            LocalDate now = DateTimeUtil.getLocalDateNowGMT();
            for (Object key : keys) {
                Element element = systemConfigurationCache.get(key);
                SystemConfigurationDTO systemConfigurationDTO = (SystemConfigurationDTO) element.getObjectValue();
                if ((systemConfigurationDTO.getStartDate().isBefore(now) || systemConfigurationDTO.getStartDate().isEqual(now)) &&
                        systemConfigurationDTO.getEndDate() == null || (systemConfigurationDTO.getEndDate().isAfter(now) || systemConfigurationDTO.getEndDate().isEqual(now))) {
                    systemConfigurationDTOs.add(systemConfigurationDTO);
                }
            }
        }
        return systemConfigurationDTOs;
    }

    /**
     * @return List<AccessConfigurationDTO>
     */
    public List<AccessConfigurationDTO> getAccessConfigurations() {
        List<AccessConfigurationDTO> accessConfigurationDTOs = new ArrayList<>();
        synchronized (accessConfigurationCache) {
            List keys = accessConfigurationCache.getKeys();
            for (Object key : keys) {
                Element element = accessConfigurationCache.get(key);
                AccessConfigurationDTO value = (AccessConfigurationDTO) element.getObjectValue();
                accessConfigurationDTOs.add(value);
            }
        }
        return accessConfigurationDTOs;
    }
}
