package com.mmj.data.core.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.EnumSet;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EmployeeStatusEnum implements Serializable {

    A("ACTIVE"),
    S("SUSPENDED"),
    L("LOA"),
    O("ON_STRIKE"),
    R("RETIRED"),
    T("TERMINATED");

    private String value;

    EmployeeStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static EmployeeStatusEnum getSingleByName(String name) {
        return EnumSet.allOf(EmployeeStatusEnum.class)
                .stream()
                .filter(e -> e.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", name)));
    }

    public static EmployeeStatusEnum getSingleByValue(String value) {
        return EnumSet.allOf(EmployeeStatusEnum.class)
                .stream()
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", value)));
    }

}
