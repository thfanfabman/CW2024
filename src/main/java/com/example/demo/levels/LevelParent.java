package com.example.demo.levels;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.planes.FighterPlane;
import com.example.demo.actors.planes.UserPlane;
import com.example.demo.actors.planes.planeFactory;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;


/**
 * The {@code LevelParent} class serves as an abstract base for all levels in the Sky Battle game.
 * It manages the overall game state, actors (e.g., player, enemies, projectiles), and the game loop.
 *
 * Subclasses must define specific level behavior, including spawning enemies and checking for game-over conditions.
 */

public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

	private int currentNumberOfEnemies;
	private LevelView levelView;
	private int killsToProgress;
	private Label killCountLabel;


	private boolean isPaused = false;

	/**
	 * Constructs a {@code LevelParent} instance with basic game properties.
	 *
	 * @param backgroundImageName The file path of the background image for the level.
	 * @param screenHeight        The height of the game window.
	 * @param screenWidth         The width of the game window.
	 * @param playerInitialHealth The player's starting health.
	 * @param killsToProgress     The number of enemy kills needed to progress to the next level.
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, int killsToProgress) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = planeFactory.createUserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.killsToProgress = killsToProgress;

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
	}

	/**
	 * Initializes friendly units, such as the player's plane.
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Checks the game-over conditions, e.g., whether the player has been destroyed.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Spawns enemy units for the current level.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Instantiates the {@link LevelView} for the level, which manages visual components.
	 *
	 * @return A {@link LevelView} instance.
	 */
	protected abstract LevelView instantiateLevelView();


	/**
	 * Initializes the scene with the background, player's plane, and HUD.
	 *
	 * @return The initialized {@link Scene} object for the level.
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		initializeKillProgressDisplay();
		return scene;
	}

	/**
	 * Starts the game by focusing on the background and starting the game loop.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * Transitions to the next level.
	 *
	 * @param levelName The fully qualified class name of the next level.
	 */
	public void goToNextLevel(String levelName) {
		timeline.stop();
		setChanged();
		notifyObservers(levelName);
	}

	/**
	 * Updates all actors, projectiles, and game conditions during the game loop.
	 */

	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateLevelView();
		checkIfGameOver();
	}

	/**
	 * Initializes the game loop timeline to repeatedly update the scene at a fixed interval.
	 * The loop runs indefinitely and updates the game state at regular intervals.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Initializes the background image and sets up event listeners for key presses and releases.
	 * Handles user controls like movement, shooting, and pausing the game.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (isPaused) {
					resumeGame();
					return;
				}
				if (kc == KeyCode.UP) user.moveUp();
				if (kc == KeyCode.DOWN) user.moveDown();
				if (kc == KeyCode.SPACE) fireProjectile();
				if (kc == KeyCode.ESCAPE) pauseGame();
			}
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
			}
		});
		root.getChildren().add(background);
	}

	/**
	 * Initializes and displays the kill count label on the screen.
	 * The label shows the number of kills and the goal to progress to the next level.
	 */
	private void initializeKillProgressDisplay() {
		killCountLabel = new Label("Kills: 0 / " + killsToProgress);
		killCountLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
		killCountLabel.setLayoutX(screenWidth - 170);
		killCountLabel.setLayoutY(35);
		root.getChildren().addAll( killCountLabel);
	}

	/**
	 * Fires a projectile from the user's plane.
	 * If the projectile is successfully created, it is added to the scene and tracked.
	 */
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		if (projectile == null) {
			return;
		}
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	/**
	 * Generates enemy projectiles.
	 */
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	/**
	 * Spawns enemy projectiles and adds them to the game.
	 *
	 * @param projectile The projectile to spawn.
	 */
	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * Updates all actors in the game, including planes and projectiles.
	 */
	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	/**
	 * Removes all destroyed actors from the game world.
	 */
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	/**
	 * Removes destroyed actors from a given list.
	 *
	 * @param actors The list of actors to check for destruction.
	 */
	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	/**
	 * Handles collisions between friendly planes and enemy units.
	 */
	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	/**
	 * Handles collisions between user projectiles and enemy units.
	 */
	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	/**
	 * Handles collisions between enemy projectiles and friendly units.
	 */
	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	/**
	 * Handles collisions between two lists of actors.
	 *
	 * @param actors1 The first list of actors to check for collisions.
	 * @param actors2 The second list of actors to check for collisions.
	 */
	private void handleCollisions(List<ActiveActorDestructible> actors1,
			List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
					updateKillCount();
				}
			}
		}
	}

	/**
	 * Checks if any enemy units have penetrated the player's defenses(gotten past the player).
	 */
	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeUnshieldedDamage();
				enemy.setPenetrated(true);
				enemy.destroy();
			}
		}
	}

	/**
	 * Updates the HUD and handles kill progress updates.
	 */
	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * Updates the player's kill count and updates the HUD accordingly.
	 */
	private void updateKillCount() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if(!enemy.isPenetrated() && enemy.isDestroyed()){
				user.incrementKillCount();
			}
		}
		updateKillProgress();
	}

	/**
	 * Updates the on-screen kill progress.
	 */
	private void updateKillProgress() {
		killCountLabel.setText("Kills: " + user.getNumberOfKills() + " / " + killsToProgress);
	}

	/**
	 * Determines if an enemy has passed the player's defenses.
	 *
	 * @param enemy The enemy to check.
	 * @return {@code true} if the enemy has penetrated the defenses; otherwise, {@code false}.
	 */
	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	/**
	 * Stops the game and shows the "You Win" screen.
	 * Displays end game options, such as exiting the game.
	 */
	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
		showEndGameOptions();
	}

	/**
	 * Stops the game and shows the "Game Over" screen.
	 * Displays end game options, such as exiting the game.
	 */
	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
		showEndGameOptions();
	}

	/**
	 * Displays the end game options, which include an "Exit" button.
	 * This method is typically called when the game is over, either due to a win or a loss.
	 * The "Exit" button allows the player to close the application.
	 *
	 * <p>If you want to allow the player to retry the game, you can uncomment the code for the "Retry" button
	 * and implement the necessary logic to restart the current level.</p>
	 */
	private void showEndGameOptions() {
		//Button retryButton = new Button("Retry");
		Button exitButton = new Button("Exit");

		//retryButton.setStyle("-fx-font-size: 18px;");
		exitButton.setStyle("-fx-font-size: 18px;");

		//retryButton.setLayoutX(screenWidth / 2 - 80);
		//retryButton.setLayoutY(screenHeight - 100);

		exitButton.setLayoutX(screenWidth / 2);
		exitButton.setLayoutY(screenHeight - 100);

		exitButton.setOnAction(e -> System.exit(0));

		root.getChildren().addAll(exitButton);
	}


	/**
	 * Returns the user-controlled plane.
	 * This plane represents the player's plane in the game.
	 *
	 * @return The {@link UserPlane} controlled by the player.
	 */
	protected UserPlane getUser() {
		return user;
	}

	/**
	 * Returns the root node of the game scene.
	 * The root node holds all visual elements of the game, such as planes, projectiles, and the background.
	 *
	 * @return The root {@link Group} node of the game scene.
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * getter method for the number of enemies current in the level
	 * @return current number of enemies
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * adds an enemy to a list of enemies in the level
	 * @param enemy enemy entity to be added
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * getter method for the lower bounds where the enemy can be
	 * @return lower bound where enemy can be on screen
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * getter method for screen width
	 * @return screen width in px
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * getter method for screen height
	 * @return screen height in px
	 */
	protected double getScreenHeight(){
		return screenHeight;
	}

	/**
	 * checks if the user is destroyed
	 *
	 * @return {@code true} if the user is destroyed
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * updates the number of enemies currently in the level
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	/**
	 * Pauses the game timeline.
	 */
	private void pauseGame() {
		if (!isPaused) {
			timeline.pause();
			isPaused = true;
		}
	}

	/**
	 * Resumes the game timeline after being paused.
	 */
	private void resumeGame() {
		if (isPaused) {
			timeline.play();
			isPaused = false;
		}
	}
}
