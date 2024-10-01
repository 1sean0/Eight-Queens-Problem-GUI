# Eight Queens Problem GUI

## Overview
The Eight Queens Problem is a classic algorithmic problem in which the goal is to place eight queens on an 8x8 chessboard so that no two queens threaten each other. This graphical user interface (GUI) is built using **Java Swing**, providing an interactive way to visualize the process of solving the problem through a step-by-step placement of queens.

## Features
- **Grid Visualization**: Displays an 8x8 chessboard with each cell represented by a label.
- **Interactive Controls**: Includes buttons for placing queens and restarting the game.
- **Status Indicators**: Shows the current move count, status messages, and backtrack status.
- **Backtracking**: Automatically backtracks to previous rows when a queen cannot be placed, ensuring all possibilities are explored.

### Imports
- `javax.swing.*`: Provides classes for building the GUI components like frames, panels, buttons, and labels.
- `java.awt.*`: Contains classes for the AWT (Abstract Window Toolkit) components and layouts.
- `java.awt.event.*`: Includes classes for handling events like button clicks.

### Variables
- `board`: 2D array representing the chessboard.
- `currentRow`, `currentColumn`: Track the current position for placing queens.
- `labels`: GUI labels representing the chessboard cells.
- `statusLabel`, `moveLabel`, `backtrackLabel`: Labels to display status messages and move count.

## Author
Created by Sean Solomon.
