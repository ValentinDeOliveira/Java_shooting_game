package Model.Moveable;

import Automaton.Category_a;
import Automaton.Direction_a;
import Model.Constants_model;
import Model.Entity;
import Model.Map.Map;
import Model.Weapon.Bullet;
import Model.Weapon.Pistol;
import Model.Weapon.Weapon;
import Model.automatons.AutomatonFactory;
import Model.automatons.AutomatonsModel;
import info3.game.CanvasListener;

public class Player extends Moveable {
	// instance of the player (used for example for knowing where the player is on
	// the map) => Singleton pattern
	static Player m_player = null;
	// weapon held by the player
	private Weapon m_weapon;
	// we can only have a single instance of the dog
	private Dog m_dog;
	private boolean m_shoot;
	private long m_moveElapsed;
	private boolean m_fire;

	/**
	 * Create the player
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
	private Player(float m_x, float m_y, int vision, int lifePoints) {
		super(m_x, m_y, vision, Constants_model.PLAYER_SCALE, lifePoints);
		if (this.m_automaton == null) {
			this.m_automaton = AutomatonsModel.m_automatons.get("Player");
		}
		this.m_weapon = new Pistol();
		m_fire = false;
		this.m_dog = new Dog(m_constants.SCREEN_WIDTH / 2 + m_constants.HUNDRED_PX_OFFSET,
				m_constants.SCREEN_HEIGHT / 2, 3000, Constants_model.DOG_SCALE, m_constants.DOG_HP);
		this.m_lifePoints=lifePoints;

		m_automaton = AutomatonFactory.getEntityType("Player").getAutomaton();
	}

	/**
	 * Get the instance of the player
	 * 
	 * @return a new player if player was not already created, else, the instance of
	 *         the player
	 */
	public static Player getInstance() {
		if (m_player == null) {
			Constants_model c = new Constants_model();
			m_player = new Player(c.SCREEN_WIDTH / 2, c.SCREEN_HEIGHT / 2,700, c.PLAYER_HP);
		}

		return m_player;
	}

	public Dog getDog() {
		return m_dog;
	}

	public boolean getShoot() {
		return m_shoot;
	}

	public void setShoot() {
		if (!m_shoot) {
			m_shoot = true;
		}
	}

	public void resetShoot() {
		if (m_shoot) {
			m_shoot = false;
		}
	}

	
	public boolean isM_shoot() {
		return m_shoot;
	}

	public void setM_shoot(boolean m_shoot) {
		this.m_shoot = m_shoot;
	}

	@Override
	public void updateOrientation(int orientation) {
		switch (orientation) {
		case view.Constants.MOVE_UP:
			this.m_orientation = 270;
			break;
		case view.Constants.MOVE_LEFT:
			this.m_orientation = 180;
			break;

		case view.Constants.MOVE_DOWN:
			this.m_orientation = 90;
			break;
		case view.Constants.MOVE_RIGHT:
			this.m_orientation = 0;
			break;

		}

	}

	public void updateOrientation(int x, int y) {
		int x1 = (int) Player.getInstance().getX();
		int y1 = (int) Player.getInstance().getY();
		int x2 = x;
		int y2 = y;
		float slope = Math.abs((float) (y2 - y1) / (float) (x2 - x1));
		float angel = (int) Math.toDegrees(Math.atan((slope)));
		
		if (x2 > x1 && y2 < y1) {
			angel = 360 - angel;

		} else {
			if (x2 <= x1 && y2 >= y1) {
				angel = 180 - angel;

			} else {
				if (x2 < x1 && y2 < y1) {
					angel = 180 + angel;

				}
			}
		}

		this.m_orientation = angel;

		
	}

	public Weapon getWeapon() {
		return this.m_weapon;
	}

	@Override
	public void updateOrientation1(int orientantion) {
		// TODO Auto-generated method stub

	}

	
	public boolean isM_fire() {
		return m_fire;
	}

	public void setM_fire(boolean m_fire) {
		this.m_fire = m_fire;
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
	public boolean Closest(Map map , Direction_a dir, Category_a cat) {
		return false;
	}

	@Override
	public boolean Cell(Map map, Direction_a dir, Category_a cat) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean GotPower_a() {
		if (m_lifePoints == 0) {
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
	public void Hit(Entity E,Map map) {
		if (!m_fire)
		{
		Bullet B = new Bullet(this.getX(), this.getY(),1,E);
		m_fire = true;
		float oppose = CanvasListener.m_y  - Player.getInstance().getY();
		float adjacent = CanvasListener.m_x - Player.getInstance().getX();
		float hypotenuse = (float) Math.sqrt(Math.pow(oppose, 2) + Math.pow(adjacent, 2));
		float sinus = oppose / hypotenuse;
		float cosinus = adjacent / hypotenuse;
		B.setM_avancerX(cosinus);
		B.setM_avancerY(sinus);
		map.getEntities().add(B);
		this.getWeapon().setm_ammo(this.getWeapon().getm_ammo()-1);	
		map.setDirtyFlag(true);
		}
		
	}

	@Override
	public void Turn(Direction_a dir) {
		switch (dir){
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
		m_dog.setM_x(Player.getInstance().getX());
		m_dog.setM_y(Player.getInstance().getY());
		m_dog.setM_automatonState(0);
		map.setDirtyFlag(true);
	}

	@Override
	public void Wizz(Moveable e , Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Explode(Map map) {
		map.getEntities().clear();
		
	}
	
}
