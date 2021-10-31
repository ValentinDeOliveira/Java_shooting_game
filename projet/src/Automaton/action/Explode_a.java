package Automaton.action;

import Model.Map.Map;
import Model.Moveable.Moveable;

public class Explode_a extends Action_a {
	
	public Explode_a(int percent) {
		this.percent = percent;
	}
	
	
	public void apply(Moveable e, Map map) {		
		e.Explode(map);
	}
}
