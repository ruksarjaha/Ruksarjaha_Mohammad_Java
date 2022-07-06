package com.company.pointofsale;

import org.junit.Test;

import static org.junit.Assert.*;

public class IceCreamTest {

    @Test
    public void shouldReturnTotalAmount(){

        IceCream iceCream = new IceCream();
        iceCream.setFlavor("cashew");

        double expected = 3.00;
        iceCream.totalAmount();
        double actual = iceCream.getPrice();

        assertEquals(expected, actual, 0.000000001);

    }

    @Test
    public void shouldReturnNumberOfScoopsThatCanFitInTheCone(){
        IceCream iceCream = new IceCream();
        iceCream.setNumberOfScoops(4);

        String expected = "Too many Scoops";
        String actual = iceCream.scoopsPerCone();
         assertEquals(expected, actual);


    }

    @Test
    public void shouldTellIfWeCanAddSprinklers(){
        IceCream iceCream = new IceCream();
        iceCream.setSprinklers(false);

        String expected = "No Sprinklers";
        String actual = iceCream.addSprinklers();

        assertEquals(expected, actual);
    }

}