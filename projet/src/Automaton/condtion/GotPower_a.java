package Automaton.condtion;

import Model.Map.Map;
import Model.Moveable.Moveable;

public class GotPower_a implements ICondition_a{

	@Override
	public boolean eval(Moveable e, Map map) {
		return e.GotPower_a();
	}


}
