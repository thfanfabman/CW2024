package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.planes.planeFactory;

/**
 * Represents the second level in the game.
 * In this level, the player must defeat a number of enemies to progress to the boss level.
 */
public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.levels.LevelBoss";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 10;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final double ENEMY2_SPAWN_PROBABILITY = 0.4;

    /**
     * Constructs a new LevelTwo with the specified screen height and width.
     *
     * @param screenHeight the height of the game screen
     * @param screenWidth the width of the game screen
     */
    public LevelTwo(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE);
    }

    /**
     * Checks if the game is over.
     * The game is lost if the user's plane is destroyed, and won if the player reaches the kill target.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (userHasReachedKillTarget()) {
            goToNextLevel(NEXT_LEVEL);
        }
    }

    /**
     * Initializes the friendly units for LevelTwo.
     * Adds the user plane to the scene.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Spawns enemy units for LevelTwo.
     * Enemies are spawned randomly with a defined probability, and some enemies are of a different type ("enemy2").
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                if (Math.random() < ENEMY2_SPAWN_PROBABILITY) {
                    double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                    ActiveActorDestructible newEnemy = planeFactory.createEnemyPlane("enemy2", getScreenWidth(), newEnemyInitialYPosition);
                    addEnemyUnit(newEnemy);
                } else {
                    double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                    ActiveActorDestructible newEnemy = planeFactory.createEnemyPlane("enemy", getScreenWidth(), newEnemyInitialYPosition);
                    addEnemyUnit(newEnemy);
                }
            }
        }
    }

    /**
     * Instantiates the level view for LevelTwo.
     * The level view displays the player's health and other game UI elements.
     *
     * @return a new instance of LevelView
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    /**
     * Checks if the user has reached the kill target for advancing to the next level.
     *
     * @return true if the user has killed enough enemies to advance, false otherwise
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}
