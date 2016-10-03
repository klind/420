package com.mmj.data.web.webservice;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class JaxRsActivator extends Application {
    public JaxRsActivator() {
        super();
    }
}
