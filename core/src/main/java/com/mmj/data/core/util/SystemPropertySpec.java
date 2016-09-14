package com.mmj.data.core.util;

public class SystemPropertySpec {
    private String propertySetName;
    private String systemPropertyNamePrefix;

    public SystemPropertySpec() {
    }

    public SystemPropertySpec(String propertySetName, String systemPropertyNamePrefix) {
        this.propertySetName = propertySetName;
        this.systemPropertyNamePrefix = systemPropertyNamePrefix;
    }

    public String getPropertySetName() {
        return propertySetName;
    }

    public void setPropertySetName(String propertySetName) {
        this.propertySetName = propertySetName;
    }

    public String getSystemPropertyNamePrefix() {
        return systemPropertyNamePrefix;
    }

    public void setSystemPropertyNamePrefix(String systemPropertyNamePrefix) {
        this.systemPropertyNamePrefix = systemPropertyNamePrefix;
    }
}
