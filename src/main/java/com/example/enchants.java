/*
 * This class file contains all object creation involving enchants
 * Enchants are modifyers contained within an array list inside of weapon objects
 * Enchants trigger effects with built in methods to handle all scenarios
 */

package com.example;

// This is the abstract class that all enchants are based off of
// This contains all methods related to processing a proc
public abstract class enchants {

    // this is the required data for all enchant objects
    String name;
    effects effectName;
    double procChance; //this is dynamic proc chance that scales with weapon accuracy

    void procConfirm(character target){}; // method that handels first time proc's
    void procAlreadyHappened(character target, int spot){}; // method that handles if proc has already happened

    //this method checks the target for all the effects it already contains to trigger the previous undefined methods
    void proc(character target){
        if (target.effects.size() > 0){ //if proc has happened before
            for (int loop = 0; loop < target.effects.size(); loop++){
                if (target.effects.get(loop).name.equals(this.effectName.name)){
                    procAlreadyHappened(target, loop);
                }
            }
        }
        else {procConfirm(target);} //if proc has not happened before
    }
}

// This is an enchant for the status effect execution
// For more info go to the effects page for execution
class detonation extends enchants{
    
    //generic create
    detonation(){
        this.name = "detonation";
        this.effectName = new execution();
        this.procChance = 0.2;
    }
    

    //only has a first time method as execution does not stack or scale over time
    void procConfirm(character target){
        target.effects.add(new execution());
    }

}

// This is an enchant for the status effect poison
// For more info go to the effects page for poison
class venom extends enchants{

    //generic create
    venom(){
        this.name = "venom";
        this.effectName = new poison();
        this.procChance = 1.0;
    }

    //if a proc has happened previously add stacks
    void procAlreadyHappened(character target, int spot){
        target.effects.get(spot).addStacks();
    }

    //if a proc is first time create object in target's effects arraylist
    void procConfirm(character target){
        target.effects.add(new poison());
    }
}

// This is an enchant for the status effect boils
// For more info go to the effects page for boils
class rot extends enchants{

    // generic create
    rot(){
        this.name = "rot";
        this.effectName = new boils();
        this.procChance = 0.70;
    }

    //  if a proc has happened previously add stacks
    void procAlreadyHappened(character target, int spot){
        target.effects.get(spot).addStacks();
    }

    //if a proc is first time create object in target's effects arraylist
    void procConfirm(character target){
        target.effects.add(new boils());
    } 
}

