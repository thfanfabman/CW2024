### Github Link
https://github.com/thfanfabman/CW2024

### Compilation
**Prerequisites**
+ Java 19 JDK or later
+ JavaFX
+ Apache Maven

**Cloning**

clone the code from the repository 
*CLI*
```shell
git clone https://github.com/thfanfabman/CW2024
cd CW2024
```

Otherwise, you could download the code in zip form from the github page
+ click on the code button 
+ download as zip
+ extract the zip file

**Build and run**

*CLI*
```shell
mvn clean compile javafx:run
```
*IntelliJ*
+ Import the project
+ Setup JDK 19
+ Run the application via the main class

### Implemented and working
+ Added a firing delay of 0.2 seconds to the player
+ main menu/how to play screen
  + Added a rudimentary main menu screen with buttons
  + Start game - starts the game
  + how to play - changes scene to just text describing how to play the game
  + exit - exits the game
+ Kill count - text on the top right keeps track of the user's kills and how much they need to progress
+ Boss Health bar - appears at the bottom of the screen on the boss level
+ New enemy type - 40% chance to spawn when an enemy plane spawns on lvl 2
+ Added brief invincibility to the player after getting hit
  + The takeDamage function is overriden in the userPlane class so that it checks when they last took damage and applies damage if it was more than 0.15 seconds ago

### Implemented and not working properly
+ Mid game pausing
  + Pressing escape in game WILL pause the game. There just isn't any UI or indication of pausing other than everything suddenly stopping
  + pressing any key resumes the game
+ end game buttons
  + Added buttons to win and loss screen but didn't implement the retry button

### Not implemented
+ SFX
+ Settings/Keybinds
+ pause menu - tried doing it for a while somewhere around the midpoint of the project
and it didn't work so it got put on hold indefinitely while i worked on other stuff

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
+ added retry button and exit button on game win or loss
+ added logic to check if enemy was destroyed from getting past the user, no longer increments kill count if so, 
also no longer grants the user invincibility.

**LevelView**
+ fixed lossScreenYPosisition typo
+ adjusted loss screen position (X & Y) so that the game over image is centered

**LevelTwo**
+ changed to instantiate LevelView instead of LevelViewLevelTwo
+ renamed to LevelBoss
+ added boss health bar and subsequent functions for it

**GameOverImage**
+ scaled the image to fit within the bounds of the screen

**ActiveActorDestructible**
+ added boolean isPenetrated to check is enemies were destroyed from reaching the edge of the screen,
no longer increments kill count if so 

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
+ added takeUnshieldedDamage function to take damage without giving invincibility when an enemy gets past the player

**UserProjectile**
+ adjusted image height to match new image size

**Resources**
+ cropped images to remove transparent bounds so that the projectiles would hit within a more reasonable range
+ added new enemy and background
+ renamed background2 to backgroundboss, added new background2

**Removed due to lack of usage**
+ shield.png
+ ShieldImage.java