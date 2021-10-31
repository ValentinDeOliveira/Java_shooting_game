package Model.Map;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import Model.Constants_model;
import Model.Entity;
import Model.Moveable.Dog;
import Model.Moveable.Ennemy;
import Model.Moveable.Player;

public class Minimap {
	private Canvas m_background;

	private Map m_map;

	private Constants_model m_constants;

	public Minimap(Map map) {
		m_map = map;
		m_background = new Canvas();

		m_constants = new Constants_model();

		// center x and put at the bottom of the screen with an offset of 100px
		m_background.setBounds(m_constants.SCREEN_WIDTH / 2 - m_constants.MINIMAP_WIDTH / 2,
				(m_constants.SCREEN_HEIGHT - m_constants.MINIMAP_HEIGHT - m_constants.HUNDRED_PX_OFFSET),
				m_constants.MINIMAP_WIDTH, m_constants.MINIMAP_HEIGHT);

	}

	/**
	 * Compute the coordinates to displate in the minimap following a x and y
	 * position
	 * 
	 * @param xValue x coordinate to calculate
	 * @param yValue y coordinate to calculate
	 * @return the scaled coordinates for the minimap
	 */
	private int[] generateCoordinates(float xValue, float yValue) {
		int[] absoluteCoordinates = m_map.getAbsoluteCoordinates(), results = new int[2];
		int minValue, min, max, result;

		// calculation of X

		minValue = m_constants.SCREEN_WIDTH / 2 - m_constants.MINIMAP_WIDTH / 2;
		min = absoluteCoordinates[0];
		max = (int) (min + m_constants.SCREEN_WIDTH + m_constants.SCREEN_WIDTH * m_constants.INVISIBLE_RATIO * 2);

		result = (int) (((xValue - min) / (max - min)) * m_constants.MINIMAP_WIDTH + minValue);
		results[0] = result;

		// calculation of Y

		minValue = m_constants.SCREEN_HEIGHT - m_constants.MINIMAP_HEIGHT - m_constants.HUNDRED_PX_OFFSET;
		min = absoluteCoordinates[1];
		max = (int) (min + m_constants.SCREEN_HEIGHT + m_constants.SCREEN_HEIGHT * m_constants.INVISIBLE_RATIO * 2);

		result = (int) (((yValue - min) / (max - min)) * m_constants.MINIMAP_HEIGHT + minValue);
		results[1] = result;

		return results;
	}

	/**
	 * draw a circle on the minimap where the entity is located
	 * 
	 * @param c     color to display on the map
	 * @param e     entity to display on the map
	 * @param g     graphic context
	 * @param scale scale of the entity
	 */
	private void drawMinimapCircle(Color c, Entity e, Graphics g, double scale) {
		if (!m_map.isInMap((int) e.getX(), (int) e.getY())) {
			return;
		}

		int xDotPosition = (int) (e.getX());
		int yDotPosition = (int) (e.getY());

		int[] coordinates = generateCoordinates(xDotPosition, yDotPosition);

		if (coordinates != null) {
			g.setColor(c);
			g.fillOval(coordinates[0], coordinates[1], m_constants.CIRCLE_RADIUS, m_constants.CIRCLE_RADIUS);
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.lightGray);
		g.fillRect(m_background.getX(), m_background.getY(), m_background.getWidth(), m_background.getHeight());

		LinkedList<Entity> entities = m_map.getEntities();
		for (Entity e : entities) {
			if (e instanceof Ennemy && ((Ennemy) e).getDetected()) {
				drawMinimapCircle(Color.red, e, g, Constants_model.ENNEMY_SCALE);
			}
			if (e instanceof Dog) {
				drawMinimapCircle(Color.yellow, e, g, Constants_model.DOG_SCALE);
			}
			if (e instanceof Player) {
				drawMinimapCircle(Color.green, e, g, Constants_model.PLAYER_SCALE);

			}
		}

	}
}
