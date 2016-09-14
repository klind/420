package com.mmj.data.ejb.session;

import com.mmj.data.ejb.dao.ProfileDao;
import com.mmj.data.ejb.model.ProfileEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@LocalBean
public class ProfileSB {
    private static final Logger LOG = LoggerFactory.getLogger(ProfileSB.class);

    @Inject
    private ProfileDao profileDao;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ProfileEN saveNewProfile(ProfileEN profileEN) {
        profileDao.saveNewProfile(profileEN);
        return profileEN;
    }
}
