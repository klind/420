package com.mmj.data.ejb.schedule;

import com.mmj.data.core.util.TokenStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class TokenStorageSchedulerSB {
    private static final Logger LOG = LoggerFactory.getLogger(TokenStorageSchedulerSB.class);

    @PostConstruct
    public void init() {
        LOG.debug("TokenStorageSchedulerSB init");
    }

    @Schedule(second = "0", minute = "*", hour = "*", dayOfMonth = "*", month = "*", year = "*", persistent = false)
    private void cleanTokenStorage() {
        LOG.debug("cleanTokenStorage......");
        TokenStorage.cleanup();
    }
}
