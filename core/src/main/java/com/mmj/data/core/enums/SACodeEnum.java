package com.mmj.data.core.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SACodeEnum implements Serializable {
    SA1, SA2, SA3, SA4, SA5, SA6;
}
