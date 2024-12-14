package com.example.demo.UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The GameOverImage class is responsible for displaying the "Game Over" image
 * when the player loses the game.
 */
public class GameOverImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	/**
	 * Constructs a new GameOverImage at the specified position.
	 * The image will be loaded from the resources folder.
	 *
	 * @param xPosition the X position where the image should be placed
	 * @param yPosition the Y position where the image should be placed
	 */
	public GameOverImage(double xPosition, double yPosition) {
		Image gameOverImg = new Image(getClass().getResource(IMAGE_NAME).toExternalForm(), 700, 700, true, true);
		setImage(gameOverImg);
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

}
