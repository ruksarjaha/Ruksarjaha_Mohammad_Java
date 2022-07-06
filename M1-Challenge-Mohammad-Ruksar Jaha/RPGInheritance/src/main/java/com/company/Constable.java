package com.company;

public class Constable extends Character{

    /*Properties
            name
    strength (initial value = 60)
    health (initial value = 100)
    stamina (initial value = 60)
    speed (initial value = 20)
    attackPower (initial value = 5)
    running (initial value = false)
    arrested (initial value = false)
    **jurisdiction
    Abilities:
    arrest another character
    attack another character*/
    protected String jurisdiction;

    public Constable(String jurisdiction){

        this.jurisdiction = jurisdiction;
        strength = 60;
        health = 100;
        stamina = 60;
        speed = 20;
        attackPower = 5;
        running = false;
        arrested = false;


    }

    public void arrestAnotherCharacter(){
        System.out.println(this.getName() + ": Can arrest another character.");
    }
}
