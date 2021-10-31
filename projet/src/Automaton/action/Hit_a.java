package Automaton.action;

import Model.Map.Map;
import Model.Moveable.Moveable;
import Model.Moveable.Player;
import Model.Weapon.Bullet;
import info3.game.CanvasListener;

public class Hit_a extends Action_a {
	
	public Hit_a(int percent) {
		this.percent = percent;
	}
	
	
	public void apply(Moveable e, Map map) {		
		e.Hit(e,map);
		
	}
}
