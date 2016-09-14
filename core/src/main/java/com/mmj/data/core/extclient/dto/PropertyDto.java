package com.mmj.data.core.extclient.dto;

public class PropertyDto extends G4FlightDto {

    private static final long serialVersionUID = 662551786357971199L;
    private String name;
    private String value;

    public PropertyDto() {

    }

    public PropertyDto(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }
}
