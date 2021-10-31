package Automaton.game.automata.ast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import Automaton.Aef;
import Automaton.Category_a;
import Automaton.Direction_a;
import Automaton.Transition_a;
import Automaton.action.Action_a;
import Automaton.action.Explode_a;
import Automaton.action.Hit_a;
import Automaton.action.IAction_a;
import Automaton.action.Move_a;
import Automaton.action.Pop_a;
import Automaton.action.Turn_a;
import Automaton.action.Wizz_a;
import Automaton.condtion.Cell_a;
import Automaton.condtion.Closest_a;
import Automaton.condtion.GotPower_a;
import Automaton.condtion.ICondition_a;
import Automaton.condtion.Key_a;
import Automaton.condtion.Operateurunaire_a;
import Automaton.condtion.Oprateurbinaire_a;
import Automaton.condtion.True_a;

public class Autbuilder implements IVisitor {
	LinkedList<Aef> aef;
	String current_source_state_string;
	private Map<String, State> state_map;

	/**
	 * /!\ States appear as source and target of transitions.
	 * 
	 * A naive implementation would create distinct copies of the same state: - one
	 * when it is a source, - one when it is a target resulting into disconnected
	 * automaton with floating transitions.
	 * 
	 * SOLUTION We need to build a mapping from State name -->
	 * DoState(id,name,options). Thus, when encountering a state that has already
	 * been stored in the mapping we can ask the mapping what is the id we must use
	 * for that state.
	 */

	State state_id(State state) {
		State stored_state = state_map.get(state.name);
		if (stored_state != null) {
			return stored_state;
		} else {
			state_map.put(state.name, state);
			return state;
		}
	}

	void print_state_map() {
		for (State state : state_map.values()) {
			state_node(state);
		}
	}

	// CONSTRUCTOR

	public Autbuilder() {
		aef = new LinkedList<Aef>();
		this.state_map = new HashMap<String, State>();
	}

	void state_node(State state) {
		System.out.println(state);
	}

	void automaton_node(Automaton automaton) {
	}

	void transition_node(Transition transition) {
	}

	// THE METHODS REQUIRED BY IVisitor

	public Object visit(Category cat) {
		if (cat.terminal.content.equals("A")) {
			return Category_a.A;
		}
		if (cat.terminal.content.equals("C")) {
			return Category_a.C;
		}
		if (cat.terminal.content.equals("D")) {
			return Category_a.D;
		}
		if (cat.terminal.content.equals("G")) {
			return Category_a.G;
		}
		if (cat.terminal.content.equals("V")) {
			return Category_a.V;
		}
		if (cat.terminal.content.equals("O")) {
			return Category_a.O;
		}
		if (cat.terminal.content.equals("P")) {
			return Category_a.P;
		}
		if (cat.terminal.content.equals("T")) {
			return Category_a.T;
		}
		if (cat.terminal.content.equals("V")) {
			return Category_a.V;
		}
		return null;
	}

	public Object visit(Direction dir) {

		if (dir.terminal.content.equals("N")) {
			return Direction_a.N;
		}
		if (dir.terminal.content.equals("S")) {
			return Direction_a.S;
		}
		if (dir.terminal.content.equals("E")) {
			return Direction_a.E;
		}
		if (dir.terminal.content.equals("W")) {
			return Direction_a.W;
		}
		if (dir.terminal.content.equals("F")) {
			return Direction_a.F;
		}
		if (dir.terminal.content.equals("B")) {
			return Direction_a.B;
		}
		if (dir.terminal.content.equals("L")) {
			return Direction_a.L;
		}
		if (dir.terminal.content.equals("R")) {
			return Direction_a.R;
		}

		return null;
	}

	public Object visit(Key key) {
		String name = key.toString();
		switch (name) {

		// Flèches directionnelles
		case "FU":
			return 0x26;
		case "FD":
			return 0x28;
		case "FL":
			return 0x25;
		case "FR":
			return 0x27;

		// Touches Spéciales
		case "SPACE":
			return 0x20;
		case "ENTER":
			return (int) '\n';
		case "m":
			return 0x3B;
		case "l":
			return 0x4C;
		// Lettres et chiffres
		default:
			return (int) name.charAt(0);
		}
	}

	public Object visit(Value v) {
		return null;
	}

