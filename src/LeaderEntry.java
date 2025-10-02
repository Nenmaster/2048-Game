import java.io.Serializable;

/**
 * This class creates a Leaderboard Entry object.
 * 
 * Each Entry holds:
 * - a name
 * - a score
 * 
 * Usage: These entries will be used in the leaderboard class which holds 10
 * entries at a time.
 * 
 */
public final class LeaderEntry implements Serializable {

	private static final long serialVersionUID = 2L;
	private String name;
	private int score;

	public LeaderEntry(String n, int s) {
		name = n;
		score = s;
	}

	/**
	 * Get score for this entry.
	 * 
	 * @return score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Get name for this entry.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name + " : " + score;
	}
}