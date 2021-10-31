package game;

import java.util.HashMap;

import Automaton.Aef;
import Model.Map.Game_model;
import Model.automatons.AutomatonsModel;
import view.Game_graphics;

public class Game {
	Game_graphics g;
	Game_model m;

	public Game() throws Exception {
		double ennemie = 0;
		m = new Game_model(ennemie);

		g = new Game_graphics(this);
		// a modifier

	}

	public Game_graphics getGameGraphics() {
		return this.g;
	}

	public Game_model getGameModel() {
		return this.m;
	}


}
