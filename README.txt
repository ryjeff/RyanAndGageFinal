
Brief project description (2-3 sentences)

This is a simple, but fun, turn based game. The game allows you to pick your username, customize your difficulty, 
and has three unique weapons to choose from.


Test scenarios: Step-by-step instructions for testing all core functionality

When you first launch the game there will be 2 buttons to pick from. One of these buttons will be a tutorial that 
will go over how to play the game. There will be 3 buttons, two of which will let you move though a slide show 
that will give instruction on how to play. The other one will be a “return to menu” which just sends you back to 
the start. When you hit play it will send you to a screen where you can type out your username and pick your difficulty. 
When you’re done, just hit start ( if no name was typed out, then the username becomes Big R ). After confirming info, 
then the real fun starts. There will be two enemies in which you will have to pick which one to attack and you also need 
to pick one of the 3 weapons. After you come up with your strategy, you just need to hit the attack button to end your turn. 
The battle log will update and give you crucial information. The enemy will attack after your turn is over. This will continue 
tell either you’re defeated or the enemies are! Whether or not you win or lose you will be send back to the main menu to play again.


Instructions to build the program using Maven

All you need to do to run  this is to open your terminal and type out:
 mvn clean package


Instructions to run the compiled JAR file

in the same terminal type out the command: 
java --module-path “C:\javafx-sdk-21.0.6\lib” --add-modules javafx.controls -jar target\lab-1.0.0.jar
You will need to modify the "C:\javafx-sdk-21.0.6\lib" to be the path to Your own java sdk

