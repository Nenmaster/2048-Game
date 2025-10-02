import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer; // For the Timer
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private Sound menuSoundtrack;
    private Timer titleFlasher;
    private JLabel titleLabel;

    public MainMenu() {
        setTitle("2048 Game - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main panel
        JPanel mainPanel = new BackgroundPanel("images/mainmenu.png");
        mainPanel.setLayout(new BorderLayout());

        // Add music
        menuSoundtrack = new Sound("audio/mainmenusong.wav");
        menuSoundtrack.setVolume(-20.0f);
        menuSoundtrack.loop();

        // Resized title label
        titleLabel = createResizedLabel("images/d2048logo.png", 300, 100);
        startTitleFlashing("images/d2048logo.png", "images/b2048logo.png");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Add padding above the title
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Create menu buttons
        JButton startGameButton = createImageButton("images/startdefualt.png", "images/starthover.png");
        JButton leaderboardButton = createImageButton("images/leaderboarddefault.png", "images/leadboardhover.png");
        JButton exitButton = createImageButton("images/exitdefault.png", "images/exithover.png");

        startGameButton.addActionListener(e -> {
            cleanup();
            new GUIView();
            dispose();
        });

        leaderboardButton.addActionListener(e -> {
            cleanup();
            new LeaderboardGUI();
            dispose();
        });

        exitButton.addActionListener(e -> System.exit(0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        // Add buttons to the panel with spacing
        buttonPanel.add(Box.createVerticalStrut(10)); // Add spacing before the first button
        buttonPanel.add(startGameButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Add spacing between buttons
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Add spacing between buttons
        buttonPanel.add(exitButton);

        // Center-align buttons
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    private JLabel createResizedLabel(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image resizedImg = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(resizedImg));
    }

    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private JButton createImageButton(String defaultImagePath, String highlightedImagePath) {
        JButton button = new JButton(resizeIcon(new ImageIcon(defaultImagePath), 300, 100));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(resizeIcon(new ImageIcon(highlightedImagePath), 300, 100));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(resizeIcon(new ImageIcon(defaultImagePath), 300, 100));
            }
        });

        return button;
    }

    private void startTitleFlashing(String brightImagePath, String darkImagePath) {
        ImageIcon brightIcon = resizeIcon(new ImageIcon(brightImagePath), 300, 100);
        ImageIcon darkIcon = resizeIcon(new ImageIcon(darkImagePath), 300, 100);

        titleFlasher = new Timer(500, new ActionListener() {
            private boolean isBright = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isBright) {
                    titleLabel.setIcon(darkIcon);
                } else {
                    titleLabel.setIcon(brightIcon);
                }
                isBright = !isBright;
            }
        });
        titleFlasher.start();
    }

    private void cleanup() {
        menuSoundtrack.stop();
        if (titleFlasher != null) {
            titleFlasher.stop();
        }
    }
}
