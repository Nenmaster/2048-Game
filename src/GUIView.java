
/**
 * An example of initializing and displaying a 2048 game grid this is how ive been testing it
 *
 * - Creates a Grid instance for game logic and a GridGUI for visual representation
 * - Listens for keyboard input to perform grid movements and triggers animations
 * - Handles game restarts via the restartGame method
 *
 * Usage:
 * - This class can serve as a reference for attaching a grid and its GUI to a larger game board
 * - Demonstrates how to integrate grid logic Grid with a visual component GridGUI in a game setup
 */

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.Border;

public class GUIView extends JFrame {
    private Controller grid;
    private GridGUI gridGUI;
    private JLabel currScoreLabel;
    private JLabel bestScoreLabel;
    private Sound newSound;
    private boolean gameWon;

    public GUIView() {
        newSound = new Sound("audio/NES - Donkey Kong - Sound Effects/Donkey Kong SFX (4).wav");
        newSound.play();

        setTitle("2048 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(900, 700);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        grid = new Controller(4, 4);
        grid.addRandom();
        grid.addRandom();

        currScoreLabel = new JLabel("Score: " + grid.getPoints());
        bestScoreLabel = new JLabel("Best Score: " + grid.getHighestScore());

        gridGUI = new GridGUI(grid);

        addComponents(getContentPane());

        addKeyListener(new KeyAdapter() {
            private boolean animating = false;

            @Override
            public void keyPressed(KeyEvent e) {
                if (animating) {
                    return;
                }
                boolean moved = false;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        grid.slideUp();
                        moved = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        grid.slideDown();
                        moved = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        grid.slideLeft();
                        moved = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        grid.slideRight();
                        moved = true;
                        break;
                }

                if (moved) {
                    Sound newSound = new Sound("audio/NES - Donkey Kong - Sound Effects/Donkey Kong SFX (2).wav");
                    newSound.play();
                    animating = true;
                    gridGUI.animateTiles(() -> {
                        gridGUI.update();
                        currScoreLabel.setText("Score: " + grid.getPoints());
                        bestScoreLabel.setText("Best Score: " + grid.getHighestScore());
                        animating = false;
                        if (grid.isGameWon() && !gameWon) {
                            gameWon = true;
                            changeBackground(new Color(238, 202, 24));
                            JOptionPane.showMessageDialog(null, "You won!");
                        } else if (gameWon) {
                        	changeBackground(new Color((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255)));
                        }
                        if (grid.isOver()) {
                            javax.swing.SwingUtilities.invokeLater(() -> {
                                new GameOverGUI(GUIView.this).setVisible(true);
                            });
                        }
                    });
                }
            }
        });
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        requestFocusInWindow();
    }

    /**
     * This method adds all the components to the main game GUI.
     *
     * @pre -
     * @post the main game GUI will have all its components
     *
     * @param pane
     */
    private void addComponents(Container pane) {
        JPanel topPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel endPanel = new JPanel();
        JPanel bottomPanel = new JPanel(new BorderLayout()); // Center layout for the game board

        topPanel.setPreferredSize(new Dimension(100, 100));
        leftPanel.setPreferredSize(new Dimension(150, 100));
        rightPanel.setPreferredSize(new Dimension(150, 100));
        endPanel.setPreferredSize(new Dimension(100, 100));

        JButton restartButton = new JButton("New Game");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        topPanel.setLayout(null);
        restartButton.setBounds(147, 60, 118, 40);
        restartButton.setFont(new Font("Arial", Font.BOLD, 15));
        topPanel.add(restartButton);

        currScoreLabel.setBounds(334, 60, 150, 40);
        currScoreLabel.setFont(new Font("Arial", Font.BOLD, 15));

        Border border = BorderFactory.createLineBorder(new Color(196, 164, 132), 4);
        currScoreLabel.setBorder(border);

        topPanel.add(currScoreLabel);

        bestScoreLabel.setBounds(480, 60, 150, 40);
        bestScoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
        bestScoreLabel.setBorder(border);

        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenu();
                dispose();
            }
        });
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 15));
        mainMenuButton.setBounds(0, 0, 118, 40);
        topPanel.add(mainMenuButton);

        topPanel.add(bestScoreLabel);

        bottomPanel.add(gridGUI, BorderLayout.CENTER);

        pane.add(topPanel, BorderLayout.NORTH);
        pane.add(leftPanel, BorderLayout.WEST);
        pane.add(rightPanel, BorderLayout.EAST);
        pane.add(bottomPanel, BorderLayout.CENTER);
        pane.add(endPanel, BorderLayout.SOUTH);

        changeBackground(new Color(250, 248, 240));

    }

    /**
     * Changes the background color of the panels
     * 
     * @param color a background color
     */
    private void changeBackground(Color color) {
        getContentPane().getComponent(0).setBackground(color);
        getContentPane().getComponent(1).setBackground(color);
        getContentPane().getComponent(2).setBackground(color);
        getContentPane().getComponent(3).setBackground(color);
        getContentPane().getComponent(4).setBackground(color);
    }

    /**
     * Restarts the game.
     *
     * @pre -
     * @post a new game will be started with a new grid
     *
     */
    public void restartGame() {
        // Restart the game logic and update the game board
        updateLeaderboard();
        grid = new Controller(4, 4);
        grid.addRandom();
        grid.addRandom();
        gridGUI.setNewGrid(grid);
        gridGUI.update();
        currScoreLabel.setText("Score: " + grid.getPoints());
        bestScoreLabel.setText("Best Score: " + grid.getHighestScore());
        requestFocusInWindow();
        newSound = new Sound("audio/NES - Donkey Kong - Sound Effects/Donkey Kong SFX (4).wav");
        newSound.play();
        gameWon = false;
        changeBackground(new Color(250, 248, 240));

    }

    /**
     * Compares the current score to the lowest score of the leaderboard to
     * determine if an update is needed.
     *
     * @pre there must be a current score
     * @post leaderboard will be updated if needed
     */
    private void updateLeaderboard() {
        if (grid.getPoints() > grid.getLowestScore()) {
            // Prompt the user for a name
            String playerName = JOptionPane.showInputDialog(
                    "Congratulations! You've made it to the top 10!\nPlease enter your name: ");

            // If user doesn't enter a name, set name to "user"
            if (playerName == "") {
                playerName = "User";
            }

            grid.updateLeaderboard(playerName, grid.getPoints());
        }
    }

}
