package com.mmj.data.ejb.session;

import com.mmj.data.ejb.dao.HealthCheckDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@LocalBean
public class HealthCheckSB {
    private static final Logger LOG = LoggerFactory.getLogger(HealthCheckSB.class);

    @Inject
    private HealthCheckDao healthCheckDao;

    public List<String> getHealthCheck(){
        List<String> result = new ArrayList<String>();
        result.add(getEmployeeCount() + " Employee Records");
        result.add(getPassengerCount() + " PassengerWaitlistDto Records");
        result.add(getSaCodeCount() + " SA Code Records");
        result.add(getSuspensionCount() + " Suspension Records");
        result.add(getSuspensionCommentCount() + " Suspension Comment Records");
        result.add(getSuspensionTypeCount() + " Suspension Type Records");
        result.add(getTravelerRelationshipCount() + " Traveler Relationship Records");
        result.add(getTravelTypesCount() + " Travel Types Records");
        return result;
    }

    public String getEmployeeCount() {
        return healthCheckDao.getEmployeeCount();
    }

    public String getPassengerCount() {
        return healthCheckDao.getPassengerCount();
    }

    public String getSaCodeCount() {
        return healthCheckDao.getSaCodeCount();
    }

    public String getConfigurationCount() {
        return healthCheckDao.getConfigurationCount();
    }

    public String getSuspensionCount() {
        return healthCheckDao.getSuspensionCount();
    }

    public String getSuspensionTypeCount() {
        return healthCheckDao.getSuspensionTypeCount();
    }

    public String getTravelerRelationshipCount() {
        return healthCheckDao.getTravelerRelationshipCount();
    }

    public String getTravelTypesCount() {
        return healthCheckDao.getTravelTypesCount();
    }

    public String getSuspensionCommentCount() {
        return healthCheckDao.getSuspensionCommentCount();
    }

}
