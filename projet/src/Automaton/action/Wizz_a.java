package Automaton.action;

import Model.Map.Map;
import Model.Moveable.Moveable;

public class Wizz_a extends Action_a {

	public Wizz_a(int percent) {
		this.percent = percent;
	}

	@Override
	public void apply(Moveable e, Map map) {		
		e.Wizz(e, map);
	}
	

}
