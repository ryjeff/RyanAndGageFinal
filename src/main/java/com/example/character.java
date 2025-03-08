/*
 * This class, the character class sets up a generic character
 * that you can use to create enemy's and players
 * 
 */
package com.example;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

// This is the abstract class that all enemy's and players are based off of
// This contains all the tracked data they have - 
// and a function for dealing damage that works for any character type
public abstract class character {
    
    //the data
    String name;
    double hp;
    double currentHp;
    double mod;
    List<effects> effects = new ArrayList<>();
    List<weapons> weapons = new ArrayList<>();

    //function for taking damage
    public void damage(double damage){
        this.currentHp = this.currentHp - damage;
    }
}

//for creating characters
class player extends character{
    public player(String name, int hp, double mod) {
        this.name = name;
        this.hp =(hp * mod);
        this.mod = mod;
        this.currentHp = this.hp;
    }
}

//for creating enemy's
class enemy extends character{
    String image; // only the enemy has imagry so it is a unique data type for enemy character
    public enemy(String name, int hp, double mod) {
        this.name = name;
        this.hp =(hp * mod);
        this.mod = mod;
        this.currentHp = this.hp;
        //use try catch to check weather a png or gif is associated with the image related to the name
        try{
            //checking if image works with png
            this.image = String.format("/images/%s.png", this.name);
            new Image(getClass().getResource(this.image).toExternalForm());
        } catch(NullPointerException e) {
            try{
                //checking if image works with gif
                this.image = String.format("/images/%s.gif", this.name);
                new Image(getClass().getResource(this.image).toExternalForm());
            } catch(NullPointerException e2) {
                //if png and gif fail hard code image to be generic enemy
                this.image = "/images/alien.png";
            }
        }
    }

}