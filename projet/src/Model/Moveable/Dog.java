package Model.Moveable;

import java.util.Iterator;
import java.util.LinkedList;

import Automaton.Category_a;
import Automaton.Direction_a;
import Model.Entity;
import Model.Map.Map;
import Model.automatons.AutomatonFactory;

public class Dog extends Moveable {
	private boolean m_isPlayerCalledBack;
	private long m_moveElapsed;

	public Dog(float m_x, float m_y, int vision, double scale, int lifePoints) {
		super(m_x, m_y, vision, scale, lifePoints);

		m_automaton = AutomatonFactory.getEntityType("Dog").getAutomaton();
		m_isPlayerCalledBack = false;
	}

	public boolean getIsPlayerCalledBack() {
		return m_isPlayerCalledBack;
	}

	@Override
	public void updateOrientation(int orientation) {
		switch (orientation) {
		case view.Constants.MOVE_DOWN:
			this.m_orientation = view.Constants.MOVE_DOWN_DOG;
			break;
		case view.Constants.MOVE_LEFT:
			this.m_orientation = view.Constants.MOVE_LEFT_DOG;
			break;
		case view.Constants.MOVE_RIGHT:
			this.m_orientation = view.Constants.MOVE_RIGHT_DOG;
			break;
		case view.Constants.MOVE_UP:
			this.m_orientation = view.Constants.MOVE_UP_DOG;
			break;
		}

	}

