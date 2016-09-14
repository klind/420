package com.mmj.data.core.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *
 */
public final class ToJson {
    private static final Logger LOG = LoggerFactory.getLogger(ToJson.class);

    private ToJson() {
    }

    public static String toJson(Object o) {
        try {
            return JsonUtils.defaultJacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (IOException e) {
            LOG.error("Could not convert {} to Json", o, e);
            return e.getMessage();
        }
    }
}
