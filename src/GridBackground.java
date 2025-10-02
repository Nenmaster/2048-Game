/**
 * Draws the static background for the game grid.
 *
 * - Renders the grid layout with empty cells using a tiled design.
 * - Supports custom grid dimensions and tile sizes.
 * - Provides a visually consistent backdrop for the game tiles.
 */

import javax.swing.*;
import java.awt.*;

public class GridBackground extends JPanel {
    private int width;
    private int height;
    private int tileSize;

    public GridBackground(int width, int height, int tileSize) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grid background
        g.setColor(new Color(0xBBADA0));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw empty grid cells
        g.setColor(new Color(0xCDC1B4));
        int cellPadding = 7;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int x = j * tileSize + cellPadding;
                int y = i * tileSize + cellPadding;
                g.fillRoundRect(x, y, tileSize - 2 * cellPadding , tileSize - 2 * cellPadding , 15, 15);
            }
        }
    }
}
