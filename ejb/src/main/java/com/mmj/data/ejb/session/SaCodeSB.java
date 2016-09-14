package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.SaCodeDTO;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.ejb.dao.SaCodeDao;
import com.mmj.data.ejb.model.SaCodeEN;
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
public class SaCodeSB {
    private static final Logger LOG = LoggerFactory.getLogger(SaCodeSB.class);

    @Inject
    private SaCodeDao saCodeDao;

    /**
     * @return Returns a list of SA Code value objects..
     * @
     */
    public List<SaCodeDTO> getSaCodes()  {
        List<SaCodeEN> saCodeENs = saCodeDao.getSaCodes();
        List<SaCodeDTO> saCodeDTOs = new ArrayList<SaCodeDTO>();
        for (SaCodeEN saCodeEN : saCodeENs) {
            saCodeDTOs.add(saCodeEN.getSaCodeDTO());
        }
        return saCodeDTOs;
    }

    /**
     *
     * @param saCodeDTO SA code vo to use.
     * @return Updated sa code vo.
     * @
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SaCodeDTO updateSaCode(SaCodeDTO saCodeDTO) throws NotFoundException {
        if (!saCodeDao.saCodeExists(saCodeDTO.getId())) {
            throw new NotFoundException("SA Code with id " + saCodeDTO.getId() + " does not exists");
        }
        SaCodeEN saCodeEN = saCodeDao.getSaCodeById(saCodeDTO.getId());
        setSACodeData(saCodeDTO, saCodeEN);
        SaCodeDTO result = saCodeEN.getSaCodeDTO();
        return result;
    }

    /**
     *
     * @param saCodeDTO SA code vo.
     * @param saCodeEN SA code entity.
     * @
     */
    private void setSACodeData(SaCodeDTO saCodeDTO, SaCodeEN saCodeEN)  {
        saCodeEN.setId(saCodeDTO.getId());
        saCodeEN.setSaCode(saCodeDTO.getSaCode());
        saCodeEN.setDescription(saCodeDTO.getDescription());
        saCodeEN.setActive(saCodeDTO.isActive());
    }

    /**
     * Get sa code by id.
     *
     * @param id Id of the sa code
     * @return SaCodeEN
     * @
     */
    public SaCodeEN getSaCodeById(Long id) throws NotFoundException {
        return saCodeDao.getSaCodeById(id);
    }

}
