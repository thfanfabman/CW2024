package com.example.demo.actors.planes;

import com.example.demo.actors.ActiveActorDestructible;

/**
 * Represents an abstract class for a fighter plane, including functionality for health, damage handling, and firing projectiles.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;

	/**
	 * Constructs a new FighterPlane object with the specified image, position, and health.
	 *
	 * @param imageName   The name of the image representing the fighter plane.
	 * @param imageHeight The height of the image representing the fighter plane.
	 * @param initialXPos The initial X position of the fighter plane.
	 * @param initialYPos The initial Y position of the fighter plane.
	 * @param health      The initial health of the fighter plane.
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Abstract method that must be implemented by subclasses to define how the fighter plane fires projectiles.
	 *
	 * @return A projectile fired by the fighter plane.
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Handles the damage taken by the fighter plane, reducing its health and destroying it if health reaches zero.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Calculates the X position for a projectile relative to the fighter plane's current position and an offset.
	 *
	 * @param xPositionOffset The offset to apply to the X position.
	 * @return The calculated X position for the projectile.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y position for a projectile relative to the fighter plane's current position and an offset.
	 *
	 * @param yPositionOffset The offset to apply to the Y position.
	 * @return The calculated Y position for the projectile.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the health of the fighter plane has reached zero.
	 *
	 * @return True if the health is zero, otherwise false.
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Retrieves the current health of the fighter plane.
	 *
	 * @return The current health of the fighter plane.
	 */
	public int getHealth() {
		return health;
	}

}
