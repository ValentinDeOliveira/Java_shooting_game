package Automaton.action;

import Automaton.Direction_a;
import Model.Map.Map;
import Model.Moveable.Moveable;

public class Move_a extends Action_a {
	Direction_a dir;

	public Move_a(int percent) {
		this.percent = percent;
	}

	public Move_a(Direction_a dir, int percent) {
		this.dir = dir;
		this.percent = percent;
	}

	public Direction_a getDir() {
		return dir;
	}

	public int getPercent() {
		return percent;
	}

	@Override
	public void apply(Moveable e, Map map) {
		if (dir == null) {
			e.move();
		} else {
			switch (dir) {
			case N:
				e.moveUp();
				break;
			case S:
				e.moveDown();
				break;
			case W:
				e.moveLeft();
				break;
			case E:
				e.moveRight();
				break;

			default:
				break;

			}
		}
	}

}
