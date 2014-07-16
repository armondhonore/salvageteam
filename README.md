#Salvage Team - Rush

Introduction

Space junk has been polluting our solar system for many years now. Its a problem for space travelers and satellites alike. Its time to help clean up this mess. Will you help and earn some credits while your at it?

Salvage Team – RUSH is the first mini-game in a series of fun filled physics scrolling games. You pilot a small ship that collects rubbish (bits and peaces) through a hull magnetic system. Its your task to clean up an area. You are scored on the rubbish collected and the length of time it takes to collect.  Each trash picked up adds 10 points to your score.  If you complete the level in under 60 seconds, you will get bonus points for the time remaining.

#How To Play

Click on the "PLAY" button to start the game.
![alt text](https://sites.google.com/site/monkygamessoftware/Home/salvage-team/rush/st_rush_start_menu.png?attredirects=0)


You will be entered into the game.  The top right of the screen (circled red) is your score.  The bottom right of the screen (also circled red) is the amount of time you have been playing. 
![alt text](https://sites.google.com/site/monkygamessoftware/Home/salvage-team/rush/rush02.png?attredirects=0)

To control your ship, use the arrow keys on the keyboard.  The UP arrow key applies thrust to the ship.  The LEFT and RIGHT arrow key rotates the ship counter-clockwise and clockwise respectively.
![alt text](https://sites.google.com/site/monkygamessoftware/Home/salvage-team/rush/rush03.png?attredirects=0)

The goal of the game is to collect trash cans.  Your ship needs to touch the trash can to pick it up.
![alt text](https://sites.google.com/site/monkygamessoftware/Home/salvage-team/rush/rush04.png?attredirects=0)

Once you have completed the level (either your time has run out or you collected all the trash, a rank and scores screen will be shown.  Near the bottom of the screen, your score is listed.  In the example below, the score for the round was 20.
![alt text](https://sites.google.com/site/monkygamessoftware/Home/salvage-team/rush/st_rush_rank_menu.png?attredirects=0)


The BACK TO MENU button brings you back to the original menu.  To view previously recorded scores, click the SCORES button.
![alt text](https://sites.google.com/site/monkygamessoftware/Home/salvage-team/rush/st_rush_scores_menu.png?attredirects=0)



The game should have the following objects: Ship, Space Junk, Map Walls, Start/End Location.

#The Ship:
* By pressing the left/right/up arrow keys to rotate and thrust.
* Should be constrained to a predefined area (the map).
* Bounces off walls (physics)

#The Menu
* Use default JMonkeyEngine screen for setting resolution
* First Screen has play, view scores, and exit buttons.
* End Screen has 2 screens
* The first has enter your name, a text box, and enter button
* The second has scores list, play & exit buttons.
* Pause Screen has resume and exit buttons
* HUD has score & running time left

#The Gameplay
* The player's ship starts is positioned at the start location
* The player is to collect space junk by touching/colliding with the junk
* The player's score increases with every space junk collected
* There is a score bonus for players who collect all space junk and get their ship to the end location (the score bonus is based on the amount of time left)

#Relese Notes:
Salvage Team: Rush
Version: 1.2.1
Copyright: GPL Version 3

Improvements for version 1.2.1

  * Changed the directory where the scores are saved to <user home>/.config/salvageteam/scores.jio
  * Removed menu animations for a more clean experiance

Improvements for version 1.2

  * Updated Menu System
  * Added Instructions to Menu
  * Added Turbo Boost
  * Added a loading screen 
  * Start menu loads faster
  * Added a version to the menu