	@Override
	public void updateOrientation1(int orientantion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tick(long elapsed, Map map) throws Exception {
		m_moveElapsed += elapsed;
		if (m_moveElapsed > 100) {
			m_moveElapsed = 0;
			this.getM_automaton().step((Moveable) this, map);
			map.checkIfCurrentChunkChanged();
		}

	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean Closest(Map map, Direction_a dir, Category_a cat) {
		int rayon = this.getVision();
		Direction_a direction_cherché = null;

		if (cat == Category_a.A) {
			Iterator<Entity> I1 = map.getEntities().iterator();
			LinkedList<Entity> List = new LinkedList<Entity>();
			while (I1.hasNext()) {
				Entity B = I1.next();
				if (B instanceof Ennemy && ((Ennemy) B).getDetected()==false) {
					if (Math.sqrt(Math.pow(B.getX() - this.getX(), 2) + Math.pow(B.getY() - this.getY(), 2)) < rayon
							&& B != this) {
						List.add(B);
					}
				}
			}
			Iterator<Entity> I2 = List.iterator();
			Entity min = null;
			if (I2.hasNext()) {
				min = I2.next();
			}
			while (I2.hasNext()) {
				Entity B = I2.next();

				if (Math.sqrt(Math.pow(B.getX() - this.getX(), 2) + Math.pow(B.getY() - this.getY(), 2)) < Math
						.sqrt(Math.pow(min.getX() - this.getX(), 2) + Math.pow(min.getY() - this.getY(), 2))) {
					min = B;
				}
			}
			if (min == null) {
				return false;
			}
			float oppose = min.getY() - this.getY();
			float adjacent = min.getX() - this.getX();
			float hypotenuse = (float) Math.sqrt(Math.pow(oppose, 2) + Math.pow(adjacent, 2));
			float sinus = oppose / hypotenuse;
			float cosinus = adjacent / hypotenuse;
			float racine_deux_sur_deux = (float) (Math.sqrt(2) / 2);
 			if ((sinus > racine_deux_sur_deux)) {
				direction_cherché = Direction_a.S;
			}
			if ((cosinus > racine_deux_sur_deux)) {
				direction_cherché = Direction_a.E;
			}
			if ((sinus < -racine_deux_sur_deux)) {
				direction_cherché = Direction_a.N;

			}
			if ((cosinus < -racine_deux_sur_deux)) {
				direction_cherché = Direction_a.W;
			}


			if (direction_cherché == dir) {
				return true;
			} else {
				return false;
			}
		}
		if (cat == Category_a.T) {
			if (Math.sqrt(Math.pow(Player.getInstance().getX() - this.getX(), 2)
					+ Math.pow(Player.getInstance().getY() - this.getY(), 2)) < rayon) {

				float oppose = Player.getInstance().getY() - this.getY();
				float adjacent = Player.getInstance().getX() - this.getX();
				float hypotenuse = (float) Math.sqrt(Math.pow(oppose, 2) + Math.pow(adjacent, 2));
				float sinus = oppose / hypotenuse;
				float cosinus = adjacent / hypotenuse;
				float racine_deux_sur_deux = (float) (Math.sqrt(2) / 2);
				if ((sinus > racine_deux_sur_deux)
						&& (cosinus < racine_deux_sur_deux && cosinus > -racine_deux_sur_deux)) {
					direction_cherché = Direction_a.S;
				}
				if ((cosinus > racine_deux_sur_deux)
						&& (sinus < racine_deux_sur_deux && sinus > -racine_deux_sur_deux)) {
					direction_cherché = Direction_a.E;
				}
				if ((sinus < -racine_deux_sur_deux)
						&& (cosinus > -racine_deux_sur_deux && cosinus < racine_deux_sur_deux)) {
					direction_cherché = Direction_a.N;

				}
				if ((cosinus < -racine_deux_sur_deux)
						&& (sinus < racine_deux_sur_deux && sinus > -racine_deux_sur_deux)) {
					direction_cherché = Direction_a.W;
				}

				if (direction_cherché == dir) {
					return true;
				} else {
					return false;
				}

			}

		}
		return false;
	}

	@Override
	public boolean Cell(Map map, Direction_a dir, Category_a cat) {
		   Direction_a direction_cherché = null;
			Iterator<Entity> I1 = map.getEntities().iterator();
			LinkedList<Entity> List = new LinkedList<Entity>();
			while (I1.hasNext()) {
				Entity B = I1.next();
				if (B instanceof Ennemy && ((Ennemy) B).getDetected()==false) {
					if (Math.sqrt(Math.pow(B.getX() - this.getX(), 2) + Math.pow(B.getY() - this.getY(), 2)) < 30
							&& B != this) {
						List.add(B);
					}
				}
			}
			Iterator<Entity> I2 = List.iterator();
			Entity min = null;
			if (I2.hasNext()) {
				min = I2.next();
			}
			while (I2.hasNext()) {
				Entity B = I2.next();

				if (Math.sqrt(Math.pow(B.getX() - this.getX(), 2) + Math.pow(B.getY() - this.getY(), 2)) < Math
						.sqrt(Math.pow(min.getX() - this.getX(), 2) + Math.pow(min.getY() - this.getY(), 2))) {
					min = B;
				}
			}
			if (min == null) {
				return false;
			}
			float oppose = min.getY() - this.getY();
			float adjacent = min.getX() - this.getX();
			float hypotenuse = (float) Math.sqrt(Math.pow(oppose, 2) + Math.pow(adjacent, 2));
			float sinus = oppose / hypotenuse;
			float cosinus = adjacent / hypotenuse;
			float racine_deux_sur_deux = (float) (Math.sqrt(2) / 2);
 			if ((sinus > racine_deux_sur_deux)) {
				direction_cherché = Direction_a.S;
			}
			if ((cosinus > racine_deux_sur_deux)) {
				direction_cherché = Direction_a.E;
			}
			if ((sinus < -racine_deux_sur_deux)) {
				direction_cherché = Direction_a.N;

			}
			if ((cosinus < -racine_deux_sur_deux)) {
				direction_cherché = Direction_a.W;
			}


			if (direction_cherché == dir) {
				return true;
			} else {
				return false;
			}
	}

	@Override
	public boolean GotPower_a() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean MyDir_a(Direction_a dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Hit(Entity E, Map map) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Turn(Direction_a dir) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Pop(Moveable e, Map map) {
		Iterator<Entity> I1 = map.getEntities().iterator();
		while (I1.hasNext()) {
			Entity B = I1.next();
			if (B instanceof Ennemy && ((Ennemy) B).getDetected()==false) {
				if (Math.sqrt(Math.pow(B.getX() - this.getX(), 2) + Math.pow(B.getY() - this.getY(), 2)) < 30
						&& B != this) {
					((Ennemy)B).setDetected(true);
				}
			}
		}
	
	}

	@Override
	public void Wizz(Moveable e, Map map) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Explode(Map map) {
		// TODO Auto-generated method stub

	}

}
