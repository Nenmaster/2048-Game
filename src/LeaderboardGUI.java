import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 * Creates a GUI for the Leaderboard using a table.
 * 
 * - Shows Rank, Name, and Score
 * - Shows the top 10 scores in the current leaderboard.
 * 
 */
public class LeaderboardGUI extends JFrame {

    private Controller controller;

    public LeaderboardGUI() {
        // Initialize window display settings
        controller = new Controller(4, 4);
        setTitle("Leaderboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700); // Match game size
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Create top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(129, 104, 78));

        // Create main menu button
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 15));
        mainMenuButton.setFocusPainted(false);
        mainMenuButton.setBackground(new Color(100, 100, 100));
        mainMenuButton.setBounds(0, 0, 118, 40);

        // Add action to the button
        mainMenuButton.addActionListener(e -> {
            new MainMenu();
            dispose();
        });

        // Create title label
        JLabel titleLabel = new JLabel("Leaderboard", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));

        // Add components to the top panel
        topPanel.add(mainMenuButton);
        topPanel.add(titleLabel);

        // Create leaderboard table
        String[] columnNames = { "Rank", "Name", "Score" };
        String[][] leaderboardData = getLeaderboardData();
        DefaultTableModel tableModel = new DefaultTableModel(leaderboardData, columnNames);

        // Create a JTable for the leaderboard and don't let it be edited
        JTable leaderboardTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Edit the style of the content displayed
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 18));
        leaderboardTable.setRowHeight(30);
        leaderboardTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        leaderboardTable.getTableHeader().setBackground(new Color(196, 164, 132));
        leaderboardTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(leaderboardTable);

        // Create bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(129, 104, 78));
        bottomPanel.setPreferredSize(new Dimension(0, 30));

        // Create a copyright label to display on the bottom panel
        JLabel copyrightLabel = new JLabel("© 2048");
        copyrightLabel.setFont(new Font("Arial", Font.BOLD, 16));
        copyrightLabel.setForeground(Color.WHITE);
        bottomPanel.add(copyrightLabel, BorderLayout.CENTER);

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Turns the leaderboard data from the controller into a 2d array.
     * 
     * @pre the controller has leaderboard data
     * @post the data will be turned into a 2d array
     * 
     * @return the 2d array of data
     */
    private String[][] getLeaderboardData() {
        ArrayList<LeaderEntry> leaderboard = controller.getLeaderBoard();
        String[][] data = new String[leaderboard.size()][3];

        for (int i = 0; i < leaderboard.size(); i++) {
            LeaderEntry entry = leaderboard.get(i);
            // Rank
            data[i][0] = String.valueOf(i + 1);
            // Name
            data[i][1] = entry.getName();
            // Score
            data[i][2] = String.valueOf(entry.getScore());
        }

        return data;
    }

}
