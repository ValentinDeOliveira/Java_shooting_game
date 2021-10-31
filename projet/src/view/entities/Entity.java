package view.entities;

import java.awt.Graphics;

import Model.Map.Map;
import view.GameCamera;

public abstract class Entity {
	private String m_path;
	private Model.Entity m_entity_model;
	
	public Entity(String path,Model.Entity e) {
		this.m_entity_model=e;
		this.m_path=path;
	}
	public  abstract void paint(Graphics g, GameCamera m);
	public Model.Entity getEntityModel(){
		return m_entity_model;
	}
	public String getPath() {
		return m_path;
		
	}
	
	public abstract void tick(long elapsed,Map map) throws Exception;
		
	

}