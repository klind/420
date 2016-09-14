package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.SuspensionTypeDTO;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.ejb.dao.SuspensionTypeDao;
import com.mmj.data.ejb.model.SuspensionTypeEN;
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
public class SuspensionTypeSB {
    private static final Logger LOG = LoggerFactory.getLogger(SuspensionTypeSB.class);

    @Inject
    private SuspensionTypeDao suspensionTypeDao;

    /**
     * @param id
     * @return
     * @
     */
    public SuspensionTypeDTO getSuspensionById(Long id) throws NotFoundException {
        SuspensionTypeEN suspensionType = suspensionTypeDao.getSuspensionTypeById(id);
        return suspensionType.getSuspensionTypeDTO();
    }

    /**
     * @return
     * @
     */
    public List<SuspensionTypeDTO> getSuspensionTypes()  {
        List<SuspensionTypeEN> suspensionTypeENs = suspensionTypeDao.getSuspensionTypes();
        List<SuspensionTypeDTO> suspensionTypeDTOs = new ArrayList<SuspensionTypeDTO>();
        for (SuspensionTypeEN suspensionTypeEN : suspensionTypeENs) {
            suspensionTypeDTOs.add(suspensionTypeEN.getSuspensionTypeDTO());
        }
        return suspensionTypeDTOs;
    }

    /**
     * @param value
     * @return SuspensionTypeDTO
     * @
     */
    public SuspensionTypeDTO getSuspensionTypeByValue(String value)  {
        SuspensionTypeEN suspensionTypeEN = suspensionTypeDao.getSuspensionTypeByValue(value);
        return suspensionTypeEN.getSuspensionTypeDTO();
    }
}
