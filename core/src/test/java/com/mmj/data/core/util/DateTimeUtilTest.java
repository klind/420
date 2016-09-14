package com.mmj.data.core.util;

import com.mmj.data.core.exception.BusinessException;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 */
public class DateTimeUtilTest {

    @Test
    public void isNowBetweenTest() {
        long oneday = 86400000;
        Date now = new Date();
        long time = now.getTime();

        Date startDate = new Date();
        Date endDate = new Date();

        Assert.assertEquals(true, DateTimeUtil.isNowBetween(now, now));
        Assert.assertEquals(true, DateTimeUtil.isNowBetween(startDate, startDate));
        Assert.assertEquals(true, DateTimeUtil.isNowBetween(endDate, endDate));
        Assert.assertEquals(true, DateTimeUtil.isNowBetween(startDate, endDate));
        Assert.assertEquals(true, DateTimeUtil.isNowBetween(endDate, startDate));

        startDate = new Date(time - (oneday * 5));
        endDate = new Date(time + (oneday * 5));

        Assert.assertEquals(false, DateTimeUtil.isNowBetween(startDate, startDate));
        Assert.assertEquals(false, DateTimeUtil.isNowBetween(endDate, endDate));
        Assert.assertEquals(true, DateTimeUtil.isNowBetween(startDate, endDate));
        Assert.assertEquals(false, DateTimeUtil.isNowBetween(endDate, startDate));

        startDate = new Date(time - (oneday * 10));
        endDate = new Date(time - (oneday * 5));

        Assert.assertEquals(false, DateTimeUtil.isNowBetween(startDate, endDate));
        Assert.assertEquals(false, DateTimeUtil.isNowBetween(endDate, startDate));

        startDate = new Date(time + (oneday * 5));
        endDate = new Date(time + (oneday * 10));

        Assert.assertEquals(false, DateTimeUtil.isNowBetween(startDate, endDate));
        Assert.assertEquals(false, DateTimeUtil.isNowBetween(endDate, startDate));
    }

    @Test
    public void stringToLocalDateTest() {
        String date = "2016-05-05";
        try {
            LocalDate localDate = LocalDate.parse(date);
            Assert.assertEquals(localDate, DateTimeUtil.stringToLocalDate(date));
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stringToLocalDateNegativeTest() {
        String date = "31/12/2016";
        try {
            LocalDate foo = DateTimeUtil.stringToLocalDate(date);   // Should throw exception.
            Assert.assertTrue(false);
        } catch (BusinessException e) {
            Assert.assertTrue(true);
        }
    }


}
