package Automaton.condtion;
import Model.Map.Map;
import Model.Moveable.Moveable;
import info3.game.CanvasListener;

public class True_a implements ICondition_a{


	
	@Override
	public boolean eval(Moveable e, Map map) {
		return e.True();
	}
	
	
}
