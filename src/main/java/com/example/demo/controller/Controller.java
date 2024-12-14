package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.levels.LevelParent;

/**
 * The {@code Controller} class serves as the main game controller responsible
 * for managing level transitions and game initialization.
 * <p>
 * This class uses reflection to dynamically load and instantiate level classes.
 * It also acts as an {@link Observer} to respond to level events and transitions.
 */
public class Controller implements Observer {

	/** The fully qualified class name for the first level. */
	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";

	/** The primary stage for displaying the game. */
	private final Stage stage;

	/**
	 * Initializes a new {@code Controller} with the given stage.
	 *
	 * @param stage the primary stage used to display the game
	 */
	public Controller(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Launches the game by starting the first level.
	 *
	 * @throws ClassNotFoundException    if the level class cannot be found
	 * @throws NoSuchMethodException     if the level constructor is not found
	 * @throws InstantiationException    if the level class cannot be instantiated
	 * @throws IllegalAccessException    if access to the constructor is denied
	 * @throws InvocationTargetException if the constructor throws an exception
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		stage.show();
		goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	/**
	 * Transitions to the specified level by dynamically loading the level class.
	 *
	 * @param className the fully qualified name of the level class
	 * @throws ClassNotFoundException    if the specified level class cannot be found
	 * @throws NoSuchMethodException     if the constructor with the specified signature is not found
	 * @throws InstantiationException    if the level class cannot be instantiated
	 * @throws IllegalAccessException    if access to the constructor is denied
	 * @throws InvocationTargetException if the constructor throws an exception
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
		myLevel.addObserver(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame();
	}

	/**
	 * Handles updates from observed objects (levels) and transitions to the next level.
	 *
	 * @param observable the observed object that triggered the update
	 * @param arg        an argument passed by the observed object, typically the next level's class name
	 */
	@Override
	public void update(Observable observable, Object arg) {
		try {
			goToLevel((String) arg);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
	}
}
