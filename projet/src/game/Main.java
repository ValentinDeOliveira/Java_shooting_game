package game;

import java.awt.EventQueue;
import java.util.LinkedList;

import Automaton.Aef;
import Automaton.game.automata.ast.AST;
import Automaton.game.automata.ast.Autbuilder;
import Automaton.game.automata.parser.AutomataParser;
import Model.Constants_model;
import Model.automatons.AutomatonsModel;

public class Main {

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Constants_model constants = new Constants_model();
					AST ast = AutomataParser.from_file(constants.PATH_TO_AUTOMATON);
					Autbuilder B = new Autbuilder();
					LinkedList<Aef> aef_list = ((LinkedList<Aef>) ast.accept(B));
					for (int i = 0; i < aef_list.size(); i++) {
						AutomatonsModel.m_automatons.put(aef_list.get(i).getName(), aef_list.get(i));
					}

					Menu frame = new Menu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
