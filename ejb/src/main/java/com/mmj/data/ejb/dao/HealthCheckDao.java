package com.mmj.data.ejb.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;

public class HealthCheckDao {

    private static final Logger LOG = LoggerFactory.getLogger(HealthCheckDao.class);

    @PersistenceContext
    private EntityManager em;

    public String getCount(String nativeQueryString) {
        String result = "";
        Object data = null;
        try {
            data = (BigInteger) em.createNativeQuery(nativeQueryString).getSingleResult();
        } catch (NoResultException e) {
            LOG.error("Could not run native query string: " + nativeQueryString);
        }
        result = data.toString();
        return result;
    }

    public String getEmployeeCount() {
        return getCount("SELECT COUNT(*) FROM employee;");
    }

    public String getPassengerCount() {
        return getCount("SELECT COUNT(*) FROM passenger;");
    }

    public String getSaCodeCount() {
        return getCount("SELECT COUNT(*) FROM sa_code;");
    }

    public String getConfigurationCount() {
        return getCount("SELECT COUNT(*) FROM configuration;");
    }

    public String getSuspensionCount() {
        return getCount("SELECT COUNT(*) FROM suspension;");
    }

    public String getSuspensionTypeCount() {
        return getCount("SELECT COUNT(*) FROM suspensiontype;");
    }

    public String getTravelerRelationshipCount() {
        return getCount("SELECT COUNT(*) FROM travelerrelationship;");
    }

    public String getTravelTypesCount() {
        return getCount("SELECT COUNT(*) FROM traveltypes;");
    }

    public String getSuspensionCommentCount() {
        return getCount("SELECT COUNT(*) FROM suspension_comment;");
    }

}
