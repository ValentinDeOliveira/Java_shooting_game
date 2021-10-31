package view.background;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import Model.Constants_model;
import Model.Entity;
import Model.Map.Game_model;
import Model.Map.Season;
import Model.Moveable.Dog;
import Model.Moveable.Ennemy;
import Model.Moveable.Player;
import view.Constants;

public class Background_painter {

	Image[] m_images;

	public Background_painter() {

		m_images = new Image[14];
		for (int i = 0; i < 14; i++) {
			Image img = Toolkit.getDefaultToolkit().getImage(Constants.ARRAY_PATH[i]);
			m_images[i] = img;
		}

	}

	public void paint(Graphics g, Season s, int width, int height) {
		int n = s.getCurrentSeason();
		Image i = m_images[n];
		g.drawImage(i, 0, 0, width, height, null);

	}
	public void paint (Graphics g, boolean GameOver, int width, int height) {
		if (GameOver) {
			String s="Game Over !! The player is dead !!\n";
					 
			g.setColor(Color.black);
			Font f= new Font("default",Font.ITALIC,70);
			g.setFont(f);
			FontRenderContext frc = 
		            new FontRenderContext(null, true, true);

		    Rectangle2D r2D = f.getStringBounds(s, frc);
		    int rWidth = (int) Math.round(r2D.getWidth());
		    int rHeight = (int) Math.round(r2D.getHeight());
		    int rX = (int) Math.round(r2D.getX());
		    int rY = (int) Math.round(r2D.getY());
		
			
			//g.drawString(s, width/2-, height/2);
			

		    int a = (width / 2) - (rWidth / 2) - rX;
		    int b = (height / 2) - (rHeight / 2) - rY;

		    
		    g.drawString(s,  a,  b);
		    s="you have killed "+Game_model.m_score+" ennemy";
		    r2D = f.getStringBounds(s, frc);
		     rWidth = (int) Math.round(r2D.getWidth());
		     rHeight = (int) Math.round(r2D.getHeight());
		     rX = (int) Math.round(r2D.getX());
		     rY = (int) Math.round(r2D.getY());
		
			
			
			

		     a = (width / 2) - (rWidth / 2) - rX;
		     b = (height / 2) - (rHeight / 2) -3* rY;

		    
		    g.drawString(s,  a,  b);
			
		}
		
	}
	
	
	

}