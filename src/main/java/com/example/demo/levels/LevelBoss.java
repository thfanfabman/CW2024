package com.example.demo.levels;

import com.example.demo.actors.planes.FighterPlane;
import com.example.demo.actors.planes.planeFactory;
import javafx.scene.control.ProgressBar;

/**
 * Represents the boss level in the game.
 * This level includes a boss fight where the player faces a powerful enemy with a health bar.
 */
public class LevelBoss extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/backgroundboss.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private FighterPlane boss;
	private ProgressBar bossHealthBar;

	/**
	 * Constructs a new LevelBoss with the specified screen height and width.
	 *
	 * @param screenHeight the height of the game screen
	 * @param screenWidth the width of the game screen
	 */
	public LevelBoss(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, 1);
	}

	/**
	 * Initializes the friendly units for the boss level.
	 * Adds the user plane to the scene and sets up the boss health bar.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
		initializeBossHealthBar();
	}

	/**
	 * Initializes and displays the boss's health bar on the screen.
	 * The health bar is positioned near the bottom center of the screen.
	 */
	private void initializeBossHealthBar() {
		bossHealthBar = new ProgressBar(1.0);
		bossHealthBar.setPrefWidth(400);
		bossHealthBar.setPrefHeight(20);
		bossHealthBar.setStyle("-fx-accent: red;");
		bossHealthBar.setLayoutX((getScreenWidth() - 400) / 2);
		bossHealthBar.setLayoutY(getScreenHeight() - 100);
		getRoot().getChildren().add(bossHealthBar);
	}

	/**
	 * Checks if the game is over.
	 * The game is lost if the user's plane is destroyed, and won if the boss is destroyed.
	 */
	@Override
	protected void checkIfGameOver() {
		updateBossHealthBar();
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	/**
	 * Spawns the enemy units for the boss level.
	 * This method creates the boss enemy and adds it to the game once no enemies are present.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			boss = planeFactory.createEnemyPlane("boss", 0, 0);
			addEnemyUnit(boss);
		}
	}

	/**
	 * Updates the boss's health bar based on the current health of the boss.
	 * The progress bar reflects the ratio of the boss's current health to its maximum health.
	 */
	private void updateBossHealthBar() {
		double healthRatio = boss.getHealth() / 25.0;
		bossHealthBar.setProgress(healthRatio);
	}

	/**
	 * Instantiates the level view for the boss level.
	 * The level view displays the player's health and other game UI elements.
	 *
	 * @return a new instance of LevelView
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}
}
