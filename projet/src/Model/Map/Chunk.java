package Model.Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import Model.Constants_model.TextureTypes;
import Model.Entity;
import Model.Moveable.Ennemy;
import view.GameCamera;

public class Chunk {
	private Texture m_texture;

	private Rectangle2D m_chunk;

	private Entity m_currentEntity;

	/**
	 * Create a chunk (a small square of the map, used for the generation)
	 * 
	 * @param m_x    x position of the chunk
	 * @param m_y    y position of the chunk
	 * @param width  width of the chunk
	 * @param height height of the chunk
	 */
	public Chunk(int m_x, int m_y, int width, int height, Texture texture) {
		m_texture = texture;
		m_currentEntity = null;
		m_chunk = new Rectangle2D.Double(m_x, m_y, width, height);
	}

	

	public int getX() {
		return (int) m_chunk.getX();
	}

	public int getY() {
		return (int) m_chunk.getY();
	}

	public int getWidth() {
		return (int) m_chunk.getWidth();
	}

	public int getHeight() {
		return (int) m_chunk.getHeight();
	}

	public boolean isInChunk(int x, int y, int width, int height) {
		return m_chunk.contains(x, y);
	}

	public Texture getTexture() {
		return m_texture;
	}

	public Entity getEntity() {
		return m_currentEntity;
	}

	public void setEntity(Entity m_currentEntity) {
		this.m_currentEntity = m_currentEntity;
	}

	public Rectangle2D getChunk() {
		return m_chunk;
	}

}
