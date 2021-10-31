package view;

import Model.Moveable.Player;
import graphics.info3.game.graphics.GameCanvas;

public class GameCamera {
	private float xOffsset,YOffset;
	private GameCanvas m_canvas;
	
	public GameCamera(GameCanvas c,float xOff,float yOff) {
		this.xOffsset=xOff;
		this.YOffset=yOff;
		this.m_canvas=c;
	}
	
	public void move(float x, float y) {
		this.xOffsset+=x;
		this.YOffset+=y;
	}

	public float getxOffsset() {
		return xOffsset;
	}

	public void setxOffsset(float xOffsset) {
		this.xOffsset = xOffsset;
	}

	public float getYOffset() {
		return YOffset;
	}

	public void setYOffset(float yOffset) {
		YOffset = yOffset;
	}
	public void CenterCamera() {
		Player p = Player.getInstance();
		this.xOffsset=p.getX()-m_canvas.getWidth()/2;
		this.YOffset=p.getY()-m_canvas.getHeight()/2;
		
	}
	

}
