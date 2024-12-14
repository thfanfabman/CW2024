package com.example.demo.actors.planes;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.projectiles.projectileFactory;
import javafx.scene.effect.Glow;

/**
 * Represents a user-controlled fighter plane with movement, firing, and damage-handling functionality.
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = 650.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 60;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 220;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 40;

	private int velocityMultiplier;
	private int numberOfKills;

	private static final long FIRING_COOLDOWN_NANOS = 200_000_000; // Cooldown time for firing projectiles
	private static final long I_FRAMES = 150_000_000; // Invincibility frames after taking damage

	private long lastHitTime = 0;
	private long lastFireTime = 0;
	private long currentTime;

	/**
	 * Constructs a user-controlled fighter plane with specified initial health.
	 *
	 * @param initialHealth The initial health of the user plane.
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
	}

	/**
	 * Updates the position of the user plane based on its vertical movement.
	 */
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}
	}

	/**
	 * Updates the actor, managing invincibility effects and other time-based mechanics.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		currentTime = System.nanoTime();
		if (currentTime - lastHitTime > I_FRAMES) {
			this.setEffect(null); // Remove visual glow effect after invincibility ends
		}
	}

	/**
	 * Fires a projectile if the firing cooldown period has elapsed.
	 *
	 * @return A projectile object, or {@code null} if the plane cannot fire.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		currentTime = System.nanoTime();
		if (currentTime - lastFireTime < FIRING_COOLDOWN_NANOS) {
			return null; // Firing is on cooldown
		}
		lastFireTime = currentTime;
		return projectileFactory.createProjectile(
				"user",
				PROJECTILE_X_POSITION,
				getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET)
		);
	}

	/**
	 * Handles the user plane taking damage with a cooldown for invincibility frames.
	 */
	@Override
	public void takeDamage() {
		currentTime = System.nanoTime();
		if (currentTime - lastHitTime > I_FRAMES) {
			super.takeDamage();
			lastHitTime = currentTime;
			setEffect(new Glow(0.8)); // Apply visual effect to indicate damage
		}
	}

	/**
	 * Inflicts damage without applying invincibility frames.
	 */
	public void takeUnshieldedDamage() {
		super.takeDamage();
	}

	/**
	 * Moves the user plane upward by setting a negative velocity multiplier.
	 */
	public void moveUp() {
		velocityMultiplier = -1;
	}

	/**
	 * Moves the user plane downward by setting a positive velocity multiplier.
	 */
	public void moveDown() {
		velocityMultiplier = 1;
	}

	/**
	 * Stops the vertical movement of the user plane.
	 */
	public void stop() {
		velocityMultiplier = 0;
	}

	/**
	 * Retrieves the current number of kills achieved by the user plane.
	 *
	 * @return The number of kills.
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the kill count by one.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Checks if the user plane is currently moving.
	 *
	 * @return {@code true} if the plane is moving; {@code false} otherwise.
	 */
	private boolean isMoving() {
		return velocityMultiplier != 0;
	}
}
