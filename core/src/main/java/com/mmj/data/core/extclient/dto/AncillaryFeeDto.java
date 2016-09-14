package com.mmj.data.core.extclient.dto;

import java.util.List;

public class AncillaryFeeDto extends FeeDto {

    private static final long serialVersionUID = -6838243350527206520L;

    private List<PropertyDto> properties;

    public List<PropertyDto> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyDto> properties) {
        this.properties = properties;
    }

    public String getProperty(String name) {

        for(PropertyDto propertyDto : properties){
            if(propertyDto.getName().equals(name)){
                return propertyDto.getValue();
            }
        }
        return null;
    }
}
