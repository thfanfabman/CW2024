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
        // Create a label for the main menu text
        Label menuLabel = new Label("Welcome to Sky Battle");
        menuLabel.setFont(new Font("Arial", 50));
        menuLabel.setTextFill(Color.BLACK);

        // Create buttons for "Start Game" and "Exit"
        Button startButton = new Button("Start Game");
        startButton.setFont(new Font("Arial", 16));
        startButton.setPrefWidth(150);
        Button exitButton = new Button("Exit");

        exitButton.setFont(new Font("Arial", 16));
        exitButton.setPrefWidth(150);

        // Set button actions
        startButton.setOnAction(event -> {
            try {
                controller.launchGame();
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
        exitButton.setOnAction(event -> stage.close());

        VBox menuBox = new VBox(10);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.getChildren().addAll(menuLabel, startButton, exitButton);
        menuBox.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, null)));


        // Set up the scene and display it in the stage
        Scene scene = new Scene(menuBox, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}