package com.mmj.data.core.enums;

public enum SystemConfigurationEnum {
    FIRST_SEARCH_DATE(false),
    IS_PAY_NOW_BUTTON_VISIBLE(true);

    private boolean required;

    private SystemConfigurationEnum(boolean required) {
        this.required = required;
    }

    public boolean isRequired() {
        return required;
    }
}
