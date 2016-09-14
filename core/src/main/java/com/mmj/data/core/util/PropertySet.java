package com.mmj.data.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PropertySet implements Comparable<PropertySet> {

    private final static Logger LOGGER = LoggerFactory.getLogger(PropertySet.class);

    private String name;
    private List<Property> properties;

    public PropertySet() {
        this("Unnamed");
    }

    public PropertySet(String name) {
        this.name = name;
        this.properties = new ArrayList<Property>();
    }

    public PropertySet(Class<? extends PropertySetEnum> propertyEnumClass) {
        this.name = propertyEnumClass.getSimpleName();
        this.properties = new ArrayList<Property>();
        Method getKey = null;
        Method getValue = null;
        Method getValueDefault = null;
        Method getIsValueDefault = null;
        Method getIsSensitive = null;

        for (Method method : propertyEnumClass.getMethods()) {
            if (method.getName().equalsIgnoreCase("getKey")) {
                getKey = method;
            } else if (method.getName().equalsIgnoreCase("getValue") && method.getParameterTypes().length == 0) {
                getValue = method;
            } else if (method.getName().equalsIgnoreCase("getValueDefault") && method.getParameterTypes().length == 0) {
                getValueDefault = method;
            } else if (method.getName().equalsIgnoreCase("isValueDefault")) {
                getIsValueDefault = method;
            } else if (method.getName().equalsIgnoreCase("isSensitive")) {
                getIsSensitive = method;
            }
        }

        Object[] enumValues = propertyEnumClass.getEnumConstants();
        for (Object enumValue : enumValues) {
            String enumKey = enumValue.toString();
            String systemPropertyName = null;
            String value = null;
            String valueDefault = null;
            Boolean isValueDefault = null;
            Boolean isSensitive = false;
            try {
                if (getKey != null) {
                    systemPropertyName = (String) getKey.invoke(enumValue);
                }
                if (getValue != null) {
                    value = (String) getValue.invoke(enumValue);
                }
                if (getValueDefault != null) {
                    valueDefault = (String) getValueDefault.invoke(enumValue);
                }
                if (getIsValueDefault != null) {
                    isValueDefault = (Boolean) getIsValueDefault.invoke(enumValue);
                }
                if (getIsSensitive != null) {
                    isSensitive = (Boolean) getIsSensitive.invoke(enumValue);
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Unable to properly access PropertyEnum", e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException("Unable to properly access PropertyEnum", e);
            }

            Property property = new Property(enumKey, systemPropertyName, value, valueDefault, isValueDefault, isSensitive);

            this.properties.add(property);
        }

    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Property> getProperties() {
        return this.properties;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Name: ").append(this.getName()).append(System.getProperty("line.separator"));
        Boolean first = true;
        for (Property property : this.getProperties()) {
            if (first) {
                first = false;
            } else {
                str.append(System.getProperty("line.separator"));
            }
            str.append(property.toString());
        }

        return str.toString();
    }

    @Override
    public int compareTo(PropertySet that) {
        return this.getName().compareTo(that.getName());
    }
}
