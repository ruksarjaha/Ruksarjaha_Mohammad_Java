package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConstableTest {

    @Test
    public void shouldReturnHealthPointsOfWarrior(){

        Constable constable = new Constable();
        constable.setHealth(30);

        int expected = 40;
        int actual = constable.healthPoints();
         assertEquals(expected, actual);

    }

}