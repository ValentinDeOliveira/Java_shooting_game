package Automaton;

import java.util.Iterator;
import java.util.LinkedList;

import Automaton.action.IAction_a;
import Automaton.condtion.ICondition_a;

public class Transition_a {
	String source;
	String target;
	ICondition_a c;
	LinkedList<IAction_a> a;

	public Transition_a(String source, String target, ICondition_a c, LinkedList<IAction_a> a) {
		this.source = source;
		this.target = target;
		this.c = c;
		this.a = a;
	}

	public String getSource() {
		return source;
	}

	public String getTarget() {
		return target;
	}

	public ICondition_a getC() {
		return c;
	}

	public LinkedList<IAction_a> getA() {
		return a;
	}

}
