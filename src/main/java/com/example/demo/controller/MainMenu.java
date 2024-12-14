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

/**
 * The {@code MainMenu} class creates and displays the main menu for the Sky Battle game.
 * <p>
 * The menu provides options to start the game, view instructions on how to play, or exit the application.
 */
public class MainMenu {

    private int times_called = 0;

    /** The primary stage on which the menu is displayed. */
    private final Stage stage;

    /** The game controller responsible for starting the game. */
    private final Controller controller;

    /**
     * Initializes a new {@code MainMenu} with the given stage and controller.
     *
     * @param stage      the primary stage used for displaying the menu
     * @param controller the game controller that manages game flow
     */
    public MainMenu(Stage stage, Controller controller) {
        this.stage = stage;
        this.controller = controller;
    }

    /**
     * Displays the main menu with options to start the game, view the instructions, or exit.
     * @param screenHeight height of the stage
     * @param screenWidth width of the stage
     */
    public void showMenu(int screenWidth, int screenHeight) {
        times_called++;
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
                throw new RuntimeException("Failed to launch the game.", e);
            }
        });

        howToPlay.setOnAction(event -> showHowToPlay(screenWidth,screenHeight));
        exitButton.setOnAction(event -> stage.close());

        VBox menuBox = new VBox(10);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, CornerRadii.EMPTY, null)));
        menuBox.getChildren().addAll(menuLabel, startButton, howToPlay, exitButton);
        Scene scene = new Scene(menuBox, screenWidth, screenHeight);


        stage.setScene(scene);
        if(times_called > 1){
            stage.sizeToScene();
        }
        stage.setHeight(screenHeight);
        stage.setWidth(screenWidth);
        stage.show();
    }

    /**
     * Displays the "How To Play" screen with game instructions.
     * <p>
     * Provides details on game controls, objectives, and general gameplay mechanics.
     *
     * @param screenHeight stage height
     * @param screenWidth stage width
     */
    private void showHowToPlay(int screenWidth, int screenHeight) {
        Label howToPlayLabel = new Label("""
            How To Play:

            You are the black plane on the left

            1. Use arrow keys to move your plane.
            2. Press spacebar to shoot.
            3. Getting hit by enemy attacks or letting pass you makes you take damage.
            4. Avoid enemy attacks and destroy all targets to progress to the next level.
            5. Defeat the boss to win!

            Press escape anytime to pause the game, press any key to resume.
            """);
        howToPlayLabel.setFont(new Font("Arial", 24));
        howToPlayLabel.setTextFill(Color.BLACK);

        Button returnButton = new Button("Return to Main Menu");
        returnButton.setFont(new Font("Arial", 16));
        returnButton.setPrefWidth(200);
        returnButton.setOnAction(event -> showMenu(screenWidth,screenHeight));

        VBox howToPlayBox = new VBox(10);
        howToPlayBox.setAlignment(Pos.CENTER);
        howToPlayBox.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, CornerRadii.EMPTY, null)));
        howToPlayBox.getChildren().addAll(howToPlayLabel, returnButton);

        Scene howToPlayScene = new Scene(howToPlayBox, screenWidth, screenHeight);

        stage.setScene(howToPlayScene);
        stage.sizeToScene();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.show();
    }
}
