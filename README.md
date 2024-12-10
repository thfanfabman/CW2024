### Implemented and working
+ Added a firing delay of 0.2 seconds to the player
+ Added brief invincibility to the player after getting hit
  + The takeDamage function is overriden in the userPlane class so that it checks when they last took damage and applies damage if it was more than 0.15 seconds ago

### Implemented and not working properly
+ main menu/how to play screen
  + Main menu is centered when starting the game but the "how to play" screen is not centered and will cause the main menu screen to also be off centered when returning to the main menu
+ Mid game pausing
  + Pressing escape in game WILL pause the game. There just isn't any UI or indication of pausing other than everything suddenly stopping
  + pressing any key resumes the game

### Not implemented

### Added Classes
**MainMenu**
+ Created main menu for the game
+ Buttons for starting the game, "tutorial", and exiting
+ how to play is just text instructions
+ how to play current breaks the main menu and makes everything off center even when you return to the main menu

### Modified Classes

**General**
+ moved into packages

**Main**
+ Changed to show the main menu instead of instantly starting the game

**ShieldImage**
+ changed the getResource name to match the image in the resources folder

**LevelOne**
+ added missing {} to existing if statement

**LevelParent**
+ added timeline.stop to the goToNextLevel function so that the game doesn't just stop when going to level 2
+ added logic to fireProjectile to accept null (returned when the user tries to fire during the cooldown period)
+ added pausegame and resumegame function which pause and play timeline

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
+ cropped images to remove transparent bounds so that the projectiles would hit within a more reasonable range