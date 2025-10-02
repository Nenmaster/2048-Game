/**
 * This is the class that holds the main method to play the game.
 *
 */
public class PlayGame {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainMenu();
        });
    }
}
