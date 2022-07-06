package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class FarmerTest {

    @Test
    public void shouldDisplayFarmersStamina(){

        Farmer farmer = new Farmer();

        farmer.setStamina(20);

        int expected = 10;
        int actual = farmer.farming();
        assertEquals(expected, actual);
    }

}