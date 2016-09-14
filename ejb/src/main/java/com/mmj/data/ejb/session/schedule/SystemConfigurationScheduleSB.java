package com.mmj.data.ejb.session.schedule;

import com.mmj.data.core.interceptors.TimerInterceptor;
import com.mmj.data.ejb.cache.G4tcCache;
import com.mmj.data.ejb.session.EmployeeSB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

@Singleton
@Startup
public class SystemConfigurationScheduleSB {
    private static final Logger LOG = LoggerFactory.getLogger(SystemConfigurationScheduleSB.class);

    @Inject
    private EmployeeSB employeeSB;

    @Inject
    private G4tcCache g4tcCache;

    @PostConstruct
    public void init() {
        LOG.debug("SystemConfigurationScheduleSB init");
    }

    /*
     * Every 15 minutes within the hour
     */
    @Schedule(minute = "*/15", hour = "*", persistent = false)
    @Interceptors({TimerInterceptor.class})
    private void systemConfigurationSchedule() {
        LOG.debug("systemConfigurationSchedule......");
        try {
            g4tcCache.loadSystemConfigurations();
            g4tcCache.loadAccessConfigurations();
        } catch (Exception e) {
            LOG.error("Could not load system configutations.", e);
        }
    }
}
