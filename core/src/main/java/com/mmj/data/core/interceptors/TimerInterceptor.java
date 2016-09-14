package com.mmj.data.core.interceptors;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * Timer Interceptor, used to help track time of services. Can be used for debugging or performace tracking purposes.
 */
public class TimerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(TimerInterceptor.class);

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        Object result = null;
        if (LOG.isDebugEnabled()) {
            String className = context.getTarget().getClass().getName();
            String methodName = context.getMethod().getName();
            Object[] params = context.getParameters();
            if (params.length != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Entering ").append(className).append(".").append(methodName).append("(");
                for (Object param : params) {
                    sb.append(param != null ? param.toString() : "null").append(", ");
                }
                sb.delete(sb.length() - 2, sb.length());
                sb.append(")");
                LOG.debug(sb.toString());
            }
            StopWatch stopwatch = new StopWatch();
            try {
                stopwatch.start();
                result = context.proceed();
            } finally {
                stopwatch.stop();
                LOG.debug("Execution of {}.{} took {} milliseconds", new Object[]{className, methodName, stopwatch.getTime()});
                LOG.debug("Result of execution: {}", result);
            }
        } else {
            result = context.proceed();
        }
        return result;
    }
}
