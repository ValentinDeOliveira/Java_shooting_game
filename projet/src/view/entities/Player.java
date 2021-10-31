package view.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

import Model.Map.Map;
import view.Constants;
import view.GameCamera;

public class Player extends Entity {

	BufferedImage[] m_images_move;
	BufferedImage[] m_images_reload;
	BufferedImage[] m_images_shoot;
	BufferedImage[] m_action;
	int m_index_image;
	int m_elapsed_photo;
	boolean m_increment;
	boolean loaded;
	BufferedImage m_current_image;
	float m_current_orientation;
	BufferedImage m_heart;
	BufferedImage m_gun;

	public Player(Model.Moveable.Player e) {
		super("src/view/resources/Entity/player/", e);
		String r = this.getPath();

		m_images_move = loadSprite(r + "move/move", Constants.PLAYER_NUMBER_OF_POSITIONS_MOVE);
		m_images_reload = loadSprite(r + "reload/reload", Constants.PLAYER_NUMBER_OF_POSITIONS_RELOAD);
		m_images_shoot = loadSprite(r + "shoot/shoot", Constants.PLAYER_NUMBER_OF_POSITIONS_SHOOT);
		

		m_increment =true;
		m_index_image=0;
		m_action = m_images_move;
		m_current_image = m_action[m_index_image];
		m_current_orientation = this.getEntityModel().getOrientation();
		if (((Model.Moveable.Moveable) this.getEntityModel()).getCurrentHitbox() == null) {

			int width = m_current_image.getWidth();
			int height = m_current_image.getHeight();
			((Model.Moveable.Moveable) this.getEntityModel()).initHitbox(width, height);
		}
		m_heart = loadImage("src/view/resources/utils/heart.png");
		m_gun = loadImage("src/view/resources/utils/gun.png");

	}

	public BufferedImage[] loadSprite(String path, int x) {

		BufferedImage[] result = new BufferedImage[x];
		for (int i = 0; i < x; i++) {
			int n = i + 1;
			String s = path + n + ".png";
			File imageFile = new File(s);

			if (imageFile.exists()) {
				BufferedImage image = null;
				try {
					image = ImageIO.read(imageFile);
				} catch (Exception ex) {
				}
				result[i] = image;
			}
		}
		return result;

	}

	@Override
	public void paint(Graphics g, GameCamera m) {
		int width = m_current_image.getWidth();
		int height = m_current_image.getHeight();
		double scale = Model.Constants_model.PLAYER_SCALE;
		int x = (int) this.getEntityModel().getX();
		int y = (int) this.getEntityModel().getY();
		

		g.drawImage(m_current_image, x - (int) (m.getxOffsset()), y - (int) (m.getYOffset()), (int) (scale * width),
				(int) (scale * height), null);

		this.paint_life_bar(g, m);
		this.paint_gun_bar(g, m);
	}

	public void paint_life_bar(Graphics g, GameCamera m) {
		int m_maximum = (((Model.Moveable.Player) (this.getEntityModel())).getMaxLifePoints());
		int m_progress = (((Model.Moveable.Player) (this.getEntityModel())).getLifePoints());
		int m_border = 10;
		int m_height = 70;
		int m_width = 420;
		g.setColor(Color.white);
		g.fillRect(10, 10, m_width-3*m_border, m_height-2*m_border);
		if (m_progress>=40) {
			g.setColor(Color.GREEN);
		}
		else {

		g.setColor(Color.RED);
		}
		g.fillRect(m_border, m_border, ((int) ((m_width - 3*m_border) * ((float) m_progress / (float) m_maximum))),
				m_height - 2 * m_border);
		g.setColor(Color.BLACK);
		for (int i = 0; i <= 3; i++) {
			g.drawRect(i * (m_width / 4 - 3 * m_border / 4) + m_border, m_border, m_width / 4 - 3 * m_border / 4,
					m_height - 2 * m_border);
		}

		g.drawImage(m_heart, m_width, 13, 40, 40, null);

	}

