package Model.Weapon;

import Automaton.Category_a;
import Automaton.Direction_a;
import Model.Entity;
import Model.Map.Map;
import Model.Moveable.Moveable;

public class Pistol extends Weapon {

	public Pistol(int m_x, int m_y, int damage, int ammo) {
		super(m_x, m_y, damage, ammo);
	}
	public Pistol() {
		this.m_max_ammo=10;
		this.m_ammo=5;
		
	}
	@Override
	public void tick(long elapsed, Map map) throws Exception {
	
		
	}
	@Override
	public boolean Closest(Map map , Direction_a dir, Category_a cat) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean Cell(Map map, Direction_a dir, Category_a cat) {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void Wizz(Moveable e , Map map) {
	}
	   
	@Override
	public void Explode(Map map) {
		// TODO Auto-generated method stub
		
	}
}
