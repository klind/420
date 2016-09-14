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
public class CacheHandlerScheduleSB {
    private static final Logger LOG = LoggerFactory.getLogger(CacheHandlerScheduleSB.class);

    @Inject
    private EmployeeSB employeeSB;

    @Inject
    private G4tcCache g4tcCache;

    @PostConstruct
    public void init() {
        LOG.debug("CacheHandlerScheduleSB init");
        g4tcCache.load();
    }

    //@Schedule(minute = "*", hour = "*", persistent = false)
    //@Schedule(dayOfWeek="*", hour = "12", persistent = false)
    @Interceptors({TimerInterceptor.class})
    private void schedule() {
        LOG.debug("CacheHandlerScheduleSB schedule");
        g4tcCache.refresh();
    }


}
