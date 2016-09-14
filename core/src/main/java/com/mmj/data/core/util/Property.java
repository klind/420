package com.mmj.data.core.util;

public class Property {
    private String enumValue;
    private String systemPropertyName;
    private String value;
    private String valueDefault;
    private Boolean isValueDefault;
    private Boolean sensitive;

    public Property() {
    }

    public Property(String enumValue, String systemPropertyName, String value, String valueDefault, Boolean isValueDefault) {
        this(enumValue, systemPropertyName, value, valueDefault, isValueDefault, false);
    }

    public Property(String enumValue, String systemPropertyName, String value, String valueDefault, Boolean isValueDefault, Boolean sensitive) {
        this.enumValue = enumValue;
        this.systemPropertyName = systemPropertyName;
        this.value = value;
        this.valueDefault = valueDefault;
        this.isValueDefault = isValueDefault;
        this.sensitive = sensitive;
    }

    public String getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getSystemPropertyName() {
        return systemPropertyName;
    }

    public void setSystemPropertyName(String systemPropertyName) {
        this.systemPropertyName = systemPropertyName;
    }

    public String getValue() {
        if (!isSensitive()) {
            return value;
        } else {
            return PropertySetUtil.maskValue(value);
        }
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueDefault() {
        if (!isSensitive()) {
            return valueDefault;
        } else {
            return PropertySetUtil.maskValue(valueDefault);
        }
    }

    public void setValueDefault(String valueDefault) {
        this.valueDefault = valueDefault;
    }

    public Boolean getIsValueDefault() {
        return isValueDefault;
    }

    public void setIsValueDefault(Boolean isValueDefault) {
        this.isValueDefault = isValueDefault;
    }

    public Boolean isSensitive() {
        return sensitive;
    }

    @Override
    public String toString() {
        return String.format("ENUM_VALUE: %s, SYSTEM_PROPERTY_NAME: %s, DEFAULT_VALUE: %s, VALUE: %s, IS_VALUE_DEFAULT: %s",
                             this.getEnumValue(), this.getSystemPropertyName(), this.getValueDefault(), this.getValue(), !this.getIsValueDefault());
    }


}
