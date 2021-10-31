package Automaton.condtion;

import Model.Map.Map;
import Model.Moveable.Moveable;
import info3.game.CanvasListener;

public class Key_a implements ICondition_a {

	int key;

	public Key_a(int key) {
		super();
		this.key = key;
	}

	@Override
	public boolean eval(Moveable e, Map map) {
		return e.Key(key);
	}

}
