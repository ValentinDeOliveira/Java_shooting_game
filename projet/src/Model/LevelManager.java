package Model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import Model.Map.Game_model;
import Model.Moveable.Ennemy;

public class LevelManager {
	private int m_Level;
	private int m_Ennemy_damage;
	private int m_Ennmey_life_points;
	private int m_Ennemy_density;

	public LevelManager() {
		m_Level = 1;
		m_Ennmey_life_points = 1;
		m_Ennemy_damage=1;
		m_Ennemy_density=1;

	}

	public void incLevel() {
		this.m_Level = this.m_Level + 1;
		
		update();
	}

	public void update() {
		m_Ennmey_life_points ++;
		m_Ennemy_damage++;
		m_Ennemy_density++;

	}

	public int getM_Ennemy_damage() {
		return m_Ennemy_damage;
	}

	public int getM_Ennmey_life_points() {
		return m_Ennmey_life_points;
	}

	public int getM_Ennemy_density() {
		return m_Ennemy_density;
	}
	
	public void paintLevel(Graphics g,int width,int height) {
		String s=" Level : "+m_Level;
		
		Font f= new Font("default",Font.ITALIC,30);
		g.setFont(f);
		FontRenderContext frc = 
	            new FontRenderContext(null, true, true);

	    Rectangle2D r2D = f.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rHeight = (int) Math.round(r2D.getHeight());
	    int rX = (int) Math.round(r2D.getX());
	    int rY = (int) Math.round(r2D.getY());
	    g.setColor(Color.BLUE);
	    g.fillRect(width-2*rWidth, 10, rWidth, rHeight);
	    g.setColor(Color.black);
		g.drawString(s,width-2*rWidth,35);
		
		int score=Game_model.m_score;
		s="Score : "+score;
		 r2D = f.getStringBounds(s, frc);
	    rWidth = (int) Math.round(r2D.getWidth());
	     rHeight = (int) Math.round(r2D.getHeight());
	     rX = (int) Math.round(r2D.getX());
	     rY = (int) Math.round(r2D.getY());
	    g.setColor(Color.BLUE);
	    g.fillRect(width-2*rWidth, 55, rWidth, rHeight);
	    g.setColor(Color.black);
		g.drawString(s,width-2*rWidth,80);
		
		
		
	}

}
