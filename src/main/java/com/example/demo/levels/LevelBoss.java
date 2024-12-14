package com.example.demo.levels;

import com.example.demo.actors.planes.FighterPlane;
import com.example.demo.actors.planes.planeFactory;
import javafx.scene.control.ProgressBar;

public class LevelBoss extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/backgroundboss.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private FighterPlane boss;
	private ProgressBar bossHealthBar;

	public LevelBoss(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, 1);
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
		initializeBossHealthBar();
	}

	private void initializeBossHealthBar() {
		bossHealthBar = new ProgressBar(1.0);
		bossHealthBar.setPrefWidth(400);
		bossHealthBar.setPrefHeight(20);
		bossHealthBar.setStyle("-fx-accent: red;");
		bossHealthBar.setLayoutX((getScreenWidth() - 400) / 2);
		bossHealthBar.setLayoutY(getScreenHeight() - 100);
		getRoot().getChildren().add(bossHealthBar);
	}

	@Override
	protected void checkIfGameOver() {
		updateBossHealthBar();
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			boss = planeFactory.createEnemyPlane("boss",0,0);
			addEnemyUnit(boss);
		}
	}

	private void updateBossHealthBar() {
		double healthRatio = boss.getHealth() / 25.0;
		bossHealthBar.setProgress(healthRatio);
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

}
