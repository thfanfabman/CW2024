### Github Link
https://github.com/thfanfabman/CW2024


### Implemented and working
+ Added a firing delay of 0.2 seconds to the player
+ main menu/how to play screen
  + Added a rudimentary main menu screen with buttons
  + Start game - starts the game
  + how to play - changes scene to just text describing how to play the game
  + exit - exits the game
+ Kill count - text on the top right keeps track of the user's kills and how much they need to progress
+ Boss Health bar - appears at the bottom of the screen on the boss level

### Implemented and not working properly
+ Mid game pausing
  + Pressing escape in game WILL pause the game. There just isn't any UI or indication of pausing other than everything suddenly stopping
  + pressing any key resumes the game
+ Added brief invincibility to the player after getting hit
  + The takeDamage function is overriden in the userPlane class so that it checks when they last took damage and applies damage if it was more than 0.15 seconds ago
  + Oversight makes it so that player also gets invincibility when an enemy plane makes it past them which is not intended. it is intended to only be given when taking damage from a projectile

### Not implemented
+ SFX
+ Settings/Keybinds

### Added Classes
**MainMenu**
+ Created main menu for the game
+ Buttons for starting the game, "tutorial", and exiting
+ how to play is just text instructions
+ how to play current breaks the main menu and makes everything off center even when you return to the main menu

**LevelTwo**
+ Created a new Level and then renamed the previous leveltwo to levelboss
+ has a 40% chance to spawn enemyPlane2 instead of enemyPlane
+ functionally the same as level1 otherwise

**enemyPlane2**
+ New enemy for LevelTwo
+ oscillates between moving up and down

**PlaneFactory**
+ Factory for creating planes in a scene, enemies and users

**ProjectileFactory**
+ Factory for creating projectiles

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
+ added getScreenHeight for health bar

**LevelView**
+ fixed lossScreenYPosisition typo
+ adjusted loss screen position (X & Y) so that the game over image is centered


**LevelTwo**
+ changed to instantiate LevelView instead of LevelViewLevelTwo
+ renamed to LevelBoss
+ added boss health bar and subsequent functions for it

**GameOverImage**
+ scaled the image to fit within the bounds of the screen

**Boss**
+ movement bounds & image height adjusted to match new image size
+ now glows instead while shielded
+ adjusted MAX_FRAMES_WITH_SHIELD to be lower, adjusted shield probability to compensate

**EnemyPlane**
+ image height adjusted to match new image size

**EnemyProjectile**
+ image height adjusted to match new image size

**UserPlane**
+ movement bounds & image height adjusted to match new image size
+ Added variable to track when user last fired a projectile
+ Added firing cooldown of 0.2 seconds
+ Gave player 0.15 of invincibility after taking damage
+ UserPlane glows to indicate invincibility

**UserProjectile**
+ adjusted image height to match new image size

**Resources**
+ cropped images to remove transparent bounds so that the projectiles would hit within a more reasonable range
+ added new enemy and background

**Removed due to lack of usage**
+ shield.png
+ ShieldImage.java