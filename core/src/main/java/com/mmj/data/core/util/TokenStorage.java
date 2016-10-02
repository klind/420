package com.mmj.data.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class TokenStorage {
    private static final Logger LOG = LoggerFactory.getLogger(TokenStorage.class);
    private static final Map<String, Long> tokens = new HashMap<>();

    public static boolean isTokenValid(String token) {
        boolean valid = false;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Token storage size : {}", tokens.size());
            tokens.entrySet().forEach(e -> {
                        LOG.debug("Token : {}", e.getKey());
                        LOG.debug("Expires in : {} seconds", (e.getValue() - System.currentTimeMillis()) / 1000);
                    }
            );
        }
        if (tokens.keySet().contains(token)) {
            Long tokenValidTo = tokens.get(token);
            if (System.currentTimeMillis() < tokenValidTo) {
                valid = true;
            } else {
                removeToken(token);
            }
        }

        return valid;
    }

    public static void removeToken(String token) {
        if (tokens.keySet().contains(token)) {
            LOG.debug("Removing from token storage: {}", token);
            tokens.keySet().remove(token);
        }
    }

    public static void addToken(String token) {
        if (!tokens.keySet().contains(token)) {
            LOG.debug("Adding to token storage {}", token);
            tokens.put(token, System.currentTimeMillis() + 60*60*1000);
        }
    }

    public static void cleanup() {
        tokens.entrySet().removeIf(e -> e.getValue() < System.currentTimeMillis());
    }
}
