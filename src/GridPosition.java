/**
 * Represents a single position in the game grid
 * This class focuses only on the position of a tile not its movement or states
 *
 * - Tracks the row and column of a tile.
 * - Used for referencing tile locations in the grid.
 * - Used in operations where only the tile's location is relevant
 */

public class GridPosition {
    int row, col;

    public GridPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRowOffset(GridPosition other) {
        return this.row - other.row;
    }

    public int getColOffset(GridPosition other) {
        return this.col - other.col;
    }

    @Override
    public String toString() {
        return "GridPosition{row=" + row + ", col=" + col + "}";
    }

}
