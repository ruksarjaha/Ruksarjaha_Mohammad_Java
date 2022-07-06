package com.company;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConverterIfTest {

    private ConverterIf conIf;

    @Before
    public void setUp(){
        conIf = new ConverterIf();
    }
    @Test
    public void convertMonthNumberToMonthName() {

        String expected = "December";
        String actual = conIf.convertMonth(12);
        assertEquals(expected , actual);

    }

    @Test
    public void convertDayNumberToDayName(){

        String expected = "Friday";
        String actual = conIf.convertDay(6);
        assertEquals(expected, actual);
    }


}