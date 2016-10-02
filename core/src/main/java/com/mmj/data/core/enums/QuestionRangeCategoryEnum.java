package com.mmj.data.core.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum QuestionRangeCategoryEnum implements Serializable {
    AGE_RANGE, SLEEP, CAFFEINE_DRINKS, BOWEL_MOVEMENTS, ACTIVITY_LEVEL, BODY_FAT_MALE, BODY_FAT_FEMALE;

}
