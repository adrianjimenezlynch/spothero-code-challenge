package com.spothero.challenge.util;

import com.spothero.challenge.bo.Rates;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class RateUtilsTest {

    @Before
    public void init() throws IOException {
        RateUtils.initializeRates();
    }

    @Test
    public void initializeRates_OK() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        assertEquals(5, getRatesPrivateField().getRates().size());
    }

    @Test(expected = NotFoundException.class)
    public void getPrice_StarTimeNotInRange() {
        RateUtils.getPrice("mon", 4, 12);
    }

    @Test(expected = NotFoundException.class)
    public void getPrice_EndTimeNotInRange() {
        RateUtils.getPrice("mon", 12, 22);
    }

    @Test(expected = NotFoundException.class)
    public void getPrice_EndTimeEqualsEndOfRange() {
        RateUtils.getPrice("mon", 12, 21);
    }

    @Test
    public void getPrice_StartTimeEqualsStartOfRange() {
        int rate = RateUtils.getPrice("mon", 9, 20);
        assertEquals(1500, rate);
    }

    @Test
    public void getPrice_StartTimeAndEndTimeInRange() {
        int rate = RateUtils.getPrice("mon", 12, 20);
        assertEquals(1500, rate);
    }

    @Test(expected = NotFoundException.class)
    public void getPrice_DayNotInRange() {
        RateUtils.getPrice("abc", 12, 20);
    }

    @Test(expected = NotFoundException.class)
    public void getPrice_RatesNotInitialized() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        setRatesToNull();
        RateUtils.getPrice("mon", 1, 1);
    }

    @Test
    public void getRateForDates_OK() {
        int rate = RateUtils.getRateForDates("2015-07-01T09:00:00Z", "2015-07-01T12:00:00Z");
        assertEquals(1750, rate);
    }

    @Test(expected = BadRequestException.class)
    public void getRateForDates_BadStartDate() {
        RateUtils.getRateForDates("2015-07-01T0", "2015-07-01T12:00:00Z");
    }

    @Test(expected = BadRequestException.class)
    public void getRateForDates_BadEndDate() {
        RateUtils.getRateForDates("2015-07-01T09:00:00Z", "2015-07-0");
    }

    @Test(expected = BadRequestException.class)
    public void getRateForDates_StartDateBeforeEndDate() {
        RateUtils.getRateForDates("2015-07-01T12:00:00Z", "2015-07-01T09:00:00Z");
    }

    @Test(expected = BadRequestException.class)
    public void getRateForDates_24HourDifference() {
        RateUtils.getRateForDates("2015-07-01T09:00:00Z", "2015-07-03T12:00:00Z");
    }

    private void setRatesToNull() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Field privateField = Class.forName("com.spothero.challenge.util.RateUtils").getDeclaredField("rates");
        privateField.setAccessible(true);
        RateUtils rateUtils = new RateUtils();
        privateField.set(rateUtils, null);
    }

    private Rates getRatesPrivateField() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Field privateField = Class.forName("com.spothero.challenge.util.RateUtils").getDeclaredField("rates");
        privateField.setAccessible(true);
        RateUtils rateUtils = new RateUtils();
        return (Rates) privateField.get(rateUtils);
    }

}
