package Model.Moveable;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

import Model.Entity;
import utils.Basics;
import view.GameCamera;

public abstract class Moveable extends Entity {

	protected int m_speed;
	protected int m_vision;
	protected double m_scale;
	protected Hitbox m_hitbox;
	protected int m_lifePoints;
	protected int m_maxLifePoints;
	protected float m_avancerX;
	protected float m_avancerY;
	
	/**
	 * Create a Moveable entity, add a hitbox for detecting collisions
	 * 
	 * @param m_x         x position
	 * @param m_y         y position
	 * @param m_automaton automaton of the Moveable entity
	 * @param path        path to the sprite
	 * @param nrows       number of rows in the sprite
	 * @param ncols       number of columns in the sprite
	 */
	
	public Moveable(float m_x, float m_y, int vision, double scale, int lifePoints) {
		super(m_x, m_y);
		this.m_scale = scale;
		m_lifePoints = lifePoints;

		m_maxLifePoints = lifePoints;
		this.m_vision = vision;

		m_speed = m_constants.SPEED;
		this.m_vision = vision;
		m_maxLifePoints = lifePoints;
		m_lifePoints = lifePoints;
	}

	public float getM_avancerX() {
		return m_avancerX;
	}

	public float getM_avancerY() {
		return m_avancerY;
	}

	public void setM_avancerX(float m_avancerX) {
		this.m_avancerX = m_avancerX;
	}

	public void setM_avancerY(float m_avancerY) {
		this.m_avancerY = m_avancerY;
	}

	/**
	 * Know if a given point is in the hitbox
	 * 
	 * @param x coordinates of the point to check
	 * @param y coordinates of the point to check
	 * @return true if the coordinates are in the hitbox, else false
	 */
	public boolean isInHitbox(float x, float y) {
		return m_hitbox.isInHitbox(x, y);
	}

	
	
	public abstract void move();

	/**
	 * Move the player and the hitbox down
	 */
	public void moveDown() {

		m_y += m_speed;
		updateHitbox();
		updateOrientation(view.Constants.MOVE_DOWN);
	}

	/**
	 * Move the player and the hitbox up
	 */
	public void moveUp() {

		m_y -= m_speed;
		updateHitbox();
		updateOrientation(view.Constants.MOVE_UP);

	}

	/**
	 * Move the player and the hitbox left
	 */
	public void moveLeft() {

		m_x -= m_speed;
		updateHitbox();
		updateOrientation(view.Constants.MOVE_LEFT);
	}

	/**
	 * Move the player and the hitbox right
	 */
	public void moveRight() {

		m_x += m_speed;
		updateHitbox();
		updateOrientation(view.Constants.MOVE_RIGHT);
	}

	public abstract void updateOrientation1(int orientantion);

	public Ellipse2D getHitbox() {
		return m_hitbox.getHitbox();
	}

	public Hitbox getCurrentHitbox() {
		return m_hitbox;
	}

	public int getSpeed() {
		return m_speed;
	}

	public void setSpeed(int m_speed) {
		this.m_speed = m_speed;
	}

	public int getLifePoints() {
		return this.m_lifePoints;
	}
	

	public void setM_lifePoints(int m_lifePoints) {
		this.m_lifePoints = m_lifePoints;
	}

	private void updateHitbox() {
		m_hitbox.setHitboxFrame((int) (m_x), (int) (m_y), (int) getHitbox().getWidth(), (int) getHitbox().getHeight());
	}

	public int getMaxLifePoints() {
		return this.m_maxLifePoints;
	}

	public void paintHitbox(Graphics g, GameCamera m) {
		m_hitbox.paint(g, m);
	}

	protected void setHitboxCoordinates(int newX, int newY, int width, int height) {
		m_hitbox.setHitboxFrame(newX, newY, width, height);
	}

	/**
	 * Give the center coordinates of the moveable following the center of the
	 * 
	 * hitbox
	 * 
	 * @return the center coordinates
	 */
	public int[] getCenterCoordinates() {
		int result[] = new int[2];
		if (m_hitbox == null) {
			result[0] = (int) m_x;
			result[1] = (int) m_y;
		} else {
			result[0] = (int) (m_hitbox.getHitbox().getCenterX());
			result[1] = (int) (m_hitbox.getHitbox().getCenterY());
		}

		

		return result;
	}

	public void clear() {

		m_automaton = null;

		m_hitbox = null;
	}

	public boolean isDead() {
		return m_lifePoints <= 0;
	}

	public int getVision() {
		return m_vision;
	}

	public abstract void updateOrientation(int orientation);

	public void initHitbox(int width, int height) {
		Basics basics = new Basics();

		int min = basics.min(width, height);

		m_hitbox = new Hitbox(m_x, m_y, min, m_scale, width, height);

	}
	public  boolean GotPower_a()
	{
		if (m_lifePoints == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
}
