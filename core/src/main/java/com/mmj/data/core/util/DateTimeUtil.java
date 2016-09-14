package com.mmj.data.core.util;

import com.mmj.data.core.exception.BusinessException;
import org.joda.time.LocalDate;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import static com.mmj.data.core.constant.Constants.DATE_REGEX;

/**
 *
 */
public final class DateTimeUtil {

    private DateTimeUtil() {
    }

    /**
     * Returns a instance of java.sql.Timestamp with current time in GMT
     *
     * @return java.sql.Timestamp
     */
    public static Timestamp getNowGMT() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * Returns a instance of java.time.LocalDate with current time in GMT
     *
     * @return java.time.LocalDate
     */
    public static java.time.LocalDate getLocalDateNowGMT() {
        return getNowGMT().toLocalDateTime().toLocalDate();
    }

    /**
     * Returns true if now is between startDate and endDate ( inclusive )
     * otherwise false.
     *
     * @param startDate
     * @param endDate
     * @return boolean
     */
    public static boolean isNowBetween(Date startDate, Date endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("StartDate can not me null");
        }

        Date now = new Date();

        LocalDate jodaNow = new LocalDate(now.getTime());
        LocalDate jodaStart = new LocalDate(startDate.getTime());
        LocalDate jodaEnd = endDate == null ? null : new LocalDate(endDate.getTime());

        return (
                (jodaNow.isEqual(jodaStart) || jodaNow.isAfter(jodaStart))
                        &&
                (jodaEnd == null || (jodaNow.isEqual(jodaEnd) || jodaNow.isBefore(jodaEnd)))
        );
    }

    /**
     * @param date
     * @return java.time.LocalDate
     */
    public static java.time.LocalDate dateToLocalDate(Date date) {
        return date == null ? null : Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * @param date
     * @return Date
     */
    public static Date localDateToDate(java.time.LocalDate date) {
        return date == null ? null : Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @param date
     * @return java.time.LocalDate
     */
    public static java.time.LocalDate stringToLocalDate(String date) throws BusinessException {
        if (date == null || !date.matches(DATE_REGEX)) {
            throw new BusinessException("Date ( " + date + " ) is not valid, must match regex " + DATE_REGEX);
        }

        return java.time.LocalDate.parse(date);
    }
}
