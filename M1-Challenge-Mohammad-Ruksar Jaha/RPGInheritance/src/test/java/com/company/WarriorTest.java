package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class WarriorTest {

    @Test
    public void shouldAttackAnotherCharacter(){

        Warrior warrior = new Warrior();
        warrior.setAttackPower(20);

        int expected = 25;
        int actual = warrior.attackingPower();

        assertEquals(expected, actual);




    }

}