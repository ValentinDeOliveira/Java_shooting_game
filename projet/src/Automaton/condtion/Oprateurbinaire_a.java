package Automaton.condtion;

import Model.Map.Map;
import Model.Moveable.Moveable;

public class Oprateurbinaire_a implements ICondition_a{
	String operator;
	ICondition_a leftCondition;
	ICondition_a rightCondition;
	
	public Oprateurbinaire_a(String operator, ICondition_a left, ICondition_a right) {
		this.operator = operator;
		this.leftCondition = left;
		this.rightCondition = right;
	}


	@Override
	public boolean eval(Moveable e, Map map) {
		switch(operator) {
		case "&" :
			return leftCondition.eval(e,map) && rightCondition.eval(e,map);
		case "/" :
			return leftCondition.eval(e,map) || rightCondition.eval(e,map); 
		}
		return false;
	}

	
}


