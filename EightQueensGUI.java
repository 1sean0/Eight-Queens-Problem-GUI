import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EightQueensGUI extends JFrame {
    private static final int SIZE = 8;
    private int[][] board = new int[SIZE][SIZE];
    private int currentRow = 0;
    private JLabel[][] labels = new JLabel[SIZE][SIZE];
    private JLabel statusLabel = new JLabel("Status: ");
    private JLabel moveLabel = new JLabel("Move: 0"); // Start from Move 0
    private JButton nextButton = new JButton("Next");
    private JButton restartButton = new JButton("Restart");
    private int currentColumn = 0; // Track the current column to try placing the next queen
    private int moveCount = 0; // Track the move count starting from 0
    private JLabel backtrackLabel = new JLabel("Backtrack Status: ");
    private JLabel seanLabel = new JLabel("Created by Sean Solomon");

    public EightQueensGUI() {
        setTitle("8 Queens Problem");
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE + 1, SIZE + 1)); // Extra row and column for labels
        
        // Create row labels
        gridPanel.add(new JLabel(" ")); // Top-left corner
        for (int i = 0; i < SIZE; i++) {
            gridPanel.add(new JLabel(String.valueOf(i), SwingConstants.CENTER));
        }

        // Create grid and column labels
        for (int i = 0; i < SIZE; i++) {
            gridPanel.add(new JLabel(String.valueOf(i), SwingConstants.CENTER)); // Row label
            for (int j = 0; j < SIZE; j++) {
                labels[i][j] = new JLabel(" ", SwingConstants.CENTER);
                labels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanel.add(labels[i][j]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);
        JPanel statusPanel = new JPanel(new GridLayout(4, 1));
        statusPanel.add(moveLabel);
        statusPanel.add(statusLabel);
        statusPanel.add(backtrackLabel);
        statusPanel.add(seanLabel);
        add(statusPanel, BorderLayout.SOUTH);
        add(createControlPanel(), BorderLayout.NORTH);

        setVisible(true);
        restart(); // Initialize the board on startup
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.add(nextButton);
        controlPanel.add(restartButton);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeNextQueen();
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });

        return controlPanel;
    }

    private void placeNextQueen() {
        if (currentRow < SIZE) {
            while (currentColumn < SIZE) {
                if (isSafe(currentRow, currentColumn)) {
                    board[currentRow][currentColumn] = 1; // Place queen
                    labels[currentRow][currentColumn].setText("Q");
                    statusLabel.setText("Status: A queen is placed in row " + currentRow + " and column " + currentColumn);
                    moveCount++; // Increment move count after placing the queen
                    moveLabel.setText("Move: " + moveCount); // Update move label
                    currentRow++; // Move to the next row
                    currentColumn = 0; // Reset column for the next row
                    return; // Exit to allow the next queen to be placed
                }
                currentColumn++; // Try the next column
            }

            // If we reached here, no valid position was found, so we backtrack
            if (currentRow > 0) {
                backtrack(); // Backtrack to the previous row
            } else {
                statusLabel.setText("Status: No valid placements found. Restart to try again.");
            }
        } else {
            statusLabel.setText("Status: All queens placed successfully!");
            nextButton.setEnabled(false); // Disable the next button when done
        }
    }

    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 1) {
                    if (j == col || Math.abs(row - i) == Math.abs(col - j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void backtrack() {
        if (currentRow > 0) {
            currentRow--; // Move back to the previous row
            currentColumn = 0; // Reset column for the backtracked row

            // Find the last placed queen and remove it
            for (int col = 0; col < SIZE; col++) {
                if (board[currentRow][col] == 1) {
                    board[currentRow][col] = 0; // Remove queen
                    labels[currentRow][col].setText(" ");
                    backtrackLabel.setText("Backtrack Status: Backtracking to row " + currentRow + " and trying next column.");
                    // Now try the next column for the current row
                    currentColumn = col + 1; // Start from the next column
                    placeNextQueen(); // Attempt to place in the next column
                    return;
                }
            }
        } else {
            statusLabel.setText("Status: No valid positions to backtrack. Restart to try again.");
        }
    }

    private void restart() {
        board = new int[SIZE][SIZE];
        currentRow = 0;
        currentColumn = 0; // Reset column
        moveCount = 0; // Reset move count to 0
        moveLabel.setText("Move: " + moveCount); // Reset move label
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                labels[i][j].setText(" ");
            }
        }
        statusLabel.setText("Status: Restarted. Place the first queen.");
        backtrackLabel.setText("Backtrack Status: ");
        nextButton.setEnabled(true); // Enable next button after restart
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EightQueensGUI::new);
    }
}
