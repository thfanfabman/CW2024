package com.example.demo.actors.planes;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.projectiles.projectileFactory;

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

    public EnemyPlane2(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
        this.vertical_velocity = (Math.random() < 0.5) ? -5 : 5;
    }

    @Override
    public void updatePosition() {
        setTranslateY(getTranslateY() + vertical_velocity);
        double currentPosition = getLayoutY() + getTranslateY();
        if (currentPosition <= Y_POSITION_UPPER_BOUND || currentPosition >= Y_POSITION_LOWER_BOUND) {
            vertical_velocity = -vertical_velocity; // Reverse direction
        }
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return projectileFactory.createProjectile(TYPE,projectileXPosition, projectileYPostion);
        }
        return null;
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

}

