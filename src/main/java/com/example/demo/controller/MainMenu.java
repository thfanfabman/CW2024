package com.example.demo.controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;


public class MainMenu {

    private Stage stage;
    private Controller controller;

    public MainMenu(Stage stage, Controller controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public void showMenu() {
        // Create a label for the main menu text
        Label menuLabel = new Label("Welcome to Sky Battle");
        menuLabel.setFont(new Font("Arial", 24));
        menuLabel.setTextFill(Color.BLACK);

        // Create buttons for "Start Game" and "Exit"
        Button startButton = new Button("Start Game");
        Button exitButton = new Button("Exit");

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

        // Use VBox to align the label and buttons vertically
        VBox menuBox = new VBox(10); // The number 10 specifies spacing between elements
        menuBox.setAlignment(Pos.CENTER);
        menuBox.getChildren().addAll(menuLabel, startButton, exitButton);

        // Set up the scene and display it in the stage
        Scene scene = new Scene(menuBox, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}