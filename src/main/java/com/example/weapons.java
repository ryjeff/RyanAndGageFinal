/*
 * This class handles the creation of weapons
 * along with the handling of their attack functions
 * weapons are contained within array lists inside of characters objects
 */

package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//abstract generic weapon this includs methods for all
//generic applicable functions that can be over written for 
//more specific weapon modifications
abstract public class weapons {
    String name;
    int ammo; 
    int currentAmmo; 
    int accuracy;
    int projectiles;
    double damage;
    List<enchants> enchants = new ArrayList<>();

    //method that checks if an attack will hit or miss 
    //the mod is set to 1.0 for weapon shots, but changed for
    //status effect calculations this means status effects 
    //have dynamic hit chances based off the weapon they are attached to
    public boolean accuracyCheck(double mod){
        Random random = new Random();
        int rng = random.nextInt((int) Math.round(100 / mod));
        if(rng <= this.accuracy ){
            return true;
        }
        else{
            return false;
        }
    }

    //generic attack method used by both player and enemy to do damage to each other
    //this method works with all weapons and changes dynamicly based on weapon
    //this method utilizes the accuracy check method from earlier for hit detection, and proc detection
    String attack(character attacker, character target){

        //these are stats being tracked for the after attack battle log
        //any time you see these in the method it is just keeping track of important data
        int totalShots = 0;
        int successfulHits = 0;
        double beforeHP = target.currentHp;
        int enchantProcs = 0;

        //loop for as many projectiles the weapon shoots.
        for (int loop = 0; loop < this.projectiles; loop++){

            //make sure there is ammo before shot happens, if not end early wasting attackers turn
            if (this.currentAmmo == 0){
                loop = this.projectiles;
                break;
            }

            //this is accuracy check for the shot
            if (accuracyCheck(1.0) == true){ 
                successfulHits +=1;
                target.damage(this.damage); //uses built in damage method for character objects

                if(target.effects.isEmpty() == false){ //this is checking if the target is under any status effects

                    //trigger all the on damage effects
                    for (int loop3 = 0; loop3 < target.effects.size(); loop3 ++){
                        target.effects.get(loop3).on_damage(target);
                    }
                }

                if(this.enchants.size() > 0){ //checks the weapon being used for attack for enchants

                    //loop through enchants list
                    for (int loop2 = 0; loop2 < this.enchants.size(); loop2 ++){

                        //re-use the accuracy check from earlier but for enchant procs
                        if (accuracyCheck((enchants.get(loop2).procChance)) == true){ 
                            enchantProcs +=1;
                            enchants.get(loop2).proc(target);
                        }
                    }
                }
            }

            totalShots += 1; // stat tracking
            this.currentAmmo -= 1;

            // a second check for ammo end loop early
            if (this.currentAmmo == 0){
                loop = this.projectiles;
            }

            // a check for enemy hp after they get damaged end loop early
            if (target.currentHp < 0){
                loop = this.projectiles;
            }
        }

        double afterHP = target.currentHp; //Variable to get the hp after all damage is done
        double totalDamage = beforeHP - afterHP; //simple equation to calculate how much damage was done in this attack instance

        //a lot of if and else if statements to create a fully customisable battle log message
        if (this.currentAmmo == 0 && enchantProcs > 0){
            return String.format("%s attacked %s\nwith %s and shot %d projectiles\n hit %d times for %.1f damage\n%s has proced status effects %d times\n Ammo depleted",attacker.name, target.name, this.name, totalShots, successfulHits, totalDamage, target.name, enchantProcs);
        }
        else if (enchantProcs > 0) {
            return String.format("%s attacked %s\nwith %s and shot %d projectiles\n hit %d times for %.1f damage\n%s has proced status effects %d times",attacker.name, target.name, this.name, totalShots, successfulHits, totalDamage, target.name, enchantProcs);
        }
        else if (this.currentAmmo == 0 && totalShots > 0){
            return String.format("%s attacked %s\nwith %s and shot %d projectiles\n hit %d times for %.1f damage\n Ammo depleted",attacker.name, target.name, this.name,totalShots, successfulHits, totalDamage);
        }
        else if (currentAmmo == 0){
            return "Ammo Depleted";
        }
        else return String.format("%s attacked %s\nwith %s and shot %d projectiles\n hit %d times for %.1f damage",attacker.name, target.name, this.name,totalShots, successfulHits, totalDamage);
    }
}

// These are all of the weapons in the game
// All created with the generic abstract class from earlier
class pistol extends weapons{
    pistol(){
        this.name = "pistol";
        this.ammo = 500;
        this.currentAmmo = 500;
        this.accuracy = 70;
        this.projectiles = 1;
        this.damage = 6;
        this.enchants.add(new detonation());
    }
}

class shotgun extends weapons{
    shotgun(){
        this.name = "shotgun";
        this.ammo = 200;
        this.currentAmmo = 200;
        this.accuracy = 35;
        this.projectiles = 20;
        this.damage = 1.5;
        this.enchants.add(new rot());
    }
}

class smg extends weapons{
    smg(){
        this.name = "smg";
        this.ammo = 200;
        this.currentAmmo = 200;
        this.accuracy = 50;
        this.projectiles = 5;
        this.damage = 3;
        this.enchants.add(new venom());
    }
}

class raygun extends weapons{
    raygun(){
        this.name = "raygun";
        this.ammo = 350;
        this.currentAmmo = 350;
        this.accuracy = 75;
        this.projectiles = 2;
        this.damage = 3;
    }
}

class laser extends weapons{
    laser(){
        this.name = "laser";
        this.ammo = 40;
        this.currentAmmo = 40;
        this.accuracy = 95;
        this.projectiles = 1;
        this.damage = 5;
    }
}

class disintegrationray extends weapons{
    disintegrationray(){
        this.name = "disintegrationRay";
        this.ammo = 10;
        this.currentAmmo = 10;
        this.accuracy = 85;
        this.projectiles = 1;
        this.damage = 12;
    }
}

class spacerays extends weapons{
    spacerays(){
        this.name = "spacerays";
        this.ammo = 6;
        this.currentAmmo = 6;
        this.accuracy = 40;
        this.projectiles = 3;
        this.damage = 9;
    }
}
