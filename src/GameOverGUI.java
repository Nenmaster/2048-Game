
/**
 * A dialog displayed when the game is over
 *
 * - Shows a Game Over message to the user.
 * - Provides options to restart the game or exit the application
 * - Communicates with the parent frame GUIView to restart the game
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameOverGUI extends JDialog {

    public GameOverGUI(JFrame parent) {
        super(parent, "Game Over", true);
        setLayout(new BorderLayout());

        // Create the game over message label
        JLabel message = new JLabel("Game Over!", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 24));
        add(message, BorderLayout.CENTER);

        // Create panel and buttons
        JPanel buttonPanel = new JPanel();
        JButton restartButton = new JButton("Restart");
        JButton exitButton = new JButton("Exit");

        restartButton.addActionListener((ActionEvent e) -> {
            dispose();
            ((GUIView) parent).restartGame();
        });

        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(300, 150);
        setLocationRelativeTo(parent);
    }
}
