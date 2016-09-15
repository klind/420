package com.mmj.data.ejb.session.schedule;

import com.mmj.data.core.interceptors.TimerInterceptor;
import com.mmj.data.ejb.cache.G4tcCache;
import com.mmj.data.ejb.session.EmployeeSB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

@Singleton
@Startup
public class ResetAllotmentScheduleSB {
    private static final Logger LOG = LoggerFactory.getLogger(ResetAllotmentScheduleSB.class);

    @Inject
    private EmployeeSB employeeSB;

    @Inject
    private G4tcCache g4tcCache;

    @PostConstruct
    public void init() {
        LOG.debug("ResetAllotmentScheduleSB init");
    }

    //@Schedule(minute = "0", hour = "0", dayOfMonth = "1", month = "Jan", year = "*", persistent = false)
    @Interceptors({TimerInterceptor.class})
    private void resetAllotmentSchedule() {
        LOG.debug("resetAllotmentSchedule......");
        try {
            employeeSB.resetPassAllotment();
        } catch (Exception e) {
            LOG.error("Could not reset pass allotment.", e);
        }
    }


}
