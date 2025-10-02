import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Tile {
    private static int idCounter = 0;
    private int id;
	private int value;
    private List<Integer> mergedFromIds;
    private boolean isMerged;

	public Tile(int val) {
		this.value = val;
        this.id = idCounter++;
        this.mergedFromIds = new ArrayList<>();
        this.isMerged = false;
	}

    /**
     * Returns the value of the tile.
     * @return The current value of the tile.
    */
	public int getValue() {
		return value;
	}

     /**
     * Doubles the value of the tile. Used during merging operations.
     * @return The new value of the tile after doubling.
     */
	public int doubleVal() {
		return value *= 2;
	}

     /**
     * Gets the unique ID of the tile.
     * @return The tile's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets a specific ID for the tile. Typically not used in normal operations.
     * @param id The ID to assign to the tile.
     */
    public void setId(int id) {
    	this.id = id;
    }

    /**
     * Adds the ID of a tile that merged into this tile.
     * @param Id The ID of the merged tile.
     */
    public void addMergedFromId(int Id) {
        mergedFromIds.add(Id);
    }

    /**
     * Returns a copy of the list of IDs from tiles that merged to form this tiles
     * @return A list of merged tile IDs.
     */
    public List<Integer> getMergedFromIds() {
    	return new ArrayList<>(mergedFromIds);
    }

    /**
     * Clears the list of merged tile IDs. Used to reset state after a move.
     */
    public void clearMergedFromIds() {
    	mergedFromIds.clear();
    }

    /**
     * Checks whether the tile has been merged during the current move.
     * @return True if the tile has been merged; otherwise, false.
     */
    public boolean isMerged() {
        return isMerged;
    }

     /**
     * Sets the merged status of the tile.
     * @param isMerged True to mark the tile as merged; otherwise, false.
     */
    public void setMerged(boolean isMerged) {
        this.isMerged = isMerged;
    }

    /**
     * Resets the merged status of the tile to false.
     */
    public void resetMerged() {
        this.isMerged = false;
    }

    /**
     * Checks for equality between this tile and another object.
     * Two tiles are considered equal if they have the same value and ID.
     * @param obj The object to compare against.
     * @return True if the tiles are equal; otherwise, false.
     */
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;;
        Tile other = (Tile) obj;
        return this.value == other.value && this.id == other.id;
    }

    /**
     * Generates a hash code for the tile based on its value and ID.
     * @return The hash code for the tile.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value, id);
    }

    /**
     * Returns a string representation of the tile.
     * Includes the tile's ID and value for easy debugging.
     * @return A string representation of the tile.
     */
    @Override
    public String toString() {
        return "Tile{id=" + id + ", value=" + value + "}";
    }
}
