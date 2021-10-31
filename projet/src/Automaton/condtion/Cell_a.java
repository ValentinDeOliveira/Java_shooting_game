package Automaton.condtion;

import java.util.Iterator;

import Automaton.Category_a;
import Automaton.Direction_a;
import Model.Entity;
import Model.Map.Map;
import Model.Moveable.Moveable;
import Model.Moveable.Player;

public class Cell_a implements ICondition_a {

	Direction_a dir;
	Category_a cat;
	
	public Cell_a(Direction_a dir, Category_a cat) {
		super();
		this.dir = dir;
		this.cat = cat;
	}

	@Override
	public boolean eval(Moveable e, Map map) {
		return e.Cell(map, dir, cat);

	}



}
