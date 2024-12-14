package com.example.demo.actors;

import javafx.scene.image.*;

/**
 * Represents a base class for all active actors in the game, such as planes or projectiles.
 * This class extends {@link ImageView} to display the actor's image and provides
 * utility methods for movement.
 */
public abstract class ActiveActor extends ImageView {

	/** The folder location where all actor images are stored. */
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructs an {@code ActiveActor} with a specified image, size, and initial position.
	 *
	 * @param imageName   The name of the image file located in the image resources folder.
	 * @param imageHeight The height of the actor's image. The width is adjusted automatically
	 *                    to preserve the image's aspect ratio.
	 * @param initialXPos The initial X-coordinate where the actor will be placed.
	 * @param initialYPos The initial Y-coordinate where the actor will be placed.
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Updates the position of the actor. Subclasses must implement this method to define
	 * their specific movement behavior or updates per game frame.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by the specified distance.
	 *
	 * @param horizontalMove The distance to move horizontally. A positive value moves
	 *                       the actor to the right, and a negative value moves it to the left.
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by the specified distance.
	 *
	 * @param verticalMove The distance to move vertically. A positive value moves
	 *                     the actor downward, and a negative value moves it upward.
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}
}
