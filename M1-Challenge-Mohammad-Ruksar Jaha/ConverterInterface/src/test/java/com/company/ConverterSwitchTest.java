package com.company;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConverterSwitchTest {

    private ConverterSwitch conSwitch;
    @Before
    public void setUp(){
        conSwitch = new ConverterSwitch();
    }
    @Test
    public void convertMonthNumberInToMonthName() {

        String expected = "April";
        String actual = conSwitch.convertMonth(4);
        assertEquals(expected, actual);

    }

    @Test
    public void convertDayNumberIntoDayName() {

        String expected = "Monday";
        String actual = conSwitch.convertDay(2);
        assertEquals(expected, actual);
    }
}