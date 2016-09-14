package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.TravelerRelationshipDTO;
import com.mmj.data.ejb.dao.TravelerRelationshipDao;
import com.mmj.data.ejb.model.TravelerRelationshipEN;
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
public class TravellerRelationshipsSB {
    private static final Logger LOG = LoggerFactory.getLogger(TravellerRelationshipsSB.class);

    @Inject
    private TravelerRelationshipDao travelerRelationshipDao;

    /**
     * @return List<TravelerRelationshipDTO>
     * @
     */
    public List<TravelerRelationshipDTO> getRelationshipTypes()  {
        List<TravelerRelationshipDTO> result = new ArrayList<TravelerRelationshipDTO>();
        List<TravelerRelationshipEN> travelerRelationshipENs = travelerRelationshipDao.getRelationshipTypes();
        for (TravelerRelationshipEN travelerRelationshipEN : travelerRelationshipENs) {
            result.add(travelerRelationshipEN.getTravelerRelationshipDTO());
        }
        return result;
    }
}
