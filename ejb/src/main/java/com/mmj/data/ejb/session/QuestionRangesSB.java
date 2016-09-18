package com.mmj.data.ejb.session;

import com.mmj.data.core.dto.entity.QuestionRangeDTO;
import com.mmj.data.ejb.dao.ProfileDao;
import com.mmj.data.ejb.dao.QuestionRangesDao;
import com.mmj.data.ejb.model.QuestionRangeEN;
import com.mmj.data.ejb.transformer.Transformer;
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
public class QuestionRangesSB {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionRangesSB.class);

    @Inject
    private ProfileDao profileDao;

    @Inject
    private QuestionRangesDao questionRangesDao;

    @Inject
    private Transformer transformer;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<QuestionRangeDTO> getQuestionRanges() {
        List<QuestionRangeDTO> result = new ArrayList<>();
        List<QuestionRangeEN> questionRangesENs = questionRangesDao.getQuestionRanges();
        for (QuestionRangeEN questionRangesEN : questionRangesENs) {
            result.add(transformer.getQuestionRangeDTO(questionRangesEN));
        }
        return result;
    }
}
