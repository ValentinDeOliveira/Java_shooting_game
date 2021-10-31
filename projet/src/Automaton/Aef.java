package Automaton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import Automaton.action.Action_a;
import Automaton.action.IAction_a;
import Automaton.condtion.ICondition_a;
import Model.Map.Map;
import Model.Moveable.Moveable;

public class Aef {
	String name;
	ArrayList<Transition_a> transitions;
	String initials;

	public Aef() {

		transitions = new ArrayList<Transition_a>();
	}

	public Aef(String name) {
		this.name = name;

		transitions = new ArrayList<Transition_a>();
	}

	public void name(String name) {
		this.name = name;
	}

	public void add_transition(String source, String target, ICondition_a c, LinkedList<IAction_a> a) {
		Iterator<Transition_a> t = transitions.iterator();
		int index = 0;
		int oujesuis = 0;
		boolean trouver = false;
		while (t.hasNext()) {
			if (t.next().source.equals(source) && trouver == false) {
				oujesuis = index;
				index = 0;
				trouver = true;
			}
			index++;
		}
		transitions.add(oujesuis + index, new Transition_a(source, target, c, a));
	}

	Action_a action_a_choisir(LinkedList<IAction_a> a) {
		Random random = new Random();
		int choisit = random.nextInt(100);
		int sum = 0;
		Action_a act;
		for (int i = 0; i < a.size(); i++) {
			act = (Action_a) a.get(i);
			sum += act.percent;
			if (choisit <= sum) {
				return act;
			}
		}
		return null;

	}

	/**
	 * @param E
	 * @throws Exception
	 */
	public void step(Moveable E, Map map) throws Exception {
		boolean transition = false;
		if (transitions.size() > 0) {
			int nombre = E.getM_automatonState();
			if (nombre < transitions.size())
			{
			String s = transitions.get(nombre).source;
			while (nombre < transitions.size() && transitions.get(nombre).source.equals(s) && !transition) {
				boolean condition = transitions.get(nombre).c.eval(E, map);
				if (condition) {
					transition = true;
					Action_a a = action_a_choisir(transitions.get(nombre).a);
					if (a != null) {
						a.apply(E, map);
					}
				}

				nombre++;
			}
			if (transition == true) {
				Iterator<Transition_a> t = transitions.iterator();
				boolean trouver = false;
				int index = -1;
				while (t.hasNext() && !trouver) {
					if (t.next().source.equals(transitions.get(nombre - 1).target)) {
						trouver = true;
					}
					index++;
				}
				if (trouver == false) {
					E.setM_automatonState(10000);
				} else {
					E.setM_automatonState(index);
				}
			}
			}
		}

	}

	public String getName() {
		return name;
	}

}
