### Added Classes
**MainMenu**
+ Created main menu for the game
+ Buttons for starting the game, "tutorial", and exiting
+ how to play is just text instructions
+ how to play current breaks the main menu and makes everything off center even when you return to the main menu

### Modifications made

**Main**
+ Changed to show the main menu instead of instantly starting the game

**ShieldImage**
+ changed the getResource name to match the image in the resources folder

**LevelOne**
+ added missing {} to existing if statement

**LevelParent**
+ added timeline.stop to the goToNextLevel function so that the game doesn't just stop when going to level 2
+ added logic to fireProjectile to accept null (returned when the user tries to fire during the cooldown period)

**LevelView**
+ fixed lossScreenYPosisition typo
+ adjusted loss screen position (X & Y) so that the game over image is centered

**LevelTwo**
+ changed to instantiate LevelView instead of LevelViewLevelTwo

**GameOverImage**
+ scaled the image to fit within the bounds of the screen

**Boss**
+ movement bounds & image height adjusted to match new image size
+ now glows while shielded

**EnemyPlane**
+ image height adjusted to match new image size

**EnemyProjectile**
+ image height adjusted to match new image size

**UserPlane**
+ movement bounds & image height adjusted to match new image size
+ Added variable to track when user last fired a projectile
+ Added firing cooldown of 0.2 seconds
+ Gave player 0.2 of invincibility after taking damage

**UserProjectile**
+ adjusted image height to match new image size

**Resources**
+ cropped images to remove transparent bounds to fix hitboxes