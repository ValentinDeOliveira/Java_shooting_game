package Model.Weapon;

import java.util.Iterator;

import Automaton.Category_a;
import Automaton.Direction_a;
import Model.Constants_model;
import Model.Entity;
import Model.Map.Map;
import Model.Moveable.Dog;
import Model.Moveable.Ennemy;
import Model.Moveable.Moveable;
import Model.Moveable.Player;
import Model.automatons.AutomatonFactory;

public class Bullet extends Moveable {

	private long m_lifeDuration;
	private long m_moveElapsed;
	private int m_user;
	Entity m_userFire;

	public Bullet(float m_x, float m_y, int m_user, Entity E) {
		super(m_x, m_y, 2, Constants_model.BULLET_SCALE, 1);
		this.m_user = m_user;
		m_automaton = AutomatonFactory.getEntityType("Bullet").getAutomaton();
		m_userFire = E;
	}

	@Override
	public void updateOrientation1(int orientantion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateOrientation(int orientation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tick(long elapsed, Map map) throws Exception {
		m_moveElapsed += elapsed;
		m_lifeDuration += elapsed;
		int speeed_ball = 0;
		int limite_life = 0;
		if (m_user == 1) {
			speeed_ball = 100;
			limite_life = 2000;
			
		}
		if (m_user == 2) {
			speeed_ball = 200;
			limite_life = 2000;
		}
		if (m_moveElapsed > speeed_ball) {
			m_moveElapsed = 0;
			this.getM_automaton().step((Moveable) this, map);
			map.checkIfCurrentChunkChanged();
		}
		if (m_lifeDuration > limite_life) {
			map.getEntities().remove(this);
			if (m_userFire instanceof Player) {
				((Player) m_userFire).setM_fire(false);
			} 
			if (m_userFire instanceof Ennemy) {
				((Ennemy)m_userFire).setM_fire(false);
			}
			map.setDirtyFlag(true);
		}

	}

	@Override
	public void move() {
		m_x += 50 * m_avancerX;
		m_y += 50 * m_avancerY;

	}

	@Override
	public boolean Closest(Map map, Direction_a dir, Category_a cat) {
		return false;
	}

	@Override
	public boolean Cell(Map map, Direction_a dir, Category_a cat) {

		if (m_user == 1) {
			boolean retourne = false;
			if (dir == Direction_a.F) {
				if (cat == Category_a.V) {
					retourne = false;
				} else {
					retourne = true;
				}
				float x = m_x;
				float y = m_y;
				Iterator<Entity> I = map.getEntities().iterator();
				Entity E = null;
				while (I.hasNext()) {
					E = I.next();
					if (E instanceof Ennemy) {
						float distance = (float) Math.sqrt(Math.pow(x - E.getX(), 2) + Math.pow(y - E.getY(), 2));
						if (distance <= 100) {
							return retourne;
						}
					}
				}
				return !retourne;
			}
		}
		if (m_user == 2) {
			boolean retourne = false;
			if (dir == Direction_a.F) {
				if (cat == Category_a.V) {
					retourne = false;
				} else {
					retourne = true;
				}
				float x = m_x;
				float y = m_y;
				float distance = (float) Math.sqrt(
						Math.pow(x - Player.getInstance().getX(), 2) + Math.pow(y - Player.getInstance().getY(), 2));
				if (distance <= 100) {
					return retourne;
				}

				return !retourne;
			}
		}

		return false;
	}

	@Override
	public boolean GotPower_a() {
		return false;
	}

	@Override
	public boolean MyDir_a(Direction_a dir) {
		return false;
	}

	@Override
	public void Hit(Entity E, Map map) {

	}

	@Override
	public void Turn(Direction_a dir) {

	}

	@Override
	public void Pop(Moveable e, Map map) {

	}

	@Override
	public void Wizz(Moveable e , Map map) {

	}

	@Override
	public void Explode(Map map) {
		if (m_user == 1) {
			float x = m_x;
			float y = m_y;
			Iterator<Entity> I = map.getEntities().iterator();
			Entity E = null;
			while (I.hasNext()) {
				E = I.next();
				if (E instanceof Ennemy) {
					float distance = (float) Math.sqrt(Math.pow(x - E.getX(), 2) + Math.pow(y - E.getY(), 2));
					if (distance <= 100) {
						((Moveable) E).setM_lifePoints(((Moveable) E).getLifePoints() - 1);
					}
				}
			}
			map.setDirtyFlag(true);
			((Player) m_userFire).setM_fire(false);
			map.getEntities().remove(this);
		}
		if (m_user == 2) {
			Player.getInstance().setM_lifePoints(Player.getInstance().getLifePoints() - 1);
			map.setDirtyFlag(true);
			map.getEntities().remove(this);
		}
	}

}
