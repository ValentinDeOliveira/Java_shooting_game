package Automaton.condtion;

import Automaton.action.IAction_a;
import Model.Map.Map;
import Model.Moveable.Moveable;

public class Operateurunaire_a implements ICondition_a {
		
		String operator;
		ICondition_a condition;
		
		public Operateurunaire_a(String operator, ICondition_a condition) {
			this.operator = operator;
			this.condition = condition;
		}


		@Override
		public boolean eval(Moveable e, Map map) {
			return !condition.eval(e,map);
		}

		

		
	}

