import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Leaderboard implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<LeaderEntry> board;

	public Leaderboard() {
		board = new ArrayList<LeaderEntry>();
		int valid = 0;

		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("lb.ser"))) {
			Leaderboard lb = (Leaderboard) input.readObject();
			board = lb.getLeaderBoard();
			valid = 1;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		// if no file is found, create a default leaderboard file
		if (valid == 0) {
			for (int i = 0; i < 10; i++) {
				board.add(new LeaderEntry("--", 0));
			}

			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("lb.ser"))) {
				oos.writeObject(this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets the lowest score in the leaderboard.
	 * 
	 * @pre there are scores in the leaderboard
	 * @post returns the lowest score
	 * 
	 * @return lowest score
	 */
	public int getLowestScore() {
		return board.get(9).getScore();
	}

	/**
	 * Gets the highest score in the leaderboard.
	 * 
	 * @pre there are scores in the leaderboard
	 * @post returns the highest score
	 * 
	 * @return highest score
	 */
	public int getHighestScore() {
		return board.get(0).getScore();
	}

	/**
	 * Gets the leaderboard array list with the current top 10.
	 * 
	 * @pre there are 10 leaderboard entries
	 * @post returns an array list of leaderboard entries
	 * 
	 * @return array list of leaderboard entries
	 */
	public ArrayList<LeaderEntry> getLeaderBoard() {
		return new ArrayList<LeaderEntry>(board);
	}

	/**
	 * Updates the leaderboard entries based on a passed score.
	 * 
	 * @pre pass in a name and a score
	 * @post updates the leaderboard with the new score
	 * 
	 * @param name  a name
	 * @param score a score
	 */
	public void update(String name, int score) {

		int i = 9;

		while (i >= 0 && board.get(i).getScore() < score) {
			i--;
		}

		ArrayList<LeaderEntry> updatedBoard = new ArrayList<LeaderEntry>();

		int j;
		for (j = 0; j < i + 1; j++) {
			updatedBoard.add(board.get(j));
		}

		updatedBoard.add(new LeaderEntry(name, score));

		for (j++; j < 10; j++) {
			updatedBoard.add(board.get(j - 1));
		}

		board = updatedBoard;

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("lb.ser"))) {
			oos.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// testing method
	@Override
	public String toString() {
		String list = "";

		for (int i = 0; i < board.size(); i++) {
			list += board.get(i);
			list += ", ";
		}
		return list;
	}
}