package Automaton.condtion;

import Automaton.Direction_a;
import Model.Map.Map;
import Model.Moveable.Moveable;

public class MyDir_a implements ICondition_a {

	Direction_a dir;
	
	
	public MyDir_a(Direction_a dir) {
		super();
		this.dir = dir;
	}


	@Override
	public boolean eval(Moveable e, Map map) {
		return e.MyDir_a(dir);
	}



}
