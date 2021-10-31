package Model.Moveable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import view.GameCamera;

public class Hitbox {
	private Ellipse2D m_hitbox;

	private Rectangle2D m_test;
	
	private double m_scale;

	

	private float m_x;

	private float m_y;

	private float m_radius;
	private int m_width;
	private int m_height;

	public Hitbox(float x, float y, float radius, double scale,int width,int height) {

		m_scale = scale;
		m_width=width;
		m_height=height;

		m_x = x;
		m_y = y;

		m_radius = radius;

		m_hitbox = new Ellipse2D.Float((int) (x), (int) (y), (float) (radius * scale), (float) (radius * scale));

		m_test = new Rectangle2D.Float((int) x, (int) y, (int)(m_width * scale),
				(float) (m_height * scale));
		
	}

	public void paint(Graphics g,GameCamera m) {
		g.setColor(Color.pink);
		int spriteWidth = (int) (m_width * m_scale);
		int spriteHeight = (int) (m_height * m_scale);

		if (spriteWidth > spriteHeight) {
			g.fillOval((int) (m_hitbox.getX()- (m.getxOffsset())), (int) (m_hitbox.getY()-m.getYOffset()), (int) (m_hitbox.getWidth()),
					(int) (m_hitbox.getHeight()));
		} else {
			g.fillOval((int) (m_hitbox.getX()- (m.getxOffsset())),
					(int) (m_hitbox.getY()-m.getYOffset() + ((spriteHeight) / 2) - (m_radius / (2 * Math.PI))),
					(int) (m_hitbox.getWidth()), (int) (m_hitbox.getHeight()));
		}
	}

	public boolean isInHitbox(float x, float y) {
		return m_hitbox.contains(x, y);
	}

	public Ellipse2D getHitbox() {
		return m_hitbox;
	}

	public void setHitboxFrame(int x, int y, int width, int height) {
		m_hitbox.setFrame(x, y, width, height);
	}

}
