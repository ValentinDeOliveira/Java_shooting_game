package Automaton.action;

import Model.Map.Map;
import Model.Moveable.Moveable;

public class Pop_a extends Action_a {

	public Pop_a(int percent) {
		this.percent = percent;
	}

	@Override
	public void apply(Moveable e, Map map) {		
		e.Pop(e, map);
	}
	


}
