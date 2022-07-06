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
        
        String expectedOutput = "January";
        int actualOutput = convertMonth(1);
        assertEquals(expectedOutput, actualOutput);


    }


}