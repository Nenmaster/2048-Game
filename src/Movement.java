/**
 * Represents a movement or change in position of a tile in the game grid.
 *
 * - Tracks the start position (`oldPos`), end position (`newPos`), tile ID, and value.
 * - Used to describe tile movements and merges during gameplay.
 * - Provides more context than `GridPosition` by encapsulating a tile's change in state.
 *
 * Key Detail:
 * - This class is distinct from `GridPosition` because it focuses on transitions (from one position to another).
 * - Necessary for animations and logical operations where the movement of a tile needs to be tracked.
 */

public class Movement {
    public GridPosition oldPos, newPos;
    public int tileId;
    public int value;
    public Movement(GridPosition oldPos, GridPosition newPos, int tileId, int value) {
        this.oldPos = oldPos;
        this.newPos = newPos;
        this.tileId = tileId;
        this.value = value;
    }
}
