package Model;

import Automaton.Aef;
import Automaton.Category_a;
import Automaton.Direction_a;
import Model.Map.Map;
import Model.Moveable.Moveable;
import info3.game.CanvasListener;

public abstract class Entity {
	// coordinates x of the entity
	protected float m_x;
	// coordinates y of the entity
	protected float m_y;
	// Automaton of the entity
	protected Aef m_automaton;
	// Orientation of the entity
	protected float m_orientation;

	// Get access to constants of the game
	protected Constants_model m_constants;

	

	protected int m_width;
	protected int m_height;
	private long m_moveElapsed;
	
	private int m_automatonState;

	/**
	 * Create an entity
	 * 
	 * @param m_x         x position of the entity
	 * @param m_y         y position of the entity
	 * @param m_automaton automaton of the entity
	 * @param path        path of the spritesheet
	 * @param nrows       number of rows in the spritesheet
	 * @param ncols       number of columns in the spritesheet
	 */
	public Entity(float m_x, float m_y) {
		this.m_x = m_x;
		this.m_y = m_y;
		
		m_constants = new Constants_model();
		try {}
		catch (Exception ex) {
			System.out.println("automate non instancie");
		}
		
		m_orientation = 0.0F;
		m_automatonState = 0;
	}

	public Entity() {

	}

	public Aef getM_automaton() {
		return this.m_automaton;
	}

	public void setM_automaton(Aef m_automaton) {
		this.m_automaton = m_automaton;
	}

	
	
	public int getM_automatonState() {
		return m_automatonState;
	}

	public void setM_automatonState(int m_automatonState) {
		this.m_automatonState = m_automatonState;
	}

	public abstract void tick (long elapsed, Map map) throws Exception;
	

	public Constants_model getConstants() {
		return m_constants;
	}

	public float getOrientation() {
		return m_orientation;
	}

	public void setM_orientation(float m_orientation) {
		this.m_orientation = m_orientation;
	}

	public float getX() {
		return m_x;
	}

	public float getY() {
		return m_y;
	}
	

	public void setM_x(float m_x) {
		this.m_x = m_x;
	}

	public void setM_y(float m_y) {
		this.m_y = m_y;
	}

	public void setM_width(int m_width) {
		this.m_width = m_width;
	}

	public void setM_height(int m_height) {
		this.m_height = m_height;
	}

	
	//condition
	 public boolean True() {
		 return true;
	 }
	 
	 public boolean Key(int key) {
		 if (CanvasListener.besoin == key) {
				return true;
			}
			return false;
	 }
	 
	  public abstract boolean Closest(Map map , Direction_a dir, Category_a cat);
	  
	  public abstract boolean Cell(Map map, Direction_a dir, Category_a cat);

	  public abstract boolean GotPower_a();
	  
	  public abstract boolean MyDir_a(Direction_a dir);
	  
	  //Action
	  public abstract void Hit(Entity E, Map map);
	  
	  public abstract void Turn(Direction_a dir);
	  
	  public abstract void Pop(Moveable e, Map map);
	  
	  public abstract void Wizz(Moveable e , Map map);
	  
	  public abstract void Explode(Map map);
	  
	  
}
