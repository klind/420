package com.mmj.data.core.util;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class PropertySetUtil {

    private static List<Reflections> reflections;

    private PropertySetUtil() {
    }

    public static List<PropertySet> getPropertySets() {
        return getPropertySets(true, null);
    }

    public static List<PropertySet> getPropertySets(Boolean sorted) {
        return getPropertySets(sorted, null);
    }

    public static List<PropertySet> getPropertySets(Boolean sorted, List<SystemPropertySpec> alsoIncludes) {
        List<PropertySet> ret = new ArrayList<PropertySet>();


        Set<Class<? extends PropertySetEnum>> propertyEnums = getPropertySetEnumClasses();
        for (Class propertyEnumClass : propertyEnums) {
            PropertySet propertySet = new PropertySet(propertyEnumClass);
            ret.add(propertySet);
        }

        if (alsoIncludes != null) {
            for (SystemPropertySpec alsoInclude : alsoIncludes) {
                PropertySet propertySet = new PropertySet(alsoInclude.getPropertySetName());
                for (String propertyName : System.getProperties().stringPropertyNames()) {
                    if (propertyName.startsWith(alsoInclude.getSystemPropertyNamePrefix())) {
                        Property property = new Property(null, propertyName, System.getProperty(propertyName), null, false);
                        propertySet.getProperties().add(property);
                    }
                }

                if (propertySet.getProperties().size() > 0) {
                    ret.add(propertySet);
                }
            }

        }
        if (sorted != null && sorted) {
            Collections.sort(ret);
        }
        return ret;
    }

    public static String maskValue(String value) {
        if (!StringUtils.isEmpty(value)) {
            Integer charsToMask = ((Double) (value.length() * .8)).intValue();
            String paddedString = value.substring(charsToMask - 1, value.length());
            return StringUtils.leftPad(paddedString, value.length(), "*");
        } else {
            return value;
        }
    }

    private static Set<Class<? extends PropertySetEnum>> getPropertySetEnumClasses() {
        Set<Class<? extends PropertySetEnum>> ret = new HashSet<Class<? extends PropertySetEnum>>();
        for (Reflections r : getReflections()) {
            ret.addAll(r.getSubTypesOf(PropertySetEnum.class));
        }
        return ret;
    }

    private static List<Reflections> getReflections() {
        if (reflections == null) {
            reflections = new ArrayList<Reflections>();
            reflections.add(new Reflections("com.allegiant"));
            reflections.add(new Reflections("com.allegiantair"));
            reflections.add(new Reflections("com.allegianttravel"));
            String additionalPackages = System.getProperty("util-property.reflection.packages", "");
            if (!StringUtils.isEmpty(additionalPackages)) {
                for (String packageDef : additionalPackages.split(",")) {
                    reflections.add(new Reflections(packageDef));
                }
            }
        }
        return reflections;
    }
}
