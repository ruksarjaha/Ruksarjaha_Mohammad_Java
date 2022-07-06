package com.company;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.*;

public class CalculatorTest {



    @Test
    public void shouldAddTwoIntegers(){
        Calculator calculator = new Calculator();
        int result = 2;
        int input = calculator.add(1, 1);
        assertEquals(result, input);
    }

    @Test
    public void shouldAddTwoDoubles(){
        Calculator calculator = new Calculator();
        double expected = 5.7;
        double actual = calculator.add(3.4, 2.3);
        assertEquals(expected, actual, 0);

    }

    @Test
    public void shouldMultiplyTwoInteger(){
        Calculator calculator = new Calculator();
        int expected = 68;
        int actual = calculator.multiply(34, 2);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldMultiplyTwoDoubles(){
        Calculator calculator = new Calculator();
        double expected = 29.480000000000004;
        double actual = calculator.multiply(6.7, 4.4);
        assertEquals(expected, actual, 0);
    }

    @Test
    public void shouldSubtractTwoIntegers(){
        Calculator calculator = new Calculator();
        int expected = -29;
        int actual = calculator.subtract(23, 52);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSubtractTwoDoubles(){
        Calculator calculator = new Calculator();
        double expected = 5;
        double actual = calculator.subtract(5.5, 0.5);
        assertEquals(expected, actual, 0);
    }

    @Test
    public void shouldDivideTwoIntegers(){
        Calculator calculator = new Calculator();
        int expected = 4;
        int actual = calculator.division(12, 3);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldDivideTwoDoubles(){
        Calculator calculator = new Calculator();
        double expected = 4.909090909090909;
        double actual = calculator.division(10.8, 2.2);
        assertEquals(expected, actual, 0);
    }
}