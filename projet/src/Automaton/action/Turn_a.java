package Automaton.action;

import Automaton.Direction_a;
import Model.Map.Map;
import Model.Moveable.Moveable;

public class Turn_a extends Action_a {
	Direction_a dir;

	public Turn_a(int percent) {
		this.percent = percent;
	}

	public Turn_a(Direction_a dir, int percent) {
		this.dir = dir;
		this.percent = percent;
	}

	@Override
	public void apply(Moveable e, Map map) {
	e.Turn(dir);
	}

}
