/**
 * Manages the visual representation of the game grid and its tiles.
 *
 * - Renders the grid background and TileGUI components
 * - Synchronizes the visual grid with the logical grid Grid
 * - Handles animations for tile movements and merges
 * - Supports grid updates and resets
 *
 * Acts as the primary GUI component for displaying the game state.
 */

 import javax.swing.*;
 import java.awt.*;
 import java.util.List;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.Iterator;
 import java.util.concurrent.atomic.AtomicInteger;


 public class GridGUI extends JLayeredPane {
     private Map<Integer, TileGUI> tiles;
     private Controller grid;
     private int tileSize = 120;
     private int padding = 30;

     public GridGUI(Controller grid) {
         this.grid = grid;
         int height = grid.getHeight();
         int width = grid.getWidth();
         tiles = new HashMap<>();

         setPreferredSize(new Dimension(width * tileSize, height * tileSize));

         GridBackground gridBackground = new GridBackground(width, height, tileSize);
         gridBackground.setBounds(0, 0, width * tileSize, height * tileSize);
         add(gridBackground, Integer.valueOf(0));



         createTiles();
     }



     /**
      * Initializes the visual grid by creating and placing `TileGUI` objects for non-empty tiles
      *
      * - Clears existing TileGUI and resets the tiles map
      * - Adds the GridBackground to represent the grid layout
      * - Calculates positions and adds tiles to the pane and tiles map
      *
      * Used during `GridGUI` initialization and when resetting or updating the grid
      */
     private void createTiles() {
         removeAll();
         tiles.clear();

         int height = grid.getHeight();
         int width = grid.getWidth();

         GridBackground gridBackground = new GridBackground(width, height, tileSize);
         gridBackground.setBounds(0, 0, width * tileSize, height * tileSize);
         add(gridBackground, Integer.valueOf(0));

         for (int i = 0; i < height; i++) {
             for (int j = 0; j < width; j++) {
                 Tile tile = grid.getTile(i, j);
                 int value = tile.getValue();
                 if (value != 0) {
                     GridPosition pos = new GridPosition(i, j);
                     TileGUI tileGUI = new TileGUI(value, pos, tileSize, tile.getId(), grid);
                     tiles.put(tile.getId(), tileGUI);


                     int padding = 5;
                     int x = j * tileSize + padding;
                     int y = i * tileSize + padding;
                     tileGUI.setBounds(x, y, tileSize - 2 * padding, tileSize - 2 * padding);

                     add(tileGUI, Integer.valueOf(1));
                 }
             }
         }
         revalidate();
         repaint();
     }


     /**
      * Synchronizes the visual grid GridGUI with the logical grid Grid
      *
      * - Removes TileGUI's that no longer exist
      * - Adds new TileGUI objects for tiles with non-zero values
      * - Updates the positions and values of existing TileGUI's
      *
      * Used to reflect changes in the grid after moves or state updates
      */
     public void update() {
         Iterator<Map.Entry<Integer, TileGUI>> iterator = tiles.entrySet().iterator();
         while (iterator.hasNext()) {
             Map.Entry<Integer, TileGUI> entry = iterator.next();
             int tileId = entry.getKey();
             boolean tileExists = false;

             for (int i = 0; i < grid.getHeight(); i++) {
                 for (int j = 0; j < grid.getWidth(); j++) {
                     Tile tile = grid.getTile(i, j);
                     if (tile.getValue() != 0 && tile.getId() == tileId) {
                         tileExists = true;
                         break;
                     }
                 }
                 if (tileExists) break;
             }

             if (!tileExists) {
                 remove(entry.getValue());
                 iterator.remove();
             }
         }

         for (int i = 0; i < grid.getHeight(); i++) {
             for (int j = 0; j < grid.getWidth(); j++) {
                 Tile tile = grid.getTile(i, j);
                 if (tile.getValue() != 0) {
                     TileGUI tileGUI = tiles.get(tile.getId());
                     if (tileGUI == null) {
                         GridPosition pos = new GridPosition(i, j);
                         tileGUI = new TileGUI(tile.getValue(), pos, tileSize, tile.getId(), grid);
                         tiles.put(tile.getId(), tileGUI);

                         int padding = 5;
                         int x = j * tileSize + padding;
                         int y = i * tileSize + padding;
                         tileGUI.setBounds(x, y, tileSize - 2 * padding, tileSize - 2 * padding);

                         add(tileGUI, Integer.valueOf(1));
                     } else {
                         int padding = 5;
                         int x = j * tileSize + padding;
                         int y = i * tileSize + padding;
                         tileGUI.setBounds(x, y, tileSize - 2 * padding, tileSize - 2 * padding);
                         tileGUI.setValue(tile.getValue());
                     }
                 }
             }
         }
         revalidate();
         repaint();
     }

     /**
      * Animates tile movements and merges based on the logical grid's changes
      *
      * - Retrieves tile movements from getMovements in the Grid class
      * - Animates each tile's movement to its new position or triggers a merge effect
      * - Handles cleanup of merged tiles and invokes onComplete after all animations finish
      * - Creates temporary TileGUI objects for tiles that have merged and no longer exist
      *
      * Used to visually represent tile movements and merges after a game action
      */
     public void animateTiles(Runnable onComplete) {
         List<Movement> movements = grid.getMovements();
         AtomicInteger animationsLeft = new AtomicInteger(movements.size());

         if (animationsLeft.get() == 0) {
             removeMergedTiles();
             onComplete.run();
             return;
         }

         Runnable finalAction = () -> {
             if (animationsLeft.decrementAndGet() == 0) {
                 removeMergedTiles();
                 onComplete.run();
             }
         };

         for (Movement movement : movements) {
             TileGUI tileGUI = tiles.get(movement.tileId);
             if (tileGUI != null) {
                 tileGUI.animateMove(movement.newPos, tileSize,finalAction);
             } else {
                 TileGUI tempTileGUI = new TileGUI(movement.value, movement.oldPos, tileSize, movement.tileId, grid);
                 tempTileGUI.setBounds(movement.oldPos.col * tileSize, movement.oldPos.row * tileSize, tileSize, tileSize);
                 add(tempTileGUI, Integer.valueOf(1));
                 tempTileGUI.animateMove(movement.newPos, tileSize, () -> {
                     remove(tempTileGUI);
                     finalAction.run();
                 });
             }
         }
     }

     /**
      * Removes TileGUI objects from the visual grid that no longer exist in the logical grid
      *
      * - Iterates through the tiles map and checks if each TileGUI corresponds to a valid tile in the grid
      * - Removes TileGUI's that no longer exist
      *
      * Used after moves to clean up merged tiles from the visual grid
      */
     private void removeMergedTiles() {
         Iterator<Map.Entry<Integer, TileGUI>> iterator = tiles.entrySet().iterator();
         while (iterator.hasNext()) {
             Map.Entry<Integer, TileGUI> entry = iterator.next();
             int tileId = entry.getKey();
             boolean tileExists = false;

             for (int i = 0; i < grid.getHeight(); i++) {
                 for (int j = 0; j < grid.getWidth(); j++) {
                     Tile tile = grid.getTile(i, j);
                     if (tile.getValue() != 0 && tile.getId() == tileId) {
                         tileExists = true;
                         break;
                     }
                 }
                 if (tileExists) break;
             }

             if (!tileExists) {
                 remove(entry.getValue());
                 iterator.remove();
             }
         }
         revalidate();
         repaint();
     }

     // Setters and Getters

     public void setNewGrid(Controller newGrid) {
         this.grid = newGrid;
         createTiles();
     }
 }
