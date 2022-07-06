package com.company;

public class Warrior extends Character{

    /*Properties
            name
    strength (initial value = 75)
    health (initial value = 100)
    stamina (initial value = 100)
    speed (initial value = 50)
    attackPower (initial value = 10)
    shieldStrength (initial value = 100)
    running (initial value = false)
    arrested (initial value = false)
    Abilities:
    attack another character*/

    public Warrior(){

        strength = 75;
        health = 100;
        stamina = 100;
        speed = 50;
        attackPower = 10;
        running = false;
        arrested = false;
        this.shieldStrength = 100;

    }

    protected int shieldStrength;

    public int getShieldStrength() {
        return shieldStrength;
    }

    public void setShieldStrength(int shieldStrength) {
        this.shieldStrength = shieldStrength;

    }

    public int attackingPower(){

        if(arrested == false){
            attackPower = this.attackPower + 5;
            System.out.println("The Warrior's attacking power is: " + attackPower);
        }
        return attackPower;


    }

}
