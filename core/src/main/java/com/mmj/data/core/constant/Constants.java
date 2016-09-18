package com.mmj.data.core.constant;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public final class Constants {

    public static final String DATE_REGEX = "^([1-2]{1}[0|9]{1}[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12]\\d|3[01])$";
    public static final String DOB_REGEX = "^([1-2]{1}[0|9]{1}[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12]\\d|3[01])$";
    public static final String PHONE_REGEX = "^[1-9]{1}\\d{9}$";
    public static final String EMPLOYEE_GENDER_REGEX = "^[M|F]{1}$"; // Male, Female, or Other
    public static final String FIRST_NAME_REGEX = "^[A-Z]{1}([a-zA-Z ,\\.'-]{1,29})$";
    public static final String MIDDLE_NAME_REGEX = "^[A-Z]{1}([a-zA-Z ,\\.'-]{0,29})$";
    public static final String LAST_NAME_REGEX = FIRST_NAME_REGEX;
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String EMAIL_REGEX = "(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static final String POSITIVE_NUMBER_REGEX = "([0]|([1-9]\\d*))";
    public static final String DATETIMEREGEX = "^([1-2]{1}[0|9]{1}[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12]\\d|3[01]) ([0-1]\\d|2[0-3])(:)([0-5]\\d)$";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat SIMPLE_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    public static final String DATE_REGEX_ERROR_TXT = "Date is not valid, must match regex " + DATE_REGEX;
    public static final ArrayList<String> BOOLEAN_TRUE = new ArrayList<>();
    public static final ArrayList<String> BOOLEAN_FALSE = new ArrayList<>();

    static {
        BOOLEAN_TRUE.add("1");
        BOOLEAN_TRUE.add("y");
        BOOLEAN_TRUE.add("true");
        BOOLEAN_TRUE.add("on");
        BOOLEAN_TRUE.add("yes");
    }

    static {
        BOOLEAN_FALSE.add("0");
        BOOLEAN_FALSE.add("n");
        BOOLEAN_FALSE.add("no");
        BOOLEAN_FALSE.add("false");
        BOOLEAN_FALSE.add("off");
    }

    private Constants() throws Exception {
        throw new Exception("This is a static class.  Please do not create an instance of it.");
    }
}
