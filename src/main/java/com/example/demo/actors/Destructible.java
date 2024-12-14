package com.example.demo.actors;

/**
 * Represents an interface for objects that can take damage and be destroyed.
 * Classes implementing this interface are expected to define behavior for handling
 * damage and destruction events.
 */
public interface Destructible {

	/**
	 * Applies damage to the object. Implementing classes should provide logic to reduce
	 * health or handle other effects when damage is taken.
	 */
	void takeDamage();

	/**
	 * Destroys the object. Implementing classes should define behavior for what happens
	 * when the object is destroyed, such as removing it from the game or triggering effects.
	 */
	void destroy();
}
