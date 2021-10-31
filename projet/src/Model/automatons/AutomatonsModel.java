package Model.automatons;

import java.util.HashMap;

import Automaton.Aef;

public class AutomatonsModel {
	public static HashMap<String, Aef> m_automatons = new HashMap<String, Aef>();;

	public Aef getAutomaton(String className) {
		return m_automatons.get(className);
	}

	public void setAutomatons(HashMap<String, Aef> m_automatons) {
		AutomatonsModel.m_automatons = m_automatons;
	}

}
