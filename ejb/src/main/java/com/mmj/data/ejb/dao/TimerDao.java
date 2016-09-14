package com.mmj.data.ejb.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 *
 */
public class TimerDao {
    private static final Logger LOG = LoggerFactory.getLogger(TimerDao.class);

    @PersistenceContext()
    private EntityManager em;

    /**
     * Returns the last run time of the specified timer
     *
     * @param timerName
     * @return Date
     */
    public Date getLastRuntime(String timerName) {
        Date d = (Date) em.createNativeQuery("SELECT MAX(last_run_time) FROM timer WHERE timer_name = :timerName")
                .setParameter("timerName", timerName).getSingleResult();
        return d;
    }

    /**
     * Updates the last run time for the specified timer.
     *
     * @param timerName
     * @param lastRuntime
     */
    public void updateLastRuntime(String timerName, Date lastRuntime) {
        int i = em.createNativeQuery("UPDATE timer set last_run_time = :lastRuntime WHERE timer_name = :timerName")
                .setParameter("lastRuntime", lastRuntime)
                .setParameter("timerName", timerName)
                .executeUpdate();

        if (i == 0) {
            em.createNativeQuery("INSERT INTO timer (timer_name, last_run_time) VALUES (:timerName, :lastRuntime)")
                    .setParameter("lastRuntime", lastRuntime)
                    .setParameter("timerName", timerName)
                    .executeUpdate();
        }
    }
}
