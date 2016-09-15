package com.mmj.data.ejb.transformer;

import com.mmj.data.core.dto.entity.ProfileDTO;
import com.mmj.data.core.dto.entity.QuestionRangesDTO;
import com.mmj.data.ejb.dao.QuestionRangesDao;
import com.mmj.data.ejb.model2.ProfileEN;
import com.mmj.data.ejb.model2.QuestionRangesEN;

import javax.inject.Inject;

public class Transformer {

    @Inject
    private QuestionRangesDao questionRangesDao;

    public ProfileEN getProfileEN(ProfileDTO profileDTO) {
        ProfileEN profileEN = new ProfileEN();
        profileEN.setAvgHoursSweatWeek(profileDTO.getAvgHoursSweatWeek());
        profileEN.setAmountNicotineDay(profileDTO.getAmountNicotineDay());
        profileEN.setActivityLevel(questionRangesDao.getQuestionRangeById(profileDTO.getActivityLevel().getId()));
        profileEN.setAgeRange(questionRangesDao.getQuestionRangeById(profileDTO.getAgeRange().getId()));
        profileEN.setAvgOunceMeatDay(profileDTO.getAvgOunceMeatDay());
        profileEN.setAvgSexWeek(profileDTO.getAvgSexWeek());
        profileEN.setBodyfat(questionRangesDao.getQuestionRangeById(profileDTO.getBodyfat().getId()));
        profileEN.setBowelMovement(questionRangesDao.getQuestionRangeById(profileDTO.getBowelMovement().getId()));
        profileEN.setCaffeineDrinks(questionRangesDao.getQuestionRangeById(profileDTO.getCaffeineDrinks().getId()));
        profileEN.setChildren(profileDTO.getChildren());
        profileEN.setDob(profileDTO.getDob());
        profileEN.setEmail(profileDTO.getEmail());
        profileEN.setFirstName(profileDTO.getFirstName());
        profileEN.setGender(profileDTO.getGender());
        profileEN.setGeneticItems(profileDTO.getGeneticItems());
        profileEN.setHadMenopause(profileDTO.getHadMenopause());
        profileEN.setHotColdNormal(profileDTO.getHotColdNormal());
        profileEN.setLastName(profileDTO.getLastName());
        profileEN.setMiddleName(profileDTO.getMiddleName());
        profileEN.setOnPrescriptionMeds(profileDTO.getOnPrescriptionMeds());
        profileEN.setPhone(profileDTO.getPhone());
        profileEN.setPreferSalivas(profileDTO.getPreferSalivas());
        profileEN.setSleep(questionRangesDao.getQuestionRangeById(profileDTO.getSleep().getId()));
        profileEN.setUseNicotine(profileDTO.getUseNicotine());
        profileEN.setVegetarian(profileDTO.getVegetarian());
        profileEN.setWeight(profileDTO.getWeight());
        return profileEN;
    }

    public ProfileDTO getProfileDTO(ProfileEN profileEN) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setAvgHoursSweatWeek(profileEN.getAvgHoursSweatWeek());
        profileDTO.setAmountNicotineDay(profileEN.getAmountNicotineDay());
        profileDTO.setActivityLevel(this.getQuestionRangeDTO(profileEN.getActivityLevel()));
        profileDTO.setAgeRange(this.getQuestionRangeDTO(profileEN.getAgeRange()));
        profileDTO.setAvgOunceMeatDay(profileEN.getAvgOunceMeatDay());
        profileDTO.setAvgSexWeek(profileEN.getAvgSexWeek());
        profileDTO.setBodyfat(this.getQuestionRangeDTO(profileEN.getBodyfat()));
        profileDTO.setBowelMovement(this.getQuestionRangeDTO(profileEN.getBowelMovement()));
        profileDTO.setCaffeineDrinks(this.getQuestionRangeDTO(profileEN.getCaffeineDrinks()));
        profileDTO.setChildren(profileEN.getChildren());
        profileDTO.setDob(profileEN.getDob());
        profileDTO.setEmail(profileEN.getEmail());
        profileDTO.setFirstName(profileEN.getFirstName());
        profileDTO.setGender(profileEN.getGender());
        profileDTO.setGeneticItems(profileEN.getGeneticItems());
        profileDTO.setHadMenopause(profileEN.getHadMenopause());
        profileDTO.setHotColdNormal(profileEN.getHotColdNormal());
        profileDTO.setLastName(profileEN.getLastName());
        profileDTO.setMiddleName(profileEN.getMiddleName());
        profileDTO.setOnPrescriptionMeds(profileEN.getOnPrescriptionMeds());
        profileDTO.setPhone(profileEN.getPhone());
        profileDTO.setPreferSalivas(profileEN.getPreferSalivas());
        profileDTO.setSleep(this.getQuestionRangeDTO(profileEN.getSleep()));
        profileDTO.setUseNicotine(profileEN.getUseNicotine());
        profileDTO.setVegetarian(profileEN.getVegetarian());
        profileDTO.setWeight(profileEN.getWeight());
        return profileDTO;
    }

    private QuestionRangesDTO getQuestionRangeDTO(QuestionRangesEN questionRangesEN) {
        QuestionRangesDTO questionRangesDTO = new QuestionRangesDTO();
        questionRangesDTO.setCategory(questionRangesEN.getCategory());
        questionRangesDTO.setId(questionRangesEN.getId());
        questionRangesDTO.setLower(questionRangesEN.getLower());
        questionRangesDTO.setUpper(questionRangesEN.getUpper());
        return questionRangesDTO;
    }
}