	public Object visit(Underscore u) {
		return null;
	}

	public void enter(FunCall funcall) {
	}

	Integer current_source_state;

	public Object exit(FunCall funcall, List<Object> params) {
		String name = funcall.name;
		Object temp[] = params.toArray();
		int nombre_parametre = funcall.parameters.size();
		switch (name) {

		case "True":
			return new True_a();
		case "Cell":
			return new Cell_a((Direction_a) temp[0], (Category_a) temp[1]);
		case "Closest":
			return new Closest_a((Direction_a) temp[0], (Category_a) temp[1]);
		case "Key":
			return new Key_a((int) temp[0]);
		case "GotPower":
			return new GotPower_a();
			
			
		case "Move":
			if (nombre_parametre == 0) {
				return new Move_a(funcall.percent);
			}
			if (nombre_parametre == 1) {
				return new Move_a((Direction_a) temp[0], funcall.percent);
			}
		case "Turn":
			return new Turn_a((Direction_a) temp[0], funcall.percent);
		case "Pop":
			return new Pop_a(funcall.percent);
		case "Hit":
			return new Hit_a(funcall.percent);
		case "Explode":
			return new Explode_a(funcall.percent);
		case "Wizz":
			return new Wizz_a(funcall.percent);
		
		default:
			return null;
		}
	}

	public Object visit(BinaryOp operator, Object left, Object right) {
		return new Oprateurbinaire_a(operator.operator, (ICondition_a) left, (ICondition_a) right);
	}

	public Object visit(UnaryOp operator, Object exp) {
		return new Operateurunaire_a(operator.operator, (ICondition_a) exp);
	}

	public Object visit(State state) {
		return state_id(state);
	}

	public void enter(Mode mode) {
		this.current_source_state_string = mode.state.name;
	}

	public Object exit(Mode mode, Object source_state, Object behaviour) {
		return behaviour;
	}

	public void enter(Condition condition) {
	}

	public Object exit(Condition condition, Object exp) {

		return exp;

	}

	public void enter(Action action) {
	}

	public Object exit(Action action, List<Object> funcalls) {
		ListIterator<Object> actionIterator = funcalls.listIterator();
		int size = funcalls.size();
		int index = 0;
		float sumPercentage = 0;
		LinkedList<Integer> indexNoPercentage = new LinkedList<Integer>();
		Action_a actions[] = new Action_a[size];
		Action_a act;
		while (actionIterator.hasNext()) {
			act = (Action_a) actionIterator.next();

			if (act.percent != -1) {
				sumPercentage += act.percent;
			} else {
				indexNoPercentage.add(index);
			}
			actions[index] = act;
			index++;
		}

		// Dans le cas où il existe des actions sans pourcentage (= -1), on déduit le
		// pourcentage réel
		if (indexNoPercentage.size() != 0) {
			ListIterator<Integer> indexIterator = indexNoPercentage.listIterator();
			float newPercentage = (100 - sumPercentage) / (float) indexNoPercentage.size();
			while (indexIterator.hasNext()) {
				actions[indexIterator.next()].percent = (int) newPercentage;
			}
		}
		return actions;
	}

	public Object visit(Transition transition, Object condition, Object action, Object target) {

		IAction_a[] temp = (Action_a[]) action;
		LinkedList<IAction_a> list = new LinkedList<IAction_a>();
		for (int i = 0; i < temp.length; i++) {
			list.add(temp[i]);
		}
		return new Transition_a(current_source_state_string, target.toString(), (ICondition_a) condition, list);

	}

	public Object visit(Behaviour behaviour, List<Object> transitions) {

		return transitions;
	}

	public void enter(Automaton automaton) {

	}

	public Object exit(Automaton automaton, Object initial_state, List<Object> modes) {
		Aef a = new Aef(automaton.name);
		Object temp[] = modes.toArray();
		for (int j = 0; j < temp.length; j++) {
			LinkedList<Object> temp2 = (LinkedList<Object>) temp[j];
			Object temp3[] = temp2.toArray();
			for (int i = 0; i < temp3.length; i++) {
				Transition_a t = (Transition_a) temp3[i];
				a.add_transition(t.getSource(), t.getTarget(), t.getC(), t.getA());
			}
		}
		return a;

	}

	public Object visit(AST bot, List<Object> automata) {
		return automata;
	}

}
