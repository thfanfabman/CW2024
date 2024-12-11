package com.example.demo.entities.planes;

import com.example.demo.entities.ActiveActorDestructible;
import com.example.demo.entities.projectiles.UserProjectile;
import javafx.scene.effect.Glow;


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
	private static final long FIRING_COOLDOWN_NANOS = 200_000_000;
	private static final long I_FRAMES = 150_000_000;
	private long lastHitTime = 0;
	private long lastFireTime = 0;
	private long currentTime;

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
	}
	
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
	
	@Override
	public void updateActor() {
		updatePosition();
		currentTime = System.nanoTime();
		if(currentTime - lastHitTime > I_FRAMES){
			this.setEffect(null);
		}
	}
	
	@Override
	public ActiveActorDestructible fireProjectile() {
		currentTime = System.nanoTime();
		if (currentTime - lastFireTime < FIRING_COOLDOWN_NANOS){
			return null;
		}
		lastFireTime = currentTime;
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	public void moveUp() {
		velocityMultiplier = -1;
	}

	public void moveDown() {
		velocityMultiplier = 1;
	}

	public void stop() {
		velocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

	@Override
	public void takeDamage(){
		currentTime = System.nanoTime();
		if(currentTime - lastHitTime > I_FRAMES){
			super.takeDamage();
			lastHitTime = currentTime;
			setEffect(new Glow(0.8));
		}
	}

}
