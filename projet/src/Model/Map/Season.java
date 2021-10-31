package Model.Map;

import java.awt.Image;

import Model.LevelManager;
import view.Constants;

public class Season {

	private int m_current_season;
	private long m_elapsed_season;
	private boolean m_increment;
	private LevelManager m_levelManager;
	private boolean complete_ascending_phase;

	public Season(LevelManager m) {
		m_current_season = Constants.SUMMER;
		m_elapsed_season = 0;
		m_increment = true;
		this.m_levelManager = m;

	}

	public int getCurrentSeason() {
		return m_current_season;

	}

	public void manageSeasonLevel() {

		if (m_increment) {
			m_current_season++;
		} else {
			m_current_season--;
		}
		if (m_current_season == Constants.SUMMER) {
			this.m_levelManager.incLevel();

		}

	}

	public boolean manageSeasonTime(long elapsed) {

		if (m_current_season == Constants.SUMMER && (elapsed > Constants.ELAPSED_SUMMER)) {
			this.m_increment = true;

			manageSeasonLevel();
			return true;

		} else {
			if (m_current_season == Constants.WINTER && (elapsed > Constants.ELAPSED_WINTER)) {
				// quand l'hiver se termine

				this.m_increment = false;
				manageSeasonLevel();
				return true;

			} else {
				if ((!(m_current_season == Constants.SUMMER || m_current_season == Constants.WINTER))
						&& elapsed > Constants.ELAPSED_TRANSITION) {

					manageSeasonLevel();

					return true;

				} else {
					return false;
				}
			}
		}

	}

}
