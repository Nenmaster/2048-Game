# 2048 Game (Java Swing)

A full implementation of the classic 2048 puzzle game built with **Java and Swing**, 
featuring custom sound effects, a leaderboard system, and a clean MVC architecture.

## Features
- Classic 2048 gameplay with full sliding/merging logic
- GUI built with Java Swing
- Persistent leaderboard (serialization with `lb.ser`)
- Custom sound effects for interactions
- Unit tests for core game logic and leaderboard

## Design
This project follows the **MVC pattern**:
- **Model**: `Grid`, `Tile`, `Movement` — handles board state and merges
- **Controller**: `Controller` — connects user input with model updates
- **View**: `GUIView` + other GUI classes for rendering game board, menus, and leaderboard

## How to Run
Compile and run:
```bash
javac -d bin src/*.java
java -cp bin PlayGame
