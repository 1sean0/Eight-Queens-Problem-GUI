import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EightQueensGUI extends JFrame {
    private static final int SIZE = 8; // Size of the chessboard
    private int[][] board = new int[SIZE][SIZE]; // 2D array representing the chessboard
    private int currentRow = 0; // Current row for placing the next queen
    private JLabel[][] labels = new JLabel[SIZE][SIZE]; // Labels for the GUI
    private JLabel statusLabel = new JLabel("Status: "); // Current status label
    private JLabel moveLabel = new JLabel("Move: 0"); // Move count label
    private JButton nextButton = new JButton("Next"); // Button to place the next queen
    private JButton restartButton = new JButton("Restart"); // Button to restart the game
    private int currentColumn = 0; // Track the current column to try placing the next queen
    private int moveCount = 0; // Count of moves made
    private JLabel backtrackLabel = new JLabel("Backtrack Status: "); // Backtrack status label
    private JLabel seanLabel = new JLabel("Created by Sean Solomon"); // Creator label

    public EightQueensGUI() {
        setTitle("8 Queens Problem");
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create grid panel for the chessboard
        JPanel gridPanel = new JPanel(new GridLayout(SIZE + 1, SIZE + 1)); // Extra row and column for labels
        
        gridPanel.add(new JLabel(" ")); // Top-left corner
        for (int i = 0; i < SIZE; i++) {
            gridPanel.add(new JLabel(String.valueOf(i), SwingConstants.CENTER)); // Column labels
        }

        for (int i = 0; i < SIZE; i++) {
            gridPanel.add(new JLabel(String.valueOf(i), SwingConstants.CENTER)); // Row label
            for (int j = 0; j < SIZE; j++) {
                labels[i][j] = new JLabel(" ", SwingConstants.CENTER); // Cell labels
                labels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set border
                gridPanel.add(labels[i][j]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);
        JPanel statusPanel = new JPanel(new GridLayout(4, 1)); // Panel for status information
        statusPanel.add(moveLabel);
        statusPanel.add(statusLabel);
        statusPanel.add(backtrackLabel);
        statusPanel.add(seanLabel);
        add(statusPanel, BorderLayout.SOUTH);
        add(createControlPanel(), BorderLayout.NORTH); // Add control panel

        setVisible(true); // Make the frame visible
        restart(); // Initialize the board on startup
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.add(nextButton);
        controlPanel.add(restartButton);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeNextQueen(); // Place the next queen
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart(); // Restart the game
            }
        });

        return controlPanel; // Return the control panel
    }

    private void placeNextQueen() {
        if (currentRow < SIZE) { // Check if there are more rows to fill
            while (currentColumn < SIZE) { // Loop through columns
                if (isSafe(currentRow, currentColumn)) { // Check if it's safe to place a queen
                    board[currentRow][currentColumn] = 1; // Place queen
                    labels[currentRow][currentColumn].setText("Q"); // Update GUI
                    statusLabel.setText("Status: A queen is placed in row " + currentRow + " and column " + currentColumn);
                    moveCount++; // Increment move count
                    moveLabel.setText("Move: " + moveCount); // Update move label
                    currentRow++; // Move to the next row
                    currentColumn = 0; // Reset column for the next row
                    return; // Exit to allow the next queen to be placed
                }
                currentColumn++; // Try the next column
            }

            // If no valid position found, backtrack
            if (currentRow > 0) {
                backtrack(); // Backtrack to the previous row
            } else {
                statusLabel.setText("Status: No valid placements found. Restart to try again.");
            }
        } else {
            statusLabel.setText("Status: All queens placed successfully!"); // Success message
            statusLabel.setForeground(new Color(0, 150, 0)); // Set text color to green
            nextButton.setEnabled(false); // Disable the next button
        }
    }

    private boolean isSafe(int row, int col) {
        // Check if placing a queen at (row, col) is safe
        for (int i = 0; i < row; i++) { // Check all previous rows
            for (int j = 0; j < SIZE; j++) { // Check all columns in the previous rows
                if (board[i][j] == 1) { // If a queen is found
                    if (j == col || Math.abs(row - i) == Math.abs(col - j)) { // Check same column or diagonals
                        return false; // Not safe
                    }
                }
            }
        }
        return true; // Safe to place the queen
    }

    private void backtrack() {
        if (currentRow > 0) { // Only backtrack if not at the first row
            currentRow--; // Move back to the previous row
            currentColumn = 0; // Reset column for the backtracked row

            // Find the last placed queen and remove it
            for (int col = 0; col < SIZE; col++) {
                if (board[currentRow][col] == 1) { // If a queen is found
                    board[currentRow][col] = 0; // Remove queen
                    labels[currentRow][col].setText(" "); // Clear the label in the GUI
                    backtrackLabel.setText("Backtrack Status: Backtracking to row " + currentRow + " and trying next column.");
                    currentColumn = col + 1; // Start from the next column
                    placeNextQueen(); // Attempt to place in the next column
                    return; // Exit after trying to place the next queen
                }
            }
        } else {
            statusLabel.setText("Status: No valid positions to backtrack. Restart to try again.");
        }
    }

    private void restart() {
        board = new int[SIZE][SIZE]; // Reset the board
        currentRow = 0; // Reset current row
        currentColumn = 0; // Reset column
        moveCount = 0; // Reset move count
        moveLabel.setText("Move: " + moveCount); // Reset move label
        for (int i = 0; i < SIZE; i++) { // Clear the GUI labels
            for (int j = 0; j < SIZE; j++) {
                labels[i][j].setText(" ");
            }
        }
        statusLabel.setText("Status: Restarted. Place the first queen."); // Update status
        statusLabel.setForeground(Color.BLACK); // Set text color to black
        backtrackLabel.setText("Backtrack Status: "); // Reset backtrack status
        nextButton.setEnabled(true); // Enable next button after restart
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EightQueensGUI::new); // Run the GUI on the Event Dispatch Thread
    }
}

