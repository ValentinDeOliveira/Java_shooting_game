package Model.Map;

public class Game_model {
	// number of seasons that the player survived
	// current map where the player plays
	private Map m_currentMap;

	public static int m_score;

	public Game_model(double ennemyPercentage) throws Exception {

		m_currentMap = new Map(ennemyPercentage);
		m_score = 0;

	}

	public Map getCurrentMap() {
		return m_currentMap;
	}

	public static void incScore() {
		m_score = m_score + 1;
	}
}