	public void paint_gun_bar(Graphics g, GameCamera m) {
		int m_maximum = (((Model.Moveable.Player) (this.getEntityModel())).getWeapon().getm_max_ammo());
		int m_progress = (((Model.Moveable.Player) (this.getEntityModel())).getWeapon().getm_ammo());
		int m_border = 10;
		int m_height = 70;
		int m_width = 420;
		g.setColor(Color.white);
		g.fillRect(700+10, 10, m_width-3*m_border, m_height-2*m_border);
		if (m_progress>=5) {
			g.setColor(Color.GREEN);
		}
		else {

		g.setColor(Color.RED);
		}
		g.fillRect(700+m_border, m_border, ((int) ((m_width - 3*m_border) * ((float) m_progress / (float) m_maximum))),
				m_height - 2 * m_border);
		g.setColor(Color.BLACK);
		for (int i = 0; i <= 3; i++) {
			g.drawRect(700+i * (m_width / 4 - 3 * m_border / 4) + m_border, m_border, m_width / 4 - 3 * m_border / 4,
					m_height - 2 * m_border);
		}

		g.drawImage(m_gun, m_width + 700, 13, 50, 50, null);
	}

	@Override
	public void tick(long elapsed, Map map) {

		if (((Model.Moveable.Player) (this.getEntityModel())).getShoot() && !loaded) {
			reload();

		}
		m_elapsed_photo += elapsed;
		if (m_elapsed_photo > 50) {
			m_elapsed_photo = 0;
			if (m_increment) {
				m_index_image++;
			} else {
				m_index_image--;

			}
			if (m_action == m_images_move) {
				switch (m_index_image) {
				case Constants.PLAYER_NUMBER_OF_POSITIONS_MOVE - 1:

					m_increment = false;
					break;
				case 0:

					m_increment = true;
					break;
				}
			} else {
				if (m_action == m_images_reload && m_index_image == Constants.PLAYER_NUMBER_OF_POSITIONS_RELOAD - 1) {
					shoot();
					((Model.Moveable.Player) (this.getEntityModel())).resetShoot();

				} else {
					if (m_action == m_images_shoot && m_index_image == Constants.PLAYER_NUMBER_OF_POSITIONS_SHOOT - 1) {
						move();

					}
				}

			}
			this.m_current_orientation = 0;
			
			m_current_image = m_action[m_index_image];
			if (this.m_current_orientation != this.getEntityModel().getOrientation()) {

				final double rads = Math.toRadians(this.getEntityModel().getOrientation());

				final double sin = Math.abs(Math.sin(rads));
				final double cos = Math.abs(Math.cos(rads));
				final int w = (int) Math.floor(m_current_image.getWidth() * cos + m_current_image.getHeight() * sin);
				final int h = (int) Math.floor(m_current_image.getHeight() * cos + m_current_image.getWidth() * sin);
				final BufferedImage rotatedImage = new BufferedImage(w, h, m_current_image.getType());
				final AffineTransform at = new AffineTransform();
				at.translate(w / 2, h / 2);
				at.rotate(rads, 0, 0);
				at.translate(-m_current_image.getWidth() / 2, -m_current_image.getHeight() / 2);
				final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
				rotateOp.filter(m_current_image, rotatedImage);

				m_current_image = rotatedImage;
				m_current_orientation = this.getEntityModel().getOrientation();

			}

		}
		try {
			this.getEntityModel().tick(elapsed, map);
		} catch (Exception e) {

		}
	}

	public void move() {
		m_action = m_images_move;
		m_increment = true;
		m_index_image = 0;
	}

	public void reload() {
		m_action = m_images_reload;
		m_elapsed_photo = 0;
		m_increment = true;
		m_index_image = 0;
		loaded = true;

	}

	public void shoot() {
		if (loaded) {
			m_elapsed_photo = 0;
			m_action = m_images_shoot;
			m_increment = true;
			m_index_image = 0;
			loaded = false;

		}

	}

	public BufferedImage loadImage(String path) {
		File imageFile = new File(path);

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

}
