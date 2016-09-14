package com.mmj.data.core.constant;

import com.mmj.data.core.enums.SACodeEnum;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public final class Constants {

    public static final String DATE_REGEX = "^([1-2]{1}[0|9]{1}[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12]\\d|3[01])$";
    public static final String DOB_REGEX = "^([1-2]{1}[0|9]{1}[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12]\\d|3[01])$";
    public static final String PHONE_REGEX = "^[1-9]{1}\\d{9}$";
    public static final String JOB_CODE_REGEX = "^\\d{4}$";
    public static final String LOCATION_REGEX = "^[A-Z]{2,4}$";
    public static final String DEPARTMENT_ID_REGEX = "^\\d{3}$";
    public static final String EMPLOYEE_ID_REGEX = "^\\d{6}$";
    public static final String EMPLOYEE_GENDER_REGEX = "^[M|F]{1}$"; // Male, Female, or Other
    public static final String EMPLOYEE_TYPE_REGEX = "^(?i)[R|I|N|T]{1}$"; // Regular, Intern, Trainee, Temporary
    public static final String STATUS_REGEX = "^(?i)[S|L|A|O|R|T]{1}$"; // Suspended, Leave of Absence, Active, On Strike, Retired, Terminated
    public static final String FIRST_NAME_REGEX = "^[A-Z]{1}([a-zA-Z ,\\.'-]{1,29})$";
    public static final String MIDDLE_NAME_REGEX = "^[A-Z]{1}([a-zA-Z ,\\.'-]{0,29})$";
    public static final String LAST_NAME_REGEX = FIRST_NAME_REGEX;
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_EU = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String EMAIL_REGEX = "(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static final String POSITIVE_NUMBER_REGEX = "([0]|([1-9]\\d*))";
    public static final String SEARCH_EMPLOYEE_ID_REGEX = "[a-zA-Z0-9]{0,6}";
    public static final String VACATIONUPGRADE = "true|false";
    public static final String SEARCH_EMPLOYEE_ID_ERROR_TXT = "The employee id must be a alphanumeric value with a length of max 6 characters";
    public static final String WILDCARD_SEARCH_REGEX = "^\\*?([a-zA-Z0-9\\-'\\. ]){1,30}\\*?$"; // mininum of one character and max 30
    public static final int PAGE_SIZE_EMPLOYEE = 25;
    public static final String DATETIMEREGEX = "^([1-2]{1}[0|9]{1}[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12]\\d|3[01]) ([0-1]\\d|2[0-3])(:)([0-5]\\d)$";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat SIMPLE_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat SIMPLE_DATE_FORMATTER_EU = new SimpleDateFormat(DATE_FORMAT_EU);
    public static final String DATE_REGEX_ERROR_TXT = "Date is not valid, must match regex " + DATE_REGEX;
    public static final String PASSENGER_SEGMENT = "PASSENGER_SEGMENT";
    public static final String PASSENGER_WAITLIST = "PASSENGER_WAITLIST";
    public static final String PASSENGER_BAG = "PASSENGER_BAG";
    public static final String PASSENGER_SSR = "PASSENGER_SSR";
    public static final String CALLER_KEY = "G4TC";
    public static final String CANCEL_REASON_P1 = "P1";
    public static final Integer CHANNEL_ID = new Integer(7);
    public static final String HEADER_API_KEY = "x-gen2-api-key";
    public static final String HEADER_CALLER_KEY = "x-gen2-caller-key";
    public static final String HEADER_CHANNEL_ID = "x-gen2-channel-id";
    public static final String HEADER_USER_ID = "x-gen2-user-id";
    public static final String HEADER_USERNAME = "x-gen2-user-name";
    public static final String SA_CODE_RETIREE = SACodeEnum.SA5.name();
    public static final ArrayList<Integer> SHOW_CANCEL = new ArrayList<>();
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


    /*
    ---------------------------
    | chc | brd | flo | cancel |
    ---------------------------
    |  0  |  0  |  0  |   Y    |
    ---------------------------
    |  0  |  0  |  1  |   Y    |
    ---------------------------
    |  0  |  1  |  0  |   N    |
    ---------------------------
    |  0  |  1  |  1  |   N    |
    ---------------------------
    |  1  |  0  |  0  |   N    |
    ---------------------------
    |  1  |  0  |  1  |   Y    |
    ---------------------------
    |  1  |  1  |  0  |   N    |
    ---------------------------
    |  1  |  1  |  1  |   N    |
    ---------------------------
    */
    static {
        SHOW_CANCEL.add(new Integer(0));
        SHOW_CANCEL.add(new Integer(1));
        SHOW_CANCEL.add(new Integer(5));
    }

    private Constants() throws Exception {
        throw new Exception("This is a static class.  Please do not create an instance of it.");
    }
}
