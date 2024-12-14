package com.example.demo.controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;


public class MainMenu {

    final private Stage stage;
    final private Controller controller;

    public MainMenu(Stage stage, Controller controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public void showMenu() {
        Label menuLabel = new Label("Welcome to Sky Battle");
        menuLabel.setFont(new Font("Arial", 50));
        menuLabel.setTextFill(Color.BLACK);

        Button startButton = new Button("Start Game");
        startButton.setFont(new Font("Arial", 16));
        startButton.setPrefWidth(150);
        Button howToPlay = new Button("How To Play");
        howToPlay.setFont(new Font("Arial", 16));
        howToPlay.setPrefWidth(150);
        Button exitButton = new Button("Exit");
        exitButton.setFont(new Font("Arial", 16));
        exitButton.setPrefWidth(150);

        startButton.setOnAction(event -> {
            try {
                controller.launchGame();
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
        howToPlay.setOnAction(event -> showHowToPlay());
        exitButton.setOnAction(event -> stage.close());

        VBox menuBox = new VBox(10);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(javafx.geometry.Insets.EMPTY); // Remove any padding
        menuBox.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, CornerRadii.EMPTY, null)));
        menuBox.getChildren().addAll(menuLabel, startButton, howToPlay, exitButton);

        Scene scene = new Scene(menuBox, 1300, 700);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    private void showHowToPlay() {
        Label howToPlayLabel = new Label("""
            How To Play:
            
            You are the black plane on the left
            
            1. Use arrow keys to move your plane.
            2. Press spacebar to shoot.
            3. Getting hit by enemy attacks or letting pass you makes you take damage.
            4. Avoid enemy attacks and destroy all targets to progress to the next level.
            5. Defeat the boss to win!
            
            Press escape anytime to pause the game, press any key to resume
            """);
        howToPlayLabel.setFont(new Font("Arial", 16));
        howToPlayLabel.setTextFill(Color.BLACK);

        Button returnButton = new Button("Return to Main Menu");
        returnButton.setFont(new Font("Arial", 16));
        returnButton.setPrefWidth(200);
        returnButton.setOnAction(event -> showMenu());

        VBox howToPlayBox = new VBox(10);
        howToPlayBox.setAlignment(Pos.CENTER);
        howToPlayBox.setPadding(javafx.geometry.Insets.EMPTY);
        howToPlayBox.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, CornerRadii.EMPTY, null)));
        howToPlayBox.getChildren().addAll(howToPlayLabel, returnButton);

        Scene howToPlayScene = new Scene(howToPlayBox, 1300, 700);
        stage.setScene(howToPlayScene);
        stage.sizeToScene();
        stage.show();
    }

}