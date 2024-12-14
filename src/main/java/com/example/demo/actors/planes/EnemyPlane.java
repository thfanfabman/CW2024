package com.example.demo.actors.planes;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.projectiles.projectileFactory;

/**
 * Represents an enemy plane in the game with basic behavior such as movement and projectile firing.
 */
public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 80;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;
	private static final String TYPE = "enemy";

	/**
	 * Constructs a new EnemyPlane object with the specified initial position.
	 *
	 * @param initialXPos The initial X position of the enemy plane.
	 * @param initialYPos The initial Y position of the enemy plane.
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally at a fixed speed.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile from the enemy plane with a chance based on the fire rate.
	 *
	 * @return A new projectile if the enemy plane fires, or null if it does not fire.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return projectileFactory.createProjectile(TYPE, projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * Updates the enemy plane by moving its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

}
