import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for 2048
 */
public class Controller {
	private Grid grid;
	private Leaderboard leaderboard;

	/**
	 * This constructor initializes the grid to a specified width and height
	 *
	 * @param initialWidth - int, the initial width
	 * @param initialHeight - int, the initial height
	 */
	public Controller(int initialWidth, int initialHeight) {
		grid = new Grid(initialWidth, initialHeight);
		leaderboard = new Leaderboard();
	}

	/**
	  * Getter for grid width
	  *
	  * @return int, the grid width
	  */
	public int getWidth() {
		return grid.getWidth();
	}

	 /**
	  * Getter for grid height
	  *
	  * @return int, the grid height
	  */
	 public int getHeight() {
	 	return grid.getHeight();
	 }
	 
	 /**
	  * Getter for points
	  *
	  * @return int, the points
	  */
	 public int getPoints() {
		 return grid.getPoints();
	 }
	 
	 /**
	  * Getter for a specific Tile
	  * 
	  * @param row - int, the row value
	  * @param col - int, the column value
	  * 
	  * @return Tile, the specific tile at the location specified
	  */
	 public Tile getTile(int row, int col) {
		 return grid.getTile(row, col);
	 }


	/**
	 * Slide the board to the right
	 */
	public void slideRight() {
		grid.slideRight();
	}

	/**
	 * Slide the board to the left
	 */
	public void slideLeft() {
		grid.slideLeft();
	}

	/**
	 * Slide the board up
	 */
	public void slideUp() {
		grid.slideUp();
	}

	/**
	 * Slide the board down
	 */
	public void slideDown() {
		grid.slideDown();
	}

	/**
	 * Add a random new tile to an empty space
	 */
	public void addRandom() {
		grid.addRandom();
	}
	
	/**
	 * Getter for the movements after a user input
	 * 
	 * @return List<Movement>, a list of movements
	 */
	public List<Movement> getMovements() {
		return new ArrayList<>(grid.getMovements());
	}

	/**
	 * Checks if the game is over (win or loss)
	 */
	public boolean isOver() {
		return grid.isOver();
	}
	
	/**
	 * Checks if there is a 2048 tile anywhere in the grid
	 */
	public boolean isGameWon() {
		return grid.isGameWon();
	}
	
	/**
	 * Checks to see if a tile is merged (mostly for GUI)
	 *
	 * @param tileId - int, the ID of the specific tile
	 * @return boolean; true if tile is merged, false otherwise
	 */
	public boolean isTileMerged(int tileId) {
		return grid.isTileMerged(tileId);
	}

	/**
	 * Reset any tiles flagged as merged for the next move
	 * (mostly for GUI)
	 */
	public void resetMergedFlags() {
		grid.resetMergedFlags();
	}

	/**
	 * Checks to see which tiles are merged, and also resets
	 * merge flags
	 */
	public void completeMove() {
		grid.completeMove();
	}
	
	/**
	 * Getter for the leaderboard
	 * 
	 * @return ArrayList<LeaderEntry>, a list of LeaderEntry objects which 
	 * 		make up the leaderboard
	 */
	public ArrayList<LeaderEntry> getLeaderBoard() {
		return leaderboard.getLeaderBoard();
	}
	
	/**
	 * Getter for the highest score
	 * 
	 * @return int, the highest score
	 */
	public int getHighestScore() {
		return leaderboard.getHighestScore();
	}
	
	/**
	 * Getter for the lowest score
	 * 
	 * @return int, the lowest score
	 */
	public int getLowestScore() {
		return leaderboard.getLowestScore();
	}
	
	/**
	 * Update the leaderboard
	 * 
	 * @param name - String, the name of the user
	 * @param score - int, the score to be inputted in the leaderboard
	 */
	public void updateLeaderboard(String name, int score) {
		leaderboard.update(name, score);
	}

	/**
	 * Puts the grid in a String format (mostly used for testing)
	 */
	@Override
	public String toString() {
		return grid.toString();
	}
}