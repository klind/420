package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.ProfileDTO;
import com.mmj.data.core.exception.AlreadyExistsException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.ejb.dao.ProfileDao;
import com.mmj.data.ejb.model.ProfileEN;
import com.mmj.data.ejb.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.MathContext;

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

    @Inject
    private Transformer transformer;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ProfileDTO createProfile(ProfileDTO profileDTO) throws AlreadyExistsException {
        if (profileDao.profileExistsByEmail(profileDTO.getEmail())) {
            throw new AlreadyExistsException("The email " + profileDTO.getEmail() + " is already in use");
        }
        ProfileEN profileEN = transformer.getProfileEN(profileDTO);
        calculateScore(profileEN);
        profileDao.saveNewProfile(profileEN);
        return transformer.getProfileDTO(profileEN);
    }

    private void calculateScore(ProfileEN profileEN) {
        BigDecimal score = new BigDecimal(0);
        MathContext mc = new MathContext(3);

        score = score.add(profileEN.getBodyfat().getScore());
        score = score.add(profileEN.getAgeRange().getScore());
        score = score.add(profileEN.getActivityLevel().getScore());
        score = score.add(profileEN.getBowelMovement().getScore());
        score = score.add(profileEN.getCaffeineDrinks().getScore());
        score = score.add(profileEN.getSleep().getScore());

        profileEN.setScore(score);

        /*if (profileEN.getOnPrescriptionMeds()) {
            score = score.multiply(new BigDecimal(1.10), mc);
        }

        if (profileEN.getVegetarian()) {
            score = score.multiply(new BigDecimal(0.90), mc);
        }

        if (profileEN.getAvgOunceMeatDay() > 15) {


        }*/
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ProfileDTO getProfileById(Long id) throws NotFoundException {
        ProfileEN profileEN = profileDao.getProfileById(id);
        return transformer.getProfileDTO(profileEN);
    }

    public ProfileDTO getProfileByEmail(String email) throws NotFoundException {
        ProfileEN profileEN = profileDao.getProfileByEmail(email);
        return transformer.getProfileDTO(profileEN);
    }
}
