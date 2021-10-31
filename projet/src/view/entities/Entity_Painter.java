package view.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import Model.Constants_model;
import Model.Map.Chunk;
import Model.Map.Map;
import view.Constants;
import view.GameCamera;

public class Entity_Painter {
	private LinkedList<Model.Entity> m_list_entities_model;
	private LinkedList<view.entities.Entity> m_list_entities_graphic;
	private GameCamera m_game_camera;
	private BufferedImage m_mud;

	public Entity_Painter(GameCamera g) {
		m_list_entities_model = new LinkedList<Model.Entity>();
		m_list_entities_graphic = new LinkedList<view.entities.Entity>();
		this.m_game_camera = g;
		view.entities.Ennemy.loadImageEnnemy();
		m_mud = loadMudImage();

	}

	public BufferedImage loadMudImage() {
		File imageFile = new File("src/view/resources/utils/mud.png");

		if (imageFile.exists()) {
			BufferedImage image = null;
			try {
				image = ImageIO.read(imageFile);
			} catch (Exception ex) {
			}
			return image;
		}
		return null;

	}

	public void update(LinkedList<Model.Entity> list) {
		for (Iterator<Model.Entity> i = list.iterator(); i.hasNext();) {

			Model.Entity e = i.next();
			if (!m_list_entities_model.contains(e)) {
				m_list_entities_model.add(e);

				view.entities.Entity r = null;
				if (e instanceof Model.Moveable.Ennemy) {

					r = new view.entities.Ennemy((Model.Moveable.Ennemy) e);
					m_list_entities_graphic.add(r);
				}
				if (e instanceof Model.Moveable.Player) {
					r = new view.entities.Player((Model.Moveable.Player) e);
					m_list_entities_graphic.add(r);
					r = new view.entities.Player_Winter((Model.Moveable.Player) e);
					m_list_entities_graphic.add(r);

				}
				if (e instanceof Model.Moveable.Dog) {
					r = new view.entities.Dog((Model.Moveable.Dog) e);
					m_list_entities_graphic.add(r);
				}
				if (e instanceof Model.Weapon.Bullet) {

					r = new view.entities.Bullet((Model.Weapon.Bullet) e);
					m_list_entities_graphic.add(r);
				}

			}

		}
		for (Iterator<view.entities.Entity> i = m_list_entities_graphic.iterator(); i.hasNext();) {
			view.entities.Entity e = i.next();
			if (!list.contains(e.getEntityModel())) {
				m_list_entities_model.remove(e.getEntityModel());
				i.remove();

			}
		}
	}

	public void paint(Graphics g, int season) {

		for (Iterator<view.entities.Entity> i = m_list_entities_graphic.iterator(); i.hasNext();) {

			view.entities.Entity e = i.next();
			if (e instanceof view.entities.Player || e instanceof view.entities.Player_Winter) {
				if (season == Constants_model.WINTER && e instanceof view.entities.Player_Winter) {
					e.paint(g, m_game_camera);
				}
				if (season != Constants_model.WINTER && e instanceof view.entities.Player) {
					e.paint(g, m_game_camera);
				}
			} else {
				e.paint(g, m_game_camera);

			}

		}

	}

	public void paint(Graphics g, LinkedList<Chunk> mud, int season) {
		if (season != Constants_model.WINTER) {
			for (Iterator<Chunk> i = mud.iterator(); i.hasNext();) {
				Chunk e = i.next();
				g.drawImage(m_mud, e.getX() - (int) (this.m_game_camera.getxOffsset()),
						e.getY() - (int) (this.m_game_camera.getYOffset()), e.getWidth(), e.getHeight(), null);

			}
		}

	}

	public void tick(LinkedList<Model.Entity> list, long elapsed, Map map) throws Exception {
		if (map.getDirtyFlag()) {
			map.setDirtyFlag(false);
			update(list);

		}
		for (Iterator<Entity> i = m_list_entities_graphic.iterator(); i.hasNext();) {
			Entity e = i.next();
			e.tick(elapsed, map);

		}
	}

}