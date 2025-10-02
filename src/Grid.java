
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Grid {
    private ArrayList<ArrayList<Tile>> grid;
    private int width;
    private int height;
    private ArrayList<ArrayList<Tile>> prevMoveState;
    private ArrayList<ArrayList<Tile>> afterMoveState;
    private int points;
    private boolean isGameWon;

    /**
     * Create a new grid with the given height and weight
     * 
     * @pre initialWidth > 3 && initialHeight > 3
     * @param initialWidth
     * @param initialHeight
     */
    public Grid(int initialWidth, int initialHeight) {
        assert initialWidth > 3 && initialHeight > 3;
        grid = new ArrayList<ArrayList<Tile>>();
        for (int i = 0; i < initialHeight; i++) {
            ArrayList<Tile> array = new ArrayList<Tile>();
            for (int j = 0; j < initialWidth; j++) {
                array.add(new Tile(0));
            }
            grid.add(array);
        }

        width = initialWidth;
        height = initialHeight;
    }

    /**
     * This method only slides the board the to right, without
     * adding in a random tile. This allows us to check the
     * sliding logic separately
     */
    private void slideRightLogic() {
        prevMoveState = copyState(grid);
        printGrid(prevMoveState);
        for (int i = 0; i < height; i++) {
            Collections.reverse(grid.get(i));
            slideHelper(grid.get(i), i);
            Collections.reverse(grid.get(i));
        }
    }

    /**
     * Slides the board to the right, merging if necessary, and adds a
     * random tile if able to
     */
    public void slideRight() {
        slideRightLogic();
        if (!gridsAreEqual(prevMoveState, grid)) {
            addRandom();
        }
        afterMoveState = copyState(grid);
        printGrid(afterMoveState);
    }

    /**
     * This method only slides the board the to left, without
     * adding in a random tile. This allows us to check the
     * sliding logic separately
     */
    private void slideLeftLogic() {
        prevMoveState = copyState(grid);
        printGrid(prevMoveState);
        System.out.println("\n");
        for (int i = 0; i < height; i++) {
            slideHelper(grid.get(i), i);
        }
    }

    /**
     * Slides the board to the left, merging if necessary, and adds a
     * random tile if able to
     */
    public void slideLeft() {
        slideLeftLogic();
        if (!gridsAreEqual(prevMoveState, grid)) {
            addRandom();
        }
        afterMoveState = copyState(grid);
        printGrid(afterMoveState);
    }

    /**
     * This method only slides the board upwards, without
     * adding in a random tile. This allows us to check the
     * sliding logic separately
     */
    private void slideUpLogic() {
        prevMoveState = copyState(grid);
        printGrid(prevMoveState);
        System.out.println("\n");
        for (int j = 0; j < width; j++) {
            ArrayList<Tile> newArray = new ArrayList<Tile>();
            for (int i = 0; i < height; i++) {
                newArray.add(grid.get(i).get(j));
            }

            slideHelper(newArray, j);

            for (int i = 0; i < height; i++) {
                grid.get(i).set(j, newArray.get(i));
            }
        }
    }

    /**
     * Slides the board upwards, merging if necessary, and adds a
     * random tile if able to
     */
    public void slideUp() {
        slideUpLogic();

        if (!gridsAreEqual(prevMoveState, grid)) { // Only add random tile if grid changes
            addRandom();
        }
        afterMoveState = copyState(grid);
        printGrid(afterMoveState);
    }

    /**
     * This method only slides the board downwards, without
     * adding in a random tile. This allows us to check the
     * sliding logic separately
     */
    private void slideDownLogic() {
        prevMoveState = copyState(grid);
        printGrid(prevMoveState);
        System.out.println("\n");
        for (int j = 0; j < width; j++) {
            ArrayList<Tile> newArray = new ArrayList<Tile>();
            for (int i = 0; i < height; i++) {
                newArray.add(grid.get(i).get(j));
            }

            Collections.reverse(newArray);
            slideHelper(newArray, j);
            Collections.reverse(newArray);

            for (int i = 0; i < height; i++) {
                grid.get(i).set(j, newArray.get(i));
            }
        }
    }

    /**
     * Slides the board downwards, merging if necessary, and adds a
     * random tile if able to
     */
    public void slideDown() {
        slideDownLogic();

        if (!gridsAreEqual(prevMoveState, grid)) { // Only add random tile if grid changes
            addRandom();
        }
        afterMoveState = copyState(grid);
        printGrid(afterMoveState);
    }

    /**
     * helper method for all the slide methods works by deleting every 0 and merging
     * same,
     * consecutive number and then adding the 0s back at the end and returning that
     * array
     */
    private void slideHelper(ArrayList<Tile> array, int rowIndex) {
        int size = array.size();
        int oldSize = size;
        for (int j = 0; j < size; j++) {
            if (array.get(j).getValue() == 0) {
                array.remove(j);
                j--;
                size--;
            } else if (j != 0) {
                if (array.get(j).getValue() == array.get(j - 1).getValue() && !array.get(j - 1).isMerged()
                        && !array.get(j).isMerged()) {
                    Tile mergedTile = mergeTiles(array.get(j - 1), array.get(j));
                    mergedTile.setMerged(true);
                    array.set(j - 1, mergedTile);
                    array.remove(j);
                    j--;
                    size--;
                } else if (array.get(j - 1).getValue() == 0) {
                    array.remove(j - 1);
                    j--;
                    size--;
                }
            }
        }
        while (array.size() < oldSize) {
            array.add(new Tile(0));
        }
    }

    /**
     * searches the grid for empty tiles and adds them as a tuple coordinate to an
     * array
     * we then get a random empty tile and make that a 2 or 4
     */
    public void addRandom() {
        ArrayList<MyTuple> tupleList = new ArrayList<MyTuple>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid.get(i).get(j).getValue() == 0) {
                    tupleList.add(new MyTuple(i, j));
                }
                if (grid.get(i).get(j).getValue() == 2048) {
                    isGameWon = true;
                }
            }
        }
        if (tupleList.size() == 0) {
            System.out.println("No empty tiles available to add a random tile.");
            return;
        }
        int randInt = (int) (Math.random() * tupleList.size());
        MyTuple gridCoords = tupleList.get(randInt);
        Tile newTile;
        double randomNum = Math.random();
        if (randomNum > 0.8) {
            newTile = new Tile(4);
        } else {
            newTile = new Tile(2);
        }
        grid.get(gridCoords.vali).set(gridCoords.valj, newTile);
    }

    /**
     * @return returns where if there is a 2048 tile anywhere in the grid
     */
    public boolean isGameWon() {
        return isGameWon;
    }

    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int value = grid.get(i).get(j).getValue();
                if (value == 0) {
                    string += "x";
                } else {
                    string += grid.get(i).get(j).getValue();
                }
                string += " | ";
            }
            string += "\n";
        }
        return string;
    }

    // Allows us to store two index values as a single value which is helpful for a
    // 2d array
    public class MyTuple {
        int vali;
        int valj;

        public MyTuple(int i, int j) {
            vali = i;
            valj = j;
        }
    }

    private boolean gridsAreEqual(ArrayList<ArrayList<Tile>> grid1, ArrayList<ArrayList<Tile>> grid2) {
        if (grid1.size() != grid2.size())
            return false;
        for (int i = 0; i < grid1.size(); i++) {
            ArrayList<Tile> row1 = grid1.get(i);
            ArrayList<Tile> row2 = grid2.get(i);
            if (row1.size() != row2.size())
                return false;
            for (int j = 0; j < row1.size(); j++) {
                Tile tile1 = row1.get(j);
                Tile tile2 = row2.get(j);
                if (tile1.getValue() != tile2.getValue())
                    return false;
            }
        }
        return true;
    }

    /**
     * Merges two tiles into a new tile with the value equaling twice the orginal
     * value of the tile. The resulting tile will retain the ID of the first tile.
     *
     * @param tile1 The primary tile will retain this ID
     * @param tile2 The second tile to merge
     * @return A new Tile object with merged value
     */
    private Tile mergeTiles(Tile tile1, Tile tile2) {
        int mergedValue = tile1.doubleVal();
        points += mergedValue;
        Tile mergedTile = new Tile(mergedValue);
        mergedTile.setId(tile1.getId());
        mergedTile.addMergedFromId(tile1.getId());
        mergedTile.addMergedFromId(tile2.getId());
        mergedTile.setMerged(true);
        return mergedTile;
    }

    // checks if board is full and no other moves are available
    public boolean isOver() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int value = grid.get(i).get(j).getValue();
                if (value == 0) {
                    return false;
                } else if (j + 1 < width && value == grid.get(i).get(j + 1).getValue()) {
                    return false;
                } else if (i + 1 < height && value == grid.get(i + 1).get(j).getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retrieves a list of movements that occurred between the previous state and
     * the current state of the grid.
     *
     * @return A list of Movement objects, each representing a tile's movement or
     *         merge.
     */
    public List<Movement> getMovements() {
        List<Movement> movements = new ArrayList<>();
        Map<Integer, GridPosition> prevPositions = new HashMap<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile prevTile = prevMoveState.get(i).get(j);
                if (prevTile.getValue() != 0) {
                    prevPositions.put(prevTile.getId(), new GridPosition(i, j));
                }
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile currTile = grid.get(i).get(j);
                if (currTile.getValue() != 0) {
                    List<Integer> originIds = new ArrayList<>();
                    if (currTile.getMergedFromIds() != null && !currTile.getMergedFromIds().isEmpty()) {
                        originIds.addAll(currTile.getMergedFromIds());
                    } else {
                        originIds.add(currTile.getId());
                    }
                    for (int originId : originIds) {
                        GridPosition oldPos = prevPositions.get(originId);
                        if (oldPos != null) {
                            movements.add(new Movement(
                                    oldPos,
                                    new GridPosition(i, j),
                                    originId,
                                    currTile.getValue()));
                            prevPositions.remove(originId);
                        }
                    }
                }
            }
        }
        return movements;
    }

    /**
     *
     * This method is used to reset the merge tracking for tiles after
     * each move.
     */
    public void clearMergedFromIds() {
        for (ArrayList<Tile> row : grid) {
            for (Tile tile : row) {
                tile.clearMergedFromIds();
            }
        }
    }

    // allow to keep track of state so random tiles arent added when the state
    // doesn't change
    private ArrayList<ArrayList<Tile>> copyState(ArrayList<ArrayList<Tile>> oldGrid) {
        ArrayList<ArrayList<Tile>> orginalState = new ArrayList<>();
        for (ArrayList<Tile> row : oldGrid) {
            ArrayList<Tile> newRow = new ArrayList<>();
            for (Tile tile : row) {
                Tile newTile = new Tile(tile.getValue());
                newTile.setId(tile.getId());
                newRow.add(newTile);
            }
            orginalState.add(newRow);
        }
        return orginalState;
    }

    public boolean isTileMerged(int tileId) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile currTile = grid.get(i).get(j);
                if (currTile.getId() == tileId) {
                    if (currTile.getMergedFromIds() != null && currTile.getMergedFromIds().size() > 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void resetMergedFlags() {
        for (ArrayList<Tile> row : grid) {
            for (Tile tile : row) {
                tile.setMerged(false);
            }
        }
    }

    public void completeMove() {
        clearMergedFromIds();
        resetMergedFlags();
    }

    // Getters

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPoints() {
        return points;
    }

    public Tile getTile(int row, int col) {
        return grid.get(row).get(col);
    }

    ///////////////////////
    // DEBUGGING Methods //
    ///////////////////////

    private void printTileArray(ArrayList<Tile> array) {
        for (Tile tile : array) {
            System.out.print(tile + " ");
        }
        System.out.println();
    }

    // for debugging grid
    private void printGrid(ArrayList<ArrayList<Tile>> gridState) {
        for (ArrayList<Tile> row : gridState) {
            for (Tile tile : row) {
                System.out.print(tile.getValue() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
