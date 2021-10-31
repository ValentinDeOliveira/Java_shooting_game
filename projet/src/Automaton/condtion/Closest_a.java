package Automaton.condtion;

import java.util.Iterator;
import java.util.LinkedList;

import Automaton.Category_a;
import Automaton.Direction_a;
import Model.Entity;
import Model.Map.Map;
import Model.Moveable.Ennemy;
import Model.Moveable.Moveable;
import Model.Moveable.Player;
import info3.game.CanvasListener;

public class Closest_a implements ICondition_a {
	Direction_a dir;
	Category_a cat;
	
	public Closest_a(Direction_a dir, Category_a cat) {
		super();
		this.dir = dir;
		this.cat = cat;
	}

	@Override
	public boolean eval(Moveable e, Map map) {
		return e.Closest(map , dir, cat);
	
	}


}
