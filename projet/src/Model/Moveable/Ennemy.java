package Model.Moveable;

import java.util.Iterator;
import java.util.LinkedList;

import Automaton.Category_a;
import Automaton.Direction_a;
import Model.Constants_model;
import Model.Entity;
import Model.Map.Chunk;
import Model.Map.Game_model;
import Model.Map.Map;
import Model.Weapon.Bullet;
import Model.Weapon.Pistol;
import Model.Weapon.Weapon;
import Model.automatons.AutomatonFactory;

public class Ennemy extends Moveable {
	// if the ennemy is detected by the dog
	private boolean m_detected;
	// damage that the ennemy
	private double m_damagePercentage;
	// weapon held by the ennemy
	private Weapon m_weapon;
	private long m_moveElapsed;
	private Chunk m_chunk;
	private boolean m_fire;

	/**
	 * Create an ennemy
	 * 
	 * @param m_x                x position
	 * @param m_y                y position
	 * @param m_automaton        automaton of the Moveable entity
	 * @param path               path to the sprite
	 * @param nrows              number of rows in the sprite
	 * @param ncols              number of columns in the sprite
	 * @param m_damagePercentage give a percentage of damage that ennemy deal when
	 *                           they shoot
	 * @param m_weapon           weapon held by the ennemy
	 */
	public Ennemy(float m_x, float m_y, int vision, double m_damagePercentage, int lifePoints) {

		super(m_x, m_y, vision, Constants_model.ENNEMY_SCALE, lifePoints);
		this.m_damagePercentage = m_damagePercentage;
		this.m_weapon = new Pistol();
		m_automaton = AutomatonFactory.getEntityType("Ennemy").getAutomaton();
		updateOrientation(5);

	}

	@Override
	public void updateOrientation(int orientation) {
		switch (orientation) {
		case view.Constants.MOVE_UP:
			this.m_orientation = view.Constants.MOVE_UP_ENNEMY;
			break;
		case view.Constants.MOVE_LEFT:
			this.m_orientation = view.Constants.MOVE_LEFT_ENNEMY;
			break;

		case view.Constants.MOVE_DOWN:
			this.m_orientation = view.Constants.MOVE_DOWN_ENNEMY;
			break;
		case view.Constants.MOVE_RIGHT:
			this.m_orientation = view.Constants.MOVE_RIGHT_ENNEMY;
			break;

		}

		
	}

	/**
	 * Compute the m_isPlayerCalledBackdistance between the current ennemy to the
	 * player (used for moving the ennemy to the player
	 * 
	 * @return distance between the ennemy and the player
	 */
	public double getDistanceFromPlayer() {
		return Math
				.sqrt(Math.pow(Player.getInstance().getX() - m_x, 2) + Math.pow(Player.getInstance().getY() - m_y, 2));
	}

	public boolean getDetected() {
		return m_detected;
	}

	public void setDetected(boolean m_detected) {
		this.m_detected = m_detected;
	}

	public Chunk getM_chunk() {
		return m_chunk;
	}

	public void setM_chunk(Chunk m_chunk) {
		this.m_chunk = m_chunk;
	}

	public boolean isM_fire() {

		return m_fire;
	}

	public void setM_fire(boolean m_fire) {
		this.m_fire = m_fire;
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

		if (cat == Category_a.T) {
			Iterator<Entity> I1 = map.getEntities().iterator();
			LinkedList<Entity> List = new LinkedList<Entity>();
			while (I1.hasNext()) {
				Entity B = I1.next();
				if (B instanceof Ennemy) {
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
			if ((sinus > racine_deux_sur_deux) && (cosinus < racine_deux_sur_deux && cosinus > -racine_deux_sur_deux)) {
				direction_cherché = Direction_a.N;
			}
			if ((cosinus > racine_deux_sur_deux) && (sinus < racine_deux_sur_deux && sinus > -racine_deux_sur_deux)) {
				direction_cherché = Direction_a.W;
			}
			if ((sinus < -racine_deux_sur_deux)
					&& (cosinus > -racine_deux_sur_deux && cosinus < racine_deux_sur_deux)) {
				direction_cherché = Direction_a.S;

			}
			if ((cosinus < -racine_deux_sur_deux) && (sinus < racine_deux_sur_deux && sinus > -racine_deux_sur_deux)) {
				direction_cherché = Direction_a.E;
			}

			if (direction_cherché == dir) {
				return true;
			} else {
				return false;
			}
		}
		if (cat == Category_a.A) {
			if (Math.sqrt(Math.pow(Player.getInstance().getX() - this.getX(), 2)
					+ Math.pow(Player.getInstance().getY() - this.getY(), 2)) < 700) {

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
		boolean retourne = false;
		float x = m_x;
		float y = m_y;
		Iterator<Entity> I = map.getEntities().iterator();
		Entity E = null;
		if (dir == Direction_a.F) {
			if (cat == Category_a.V) {
				retourne = false;
			} else {
				retourne = true;
			}

			while (I.hasNext()) {
				E = I.next();
				if (E instanceof Ennemy) {
					float distance = (float) Math.sqrt(Math.pow(x - E.getX(), 2) + Math.pow(y - E.getY(), 2));
					if (distance <= 100) {
						return retourne;
					}
				}
			}
		}
		if (dir == Direction_a.N) {
			y -= m_speed;
		}

		if (dir == Direction_a.S) {
			y += m_speed;
		}
		if (dir == Direction_a.E) {
			x += m_speed;
		}
		if (dir == Direction_a.W) {
			x -= m_speed;
		}

		if (cat == Category_a.V) {
			retourne = false;
		} else {
			retourne = true;
		}
		while (I.hasNext()) {
			E = I.next();
			if (E instanceof Ennemy && E != this) {
				float distance = (float) Math.sqrt(Math.pow(x - E.getX(), 2) + Math.pow(y - E.getY(), 2));
				if (distance <= 50) {
					return retourne;
				}
			}
		}
		return !retourne;
	}

	@Override
	public boolean GotPower_a() {
		if (m_lifePoints <= 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean MyDir_a(Direction_a dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Hit(Entity E, Map map) {
		if (!m_fire) {
			Bullet B = new Bullet(this.getX(), this.getY(), 2, this);
			m_fire = true;
			float oppose = Player.getInstance().getY() - E.getY();
			float adjacent = Player.getInstance().getX() - E.getX();
			float hypotenuse = (float) Math.sqrt(Math.pow(oppose, 2) + Math.pow(adjacent, 2));
			float sinus = oppose / hypotenuse;
			float cosinus = adjacent / hypotenuse;
			B.setM_avancerX(cosinus);
			B.setM_avancerY(sinus);
			map.getEntities().add(B);
			map.setDirtyFlag(true);
		}

	}

	@Override
	public void Turn(Direction_a dir) {
		switch (dir) {
		case N:
			updateOrientation(view.Constants.MOVE_UP);
			break;
		case S:
			updateOrientation(view.Constants.MOVE_DOWN);
			break;
		case E:
			updateOrientation(view.Constants.MOVE_RIGHT);
			break;
		case W:
			updateOrientation(view.Constants.MOVE_LEFT);
			break;
		default:
		}

	}

	@Override
	public void Pop(Moveable e, Map map) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Wizz(Moveable e, Map map) {
		map.setDirtyFlag(true);
		((Ennemy) e).getM_chunk().setEntity(null);

		map.getEntities().remove(this);
		Game_model.incScore();

	}

	@Override
	public void Explode(Map map) {

	}

}