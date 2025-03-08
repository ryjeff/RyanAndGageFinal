package com.example;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {

    //set default values and define normal settings.
    public List<enemy> BBEG = new ArrayList<>();
    List<ProgressBar> enemyHpStorage = new ArrayList<>();
    public player MC;
    private String difficulty = "normal";
    private double difficultyModPlayer = 1;
    private double difficultyModEnemy = 1;
    private int width = 400;
    private int height = 400;
    private enemy target;
    private weapons equip;
    private Boolean turn = false;
    private Boolean inFight = false;

    //starting stage
    @Override
    public void start(Stage primaryStage) {

        //setting up scene for main menu
        VBox mainMenu = new VBox();
        Scene mainMenuScene = new Scene(mainMenu, width, height);

        //Label for if you won or not
        Label winLabel = new Label("Play the game!");

        //all the items in main menu
        Button playButton = new Button("play");
        Button tutorialButton = new Button("tutorial");

        //setting up the items
        mainMenu.setSpacing(height * 0.15);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.getChildren().addAll(winLabel, playButton, tutorialButton);

        //set up scene for setting UI
        VBox menuVBox = new VBox();
        Scene settingsScene = new Scene(menuVBox, width, height);

        //all the game settings
        TextField userNameTextField = new TextField();
        userNameTextField.setPromptText("Username");
        userNameTextField.setPrefSize(width*0.3, height *0.1);
        userNameTextField.setMaxSize(width*0.3, height *0.1);


        Button startBtn = new Button("start");
        startBtn.setPrefSize(width*0.3, height *0.1);

        ChoiceBox<String> difficultyBox = new ChoiceBox<>();
        difficultyBox.getItems().addAll("easy", "normal", "hard");
        difficultyBox.setValue(difficulty);
        difficultyBox.setPrefSize(width*0.3, height *0.1);
        Label menuLabel = new Label("Difficulty");
        menuLabel.setAlignment(Pos.CENTER);
        menuLabel.setPrefSize(width*0.3, height *0.1);
        

        //putting it together
        menuVBox.setSpacing(height * 0.02);
        menuVBox.setAlignment(Pos.CENTER);
        HBox menuHBox = new HBox();
        menuHBox.setAlignment(Pos.CENTER);
        menuHBox.getChildren().addAll(difficultyBox);
        HBox textHBox = new HBox();
        textHBox.setAlignment(Pos.CENTER);
        menuVBox.getChildren().addAll(userNameTextField, menuLabel, menuHBox, textHBox, startBtn);

        //scene for confirmation screen
        VBox confirmBox = new VBox();
        Scene confirmationScene = new Scene(confirmBox, height, width);
        Label infoDisplay = new Label();
        Button confirmBtn = new Button("confirm");
        confirmBtn.setPrefSize(width*0.3, height *0.1);
        Button goBackBtn = new Button("return");
        goBackBtn.setPrefSize(width*0.3, height *0.1);

        confirmBox.setSpacing(height * 0.02);
        confirmBox.setAlignment(Pos.CENTER);
        confirmBox.getChildren().addAll(infoDisplay, confirmBtn, goBackBtn);

        //setting up scene for combat start :)

        HBox fightBox = new HBox();
        Scene fight = new Scene(fightBox, width * 3, height * 2); //the fight scene is set up to be 1200 by 800

        HBox fightPane = new HBox(); //display enemy buttons
        HBox fightPaneHp = new HBox(); //display enemy healthbar
        HBox actionBox = new HBox(); //display invintory weapons

        ProgressBar hpBar = new ProgressBar(); //player HP
        hpBar.setProgress(0.0);//defaults at 0
        hpBar.setPrefSize(((width * 3) * 0.5), ((height * 2)*0.05));
        hpBar.setMinSize(((width * 3) * 0.5), ((height * 2)*0.05));
        VBox hotBarBox = new VBox(); // this displays stats of equiped weapon
        HBox hotBarTop = new HBox(); // for top of hotbar
        HBox hotBarBottom = new HBox(); // for bottom of hotbar
        VBox hotBarSplit = new VBox();//split bottom in half
        VBox hotBarBottomSplit = new VBox(); // split bottom hotbar in half again

        Label image = new Label(); // future image
        image.setMinSize(((width * 3) * 0.15), ((height * 2)*0.15));
        Label damageLabel = new Label("Damage: "); //display damage of gun
        damageLabel.setMinSize(((width * 3) * 0.15), ((height * 2)*0.075));
        Label bulletLabel = new Label("Shots: "); // displays amount of shots fired
        bulletLabel.setMinSize(((width * 3) * 0.15), ((height * 2)*0.075));
        Label equipNameLabel = new Label("Name"); //name of currently equiped
        Label userNameLabel = new Label("UserName"); // your entered username

        VBox attackBox = new VBox();
        Label targetLabel = new Label ("Select target"); // your target
        targetLabel.setMinSize(((width * 3) * 0.15), ((height * 2)*0.075));
        Button attackButton = new Button("Attack!");
        attackButton.setMinSize(((width * 3) * 0.15), ((height * 2)*0.075));
        attackBox.getChildren().addAll(attackButton, targetLabel);

        

        ListView<String> weaponEffectsList = new ListView<String>(); //show special effects of weapon
        weaponEffectsList.setMaxSize(((width * 3) * 0.15), ((height * 2)*0.14)); // format list
        ObservableList<String>  effectsList = FXCollections.observableArrayList ("Effects");
        weaponEffectsList.setItems(effectsList);


        ProgressIndicator ammoIndicator = new ProgressIndicator(); //display ammo left in %
        ammoIndicator.setProgress(0.00); //defaults at 0
        ammoIndicator.setMinSize(((width * 3) * 0.05), ((height * 2)*0.065));
        ammoIndicator.setMaxSize(((width * 3) * 0.1), ((height * 2)*0.065));  
        HBox ammoBox = new HBox(); //push ammo and indicator together
        Label ammoLabel = new Label("Ammo: "); //display Ammo:
        ammoLabel.setMinSize(((width * 3) * 0.1), ((height * 2)*0.075));

        ProgressIndicator accuracyIndicator = new ProgressIndicator(); //display accuracy in %
        accuracyIndicator.setProgress(0.00); //defaults at 0
        accuracyIndicator.setMinSize(((width * 3) * 0.05), ((height * 2)*0.065));
        accuracyIndicator.setMaxSize(((width * 3) * 0.1), ((height * 2)*0.065));
        HBox accuracyBox = new HBox(); // put accuracy label and % together
        Label accuracyLabel = new Label("Accuracy: "); // stats Accuracy:
        accuracyLabel.setMinSize(((width * 3) * 0.1), ((height * 2)*0.075)); 

        VBox fightBoxLeft = new VBox(); // this is the left side of whole UI container

        //SMASH ALL THAT UI TOGETHER
        accuracyBox.getChildren().addAll(accuracyLabel, accuracyIndicator); //this is the bottom of your invintory
        ammoBox.getChildren().addAll(ammoLabel, ammoIndicator);//formating the bottom VVVV
        hotBarSplit.getChildren().addAll(damageLabel, bulletLabel);
        hotBarBottomSplit.getChildren().addAll(accuracyBox, ammoBox);
        hotBarBottom.getChildren().addAll(image,hotBarSplit, hotBarBottomSplit, weaponEffectsList, attackBox); 
        hotBarBottom.setPrefSize(((width * 3) * 0.8), ((height * 2)*0.15));
        hotBarBottom.setMinSize(((width * 3) * 0.8), ((height * 2)*0.15));

        hotBarTop.getChildren().addAll(equipNameLabel, hpBar, userNameLabel); //this is the top of your invintory
        hotBarTop.setPrefSize(((width * 3) * 0.8), ((height * 2)*0.05)); //formating the top VVVV
        hotBarTop.setMinSize(((width * 3) * 0.8), ((height * 2)*0.05)); 

        hotBarBox.getChildren().addAll(hotBarTop, hotBarBottom); //whole hot bar

        fightBoxLeft.setPrefSize((width * 3) *0.8, height * 2); // the left side of battle UI
        fightBoxLeft.getChildren().addAll(fightPane, fightPaneHp, actionBox, hotBarBox);

        VBox fightBoxRight = new VBox(); //the Battle log formatting
        fightBoxRight.setPrefSize(((width * 3) * 0.2), (height * 2));
        fightBoxRight.setMinSize(((width * 3) * 0.2), (height * 2));


        //Battle Log 20% width, 100% verticle
        ListView<String> battleLog = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList ();
        battleLog.setItems(items);
        battleLog.setPrefSize(((width * 3) * 0.2), (height * 2));
        ScrollPane scrollPane = new ScrollPane(battleLog);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(false); // Allow horizontal scrolling
        battleLogAdds battleLogAdds = new battleLogAdds(items);
        battleLogAdds.addLog("Battle Log");
        battleLogAdds.addLog("---------");


        fightBoxRight.getChildren().addAll(battleLog);

        fightBox.getChildren().addAll(fightBoxLeft, fightBoxRight);

        // Set up and show the stage
        primaryStage.setTitle("main menu");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
        
        //main menu button actions

        playButton.setOnAction(e ->{
            primaryStage.setTitle("Settings");
            primaryStage.setScene(settingsScene);
        });

        startBtn.setOnAction(e ->{
            String x = userNameTextField.getText();
            if (x.isEmpty()){
                x = "Big R";
            }
            infoDisplay.setText(String.format("Username: %s\nDifficulty: %s", x, difficultyBox.getValue() ));
            primaryStage.setTitle("Confirmation"); 
            primaryStage.setScene(confirmationScene);
        });

        goBackBtn.setOnAction( e -> {
            primaryStage.setTitle("Settings");
            primaryStage.setScene(settingsScene);
        });

        confirmBtn.setOnAction(e ->{

            String x = userNameTextField.getText();
            if (x.isEmpty()){
                x = "Big R";
            }
            switch (difficultyBox.getValue()){
                case "easy":
                    difficultyModPlayer = 1.2;
                    difficultyModEnemy = 0.8;
                    break;
                case "normal":
                    difficultyModPlayer = 1.0;
                    difficultyModEnemy = 1.0;
                    break;
                case "hard":
                    difficultyModPlayer = 0.8;
                    difficultyModEnemy = 8.8;
                    break;
                default:
                    System.out.println("Error: no difficulty selected");
                    break;
            }

            //create character and populate invintory
            MC = new player(x,(int) (100 * difficultyModPlayer), difficultyModPlayer);
            MC.weapons.add(new pistol());
            MC.weapons.add(new shotgun());
            MC.weapons.add(new smg());
            hpBar.setProgress((double) MC.currentHp / MC.hp);


            //create enemy
            BBEG.add(new enemy("ufo", (int) (60 * difficultyModEnemy), difficultyModEnemy));
            BBEG.get(0).weapons.add(new raygun());
            BBEG.add(new enemy("laser alien", (int) (60 * difficultyModEnemy), difficultyModEnemy));
            BBEG.get(1).weapons.add(new laser());
            BBEG.get(1).weapons.add(new disintegrationray());
            BBEG.get(1).weapons.add(new spacerays());

            //check to make sure every combatent added has at least one weapon, if not add a weapon for them.
            if (MC.weapons.isEmpty()){
                MC.weapons.add(new pistol());
            }
            for (int loop = 0; loop < BBEG.size(); loop ++){
                if (BBEG.get(loop).weapons.isEmpty()){
                    BBEG.get(loop).weapons.add(new raygun());
                }
            }
            

            for (int loop = 0; loop < BBEG.size(); loop ++){
                int targetInt = loop;
                Button selectButton = new Button();
                ProgressBar enemyHpProgressBar = new ProgressBar();
                selectButton.setGraphic(new ImageView(new Image(getClass().getResource(BBEG.get(loop).image).toExternalForm())));
                selectButton.setPrefSize(((width * 3)* 0.8 ) / BBEG.size(),((height * 2) * 0.55));
                enemyHpProgressBar.setPrefSize(((width * 3)* 0.8 ) / BBEG.size(),((height * 2) * 0.05));
                enemyHpProgressBar.setProgress((double) BBEG.get(loop).currentHp / BBEG.get(loop).hp);
                enemyHpStorage.add(enemyHpProgressBar);
                selectButton.setOnAction(event ->{
                    try{
                        target = BBEG.get(targetInt);
                        targetLabel.setText(String.format("Target: %S", target.name)); 
                        target = BBEG.get(targetInt);
                        targetLabel.setText(String.format("Target: %S", target.name)); 
                    } catch(IndexOutOfBoundsException ex){
                        battleLogAdds.addLog("This enemy is dead");
                        //target = (null); 
                        targetLabel.setText(("Select target"));
                    } catch(NullPointerException eee){
                        battleLogAdds.addLog("This enemy is dead");
                        //target = (null); 
                        targetLabel.setText(("Select target"));
                    }
                });

                fightPane.getChildren().addAll(selectButton);
                fightPaneHp.getChildren().addAll(enemyHpProgressBar);
            }

            for (int loop = 0; loop < MC.weapons.size(); loop ++){
                int equipInt = loop;
                Button selectButton = new Button(MC.weapons.get(loop).name);
                selectButton.setPrefSize(((width * 3)* 0.8 ) / MC.weapons.size(),((height * 2) * 0.2));
                selectButton.setOnAction(event ->{
                    equip = MC.weapons.get(equipInt);
                    //change display on equip
                    effectsList.clear();
                    effectsList.add("Effects");
                    accuracyIndicator.setProgress((double) equip.accuracy/100.0);
                    accuracyLabel.setText(String.format("Accuracy: %d", equip.accuracy));
                    ammoIndicator.setProgress((double)equip.currentAmmo/ equip.ammo);
                    ammoLabel.setText(String.format("Ammo: %d/%d", equip.currentAmmo, equip.ammo));
                    damageLabel.setText(String.format("Damage: %.2f", equip.damage));
                    bulletLabel.setText(String.format("Shots: %d", equip.projectiles));
                    equipNameLabel.setText(equip.name);
                    userNameLabel.setText(MC.name);
                    if (equip.enchants.size() > 0){
                        for ( int loop2 = 0; loop2 < equip.enchants.size(); loop2++){
                            effectsList.add(equip.enchants.get(loop2).name);
                        }
                    }
                });
                actionBox.getChildren().addAll(selectButton);

            }


            primaryStage.setScene(fight);
            primaryStage.setTitle("Action");
            primaryStage.setX((Screen.getPrimary().getVisualBounds().getWidth() - primaryStage.getWidth()) / 2);
            primaryStage.setY((Screen.getPrimary().getVisualBounds().getHeight() - primaryStage.getHeight()) / 2);


            turn = true;
            inFight = true;

        });

        attackButton.setOnAction (e ->{

            try{
                if (target == null){}
        }
            catch(NullPointerException ee){
                targetLabel.setText("Select target");
                battleLogAdds.addLog("Select a target");
                battleLogAdds.addLog("");
            }

            if (turn == true && inFight == true && (target != null ) && target.hp > 0 && equip != null){
                battleLogAdds.addLog(equip.attack(MC, target));
                ammoIndicator.setProgress((double)equip.currentAmmo/ equip.ammo);
                ammoLabel.setText(String.format("Ammo: %d/%d", equip.currentAmmo, equip.ammo));
                for (int loop = 0; loop < MC.effects.size(); loop++){
                    double healthBefore = MC.currentHp;
                    MC.effects.get(loop).on_turn_end(MC);
                    double damageDealt = healthBefore - MC.currentHp;
                    if (damageDealt > 0){
                        battleLogAdds.addLog(String.format("%s took %.1f status effect damage", MC.name, damageDealt));
                    }
                }

                if (MC.currentHp < 0){
                    winLabel.setText("You Lost :(");
                    inFight = false;
                    turn = true;
                    BBEG.clear();
                    enemyHpStorage.clear();
                    MC.weapons.clear();
                    actionBox.getChildren().clear();
                    fightPane.getChildren().clear();
                    fightPaneHp.getChildren().clear();
                    primaryStage.setScene(mainMenuScene);
                    primaryStage.setX((Screen.getPrimary().getVisualBounds().getWidth() - primaryStage.getWidth()) / 2); //centers the new scene
                    primaryStage.setY((Screen.getPrimary().getVisualBounds().getHeight() - primaryStage.getHeight()) / 2); //centers the new scene
                }
                else{
                    hpBar.setProgress(MC.currentHp / MC.hp);
                }

                turn = false;

                //notify user of death and set enemy png to dead png
                for (int loop = 0; loop < BBEG.size(); loop++){
                    if (BBEG.get(loop).currentHp <= 0){
                        enemyHpStorage.get(loop).setProgress(0.0);
                        battleLogAdds.addLog("");
                        battleLogAdds.addLog(String.format("%s has died", BBEG.get(loop).name));
                        battleLogAdds.addLog("");

                    }
                    else{
                        enemyHpStorage.get(loop).setProgress((double) BBEG.get(loop).currentHp / BBEG.get(loop).hp);
                    }
                }
            }
            if (turn == false){
                boolean anyAlive = false;
                for (int loop = 0; loop < BBEG.size(); loop++){
                    if (BBEG.get(loop) != null && BBEG.get(loop).currentHp > 0){
                        anyAlive = true;
                        Random rng = new Random();
                        int nextAttack = rng.nextInt(BBEG.get(loop).weapons.size());
                        battleLogAdds.addLog((BBEG.get(loop).weapons.get(nextAttack).attack(BBEG.get(loop), MC)));

                        for (int loop2 = 0; loop2 < BBEG.get(loop).effects.size(); loop2++){
                            double healthBefore = BBEG.get(loop).currentHp;
                            BBEG.get(loop).effects.get(loop2).on_turn_end(BBEG.get(loop));
                            double damageDealt = healthBefore - BBEG.get(loop).currentHp;
                            if (damageDealt > 0){
                                battleLogAdds.addLog(String.format("%s took %.1f status effect damage",BBEG.get(loop).name, damageDealt));
                            }
                        }
                    }
                }
                if (anyAlive == false){
                    winLabel.setText("You Won :)");
                    inFight = false;
                    turn = true;
                    BBEG.clear();
                    enemyHpStorage.clear();
                    MC.weapons.clear();
                    actionBox.getChildren().clear();
                    fightPane.getChildren().clear();
                    fightPaneHp.getChildren().clear();
                    primaryStage.setScene(mainMenuScene);
                    primaryStage.setX((Screen.getPrimary().getVisualBounds().getWidth() - primaryStage.getWidth()) / 2); //centers the new scene
                    primaryStage.setY((Screen.getPrimary().getVisualBounds().getHeight() - primaryStage.getHeight()) / 2); //centers the new scene
                }

                turn = true;
                battleLogAdds.addLog("");
                battleLogAdds.addLog("Your Turn");
                battleLogAdds.addLog("");
            }
        });

        TutorialCreate tutorialCreate = new TutorialCreate();
        tutorialButton.setOnAction(e ->{
            primaryStage.setScene(tutorialCreate.createTutorialUI(width, height, primaryStage, mainMenuScene));
            primaryStage.setX((Screen.getPrimary().getVisualBounds().getWidth() - primaryStage.getWidth()) / 2); //centers the new scene
            primaryStage.setY((Screen.getPrimary().getVisualBounds().getHeight() - primaryStage.getHeight()) / 2); //centers the new scene
        });


        // Create the main layout container
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Create navigation buttons
        Button prevButton = new Button("Previous");
        Button nextButton = new Button("Next");

        // Create HBox for buttons
        HBox hboxPrevNext = new HBox(20);
        hboxPrevNext.setAlignment(Pos.CENTER);
        hboxPrevNext.getChildren().addAll(prevButton, nextButton);

    }
    public static void main(String[] args) {
        launch(args);
    }
}