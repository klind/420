package com.mmj.data.core.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mmj.data.core.exception.SystemException;

import java.util.EnumSet;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EmployeeTypeEnum {
    R("REGULAR"), I("INTERN"), N("TRAINEE"), T("TEMPORARY");
    private String value;

    EmployeeTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static EmployeeTypeEnum getSingleByName(String name) {
        return EnumSet.allOf(EmployeeTypeEnum.class)
                .stream()
                .filter(e -> e.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new SystemException(String.format("Unsupported type %s.", name)));
    }

    public static EmployeeTypeEnum getSingleByValue(String value) {
        return EnumSet.allOf(EmployeeTypeEnum.class)
                .stream()
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new SystemException(String.format("Unsupported type %s.", value)));
    }

}
