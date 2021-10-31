package game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import Automaton.Aef;
import Model.automatons.AutomatonsModel;

public class OptionsModel {
	private HashMap<String, Aef> m_baseAutomatons;

	public OptionsModel() {
		this.m_baseAutomatons = (HashMap<String, Aef>) AutomatonsModel.m_automatons.clone();
	}

	public String[] getNames() {
		String[] res = new String[m_baseAutomatons.size()];
		int index = 0;

		for (String name : m_baseAutomatons.keySet()) {
			res[index++] = name;
		}

		return res;
	}

	public LinkedList<Aef> setLinkedListWithIndex(LinkedList<Integer> index) {
		LinkedList<Aef> newAef = new LinkedList<Aef>();

		for (int i = 0; i < index.size(); i++) {
			newAef.add(i, m_baseAutomatons.get(index.get(i)));
		}

		return newAef;
	}

	public void setAutomatons(LinkedList<String> indexs) {
		HashMap<String, Aef> tmp = AutomatonsModel.m_automatons;
		int index = 0;

		for (Map.Entry<String, Aef> set : tmp.entrySet()) {
			String key = set.getKey();
			Aef aef = m_baseAutomatons.get(indexs.get(index));
			AutomatonsModel.m_automatons.replace(key, aef);
			index++;
		}

		

	}
}
