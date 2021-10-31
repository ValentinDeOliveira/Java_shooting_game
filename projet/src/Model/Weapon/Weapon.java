package Model.Weapon;

import Model.Entity;

public abstract class Weapon extends Entity {
	// damages that the weapon made
	protected int m_damage;
	// number of max ammo inside the weapon
	protected int m_ammo;
	protected int m_max_ammo;

	public Weapon(float m_x, float m_y, int damage, int ammo) {
		super(m_x, m_y);

		
		m_damage = damage;
		m_ammo = ammo;
	}

	public Weapon() {

	}

	public void shot(float orientation) {
		// Bullet b = new Bullet(m_x, m_y, null, null);
		// b.getM_automaton().step(b);
	}

	public int getm_max_ammo() {
		return this.m_max_ammo;
	}

	public int getm_ammo() {
		return this.m_ammo;
	}

	public void setm_ammo(int m_ammo) {
		if (m_ammo == 0) {
			this.m_ammo = this.m_max_ammo;
		} else {
			this.m_ammo = m_ammo;
		}
	}

}
