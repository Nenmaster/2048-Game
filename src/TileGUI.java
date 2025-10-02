import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TileGUI extends JPanel {
    private JLabel label;
    public int value;
    public int id;
    public GridPosition currentPos; // add for animate move
    public GridPosition targetPos; // add for animate move
    private float scale = 1.0f;
    private Controller grid;

    public TileGUI(int value, GridPosition initialPos, int tileSize, int id, Controller grid) {
        this.value = value;
        this.currentPos = initialPos;
        this.id = id;
        this.grid = grid;
        setLayout(new BorderLayout());
        label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        setValue(value);
        add(label, BorderLayout.CENTER);
        setOpaque(false);
    }

    public void setValue(int value) {
        this.value = value;
        if (value == 0) {
            label.setText("");
        } else {
            label.setText(String.valueOf(value));
        }
        repaint();
    }

    public int getValue() {
        return value;
    }

    private Color getColorForValue(int value) {
        switch (value) {
            case 2: return new Color(0xEEE4DA);
            case 4: return new Color(0xEDE0C8);
            case 8: return new Color(0xF2B179);
            case 16: return new Color(0xF59563);
            case 32: return new Color(0xF67C5F);
            case 64: return new Color(0xF65E3B);
            case 128: return new Color(0xEDCF72);
            case 256: return new Color(0xEDCC61);
            case 512: return new Color(0xEDC850);
            case 1024: return new Color(0xEDC53F);
            case 2048: return new Color(0xEDC22E);
            default: return new Color(0x3C3A32);
        }
    }

    public int getId() {
        return id;
    }

    /**
     * Animates the movement of a tile to a target position or triggers a merge effect.
     *
     * @param targetGridPos The target grid position for the animation.
     * @param tileSize The size of each tile in pixels.
     * @param isMerge A flag indicating if this animation is for a merge effect.
     * @param onComplete A Runnable to execute after the animation is completed.
     */
    public void animateMove(GridPosition targetGridPos, int tileSize, Runnable onComplete) {
        int targetColInPixels = targetGridPos.col * tileSize;
        int targetRowInPixels = targetGridPos.row * tileSize;

        Point currentLocation = getLocation();
        int currentX = currentLocation.x;
        int currentY= currentLocation.y;

        int horizontalDistance = targetColInPixels - currentX;
        int verticalDistance = targetRowInPixels - currentY;

        int steps = 6;
        int delay = 10;

        Timer timer = new Timer(delay, null);

        boolean isMerge = grid.isTileMerged(id);
        if (horizontalDistance != 0 || verticalDistance != 0) {
            animateMovement(currentX, currentY, horizontalDistance, verticalDistance, steps, delay, () -> {
                if (isMerge) {
                    animateMergeEffect(steps, delay, () -> {
                        grid.completeMove();
                        if(onComplete != null){
                            onComplete.run();
                        }
                    });
                } else {
                    grid.completeMove();
                    if(onComplete != null){
                        onComplete.run();
                    }
                }
            });
        } else if (isMerge) {
            animateMergeEffect(steps, delay, () -> {
                grid.completeMove();
                if(onComplete != null){
                    onComplete.run();
            }
        });
        } else {
            grid.completeMove();
            if (onComplete != null) {
                onComplete.run();
            }
        }
    }

    /**
     * Animates the movement of a tile over a specified number of steps.
     *
     * @param currentX The current X-coordinate of the tile.
     * @param currentY The current Y-coordinate of the tile.
     * @param horizontalDist The total horizontal distance to move.
     * @param verticalDist The total vertical distance to move.
     * @param steps The number of steps determines how smooth the animation is
     * @param delay The delay in milliseconds between each step.
     * @param onComplete A Runnable to execute once the animation completes.
     */
    private void animateMovement(int currentX, int currentY, int horizontalDist, int verticalDist , int steps, int delay, Runnable onComplete) {
        Timer timer = new Timer(delay, null);
        final int[] currentStep = {0};

        final int stepHorizontalIncrement = horizontalDist / steps;
        final int stepVerticalIncrement = verticalDist / steps;

        final int horizontalRemainder = horizontalDist % steps;
        final int verticalRemainder = verticalDist % steps;

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentStep[0]++;

                int newX = currentX + stepHorizontalIncrement * currentStep[0];
                int newY = currentY + stepVerticalIncrement * currentStep[0];

                setLocation(newX, newY);
                repaint();

                if (currentStep[0] >= steps) {
                    timer.stop();
                    if (onComplete != null) {
                        onComplete.run();
                    }
                }
            }
        });

        timer.start();
    }


    /**
     * Animates a "merge effect" for a tile, scaling it up and then back down to simulate merging.
     *
     * @param steps The number of steps for the animation
     * @param delay The delay in milliseconds between each animation step.
     * @param onComplete A Runnable to execute once the animation is complete.
     */
    private void animateMergeEffect(int steps, int delay, Runnable onComplete) {
        Timer timer = new Timer(delay, null);
        final int[] currentStep = {0};
        final float minScale = 0.8f; // Smaller scale
        final float maxScale = 1.1f; // Slightly larger scale before returning to normal
        final Color originalColor = getBackground();
        final Color mergeHighlight = new Color(0xFFD700); // Gold-ish color for a merge effect

        setBackground(mergeHighlight); // Apply temporary highlight color

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentStep[0]++;
                float progress = (float) currentStep[0] / steps;
                float scale;
                if (progress < 0.5) {
                    scale = minScale + (maxScale - minScale) * (progress * 2);
                } else {
                    scale = maxScale - (maxScale - minScale) * ((progress - 0.5f) * 2);
                }
                setScale(scale);
                repaint();
                if (currentStep[0] >= steps) {
                    setScale(1.0f); // Reset to normal scale
                    setBackground(originalColor); // Restore original color
                    timer.stop();
                    if (onComplete != null) {
                        onComplete.run();
                    }
                }
            }
        });

        timer.start();
    }

    /**
     * Sets the scale factor for this component and triggers a revalidation and repaint.
     * The scale factor adjusts the size of the tile during animations such as merging.
     *
     * @param scale The scale factor to apply 1.0 for original size 1.0 > to make larger
     */
    private void setScale(float scale) {
        this.scale = scale;
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(getColorForValue(value));

        int width = getWidth();
        int height = getHeight();

        int scaledWidth = Math.round(width * scale);
        int scaledHeight = Math.round(height * scale);

        int x = (width - scaledWidth) / 2;
        int y = (height - scaledHeight) / 2;
        g2d.fillRoundRect(x, y, scaledWidth, scaledHeight, 15, 15);
        g2d.dispose();
    }
}
