package com.company;

public class Farmer extends Character{

    /*Properties
    name
    strength (initial value = 75)
    health (initial value = 100)
    stamina (initial value = 75)
    speed (initial value = 10)
    attackPower (initial value = 1)
    running (initial value = false)
    arrested (initial value = false)
    plowing (initial value = false)
    harvesting (initial value = false)*//**/

    protected boolean plowing;

    protected boolean harvesting;

    public Farmer(){

        strength = 75;
        health = 100;
        stamina = 75;
        speed = 10;
        attackPower = 1;
        running = false;
        arrested = false;
        this.plowing = false;
        this.harvesting = false;

    }

    public boolean isPlowing()
    {
        return plowing;
    }

    public void setPlowing(boolean plowing) {
        this.plowing = plowing;
    }

    public boolean isHarvesting() {
        return harvesting;
    }

    public void setHarvesting(boolean harvesting) {
        this.harvesting = harvesting;
    }

    public void attackAnotherCharacter(){
        System.out.println("Has ability to attack another character");
    }

    public int farming(){
        if(running == false){
            stamina = this.stamina - 10;
        }
        return stamina;
    }
}


