package com.mmj.data.ejb.session.schedule;

import com.mmj.data.core.exception.SystemException;
import com.mmj.data.core.interceptors.TimerInterceptor;
import com.mmj.data.ejb.session.RefundSB;
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
public class GuestPassAndVacationRefundScheduleSB {
    private static final Logger LOG = LoggerFactory.getLogger(GuestPassAndVacationRefundScheduleSB.class);
    public static final String GUEST_AND_VACATION_REFUND = "GUEST_AND_VACATION_REFUND";

    @Inject
    private RefundSB refundSB;

    @PostConstruct
    public void init() {
        LOG.debug("GuestPassAndVacationRefundScheduleSB init");
    }

    //@Schedule(second = "0", minute = "0", hour = "3", dayOfMonth = "*", month = "*", year = "*", persistent = false)
    //@Schedule(second = "0", minute = "0", hour = "*", persistent = false)
    @Interceptors({TimerInterceptor.class})
    private void guestPassAndVacationRefundSchedule() {
        LOG.debug("guestPassAndVacationRefundSchedule......");
        String g4tcSingletonTimer = System.getProperty("g4tc-singleton-timer");
        if (g4tcSingletonTimer != null && "on".equals(g4tcSingletonTimer)) {
            LOG.info("guestPassAndVacationRefundSchedule running on : " + System.getProperty("jboss.node.name"));
            try {
                refundSB.refundGuestPassesAndVacationUpgrades();
            } catch (SystemException e) {
                LOG.error("Could not refund guest passes and vacation upgrades.", e);
            }
        }
    }

}
