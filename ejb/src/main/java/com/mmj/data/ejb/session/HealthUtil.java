package com.mmj.data.ejb.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * Health Util that will help get information about the system we are deployed to.
 */
@Stateless
public class HealthUtil {
    private static final Logger LOG = LoggerFactory.getLogger(HealthUtil.class);

    @Inject
    private HealthCheckSB healthCheckSB;

    /**
     * @return Simple output of system variables with html break returns.
     */
    public String getAllSystemVariables() {
        String result = "";
        Properties p = System.getProperties();
        Enumeration keys = p.keys();
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            String value = (String)p.get(key);
            result += key + ": " + value + "<BR>";
        }
        return result;
    }

    /**
     * @return Simple output with html break returns.
     */
    public String getDBStuff() {
        String result = "";
        try {
            Response response;
            List<String> foo;
            foo = healthCheckSB.getHealthCheck();
            result = foo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
