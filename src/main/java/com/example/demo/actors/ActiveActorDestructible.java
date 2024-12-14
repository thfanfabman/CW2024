package com.example.demo.actors;

/**
 * Represents an active actor in the game that can be destroyed or penetrated.
 * This abstract class extends {@link ActiveActor} and implements {@link Destructible},
 * providing additional properties and behaviors for actors that can take damage
 * and be removed from the game.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	/** Tracks whether the actor has been destroyed. */
	private boolean isDestroyed;

	/** Tracks whether the actor has been penetrated (e.g., by projectiles). */
	private boolean isPenetrated = false;

	/**
	 * Constructs an {@code ActiveActorDestructible} with the specified image, size, and position.
	 *
	 * @param imageName   The name of the image file used for this actor.
	 * @param imageHeight The height of the actor's image. The width is scaled proportionally.
	 * @param initialXPos The initial X-coordinate position of the actor.
	 * @param initialYPos The initial Y-coordinate position of the actor.
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Updates the position of the actor. Subclasses must provide their specific logic
	 * for position updates, such as movement behavior.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Updates the actor's state, including position, effects, or any other game-specific behavior.
	 */
	public abstract void updateActor();

	/**
	 * Applies damage to the actor. Subclasses must implement the logic
	 * for handling health reduction or other effects when damage is taken.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Marks the actor as destroyed. Once destroyed, the actor should no longer interact with the game.
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Sets the destroyed state of the actor.
	 *
	 * @param isDestroyed {@code true} if the actor is destroyed, otherwise {@code false}.
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks if the actor is destroyed.
	 *
	 * @return {@code true} if the actor is destroyed, otherwise {@code false}.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * Sets the penetrated state of the actor. This can be used to mark
	 * whether the actor has been hit by a projectile or similar event.
	 *
	 * @param penetrated {@code true} if the actor is penetrated(hit the side of the screen), otherwise {@code false}.
	 */
	public void setPenetrated(boolean penetrated) {
		this.isPenetrated = penetrated;
	}

	/**
	 * Checks if the actor has been penetrated.
	 *
	 * @return {@code true} if the actor is penetrated, otherwise {@code false}.
	 */
	public boolean isPenetrated() {
		return isPenetrated;
	}
}
