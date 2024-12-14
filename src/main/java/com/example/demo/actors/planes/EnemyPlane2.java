package com.example.demo.actors.planes;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.projectiles.projectileFactory;

/**
 * Represents an advanced enemy plane with oscillating vertical movement and projectile firing.
 */
public class EnemyPlane2 extends FighterPlane {

    private static final String IMAGE_NAME = "enemyplane2.png";
    private static final int IMAGE_HEIGHT = 80;
    private static final int HORIZONTAL_VELOCITY = -3;
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 2;
    private static final double FIRE_RATE = .01;
    private static final int Y_POSITION_UPPER_BOUND = 0;
    private static final int Y_POSITION_LOWER_BOUND = 630;

    private int vertical_velocity;
    private static final String TYPE = "enemy";

    /**
     * Constructs a new EnemyPlane2 object with the specified initial position.
     * The plane starts with a random vertical velocity either upwards or downwards.
     *
     * @param initialXPos The initial X position of the enemy plane.
     * @param initialYPos The initial Y position of the enemy plane.
     */
    public EnemyPlane2(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
        this.vertical_velocity = (Math.random() < 0.5) ? -5 : 5; // Random vertical velocity
    }

    /**
     * Updates the position of the enemy plane by moving vertically within the set bounds
     * and horizontally at a fixed speed.
     */
    @Override
    public void updatePosition() {
        setTranslateY(getTranslateY() + vertical_velocity);
        double currentPosition = getLayoutY() + getTranslateY();
        // Reverse vertical direction if the plane reaches the upper or lower bounds
        if (currentPosition <= Y_POSITION_UPPER_BOUND || currentPosition >= Y_POSITION_LOWER_BOUND) {
            vertical_velocity = -vertical_velocity;
        }
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
     * Updates the enemy plane by moving its position and potentially firing a projectile.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

}
