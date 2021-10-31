package Model.automatons;

import Automaton.Aef;

public class AutomatonTypes {
	private Aef m_automaton;

	public AutomatonTypes(Aef automaton) {
		m_automaton = automaton;
	}

	public Aef getAutomaton() {
		return m_automaton;
	}
}
