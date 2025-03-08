/*
 * This class file contains all UI creation for the tutorial
 * This page cycles through images that showcase how to play the game
 */

package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TutorialCreate {

    // Initialize image array and
    int  currentImageIndex = 0;
    Image[] images;
    ImageView tutorialMainImage;

    public Scene createTutorialUI(int width, int height, Stage primaryStage, Scene mainMenuScene){
        primaryStage.setTitle("Tutorial");

         // Initialize image array which will be later userd to sort through
        images = new Image[] {
            new Image("/images/tutorPic1.png"),
            new Image("/images/TutPic2.png"),
            new Image("/images/TutPic3.png"),
            new Image("/images/TutPic4.png")
        };


       //This makes the root which will sort info on how this scene will be laid 
        VBox TutorialRoot = new VBox(20);
        TutorialRoot.setAlignment(Pos.CENTER);
        TutorialRoot.setPadding(new Insets(20));
        TutorialRoot.setSpacing(height * 0.22);
        TutorialRoot.setAlignment(Pos.CENTER);



        // Set up the image view, which will set the peremeters on how we want the tutorial slides to look to users 
        tutorialMainImage = new ImageView(images[0]);
        tutorialMainImage.setFitWidth(600);
        tutorialMainImage.setFitHeight(600);
        tutorialMainImage.setPreserveRatio(true);



         // Create navigation buttons
         //This button will allow you to go back on the slides 
         Button TutoriaPrevButton = new Button("Previous");
         // this button allows you to go forward on the slides 
         Button TutorialNextButton = new Button("Next");
         //this button will send you back to the main menu
         Button returnToMainMenu = new Button("Return to Main Menu");
         


         //when you press this button, it will save the last index and subtract or add based off which of the two slide moving buttons you press
         TutoriaPrevButton.setOnAction(e ->{
            if( currentImageIndex ==0){
                currentImageIndex =3;
            }
             else{
                 currentImageIndex =  currentImageIndex-1;
             }
             tutorialMainImage.setImage(images[ currentImageIndex]);
            } );
            //loops tutorial slides whcih we show diffent slide based off the oorder
            TutorialNextButton.setOnAction(e ->{
                if( currentImageIndex ==3){
                    currentImageIndex =0;
             }
             else{
                 currentImageIndex =  currentImageIndex+1;
             }
             tutorialMainImage.setImage(images[ currentImageIndex]);
            } );



            //this sets up a HBox which will keep the buttons lined up horizontally
            HBox TutoriaHbox = new HBox(20);
            TutoriaHbox.setAlignment(Pos.CENTER);
            //This makes the button layout
            TutoriaHbox.getChildren().addAll(TutoriaPrevButton,TutorialNextButton);




            //this adds the HBox to the root which will save everything we want to display in their proper places 
            TutorialRoot.getChildren().addAll( tutorialMainImage, TutoriaHbox, returnToMainMenu);
            



            //The Scene is just Root with the peremeters of the box 
            Scene TutorialScene = new Scene(TutorialRoot, width * 2, height * 2);
          

            

              //This is what will send you back to the main meanu after your done looking at the tutorial slides
            returnToMainMenu.setOnAction(e ->{
                primaryStage.setTitle("Menu");
                primaryStage.setScene(mainMenuScene);
                primaryStage.setX((Screen.getPrimary().getVisualBounds().getWidth() - primaryStage.getWidth()) / 2); //centers the new scene
                primaryStage.setY((Screen.getPrimary().getVisualBounds().getHeight() - primaryStage.getHeight()) / 2); //centers the new scene
        });
        return TutorialScene;
    }
}