package com.example.demo.actors.planes;

import java.util.*;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.projectiles.projectileFactory;
import javafx.scene.effect.Glow;

/**
 * Represents a boss fighter plane with special behavior such as shield activation and unique movement patterns.
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 900.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = .004;
	private static final int IMAGE_HEIGHT = 75;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 25;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = 10;
	private static final int Y_POSITION_LOWER_BOUND = 600;
	private static final int MAX_FRAMES_WITH_SHIELD = 100;

	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	/**
	 * Constructs a new Boss object with initial position, health, and move pattern.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
	}

	/**
	 * Updates the boss's position based on its move pattern.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Updates the boss by updating its position and shield status.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile if the boss decides to fire in the current frame.
	 *
	 * @return A new projectile if the boss fires, or null if not.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? projectileFactory.createProjectile("boss", 0, getProjectileInitialPosition()) : null;
	}

	/**
	 * Handles taking damage for the boss. If the boss is shielded, damage is not applied.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	/**
	 * Initializes the movement pattern for the boss.
	 * This pattern consists of alternating vertical movements, with random shuffling.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield status. The shield is activated randomly and lasts for a limited number of frames.
	 */
	private void updateShield() {
		if (isShielded) framesWithShieldActivated++;
		else if (shieldShouldBeActivated()) activateShield();
		if (shieldExhausted()) deactivateShield();
	}

	/**
	 * Retrieves the next move from the movement pattern.
	 *
	 * @return The next move in the pattern (positive or negative vertical movement, or no movement).
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines if the boss should fire a projectile in the current frame.
	 *
	 * @return True if the boss fires, otherwise false.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Calculates the initial position of the projectile relative to the boss's current position.
	 *
	 * @return The y-coordinate for the projectile's initial position.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Determines if the shield should be activated based on a random chance.
	 *
	 * @return True if the shield should be activated, otherwise false.
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Determines if the shield has been active for the maximum number of frames.
	 *
	 * @return True if the shield is exhausted and needs to be deactivated.
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the shield, making the boss invulnerable and applying a glowing effect.
	 */
	private void activateShield() {
		isShielded = true;
		setEffect(new Glow(1));
	}

	/**
	 * Deactivates the shield, removing the glowing effect and resetting the shield activation frame count.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		setEffect(null);
	}

}
