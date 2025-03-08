/*
 * This class file contains all object creation involving effects
 * Effects are status effects that can be placed on character objects, and are triggerd by enchants
 */

package com.example;

// This is the abstract class that all effects are based off of
// This contains all methods an effect can have so they can be changed later
public abstract class effects {

    String name; // this is for display purposes
    
    //these are undefined because each sepret effect will handle them differently, some will remain empty
    abstract void on_damage(character target); //undefined function for if status effect should trigger on damage
    abstract void on_turn_end(character target); //undefined function for if status effect should trigger when a turn ends
    abstract void addStacks(); //this is the generic function for adding stacks of a status effect
}

// This is a status effect that will execute a character with hp below 15% of their maximum
// It is unique because it has no durration, and gets applied only one time
// This will trigger on damage, and on turn end
class execution extends effects {

    //create method for execution
    execution(){
        this.name = "execution";
    }

    //this is the method that gets triggerd when a turn ends
    //when enemy hp is below 15% execute them
    void on_turn_end(character target){
        if (target.currentHp <= (target.hp * 0.85)){
            target.damage(9999);
        }
    }

    void addStacks(){}//does nothing for this

    //this is the method that triggers on damage
    //when enemy hp is below 15% execute them
    void on_damage(character target){
        if (target.currentHp <= (target.hp * 0.15)){
            target.damage(9999);
        }
    }
}

// This is a status effect that add an inf stacking damage over time effect on enemy's
// It is unique because it has no durration, but counts stacks to deal more damage over time -
// but each time it increases it gets harder to scale more
// This will trigger on damage, and on turn end
class poison extends effects {
    
    //add variables not on some status effects like damage and stack counters
    int damage; 
    int stacks;

    //generic create method
    poison(){
        this.name = "poison";
        this.stacks = 0;
        this.damage = 1;
    }

    // Method for stacking poison How it works - 
    // Starts off at 1 damage and 0 stacks
    // When your stack counter equals your damage increase damage by 1 and set stacks back to 0
    void addStacks(){
        this.stacks += 1;
        if(this.stacks >= this.damage){
            this.stacks = 0;
            this.damage = damage + 1;
        }
    }

    //method to deal damage triggering at the end of a turn
    void on_turn_end(character target){
        target.damage(this.damage);
    }

    void on_damage(character target){}// does nothing for poison

}

// This is a status effect that has a limited lifespan, but when it will deal 1 damage each time non status effect damage happens
// It is unique because it is the only status effect with a durration, each time it procs its durration increases by 3
// This will trigger on damage
class boils extends effects {

    // add variables not on some status effects like damage and durration
    int damage;
    int durration;

    // generic creat method
    boils(){
        this.damage = 1;
        this.name ="boils";
        this.durration = 3;
    }

    // when proc happens increase its durration
    void addStacks(){
        this.durration +=3;
    }

    // when damage happens, trigger its own damage
    void on_damage(character target){
        target.damage(this.damage);
    }

    // reduce the stack count when turn ends, if the durration hits 0 set damage to 0, if not keep damage at 1
    void on_turn_end(character target){
        this.durration -= 1;
        if (this.durration <= 0){
            this.durration = 0;
            this.damage = 0;
        }
        else {
            this.damage = 1;
        }
    }
}