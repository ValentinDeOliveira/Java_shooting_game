package Model.Map;

import java.util.LinkedList;

import Automaton.Aef;
import Automaton.game.automata.ast.AST;
import Automaton.game.automata.ast.Autbuilder;
import Automaton.game.automata.parser.AutomataParser;
import Model.Constants_model;
import Model.Constants_model.TextureTypes;
import Model.LevelManager;
import Model.Moveable.Ennemy;

public class EntityGenerator {
	private Constants_model m_constants;
	private LevelManager m_level_manager;

	public EntityGenerator(LevelManager level) {
		m_constants = new Constants_model();
		
		m_level_manager=level;
	}
	public boolean generateEntityToChunk(Chunk c) throws Exception {
		int odd = generateNumber();

		Aef aef = new Aef();
		AST ast = AutomataParser.from_file(m_constants.PATH_TO_AUTOMATON);
		Autbuilder B = new Autbuilder();
		aef = ((LinkedList<Aef>) ast.accept(B)).getFirst();

		if (odd <= this.m_level_manager.getM_Ennemy_density()) {
			Ennemy E = new Ennemy(c.getX(), c.getY(), 50, this.m_level_manager.getM_Ennemy_damage(),this.m_level_manager.getM_Ennmey_life_points());
			E.setM_chunk(c);
			c.setEntity(E);
			return true;
		}

		return false;
	}

	public Texture generateTexture() {
		int odd = generateNumber();
		Texture texture;
		if (odd < m_constants.MUD_SPAWN_ODD) {
			texture = new Texture(m_constants.MUD_SPEED_SLOWING, TextureTypes.MUD);
		} else {
			texture = new Texture(m_constants.SPEED, TextureTypes.NORMAL);
		}

		return texture;
	}

	private int generateNumber() {
		return (int) (Math.random() * 100);
	}
}
