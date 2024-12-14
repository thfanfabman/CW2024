package com.example.demo.controller;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The {@code Main} class serves as the entry point for the Sky Battle game application.
 * <p>
 * It initializes the primary stage, sets up its properties such as title and size,
 * and launches the main menu.
 */
public class Main extends Application {

	/** The fixed width of the game window. */
	private static final int SCREEN_WIDTH = 1300;

	/** The fixed height of the game window. */
	private static final int SCREEN_HEIGHT = 750;

	/** The title displayed in the game window's title bar. */
	private static final String TITLE = "Sky Battle";

	/**
	 * Starts the JavaFX application.
	 * <p>
	 * Initializes the primary stage, sets its dimensions, title, and creates the
	 * main menu to be displayed.
	 *
	 * @param stage the primary stage provided by JavaFX
	 */
	@Override
	public void start(Stage stage) {
		Controller myController;
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);

		// Create the game controller and main menu
		myController = new Controller(stage);
		MainMenu myMenu = new MainMenu(stage, myController);
		myMenu.showMenu(SCREEN_WIDTH,SCREEN_HEIGHT);
	}

	/**
	 * The main method launches the JavaFX application.
	 *
	 * @param args command-line arguments passed during execution
	 */
	public static void main(String[] args) {
		launch();
	}
}
