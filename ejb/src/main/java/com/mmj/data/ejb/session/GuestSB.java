package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.GuestDTO;
import com.mmj.data.core.enums.ConfigurationCategoryEnum;
import com.mmj.data.core.enums.ConfigurationIdentityEnum;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.core.util.DateTimeUtil;
import com.mmj.data.ejb.dao.ConfigurationDao;
import com.mmj.data.ejb.dao.EmployeeDao;
import com.mmj.data.ejb.dao.GuestDao;
import com.mmj.data.ejb.model.ConfigurationEN;
import com.mmj.data.ejb.model.EmployeeEN;
import com.mmj.data.ejb.model.GuestEN;
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
public class GuestSB {
    private static final Logger LOG = LoggerFactory.getLogger(GuestSB.class);

    @Inject
    private GuestDao guestDao;

    @Inject
    private ConfigurationDao configurationDao;

    @Inject
    private EmployeeDao employeeDao;

    /**
     * Returns a list of GuestDTO.
     *
     * @param id
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<GuestDTO> getGuestsByEmployeeId(String id) {
        List<GuestEN> guests = guestDao.getGuestsByEmployeeId(id);
        List<GuestDTO> guestsDTOs = new ArrayList<GuestDTO>();
        for (GuestEN guestEN : guests) {
            guestsDTOs.add(guestEN.getGuestDTO());
        }
        return guestsDTOs;
    }

    /**
     * @param guestDTO
     * @return GuestDTO
     * @
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public GuestDTO createGuest(GuestDTO guestDTO) throws NotFoundException {
        String employeeId = guestDTO.getEmployeeId();
        EmployeeEN employeeEN = employeeDao.getEmployeeById(employeeId);
        ConfigurationEN configuration = configurationDao.getConfigurationByCatIden(ConfigurationCategoryEnum.GUEST, ConfigurationIdentityEnum.MAX_ALLOWED_GUESTS);
        int maxNumberOfGuests = Integer.parseInt(configuration.getValue());
        long numberOfGuests = guestDao.getNumberOfGuests(employeeId);
        if (numberOfGuests >= maxNumberOfGuests) {
            guestDao.deleteOldestGuest(employeeId);
        }
        GuestEN guestEN = new GuestEN();
        guestEN.setEmployee(employeeEN);
        guestEN.setFirstName(guestDTO.getFirstName());
        guestEN.setMiddleName(guestDTO.getMiddleName());
        guestEN.setLastName(guestDTO.getLastName());
        guestEN.setDob(DateTimeUtil.localDateToDate(guestDTO.getDob()));
        guestEN.setGender(guestDTO.getGender());
        guestEN.setKnownTravelerNumber(guestDTO.getKnownTravelerNumber());
        guestDao.saveNewGuest(guestEN);
        return guestEN.getGuestDTO();
    }
}
