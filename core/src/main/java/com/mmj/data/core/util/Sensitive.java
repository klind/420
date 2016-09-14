package com.mmj.data.core.util;

public interface Sensitive {
    /**
     * Indicates whether the property contains sensitive information
     * @return true if sensitive
     */
    public boolean isSensitive();

    /**
     * Returns the property value
     * @param sensitiveAware indicates whether sensitive information should be handled specifically
     * @return the property value
     */
    public String getValue(boolean sensitiveAware);

    /**
     * Returns the property default value
     * @param sensitiveAware indicates whether sensitive information should be handled specifically
     * @return the property default value
     */
    public String getValueDefault(boolean sensitiveAware);
}
