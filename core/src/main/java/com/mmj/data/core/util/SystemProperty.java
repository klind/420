package com.mmj.data.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum SystemProperty implements PropertySetEnum, Sensitive {
    G4_FLIGHT_SCHEDULE_URL("g4-flight-schedule-url", ""),
    G4_FLIGHT_ORDER_URL("g4-flight-order-url", ""),
    G4_FLIGHT_ANCILLARY_URL("g4-flight-ancillary-url", ""),
    //G4_FLIGHT_RESERVATION_URL("g4-flight-reservation-url", ""),
    G4_FLIGHT_SHOP_URL("g4-flight-shop-url", ""),
    G4_LOOKUP_URL("g4-lookup-url", ""),
    G4_FLIGHT_SUMMARY_URL("g4-flight-summary-url", ""),
    G4_MY_ID_TRAVEL_URL("g4-my-id-travel-url", "");

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemProperty.class);

    private String propertyName;
    private String defaultValue;
    private boolean sensitive;

    private SystemProperty(String propertyName, String defaultValue) {
        this(propertyName, defaultValue, false);
    }

    private SystemProperty(String propertyName, String defaultValue, boolean sensitive) {
        this.propertyName = propertyName;
        this.defaultValue = defaultValue;
        this.sensitive = sensitive;
    }

    @Override
    public String getKey() {
        return this.propertyName;
    }

    @Override
    public String getValueDefault() {
        return this.getValueDefault(true);
    }

    @Override
    public String getValue() {
        return this.getValue(true);
    }

    @Override
    public boolean isValueDefault() {
        return System.getProperty(this.propertyName) == null;
    }

    @Override
    public boolean isSensitive() {
        return sensitive;
    }

    @Override
    public String getValue(boolean sensitiveAware) {
        return sensitiveAware && this.isSensitive()
                ? PropertySetUtil.maskValue(System.getProperty(this.propertyName, this.defaultValue))
                : System.getProperty(this.propertyName, this.defaultValue);
    }

    @Override
    public String getValueDefault(boolean sensitiveAware) {
        return sensitiveAware && this.isSensitive()
                ? PropertySetUtil.maskValue(this.defaultValue)
                : this.defaultValue;
    }

}
