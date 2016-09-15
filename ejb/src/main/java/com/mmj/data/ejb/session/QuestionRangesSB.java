package com.mmj.data.ejb.session;

import com.mmj.data.ejb.dao.ProfileDao;
import com.mmj.data.ejb.dao.QuestionRangesDao;
import com.mmj.data.ejb.model2.QuestionRangesEN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
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

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<QuestionRangesEN> getQuestionRanges() {
        List<QuestionRangesEN> questionRangesENs = questionRangesDao.getScoreRanges();
        return questionRangesENs;
    }
}
