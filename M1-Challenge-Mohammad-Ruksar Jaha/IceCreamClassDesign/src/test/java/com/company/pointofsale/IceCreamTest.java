package com.company.pointofsale;

import org.junit.Test;

import static org.junit.Assert.*;

public class IceCreamTest {

    @Test
    public void ShouldReturnTotalAmount(){

        IceCream iceCream = new IceCream();
        iceCream.setFlavor("cashew");

        double expected = 3.00;
        iceCream.totalAmount();
        double actual = iceCream.getPrice();

        assertEquals(expected, actual, 0.000000001);




    }

}