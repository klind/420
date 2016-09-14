package com.mmj.data.ejb.session.schedule;

import com.mmj.data.core.exception.SystemException;
import com.mmj.data.core.interceptors.TimerInterceptor;
import com.mmj.data.ejb.cache.G4tcCache;
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
public class RefundPaymentScheduleSB {
    private static final Logger LOG = LoggerFactory.getLogger(RefundPaymentScheduleSB.class);
    public static final String PAYMENT_REFUND = "PAYMENT_REFUND";

    @Inject
    private RefundSB refundSB;


    @Inject
    private G4tcCache g4tcCache;

    @PostConstruct
    public void init() {
        LOG.debug("RefundPaymentScheduleSB init");
    }

    //@Schedule(second = "0", minute = "0", hour = "4", dayOfMonth = "*", month = "*", year = "*", persistent = false)
    //@Schedule(second = "0", minute = "0", hour = "*", persistent = false)
    @Interceptors({TimerInterceptor.class})
    private void refundPaymentSchedule() {
        LOG.debug("refundPaymentSchedule......");
        String g4tcSingletonTimer = System.getProperty("g4tc-singleton-timer");
        if (g4tcSingletonTimer != null && "on".equals(g4tcSingletonTimer)) {
            LOG.info("refundPaymentSchedule running on : " + System.getProperty("jboss.node.name"));
            try {
                refundSB.refundPayments();
            } catch (SystemException e) {
                LOG.error("Could not refund payments for unused guest passes.", e);
            }
        }
    }
}
