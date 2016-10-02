package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.ProfileDTO;
import com.mmj.data.core.exception.AlreadyExistsException;
import com.mmj.data.core.exception.NotFoundException;
import com.mmj.data.ejb.dao.ProfileDao;
import com.mmj.data.ejb.dao.QuestionRangesDao;
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
import java.util.ArrayList;
import java.util.List;

import static com.mmj.data.core.enums.QuestionRangeCategoryEnum.ACTIVITY_LEVEL;
import static com.mmj.data.core.enums.QuestionRangeCategoryEnum.AGE_RANGE;
import static com.mmj.data.core.enums.QuestionRangeCategoryEnum.BODY_FAT_FEMALE;
import static com.mmj.data.core.enums.QuestionRangeCategoryEnum.BODY_FAT_MALE;
import static com.mmj.data.core.enums.QuestionRangeCategoryEnum.BOWEL_MOVEMENTS;
import static com.mmj.data.core.enums.QuestionRangeCategoryEnum.CAFFEINE_DRINKS;
import static com.mmj.data.core.enums.QuestionRangeCategoryEnum.SLEEP;

/**
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@LocalBean
public class ProfileSB {
    private static final Logger LOG = LoggerFactory.getLogger(ProfileSB.class);

    @Inject
    private ProfileDao profileDao;

    @Inject
    private QuestionRangesDao questionRangesDao;

    @Inject
    private Transformer transformer;

    public ProfileDTO createProfile(ProfileDTO profileDTO) throws AlreadyExistsException, NotFoundException {
        if (profileDao.profileExistsByEmail(profileDTO.getEmail())) {
            throw new AlreadyExistsException("The email " + profileDTO.getEmail() + " is already in use");
        }
        ProfileEN profileEN = transformer.getProfileEN(profileDTO);
        calculateScore(profileEN);
        profileDao.saveNewProfile(profileEN);
        return transformer.getProfileDTO(profileEN);
    }

    private void calculateScore(ProfileEN profileEN) throws NotFoundException {
        BigDecimal score = new BigDecimal(0);
        MathContext mc = new MathContext(3);

        score = score.add(profileEN.getGender().equals("M") ? questionRangesDao.getQuestionRangeByCatAndMark(BODY_FAT_MALE.name(), profileEN.getBodyfat()).getScore() : questionRangesDao.getQuestionRangeByCatAndMark(BODY_FAT_FEMALE.name(), profileEN.getBodyfat()).getScore());
        score = score.add(questionRangesDao.getQuestionRangeByCatAndMark(AGE_RANGE.name(), profileEN.getAgeRange()).getScore());
        score = score.add(questionRangesDao.getQuestionRangeByCatAndMark(ACTIVITY_LEVEL.name(), profileEN.getActivityLevel()).getScore());
        score = score.add(questionRangesDao.getQuestionRangeByCatAndMark(BOWEL_MOVEMENTS.name(), profileEN.getBowelMovement()).getScore());
        score = score.add(questionRangesDao.getQuestionRangeByCatAndMark(CAFFEINE_DRINKS.name(), profileEN.getCaffeineDrinks()).getScore());
        score = score.add(questionRangesDao.getQuestionRangeByCatAndMark(SLEEP.name(), profileEN.getSleep()).getScore());

        /*score = score.add(profileEN.getBodyfat().getScore());
        score = score.add(profileEN.getAgeRange().getScore());
        score = score.add(profileEN.getActivityLevel().getScore());
        score = score.add(profileEN.getBowelMovement().getScore());
        score = score.add(profileEN.getCaffeineDrinks().getScore());
        score = score.add(profileEN.getSleep().getScore());*/

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

    public ProfileDTO getProfileById(Long id) throws NotFoundException {
        ProfileEN profileEN = profileDao.getProfileById(id);
        return transformer.getProfileDTO(profileEN);
    }

    public ProfileDTO getProfileByEmail(String email) throws NotFoundException {
        ProfileEN profileEN = profileDao.getProfileByEmail(email);
        return transformer.getProfileDTO(profileEN);
    }

    public List<ProfileDTO> getProfiles() {
        List<ProfileDTO> result = new ArrayList<>();
        List<ProfileEN> profiles = profileDao.getProfiles();
        for (ProfileEN profile : profiles) {
            result.add(transformer.getProfileDTO(profile));
        }
        return result;
    }
}
