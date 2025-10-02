# 2048Project
## Authors: Mahir Kabir, Omar Mendivil, Daniel Rendon, Joshua Boyer

## Overview:
This is our 2048 game for our CSC 335 Final Project! Just like the orginal game, you start with a 4x4 grid with two tiles, each of which can either be a 2 or a 4. The game is played is by sliding the tiles on the board, and when like tiles interact, they merge. For example, when there are two 2 tiles in the same row directly next to each other, and the board is slid to the right, the two tiles will merge into a single 4 tile. The purpose of the game is to continuously merge these tiles until you reach 2048. However, whenever the board slides, a new tile is randomly added, so make sure to reach 2048 before the rest of the board fills up!

## How to Run:
In order to play our 2048 game, you simply need to run the PlayGame class.

## Design:
For this project, we implemented a MVC design. The model (M) is mostly contained in the Grid.java class, which stores the board and keeps track of any movements. The grid is made up of Tile.java objects, each of which stores a value and an ID in order to keep track of merges. The Movement.java and GridPosition.java files also help with merging tiles. The Controller.java class is our controller (C) part of the MVC pattern and stores a Grid object for the view to interact with. The view (V), then, is made up by the GUI files: GUIView.java, GameOverGUI.java, GridBackground.java, GridGUI.java, LeaderboardGUI.java, MainMenu.java, and TileGUI.java. GUIView.java is the main function controlling the GUI, and it uses the other GUI files to properly display our game. 

## WOW Factors:
The WOW Factors we chose to implement were a leaderboard and sound effects. The logic for the leaderboard is contained in the Leaderboard.java and LeaderEntry.java classes, and the LeaderboardGUI.java class then handles the GUI aspect. The sound effects are controlled in the Sound.java class, and there are sound effects for most user interactions.  

## Tests:
To test the backend of our code, which is mostly the model and control aspects of the MVC design, most of our test cases are in the MVCJUnitTests.java file. This tests mostly the Tile.java, Movement.java, GridPosition.java, Grid.java, and Controller.java classes. To test the leaderboard logic, the LeaderboardJUnitTests.java class tests theLeaderEntry.java and Leaderboard.java classes. A separate file was necessary for the leaderboard test cases to make it easier to test the lb.ser file. It is important to note that some tests may seem redundant, since if we test something in the controller, it will also test the Grid.java model simply based on how the model and controller are connected. However, we wanted to check all the model classes, then the controller classes, and so on, making sure that as the classes connect to each other, no bugs arise. 

