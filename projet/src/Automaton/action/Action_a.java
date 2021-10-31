package Automaton.action;

import Model.Map.Map;
import Model.Moveable.Moveable;

public class Action_a implements IAction_a {
	public int percent;
	@Override
	
	
	public void apply(Moveable e) throws Exception {		
	}
	
	
	public Action_a() {
		percent = -1;
	}


	public Action_a(int percent) {
		this.percent = percent;
	}


	public void apply(Moveable e, Map map) {
		
	}

}
