package com.mmj.data.ejb.transformer;

import com.mmj.data.core.dto.entity.AnswerDTO;
import com.mmj.data.core.dto.entity.ProfileDTO;
import com.mmj.data.core.dto.entity.QuestionDTO;
import com.mmj.data.core.dto.entity.QuestionRangeDTO;
import com.mmj.data.core.dto.entity.SurveyDTO;
import com.mmj.data.core.util.DateTimeUtil;
import com.mmj.data.ejb.dao.QuestionRangesDao;
import com.mmj.data.ejb.model.AnswerEN;
import com.mmj.data.ejb.model.ProfileEN;
import com.mmj.data.ejb.model.QuestionEN;
import com.mmj.data.ejb.model.QuestionRangeEN;
import com.mmj.data.ejb.model.SurveyEN;

import javax.inject.Inject;
import java.util.List;

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
        profileEN.setDob(DateTimeUtil.localDateToDate(profileDTO.getDob()));
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
        profileDTO.setId(profileEN.getId());
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
        profileDTO.setDob(DateTimeUtil.dateToLocalDate(profileEN.getDob()));
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

    public QuestionRangeDTO getQuestionRangeDTO(QuestionRangeEN questionRangesEN) {
        QuestionRangeDTO questionRangesDTO = new QuestionRangeDTO();
        questionRangesDTO.setCategory(questionRangesEN.getCategory());
        questionRangesDTO.setId(questionRangesEN.getId());
        questionRangesDTO.setLower(questionRangesEN.getLower());
        questionRangesDTO.setUpper(questionRangesEN.getUpper());
        return questionRangesDTO;
    }

    public SurveyEN getSurveyEN(SurveyDTO surveyDTO) {
        SurveyEN surveyEN = new SurveyEN();
        surveyEN.setId(surveyDTO.getId());
        surveyEN.setName(surveyDTO.getName());
        return surveyEN;
    }

    public SurveyDTO getSurveyDTO(SurveyEN surveyEN) {
        SurveyDTO surveyDTO = new SurveyDTO();
        surveyDTO.setId(surveyEN.getId());
        surveyDTO.setName(surveyEN.getName());
        return surveyDTO;
    }

    public AnswerDTO getAnswerDTO(AnswerEN answer) {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId(answer.getId());
        answerDTO.setAnswer(answer.getAnswer());
        answerDTO.setProfileId(answer.getProfile().getId());
        answerDTO.setQuestionId(answer.getQuestion().getId());
        answerDTO.setSurveyId(answer.getSurvey().getId());
        answerDTO.setQuestionRangeId(answer.getAnswerQuestionRange() != null ? answer.getAnswerQuestionRange().getId() : null);
        return answerDTO;
    }

    public QuestionDTO getQuestionDTO(QuestionEN question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setText(question.getText());
        List<QuestionRangeEN> questionRanges = question.getQuestionRanges();
        for (QuestionRangeEN questionRange : questionRanges) {
            questionDTO.getQuestionRanges().add(getQuestionRangeDTO(questionRange));
        }
        return questionDTO;
    }

    public AnswerEN getAnswerEN(AnswerDTO answerDTO) {
        AnswerEN answerEN = new AnswerEN();
        answerEN.setAnswer(answerDTO.getAnswer());
        return answerEN;
    }
}
