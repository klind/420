package com.mmj.data.core.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum RelationshipTypeEnum {
    PA(ConfigurationCategoryEnum.PASSENGER_LIMIT, ConfigurationIdentityEnum.MAX_ALLOWED_PARENTS),
    CH(ConfigurationCategoryEnum.PASSENGER_LIMIT, ConfigurationIdentityEnum.MAX_ALLOWED_CHILDREN),
    SP(ConfigurationCategoryEnum.PASSENGER_LIMIT, ConfigurationIdentityEnum.MAX_ALLOWED_SPDPTC),
    DP(ConfigurationCategoryEnum.PASSENGER_LIMIT, ConfigurationIdentityEnum.MAX_ALLOWED_SPDPTC),
    TC(ConfigurationCategoryEnum.PASSENGER_LIMIT, ConfigurationIdentityEnum.MAX_ALLOWED_SPDPTC);

    private ConfigurationCategoryEnum category;
    private ConfigurationIdentityEnum identity;

    private static List<List<RelationshipTypeEnum>> groups = new ArrayList<>();

    static {
        ArrayList<RelationshipTypeEnum> group = new ArrayList<>();
        group.add(SP);
        group.add(DP);
        group.add(TC);
        groups.add(group);
    }

    RelationshipTypeEnum(ConfigurationCategoryEnum category, ConfigurationIdentityEnum identity) {
        this.category = category;
        this.identity = identity;
    }

    public ConfigurationCategoryEnum getCategory() {
        return category;
    }

    public ConfigurationIdentityEnum getIdentity() {
        return identity;
    }

    public static List<RelationshipTypeEnum> getGroupByValue(String value) {
        List<RelationshipTypeEnum> group = new ArrayList<>();
        RelationshipTypeEnum r = getSingleByValue(value);
        for (List<RelationshipTypeEnum> g : groups) {
            if (g.contains(r)) {
                group = g;
                break;
            }
        }
        return group;
    }

    public static RelationshipTypeEnum getSingleByValue(String value) {
        return EnumSet.allOf(RelationshipTypeEnum.class)
                .stream()
                .filter(e -> e.toString().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", value)));
    }
}



